import { Params } from '@angular/router';

import { CatalogoQueryDto } from '../http/dto/catalogo/catalogo-query.dto';

export class QueryParamsUtil {
  static normalizeCatalogQuery(params: Params): CatalogoQueryDto {
    const page = QueryParamsUtil.parsePositiveNumber(params['page']) ?? 1;
    const limit = QueryParamsUtil.parsePositiveNumber(params['limit']) ?? 12;
    const categoriaId = QueryParamsUtil.parsePositiveNumber(params['categoriaId']);
    const q = typeof params['q'] === 'string' ? params['q'].trim() : undefined;
    const sortBy = QueryParamsUtil.parseSortBy(params['sortBy']);
    const sortDirection = QueryParamsUtil.parseSortDirection(params['sortDirection']);

    return {
      page,
      limit,
      categoriaId,
      q: q || undefined,
      sortBy,
      sortDirection,
    };
  }

  static toCatalogQueryParams(query: CatalogoQueryDto): Params {
    return QueryParamsUtil.removeEmptyEntries({
      page: query.page && query.page > 1 ? query.page : undefined,
      limit: query.limit && query.limit !== 12 ? query.limit : undefined,
      q: query.q?.trim() || undefined,
      categoriaId: query.categoriaId,
      sortBy: query.sortBy,
      sortDirection: query.sortDirection,
    });
  }

  private static removeEmptyEntries(params: Params): Params {
    return Object.fromEntries(
      Object.entries(params).filter(([, value]) => value !== undefined && value !== null && value !== ''),
    );
  }

  private static parsePositiveNumber(value: unknown): number | undefined {
    const parsed = Number(value);

    if (!Number.isInteger(parsed) || parsed < 1) {
      return undefined;
    }

    return parsed;
  }

  private static parseSortBy(
    value: unknown,
  ): CatalogoQueryDto['sortBy'] | undefined {
    if (
      value === 'nombreProducto' ||
      value === 'precioVisible' ||
      value === 'fechaActualizacion'
    ) {
      return value;
    }

    return undefined;
  }

  private static parseSortDirection(
    value: unknown,
  ): CatalogoQueryDto['sortDirection'] | undefined {
    if (value === 'ASC' || value === 'DESC') {
      return value;
    }

    return undefined;
  }
}
