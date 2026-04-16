import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';

import { PageResponseDto } from '../../../common/dto/page-response.dto';
import { EstadoDisponibilidadEnum } from '../../../common/enums/estado-disponibilidad.enum';
import { EstadoProductoEnum } from '../../../common/enums/estado-producto.enum';
import { SortDirectionEnum } from '../../../common/enums/sort-direction.enum';
import { BusinessRuleException } from '../../../common/exceptions/business-rule.exception';
import { ResourceNotFoundException } from '../../../common/exceptions/resource-not-found.exception';
import { PaginationUtil } from '../../../common/utils/pagination.util';
import { ProductoPublicacionUtil } from '../../../common/utils/producto-publicacion.util';
import { TextNormalizationUtil } from '../../../common/utils/text-normalization.util';
import { CategoriaEntity } from '../../categorias/entities/categoria.entity';
import { ActualizarProductoRequestDto } from '../dto/actualizar-producto.request.dto';
import { CambiarEstadoProductoRequestDto } from '../dto/cambiar-estado-producto.request.dto';
import { CrearProductoRequestDto } from '../dto/crear-producto.request.dto';
import { ListarProductosQueryDto } from '../dto/listar-productos.query.dto';
import { ProductoResumenResponseDto } from '../dto/producto-resumen.response.dto';
import { ProductoResponseDto } from '../dto/producto.response.dto';
import { ProductoMapper } from '../mappers/producto.mapper';
import { ProductoRepository } from '../repositories/producto.repository';
import { ProductoRulesValidator } from '../validation/producto-rules.validator';

/**
 * Servicio de aplicación del módulo de productos.
 */
@Injectable()
export class ProductosService {
  /**
   * Crea el servicio de productos.
   *
   * @param productoRepository Repositorio de productos.
   * @param productoRulesValidator Validador de reglas del módulo.
   */
  constructor(
    private readonly productoRepository: ProductoRepository,
    private readonly productoRulesValidator: ProductoRulesValidator,
    @InjectRepository(CategoriaEntity)
    private readonly categoriaRepository: Repository<CategoriaEntity>,
  ) {}

  /**
   * Crea un nuevo producto.
   *
   * El producto nace en estado interno coherente:
   * - ACTIVO
   * - no publicable
   * - NO_PUBLICADO
   *
   * @param request DTO de creación.
   * @returns DTO detallado del producto creado.
   */
  async crear(request: CrearProductoRequestDto): Promise<ProductoResponseDto> {
    const normalized = this.normalizeCreateRequest(request);

    await this.productoRulesValidator.ensureCanCreate(normalized);

    const producto = await this.productoRepository.createAndSave({
      categoriaId: normalized.categoriaId,
      nombreProducto: normalized.nombreProducto,
      presentacion: normalized.presentacion,
      descripcionBreve: normalized.descripcionBreve ?? null,
      precioVisible: normalized.precioVisible,
      estadoProducto: EstadoProductoEnum.ACTIVO,
      esPublicable: false,
      estadoDisponibilidad: EstadoDisponibilidadEnum.NO_PUBLICADO,
    });

    const created = await this.productoRepository.findById(producto.productoId);

    return ProductoMapper.toResponseDto(created ?? producto);
  }

  /**
   * Lista productos con paginación y filtros.
   *
   * @param query DTO de query del listado.
   * @returns Respuesta paginada de productos resumen.
   */
  async listar(
    query: ListarProductosQueryDto,
  ): Promise<PageResponseDto<ProductoResumenResponseDto>> {
    const page = PaginationUtil.normalizePage(query.page);
    const limit = PaginationUtil.normalizeLimit(query.limit);
    const q = TextNormalizationUtil.normalizeOptional(query.q);
    const sortBy = query.sortBy;
    const sortDirection = PaginationUtil.normalizeSortDirection(
      query.sortDirection ?? SortDirectionEnum.ASC,
    );

    const { items, total } = await this.productoRepository.findPaginated({
      skip: PaginationUtil.getSkip(page, limit),
      take: limit,
      q: q ?? undefined,
      categoriaId: query.categoriaId,
      estadoProducto: query.estadoProducto,
      esPublicable: query.esPublicable,
      estadoDisponibilidad: query.estadoDisponibilidad,
      sortBy,
      sortDirection,
    });

    // Load categories separately and map them to products
    const categoriaIds = [...new Set(items.map((item) => item.categoriaId).filter(Boolean))];
    const categorias = categoriaIds.length > 0
      ? await this.categoriaRepository.findByIds(categoriaIds)
      : [];
    const categoriaMap = new Map(categorias.map((c) => [c.categoriaId, c]));

    // Attach category data to each product
    for (const item of items) {
      const cat = categoriaMap.get(item.categoriaId);
      if (cat) {
        (item as any).categoria = { nombreCategoria: cat.nombreCategoria };
      }
    }

    return {
      ok: true,
      data: items.map((item) => ProductoMapper.toResumenResponseDto(item)),
      meta: PaginationUtil.buildPageMeta({
        page,
        limit,
        totalItems: total,
        sortBy,
        sortDirection,
        query: q ?? undefined,
      }),
      timestamp: new Date().toISOString(),
    };
  }

