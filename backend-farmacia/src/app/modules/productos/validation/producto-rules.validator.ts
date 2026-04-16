import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';

import { BusinessRuleException } from '../../../common/exceptions/business-rule.exception';
import { DuplicateResourceException } from '../../../common/exceptions/duplicate-resource.exception';
import { ResourceNotFoundException } from '../../../common/exceptions/resource-not-found.exception';
import { CategoriaEntity } from '../../categorias/entities/categoria.entity';
import { ActualizarProductoRequestDto } from '../dto/actualizar-producto.request.dto';
import { CrearProductoRequestDto } from '../dto/crear-producto.request.dto';
import { ProductoEntity } from '../entities/producto.entity';
import { ProductoRepository } from '../repositories/producto.repository';

/**
 * Validador de reglas de negocio del módulo de productos.
 *
 * <p>Este concentrador de validaciones asegura la integridad semántica del
 * catálogo antes de que cualquier operación de escritura alcance la base
 * de datos. Las reglas que aplica son:</p>
 *
 * <ul>
 *   <li><strong>Unicidad por identidad natural</strong>: no se permiten dos
 *   productos con el mismo {@code (categoriaId, nombreProducto, presentacion)}.
 *   Esta es la identidad natural del producto, no el PK sustituto.</li>
 *   <li><strong>Existencia de categoría</strong>: toda creación o cambio de
 *   categoría debe referenciar una categoría existente.</li>
 *   <li><strong>Actualización efectiva</strong>: si los campos enviados no
 *   producen ningún cambio real respecto al estado actual, se rechaza la
 *   operación para evitar writes innecesarios y triggers de auditoría falsos.</li>
 *   <li><strong>Coherencia de estado</strong>: no se permite cambiar al mismo
 *   estado en el que ya se encuentra el producto.</li>
 * </ul>
 *
 * <p>Estas validaciones son complementarias a los constraints de base de datos
 * ({@code UNIQUE (categoria_id, nombre_producto, presentacion)}). El validador
 * proporciona mensajes de error específicos para la UI antes de que el DB
 * lance un error genérico de constraint violation.</p>
 *
 * @see ProductoEntity para el constraint único reflejado en la tabla
 */
@Injectable()
export class ProductoRulesValidator {
  /**
   * Crea el validador de reglas.
   *
   * @param productoRepository Repositorio de productos.
   * @param categoriaOrmRepository Repositorio ORM de categorías para validaciones de referencia.
   */
  constructor(
    private readonly productoRepository: ProductoRepository,
    @InjectRepository(CategoriaEntity)
    private readonly categoriaOrmRepository: Repository<CategoriaEntity>,
  ) {}

  /**
   * Valida que un producto pueda ser creado.
   *
   * @param request DTO de creación ya normalizado.
   */
  async ensureCanCreate(request: CrearProductoRequestDto): Promise<void> {
    await this.ensureCategoriaExists(request.categoriaId);

    const existing = await this.productoRepository.findByNormalizedIdentity(
      request.categoriaId,
      request.nombreProducto,
      request.presentacion,
    );

    if (existing) {
      throw new DuplicateResourceException(
        'Ya existe un producto con ese nombre y presentación dentro de la categoría indicada.',
        'PRODUCTO_DUPLICADO',
      );
    }
  }

  /**
   * Valida que un producto pueda ser actualizado.
   *
   * @param productoId Id del producto objetivo.
   * @param request DTO parcial ya normalizado.
   * @param currentEntity Entidad actual cargada desde persistencia.
   */
  async ensureCanUpdate(
    productoId: number,
    request: ActualizarProductoRequestDto,
    currentEntity: ProductoEntity,
  ): Promise<void> {
    const hasAtLeastOneField =
      request.categoriaId !== undefined ||
      request.nombreProducto !== undefined ||
      request.presentacion !== undefined ||
      request.descripcionBreve !== undefined ||
      request.precioVisible !== undefined;

    if (!hasAtLeastOneField) {
      throw new BusinessRuleException(
        'Debes enviar al menos un campo para actualizar el producto.',
        'ACTUALIZACION_SIN_CAMBIOS',
      );
    }

    const nextCategoriaId =
      request.categoriaId !== undefined ? request.categoriaId : currentEntity.categoriaId;

    const nextNombreProducto =
      request.nombreProducto !== undefined ? request.nombreProducto : currentEntity.nombreProducto;

    const nextPresentacion =
      request.presentacion !== undefined ? request.presentacion : currentEntity.presentacion;

    const nextDescripcionBreve =
      request.descripcionBreve !== undefined
        ? request.descripcionBreve ?? null
        : currentEntity.descripcionBreve ?? null;

    const nextPrecioVisible =
      request.precioVisible !== undefined ? request.precioVisible : Number(currentEntity.precioVisible);

    if (request.categoriaId !== undefined) {
      await this.ensureCategoriaExists(request.categoriaId);
    }

    const existing = await this.productoRepository.findByNormalizedIdentity(
      nextCategoriaId,
      nextNombreProducto,
      nextPresentacion,
      productoId,
    );

    if (existing) {
      throw new DuplicateResourceException(
        'Ya existe otro producto con ese nombre y presentación dentro de la categoría indicada.',
        'PRODUCTO_DUPLICADO',
      );
    }

    const nothingChanged =
      nextCategoriaId === currentEntity.categoriaId &&
      nextNombreProducto === currentEntity.nombreProducto &&
      nextPresentacion === currentEntity.presentacion &&
      nextDescripcionBreve === (currentEntity.descripcionBreve ?? null) &&
      nextPrecioVisible === Number(currentEntity.precioVisible);

    if (nothingChanged) {
      throw new BusinessRuleException(
        'La actualización no produce cambios efectivos en el producto.',
        'ACTUALIZACION_SIN_CAMBIOS_EFECTIVOS',
      );
    }
  }

  /**
   * Valida que el cambio de estado lógico sea coherente.
   *
   * @param currentEntity Entidad actual del producto.
   * @param nextEstadoProducto Nuevo estado solicitado.
   */
  async ensureCanChangeEstado(
    currentEntity: ProductoEntity,
    nextEstadoProducto: string,
  ): Promise<void> {
    const currentEstadoProducto = String(currentEntity.estadoProducto).trim().toUpperCase();

    if (currentEstadoProducto === nextEstadoProducto) {
      throw new BusinessRuleException(
        'El producto ya se encuentra en el estado solicitado.',
        'ESTADO_PRODUCTO_SIN_CAMBIO',
      );
    }
  }

  /**
   * Verifica que exista la categoría indicada.
   *
   * @param categoriaId Id de la categoría.
   */
  private async ensureCategoriaExists(categoriaId: number): Promise<void> {
    const categoria = await this.categoriaOrmRepository.findOne({
      where: {
        categoriaId,
      },
    });

    if (!categoria) {
      throw new ResourceNotFoundException(
        'No existe la categoría indicada para el producto.',
        'CATEGORIA_NO_ENCONTRADA',
      );
    }
  }
}
