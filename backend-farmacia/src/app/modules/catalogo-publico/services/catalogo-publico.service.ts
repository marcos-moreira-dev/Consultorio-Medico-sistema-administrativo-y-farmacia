import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { In, Repository, SelectQueryBuilder } from 'typeorm';

import { PageResponseDto } from '../../../common/dto/page-response.dto';
import { EstadoProductoEnum } from '../../../common/enums/estado-producto.enum';
import { SortDirectionEnum } from '../../../common/enums/sort-direction.enum';
import { TipoRecursoMediaEnum } from '../../../common/enums/tipo-recurso-media.enum';
import { BusinessRuleException } from '../../../common/exceptions/business-rule.exception';
import { ResourceNotFoundException } from '../../../common/exceptions/resource-not-found.exception';
import { PaginationUtil } from '../../../common/utils/pagination.util';
import { ProductoPublicacionUtil } from '../../../common/utils/producto-publicacion.util';
import { TextNormalizationUtil } from '../../../common/utils/text-normalization.util';
import { CategoriaEntity } from '../../categorias/entities/categoria.entity';
import { MediaRecursoEntity } from '../../media/entities/media-recurso.entity';
import { ProductoEntity } from '../../productos/entities/producto.entity';
import { BuscarCatalogoQueryDto } from '../dto/buscar-catalogo.query.dto';
import { CatalogoItemResponseDto } from '../dto/catalogo-item.response.dto';
import { CategoriaPublicaResponseDto } from '../dto/categoria-publica.response.dto';
import { ProductoPublicoDetalleResponseDto } from '../dto/producto-publico-detalle.response.dto';
import { CatalogoPublicoMapper } from '../mappers/catalogo-publico.mapper';

/**
 * Servicio de aplicación del catálogo público.
 */
@Injectable()
export class CatalogoPublicoService {
  /**
   * Crea el servicio del catálogo público.
   *
   * @param productoOrmRepository Repositorio ORM de productos.
   * @param categoriaOrmRepository Repositorio ORM de categorías (para poblar relación manual).
   * @param mediaOrmRepository Repositorio ORM de recursos de media.
   */
  constructor(
    @InjectRepository(ProductoEntity)
    private readonly productoOrmRepository: Repository<ProductoEntity>,
    @InjectRepository(CategoriaEntity)
    private readonly categoriaOrmRepository: Repository<CategoriaEntity>,
    @InjectRepository(MediaRecursoEntity)
    private readonly mediaOrmRepository: Repository<MediaRecursoEntity>,
  ) {}

  /**
   * Lista categorías visibles públicamente.
   *
   * Implementación alternativa: usamos el repositorio de categorías directamente
   * con una subconsulta para contar productos visibles, evitando joins
   * problemáticos con metadata de TypeORM en entornos JIT.
   *
   * @returns Colección de categorías con cantidad de productos visibles.
   */
  async listarCategoriasPublicas(): Promise<CategoriaPublicaResponseDto[]> {
    const categorias = await this.categoriaOrmRepository.find({
      order: { nombreCategoria: 'ASC' },
    });

    const result: CategoriaPublicaResponseDto[] = [];

    for (const cat of categorias) {
      const count = await this.productoOrmRepository.count({
        where: {
          categoriaId: cat.categoriaId,
          estadoProducto: EstadoProductoEnum.ACTIVO,
          esPublicable: true,
        },
      });

      /*
       * Filtrar manualmente por estados visibles ya que count() con
       * operador IN requiere sintaxis especial en TypeORM.
       */
      const visibleCount = await this.productoOrmRepository.count({
        where: [
          { categoriaId: cat.categoriaId, estadoProducto: EstadoProductoEnum.ACTIVO, esPublicable: true, estadoDisponibilidad: 'DISPONIBLE' },
          { categoriaId: cat.categoriaId, estadoProducto: EstadoProductoEnum.ACTIVO, esPublicable: true, estadoDisponibilidad: 'AGOTADO' },
        ],
      });

      if (visibleCount > 0) {
        result.push({
          categoriaId: cat.categoriaId,
          nombreCategoria: cat.nombreCategoria,
          cantidadProductosVisibles: visibleCount,
        });
      }
    }

    return result;
  }

