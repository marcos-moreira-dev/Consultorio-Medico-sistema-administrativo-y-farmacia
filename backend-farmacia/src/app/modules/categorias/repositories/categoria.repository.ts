import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { SelectQueryBuilder, Repository } from 'typeorm';

import { SortDirectionEnum } from '../../../common/enums/sort-direction.enum';
import { CategoriaEntity } from '../entities/categoria.entity';

/**
 * Criterios de listado de categorías.
 */
export interface ListCategoriasCriteria {
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
   * Campo de ordenamiento.
   */
  sortBy?: 'nombreCategoria' | 'fechaCreacion' | 'fechaActualizacion';

  /**
   * Dirección del ordenamiento.
   */
  sortDirection: SortDirectionEnum;
}

/**
 * Repositorio de infraestructura para categorías.
 */
@Injectable()
export class CategoriaRepository {
  /**
   * Crea el repositorio wrapper de categorías.
   *
   * @param ormRepository Repositorio ORM de TypeORM.
   */
  constructor(
    @InjectRepository(CategoriaEntity)
    private readonly ormRepository: Repository<CategoriaEntity>,
  ) {}

  /**
   * Crea y persiste una nueva categoría.
   *
   * @param payload Datos de la nueva categoría.
   * @returns Entidad persistida.
   */
  async createAndSave(payload: Partial<CategoriaEntity>): Promise<CategoriaEntity> {
    const entity = this.ormRepository.create(payload);

    return this.ormRepository.save(entity);
  }

  /**
   * Persiste una categoría existente.
   *
   * @param entity Entidad a guardar.
   * @returns Entidad persistida.
   */
  async save(entity: CategoriaEntity): Promise<CategoriaEntity> {
    return this.ormRepository.save(entity);
  }

  /**
   * Busca una categoría por identificador.
   *
   * @param categoriaId Identificador principal.
   * @returns Entidad encontrada o null.
   */
  async findById(categoriaId: number): Promise<CategoriaEntity | null> {
    return this.ormRepository.findOne({
      where: {
        categoriaId,
      },
    });
  }

  /**
   * Busca una categoría por nombre normalizado, ignorando diferencias triviales.
   *
   * @param nombreCategoria Nombre a buscar.
   * @param excludeCategoriaId Id opcional a excluir en búsquedas de actualización.
   * @returns Entidad encontrada o null.
   */
  async findByNormalizedNombre(
    nombreCategoria: string,
    excludeCategoriaId?: number,
  ): Promise<CategoriaEntity | null> {
    const qb = this.ormRepository.createQueryBuilder('categoria');

    qb.where(
      'LOWER(TRIM(categoria.nombre_categoria)) = LOWER(TRIM(:nombreCategoria))',
      { nombreCategoria },
    );

    if (excludeCategoriaId !== undefined) {
      qb.andWhere('categoria.categoria_id <> :excludeCategoriaId', {
        excludeCategoriaId,
      });
    }

    return qb.getOne();
  }

  /**
   * Lista categorías de forma paginada.
   *
   * @param criteria Criterios de listado.
   * @returns Colección y total de elementos.
   */
  async findPaginated(
    criteria: ListCategoriasCriteria,
  ): Promise<{ items: CategoriaEntity[]; total: number }> {
    const qb = this.ormRepository.createQueryBuilder('categoria');

    this.applySearch(qb, criteria.q);
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
   * Aplica búsqueda libre sobre nombre y descripción.
   *
   * @param qb Query builder base.
   * @param q Término libre de búsqueda.
   */
  private applySearch(
    qb: SelectQueryBuilder<CategoriaEntity>,
    q?: string,
  ): void {
    if (!q) {
      return;
    }

    qb.andWhere(
      `
        (
          categoria.nombre_categoria ILIKE :search
          OR COALESCE(categoria.descripcion_breve, '') ILIKE :search
        )
      `,
      {
        search: `%${q}%`,
      },
    );
  }

  /**
   * Aplica ordenamiento seguro a la consulta.
   *
   * @param qb Query builder base.
   * @param sortBy Campo de orden.
   * @param sortDirection Dirección del orden.
   */
  private applyOrdering(
    qb: SelectQueryBuilder<CategoriaEntity>,
    sortBy: ListCategoriasCriteria['sortBy'],
    sortDirection: SortDirectionEnum,
  ): void {
    const columnMap: Record<NonNullable<ListCategoriasCriteria['sortBy']>, string> = {
      nombreCategoria: 'categoria.nombre_categoria',
      fechaCreacion: 'categoria.fecha_creacion',
      fechaActualizacion: 'categoria.fecha_actualizacion',
    };

    const resolvedColumn = sortBy ? columnMap[sortBy] : columnMap.nombreCategoria;

    qb.orderBy(resolvedColumn, sortDirection);
  }
}
