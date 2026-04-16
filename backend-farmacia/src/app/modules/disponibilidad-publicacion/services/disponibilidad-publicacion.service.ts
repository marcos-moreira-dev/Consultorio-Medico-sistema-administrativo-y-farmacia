import { Injectable } from '@nestjs/common';

import { EstadoDisponibilidadEnum } from '../../../common/enums/estado-disponibilidad.enum';
import { ResourceNotFoundException } from '../../../common/exceptions/resource-not-found.exception';
import { ProductoPublicacionUtil } from '../../../common/utils/producto-publicacion.util';
import { ProductoEntity } from '../../productos/entities/producto.entity';
import { ProductoRepository } from '../../productos/repositories/producto.repository';
import { ActualizarDisponibilidadRequestDto } from '../dto/actualizar-disponibilidad.request.dto';
import { DespublicarProductoRequestDto } from '../dto/despublicar-producto.request.dto';
import { EstadoPublicacionResponseDto } from '../dto/estado-publicacion.response.dto';
import { PublicarProductoRequestDto } from '../dto/publicar-producto.request.dto';
import { PublicacionRulesValidator } from '../validation/publicacion-rules.validator';

/**
 * Servicio de aplicación para la semántica de disponibilidad y publicación.
 */
@Injectable()
export class DisponibilidadPublicacionService {
  /**
   * Crea el servicio del módulo.
   *
   * @param productoRepository Repositorio de productos.
   * @param publicacionRulesValidator Validador de reglas del módulo.
   */
  constructor(
    private readonly productoRepository: ProductoRepository,
    private readonly publicacionRulesValidator: PublicacionRulesValidator,
  ) {}

  /**
   * Consulta el estado actual de publicación de un producto.
   *
   * @param productoId Identificador del producto.
   * @returns DTO con el estado actual de publicación.
   */
  async consultarEstado(productoId: number): Promise<EstadoPublicacionResponseDto> {
    const producto = await this.findProductoOrFail(productoId);

    return this.toEstadoResponse(producto);
  }

  /**
   * Publica un producto en la superficie pública.
   *
   * Reglas aplicadas:
   * - el producto debe estar ACTIVO;
   * - se activa `esPublicable`;
   * - `estadoDisponibilidad` deja de ser `NO_PUBLICADO`.
   *
   * @param productoId Identificador del producto.
   * @param request DTO con el estado inicial visible.
   * @returns Estado resultante de publicación.
   */
  async publicar(
    productoId: number,
    request: PublicarProductoRequestDto,
  ): Promise<EstadoPublicacionResponseDto> {
    const producto = await this.findProductoOrFail(productoId);
    const estadoDisponibilidadInicial =
      request.estadoDisponibilidadInicial ?? EstadoDisponibilidadEnum.DISPONIBLE;

    await this.publicacionRulesValidator.ensureCanPublish(
      producto,
      estadoDisponibilidadInicial,
    );

    producto.esPublicable = true;
    producto.estadoDisponibilidad = estadoDisponibilidadInicial;

    const updated = await this.productoRepository.save(producto);
    const refreshed = await this.productoRepository.findById(updated.productoId);

    return this.toEstadoResponse(refreshed ?? updated);
  }

  /**
   * Despublica un producto de la superficie pública.
   *
   * Reglas aplicadas:
   * - se fuerza `esPublicable = false`;
   * - se fuerza `estadoDisponibilidad = NO_PUBLICADO`.
   *
   * @param productoId Identificador del producto.
   * @param request DTO opcional con motivo operativo.
   * @returns Estado resultante de publicación.
   */
  async despublicar(
    productoId: number,
    request: DespublicarProductoRequestDto,
  ): Promise<EstadoPublicacionResponseDto> {
    const producto = await this.findProductoOrFail(productoId);

    await this.publicacionRulesValidator.ensureCanUnpublish(producto, request);

    const unpublishedState = ProductoPublicacionUtil.getUnpublishedState();

    producto.esPublicable = unpublishedState.esPublicable;
    producto.estadoDisponibilidad = unpublishedState.estadoDisponibilidad;

    const updated = await this.productoRepository.save(producto);
    const refreshed = await this.productoRepository.findById(updated.productoId);

    return this.toEstadoResponse(refreshed ?? updated);
  }

  /**
   * Actualiza la disponibilidad operativa visible de un producto publicado.
   *
   * Reglas aplicadas:
   * - solo aplica a productos ACTIVO y publicables;
   * - solo acepta DISPONIBLE o AGOTADO;
   * - `NO_PUBLICADO` queda reservado para despublicación.
   *
   * @param productoId Identificador del producto.
   * @param request DTO con el nuevo estado de disponibilidad.
   * @returns Estado resultante de publicación.
   */
  async actualizarDisponibilidad(
    productoId: number,
    request: ActualizarDisponibilidadRequestDto,
  ): Promise<EstadoPublicacionResponseDto> {
    const producto = await this.findProductoOrFail(productoId);

    await this.publicacionRulesValidator.ensureCanUpdateDisponibilidad(
      producto,
      request.estadoDisponibilidad,
    );

    producto.estadoDisponibilidad = request.estadoDisponibilidad;

    const updated = await this.productoRepository.save(producto);
    const refreshed = await this.productoRepository.findById(updated.productoId);

    return this.toEstadoResponse(refreshed ?? updated);
  }

  /**
   * Busca un producto y lanza error si no existe.
   *
   * @param productoId Identificador del producto.
   * @returns Producto encontrado.
   */
  private async findProductoOrFail(productoId: number): Promise<ProductoEntity> {
    const producto = await this.productoRepository.findById(productoId);

    if (!producto) {
      throw new ResourceNotFoundException('No se encontró el producto solicitado.');
    }

    return producto;
  }

  /**
   * Convierte una entidad de producto al DTO de estado de publicación.
   *
   * @param producto Entidad de producto.
   * @returns DTO serializable del estado actual.
   */
  private toEstadoResponse(producto: ProductoEntity): EstadoPublicacionResponseDto {
    return {
      productoId: producto.productoId,
      estadoProducto: String(producto.estadoProducto).trim().toUpperCase(),
      esPublicable: producto.esPublicable,
      estadoDisponibilidad: String(producto.estadoDisponibilidad).trim().toUpperCase(),
      estaPublicadoPublicamente: ProductoPublicacionUtil.isPublicVisible(
        producto.estadoProducto,
        producto.esPublicable,
        producto.estadoDisponibilidad,
      ),
      puedePublicarse: ProductoPublicacionUtil.canBePublished(producto.estadoProducto),
      fechaActualizacion: producto.fechaActualizacion.toISOString(),
    };
  }
}
