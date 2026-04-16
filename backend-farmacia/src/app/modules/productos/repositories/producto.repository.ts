import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { SelectQueryBuilder, Repository } from 'typeorm';

import { SortDirectionEnum } from '../../../common/enums/sort-direction.enum';
import { ProductoEntity } from '../entities/producto.entity';

/**
 * Criterios de listado de productos.
 */
export interface ListProductosCriteria {
  /**
   * Cantidad de registros a omitir.
   */
  skip: number;

  /**
   * Cantidad de registros a devolver.
   */
  take: number;

  /**
   * Término libre de búsqueda.
   */
  q?: string;

  /**
   * Filtro por categoría.
   */
  categoriaId?: number;

  /**
   * Filtro por estado lógico.
   */
  estadoProducto?: string;

  /**
   * Filtro por bandera de publicación.
   */
  esPublicable?: boolean;

  /**
   * Filtro por estado de disponibilidad.
   */
  estadoDisponibilidad?: string;

  /**
   * Campo de ordenamiento.
   */
  sortBy?: 'nombreProducto' | 'precioVisible' | 'fechaCreacion' | 'fechaActualizacion';

  /**
   * Dirección del ordenamiento.
   */
  sortDirection: SortDirectionEnum;
}

/**
 * Repositorio de infraestructura para productos.
 */
@Injectable()
export class ProductoRepository {
  /**
   * Crea el repositorio wrapper de productos.
   *
   * @param ormRepository Repositorio ORM de TypeORM.
   */
  constructor(
    @InjectRepository(ProductoEntity)
    private readonly ormRepository: Repository<ProductoEntity>,
  ) {}

  /**
   * Crea y persiste un nuevo producto.
   *
   * @param payload Datos del producto.
   * @returns Entidad persistida.
   */
  async createAndSave(payload: Partial<ProductoEntity>): Promise<ProductoEntity> {
    const entity = this.ormRepository.create(payload);

    return this.ormRepository.save(entity);
  }

  /**
   * Persiste un producto existente.
   *
   * @param entity Entidad a guardar.
   * @returns Entidad persistida.
   */
  async save(entity: ProductoEntity): Promise<ProductoEntity> {
    return this.ormRepository.save(entity);
  }

  /**
   * Busca un producto por identificador con su categoría.
   *
   * @param productoId Identificador del producto.
   * @returns Producto encontrado o null.
   */
  async findById(productoId: number): Promise<ProductoEntity | null> {
    return this.ormRepository.findOne({
      where: {
        productoId,
      },
      relations: {
        categoria: true,
      },
    });
  }

  /**
   * Busca un producto por identidad normalizada dentro de una categoría.
   *
   * La identidad natural se define como:
   * - categoriaId
   * - nombreProducto
   * - presentacion
   *
   * @param categoriaId Categoría del producto.
   * @param nombreProducto Nombre del producto.
   * @param presentacion Presentación del producto.
   * @param excludeProductoId Id opcional a excluir en actualizaciones.
   * @returns Entidad encontrada o null.
   */
  async findByNormalizedIdentity(
    categoriaId: number,
    nombreProducto: string,
    presentacion: string,
    excludeProductoId?: number,
  ): Promise<ProductoEntity | null> {
    const qb = this.ormRepository.createQueryBuilder('producto');

    qb.where('producto.categoria_id = :categoriaId', { categoriaId })
      .andWhere(
        'LOWER(TRIM(producto.nombre_producto)) = LOWER(TRIM(:nombreProducto))',
        { nombreProducto },
      )
      .andWhere(
        'LOWER(TRIM(producto.presentacion)) = LOWER(TRIM(:presentacion))',
        { presentacion },
      );

    if (excludeProductoId !== undefined) {
      qb.andWhere('producto.producto_id <> :excludeProductoId', {
        excludeProductoId,
      });
    }

    return qb.getOne();
  }

  /**
   * Lista productos de forma paginada.
   *
   * @param criteria Criterios de listado.
   * @returns Colección y total de elementos.
   */
  async findPaginated(
    criteria: ListProductosCriteria,
  ): Promise<{ items: ProductoEntity[]; total: number }> {
    const qb = this.ormRepository.createQueryBuilder('producto');

    this.applyFilters(qb, criteria);
    this.applyOrdering(qb, criteria.sortBy, criteria.sortDirection);

    qb.skip(criteria.skip);
    qb.take(criteria.take);

    const [items, total] = await qb.getManyAndCount();

    return {
      items,
      total,
    };
  }

  /**
   * Aplica filtros de búsqueda y estado.
   *
   * @param qb Query builder base.
   * @param criteria Criterios de listado.
   */
  private applyFilters(
    qb: SelectQueryBuilder<ProductoEntity>,
    criteria: ListProductosCriteria,
  ): void {
    if (criteria.q) {
      qb.andWhere(
        `
          (
            producto.nombre_producto ILIKE :search
            OR producto.presentacion ILIKE :search
            OR COALESCE(producto.descripcion_breve, '') ILIKE :search
          )
        `,
        {
          search: `%${criteria.q}%`,
        },
      );
    }

    if (criteria.categoriaId !== undefined) {
      qb.andWhere('producto.categoria_id = :categoriaId', {
        categoriaId: criteria.categoriaId,
      });
    }

    if (criteria.estadoProducto) {
      qb.andWhere('producto.estado_producto = :estadoProducto', {
        estadoProducto: criteria.estadoProducto,
      });
    }

    if (criteria.esPublicable !== undefined) {
      qb.andWhere('producto.es_publicable = :esPublicable', {
        esPublicable: criteria.esPublicable,
      });
    }

    if (criteria.estadoDisponibilidad) {
      qb.andWhere('producto.estado_disponibilidad = :estadoDisponibilidad', {
        estadoDisponibilidad: criteria.estadoDisponibilidad,
      });
    }
  }

  /**
   * Aplica ordenamiento seguro a la consulta.
   *
   * @param qb Query builder base.
   * @param sortBy Campo de orden.
   * @param sortDirection Dirección del orden.
   */
  private applyOrdering(
    qb: SelectQueryBuilder<ProductoEntity>,
    sortBy: ListProductosCriteria['sortBy'],
    sortDirection: SortDirectionEnum,
  ): void {
    const columnMap: Record<NonNullable<ListProductosCriteria['sortBy']>, string> = {
      nombreProducto: 'producto.nombre_producto',
      precioVisible: 'producto.precio_visible',
      fechaCreacion: 'producto.fecha_creacion',
      fechaActualizacion: 'producto.fecha_actualizacion',
    };

    const resolvedColumn = sortBy ? columnMap[sortBy] : columnMap.nombreProducto;

    qb.orderBy(resolvedColumn, sortDirection);
  }
}
