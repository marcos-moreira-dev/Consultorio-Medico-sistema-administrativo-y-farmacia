import { AppConstants } from '../constants/app.constants';
import { PageMetaDto } from '../dto/page-meta.dto';
import { SortDirectionEnum } from '../enums/sort-direction.enum';

/**
 * Entrada para construir metadatos de paginación.
 */
export interface BuildPageMetaInput {
  /**
   * Página actual.
   */
  page: number;

  /**
   * Límite por página.
   */
  limit: number;

  /**
   * Total de elementos.
   */
  totalItems: number;

  /**
   * Campo de orden opcional.
   */
  sortBy?: string;

  /**
   * Dirección de orden opcional.
   */
  sortDirection?: SortDirectionEnum | string;

  /**
   * Término de búsqueda libre opcional.
   */
  query?: string;
}

/**
 * Utilidad para normalizar y construir datos de paginación.
 */
export class PaginationUtil {
  private constructor() {}

  /**
   * Normaliza el número de página recibido.
   *
   * @param value Valor crudo.
   * @returns Página válida dentro de reglas del sistema.
   */
  static normalizePage(value?: number | string | null): number {
    const parsed = Number(value);

    if (!Number.isSafeInteger(parsed) || parsed < 1) {
      return AppConstants.PAGINATION.DEFAULT_PAGE;
    }

    return parsed;
  }

  /**
   * Normaliza el límite de registros por página.
   *
   * @param value Valor crudo.
   * @returns Límite válido acotado por el máximo del sistema.
   */
  static normalizeLimit(value?: number | string | null): number {
    const parsed = Number(value);

    if (!Number.isSafeInteger(parsed) || parsed < 1) {
      return AppConstants.PAGINATION.DEFAULT_LIMIT;
    }

    return Math.min(parsed, AppConstants.PAGINATION.MAX_LIMIT);
  }

  /**
   * Calcula el desplazamiento SQL a partir de página y límite.
   *
   * @param page Página actual.
   * @param limit Límite por página.
   * @returns Número de registros a omitir.
   */
  static getSkip(page: number, limit: number): number {
    return (page - 1) * limit;
  }

  /**
   * Normaliza la dirección de orden.
   *
   * @param value Valor crudo.
   * @returns Dirección válida de orden.
   */
  static normalizeSortDirection(value?: SortDirectionEnum | string | null): SortDirectionEnum {
    const normalized = String(value ?? AppConstants.SORT.DEFAULT_DIRECTION).trim().toUpperCase();

    return normalized === SortDirectionEnum.DESC
      ? SortDirectionEnum.DESC
      : SortDirectionEnum.ASC;
  }

  /**
   * Construye el DTO de metadatos de paginación.
   *
   * @param input Datos base para construir los metadatos.
   * @returns DTO completo de paginación.
   */
  static buildPageMeta(input: BuildPageMetaInput): PageMetaDto {
    const page = this.normalizePage(input.page);
    const limit = this.normalizeLimit(input.limit);
    const totalItems = Number.isFinite(input.totalItems) && input.totalItems >= 0
      ? Math.trunc(input.totalItems)
      : 0;

    const totalPages = totalItems === 0 ? 0 : Math.ceil(totalItems / limit);

    return {
      page,
      limit,
      totalItems,
      totalPages,
      hasPreviousPage: page > 1,
      hasNextPage: totalPages > 0 && page < totalPages,
      sortBy: input.sortBy,
      sortDirection: input.sortDirection
        ? this.normalizeSortDirection(input.sortDirection)
        : undefined,
      query: input.query,
    };
  }
}