  /**
   * Lista productos visibles públicamente.
   *
   * @param query DTO de filtros y paginación.
   * @returns Respuesta paginada del catálogo público.
   */
  async listar(
    query: BuscarCatalogoQueryDto,
  ): Promise<PageResponseDto<CatalogoItemResponseDto>> {
    return this.findPublicCatalog(query);
  }

  /**
   * Busca productos dentro del catálogo público.
   *
   * @param query DTO de búsqueda y paginación.
   * @returns Respuesta paginada de resultados.
   */
  async buscar(
    query: BuscarCatalogoQueryDto,
  ): Promise<PageResponseDto<CatalogoItemResponseDto>> {
    const normalizedQuery = TextNormalizationUtil.normalizeOptional(query.q);

    if (!normalizedQuery || normalizedQuery.length < 2) {
      throw new BusinessRuleException(
        'Debes enviar al menos 2 caracteres para buscar en el catálogo público.',
        'BUSQUEDA_CATALOGO_INVALIDA',
      );
    }

    return this.findPublicCatalog({
      ...query,
      q: normalizedQuery,
    });
  }

  /**
   * Obtiene el detalle público de un producto visible.
   *
   * @param productoId Identificador principal del producto.
   * @returns DTO detallado público.
   */
  async obtenerProductoPublico(
    productoId: number,
  ): Promise<ProductoPublicoDetalleResponseDto> {
    const qb = this.createPublicCatalogQueryBuilder();

    qb.andWhere('producto.producto_id = :productoId', { productoId });

    const producto = await qb.getOne();

    if (!producto) {
      throw new ResourceNotFoundException(
        'No se encontró el producto público solicitado.',
        'PRODUCTO_PUBLICO_NO_ENCONTRADO',
      );
    }

    /*
     * Poblar manualmente la relación 'categoria' (mismo motivo que en
     * findPublicCatalog: join explícito no carga la relación).
     */
    const categoria = await this.categoriaOrmRepository.findOne({
      where: { categoriaId: producto.categoriaId },
    });
    if (categoria) {
      producto.categoria = categoria;
    }

    const imageMap = await this.findImagenesPrincipalesByProductoIds([producto.productoId]);

    return CatalogoPublicoMapper.toProductoPublicoDetalleResponseDto(
      producto,
      imageMap.get(producto.productoId) ?? null,
    );
  }