  /**
   * Obtiene un producto por identificador.
   *
   * @param productoId Identificador principal.
   * @returns DTO detallado del producto.
   */
  async obtenerPorId(productoId: number): Promise<ProductoResponseDto> {
    const producto = await this.productoRepository.findById(productoId);

    if (!producto) {
      throw new ResourceNotFoundException('No se encontró el producto solicitado.');
    }

    return ProductoMapper.toResponseDto(producto);
  }

  /**
   * Actualiza datos base de un producto.
   *
   * Este flujo no modifica publicación ni disponibilidad.
   *
   * @param productoId Identificador principal.
   * @param request DTO parcial de actualización.
   * @returns DTO detallado del producto actualizado.
   */
  async actualizar(
    productoId: number,
    request: ActualizarProductoRequestDto,
  ): Promise<ProductoResponseDto> {
    const producto = await this.productoRepository.findById(productoId);

    if (!producto) {
      throw new ResourceNotFoundException('No se encontró el producto solicitado.');
    }

    const normalized = this.normalizeUpdateRequest(request);

    await this.productoRulesValidator.ensureCanUpdate(productoId, normalized, producto);

    if (normalized.categoriaId !== undefined) {
      producto.categoriaId = normalized.categoriaId;
    }

    if (normalized.nombreProducto !== undefined) {
      producto.nombreProducto = normalized.nombreProducto;
    }

    if (normalized.presentacion !== undefined) {
      producto.presentacion = normalized.presentacion;
    }

    if (normalized.descripcionBreve !== undefined) {
      producto.descripcionBreve = normalized.descripcionBreve;
    }

    if (normalized.precioVisible !== undefined) {
      producto.precioVisible = normalized.precioVisible;
    }

    const updated = await this.productoRepository.save(producto);
    const refreshed = await this.productoRepository.findById(updated.productoId);

    return ProductoMapper.toResponseDto(refreshed ?? updated);
  }

  /**
   * Cambia el estado lógico del producto.
   *
   * Regla de coherencia:
   * - si el producto pasa a INACTIVO, se fuerza NO_PUBLICADO y no publicable;
   * - si vuelve a ACTIVO, conserva el estado interno actual de publicación.
   *
   * @param productoId Identificador principal.
   * @param request DTO con el nuevo estado lógico.
   * @returns DTO detallado del producto actualizado.
   */
  async cambiarEstado(
    productoId: number,
    request: CambiarEstadoProductoRequestDto,
  ): Promise<ProductoResponseDto> {
    const producto = await this.productoRepository.findById(productoId);

    if (!producto) {
      throw new ResourceNotFoundException('No se encontró el producto solicitado.');
    }

    const estadoProducto = request.estadoProducto;

    await this.productoRulesValidator.ensureCanChangeEstado(producto, estadoProducto);

    producto.estadoProducto = estadoProducto;

    if (estadoProducto === EstadoProductoEnum.INACTIVO) {
      const unpublishedState = ProductoPublicacionUtil.getUnpublishedState();

      producto.esPublicable = unpublishedState.esPublicable;
      producto.estadoDisponibilidad = unpublishedState.estadoDisponibilidad;
    }

    const updated = await this.productoRepository.save(producto);
    const refreshed = await this.productoRepository.findById(updated.productoId);

    return ProductoMapper.toResponseDto(refreshed ?? updated);
  }

  /**
   * Normaliza el DTO de creación.
   *
   * @param request DTO original de entrada.
   * @returns DTO equivalente con textos y números ya normalizados.
   */
  private normalizeCreateRequest(
    request: CrearProductoRequestDto,
  ): CrearProductoRequestDto {
    const descripcionBreve = TextNormalizationUtil.normalizeOptional(
      request.descripcionBreve,
    );

    return {
      categoriaId: request.categoriaId,
      nombreProducto: TextNormalizationUtil.normalizeRequired(request.nombreProducto),
      presentacion: TextNormalizationUtil.normalizeRequired(request.presentacion),
      descripcionBreve: descripcionBreve ?? undefined,
      precioVisible: this.normalizePrice(request.precioVisible),
    };
  }

  /**
   * Normaliza el DTO parcial de actualización.
   *
   * @param request DTO original de entrada.
   * @returns DTO equivalente con textos y números ya normalizados.
   */
  private normalizeUpdateRequest(
    request: ActualizarProductoRequestDto,
  ): ActualizarProductoRequestDto {
    return {
      categoriaId: request.categoriaId,
      nombreProducto:
        request.nombreProducto !== undefined
          ? TextNormalizationUtil.normalizeRequired(request.nombreProducto)
          : undefined,
      presentacion:
        request.presentacion !== undefined
          ? TextNormalizationUtil.normalizeRequired(request.presentacion)
          : undefined,
      descripcionBreve:
        request.descripcionBreve !== undefined
          ? TextNormalizationUtil.normalizeOptional(request.descripcionBreve ?? undefined) ?? null
          : undefined,
      precioVisible:
        request.precioVisible !== undefined
          ? this.normalizePrice(request.precioVisible)
          : undefined,
    };
  }

  /**
   * Normaliza un precio a dos decimales.
   *
   * @param value Precio bruto.
   * @returns Precio normalizado.
   */
  private normalizePrice(value: number): number {
    const normalized = Number(Number(value).toFixed(2));

    if (!Number.isFinite(normalized) || normalized <= 0) {
      throw new BusinessRuleException(
        'El precio visible debe ser mayor que cero.',
        'PRECIO_VISIBLE_INVALIDO',
      );
    }

    return normalized;
  }
}