  /**
   * Ejecuta la consulta paginada base del catálogo público.
   *
   * @param query DTO de filtros y paginación.
   * @returns Respuesta paginada de catálogo público.
   */
  private async findPublicCatalog(
    query: BuscarCatalogoQueryDto,
  ): Promise<PageResponseDto<CatalogoItemResponseDto>> {
    const page = PaginationUtil.normalizePage(query.page);
    const limit = PaginationUtil.normalizeLimit(query.limit);
    const q = TextNormalizationUtil.normalizeOptional(query.q);
    const sortBy = query.sortBy;
    const sortDirection = PaginationUtil.normalizeSortDirection(
      query.sortDirection ?? SortDirectionEnum.ASC,
    );

    const qb = this.createPublicCatalogQueryBuilder();

    if (q) {
      /*
       * Búsqueda sin join con categoría: el nombre de categoría se
       * resolverá manualmente. Buscamos solo en campos del producto.
       */
      qb.andWhere(
        `
          (
            producto.nombre_producto ILIKE :search
            OR producto.presentacion ILIKE :search
            OR COALESCE(producto.descripcion_breve, '') ILIKE :search
          )
        `,
        {
          search: `%${q}%`,
        },
      );
    }

    if (query.categoriaId !== undefined) {
      qb.andWhere('producto.categoria_id = :categoriaId', {
        categoriaId: query.categoriaId,
      });
    }

    this.applyOrdering(qb, sortBy, sortDirection);

    qb.skip(PaginationUtil.getSkip(page, limit));
    qb.take(limit);

    const [items, total] = await qb.getManyAndCount();

    /*
     * Poblar manualmente la relación 'categoria' ya que usamos un join
     * explícito en vez de 'leftJoinAndSelect' con nombre de relación.
     * Sin esto, el mapper no podría acceder a entity.categoria.nombreCategoria.
     */
    const categoriaIds = [...new Set(items.map((item) => item.categoriaId))];
    const categorias = await this.categoriaOrmRepository.findByIds(categoriaIds);
    const categoriaMap = new Map(categorias.map((c) => [c.categoriaId, c]));
    items.forEach((item) => {
      item.categoria = categoriaMap.get(item.categoriaId);
    });

    const imageMap = await this.findImagenesPrincipalesByProductoIds(
      items.map((item) => item.productoId),
    );

    return {
      ok: true,
      data: items.map((item) =>
        CatalogoPublicoMapper.toCatalogoItemResponseDto(
          item,
          imageMap.get(item.productoId) ?? null,
        ),
      ),
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
   * Busca imágenes principales de productos por lote.
   *
   * Se toma como imagen principal la última imagen asociada por id de recurso.
   *
   * @param productoIds Identificadores de producto.
   * @returns Mapa productoId -> url pública.
   */
  private async findImagenesPrincipalesByProductoIds(
    productoIds: number[],
  ): Promise<Map<number, string>> {
    if (productoIds.length === 0) {
      return new Map<number, string>();
    }

    const medias = await this.mediaOrmRepository.find({
      where: {
        productoId: In(productoIds),
        tipoRecurso: TipoRecursoMediaEnum.IMAGEN_PRODUCTO,
      },
      order: {
        mediaRecursoId: 'DESC',
      },
    });

    const imageMap = new Map<number, string>();

    for (const media of medias) {
      if (media.productoId && !imageMap.has(media.productoId)) {
        imageMap.set(media.productoId, media.urlPublica);
      }
    }

    return imageMap;
  }

  /**
   * Crea el query builder base para catálogo público.
   *
   * Reglas de visibilidad pública:
   * - producto ACTIVO;
   * - producto publicable;
   * - disponibilidad visible: DISPONIBLE o AGOTADO.
   *
   * Nota: no hacemos join con categoria aquí. La categoría se resuelve
   * manualmente después de obtener los productos para evitar errores
   * de metadatos de TypeORM en entornos JIT.
   *
   * @returns Query builder base del catálogo público.
   */
  private createPublicCatalogQueryBuilder(): SelectQueryBuilder<ProductoEntity> {
    return this.productoOrmRepository
      .createQueryBuilder('producto')
      .where('producto.estado_producto = :estadoProducto', {
        estadoProducto: EstadoProductoEnum.ACTIVO,
      })
      .andWhere('producto.es_publicable = :esPublicable', {
        esPublicable: true,
      })
      .andWhere('producto.estado_disponibilidad IN (:...estadosVisibles)', {
        estadosVisibles: [...ProductoPublicacionUtil.ESTADOS_VISIBLES_PUBLICAMENTE],
      });
  }

  /**
   * Aplica ordenamiento seguro a la consulta pública.
   *
   * @param qb Query builder base.
   * @param sortBy Campo de orden.
   * @param sortDirection Dirección del orden.
   */
  private applyOrdering(
    qb: SelectQueryBuilder<ProductoEntity>,
    sortBy: BuscarCatalogoQueryDto['sortBy'],
    sortDirection: SortDirectionEnum,
  ): void {
    const columnMap: Record<NonNullable<BuscarCatalogoQueryDto['sortBy']>, string> = {
      nombreProducto: 'producto.nombre_producto',
      precioVisible: 'producto.precio_visible',
      fechaActualizacion: 'producto.fecha_actualizacion',
    };

    const resolvedColumn = sortBy ? columnMap[sortBy] : columnMap.nombreProducto;

    qb.orderBy(resolvedColumn, sortDirection);
  }
}
