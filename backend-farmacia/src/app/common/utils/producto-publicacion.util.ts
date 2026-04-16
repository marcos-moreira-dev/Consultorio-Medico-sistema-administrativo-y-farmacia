import { EstadoDisponibilidadEnum } from '../enums/estado-disponibilidad.enum';
import { EstadoProductoEnum } from '../enums/estado-producto.enum';

/**
 * Estado interno forzado para un producto despublicado.
 */
export interface UnpublishedProductState {
  /**
   * Bandera de publicación desactivada.
   */
  esPublicable: false;

  /**
   * Estado de disponibilidad reservado para productos no visibles públicamente.
   */
  estadoDisponibilidad: EstadoDisponibilidadEnum.NO_PUBLICADO;
}

/**
 * Utilidades compartidas para semántica de publicación de producto.
 */
export class ProductoPublicacionUtil {
  /**
   * Estados de disponibilidad que sí son visibles en catálogo público.
   */
  static readonly ESTADOS_VISIBLES_PUBLICAMENTE = [
    EstadoDisponibilidadEnum.DISPONIBLE,
    EstadoDisponibilidadEnum.AGOTADO,
  ] as const;

  private constructor() {}

  /**
   * Normaliza un estado lógico de producto.
   *
   * @param value Valor crudo.
   * @returns Estado normalizado o null.
   */
  static normalizeEstadoProducto(value: unknown): EstadoProductoEnum | null {
    const normalized = String(value ?? '').trim().toUpperCase();

    if (normalized === EstadoProductoEnum.ACTIVO) {
      return EstadoProductoEnum.ACTIVO;
    }

    if (normalized === EstadoProductoEnum.INACTIVO) {
      return EstadoProductoEnum.INACTIVO;
    }

    return null;
  }

  /**
   * Normaliza un estado de disponibilidad.
   *
   * @param value Valor crudo.
   * @returns Estado normalizado o null.
   */
  static normalizeEstadoDisponibilidad(value: unknown): EstadoDisponibilidadEnum | null {
    const normalized = String(value ?? '').trim().toUpperCase();

    if (normalized === EstadoDisponibilidadEnum.DISPONIBLE) {
      return EstadoDisponibilidadEnum.DISPONIBLE;
    }

    if (normalized === EstadoDisponibilidadEnum.AGOTADO) {
      return EstadoDisponibilidadEnum.AGOTADO;
    }

    if (normalized === EstadoDisponibilidadEnum.NO_PUBLICADO) {
      return EstadoDisponibilidadEnum.NO_PUBLICADO;
    }

    return null;
  }

  /**
   * Determina si un producto podría publicarse según su estado lógico.
   *
   * @param estadoProducto Estado lógico del producto.
   * @returns true si el producto puede publicarse.
   */
  static canBePublished(estadoProducto: unknown): boolean {
    return this.normalizeEstadoProducto(estadoProducto) === EstadoProductoEnum.ACTIVO;
  }

  /**
   * Determina si un estado de disponibilidad es visible públicamente.
   *
   * @param estadoDisponibilidad Estado de disponibilidad.
   * @returns true si el estado es visible en catálogo.
   */
  static isVisiblePublicDisponibilidad(estadoDisponibilidad: unknown): boolean {
    const normalized = this.normalizeEstadoDisponibilidad(estadoDisponibilidad);

    return (
      normalized === EstadoDisponibilidadEnum.DISPONIBLE ||
      normalized === EstadoDisponibilidadEnum.AGOTADO
    );
  }

  /**
   * Determina si un producto es visible actualmente en la superficie pública.
   *
   * @param estadoProducto Estado lógico del producto.
   * @param esPublicable Bandera de publicación.
   * @param estadoDisponibilidad Estado de disponibilidad operativa.
   * @returns true si el producto debe aparecer en catálogo público.
   */
  static isPublicVisible(
    estadoProducto: unknown,
    esPublicable: boolean,
    estadoDisponibilidad: unknown,
  ): boolean {
    return (
      this.canBePublished(estadoProducto) &&
      esPublicable === true &&
      this.isVisiblePublicDisponibilidad(estadoDisponibilidad)
    );
  }

  /**
   * Devuelve la proyección interna coherente de un producto despublicado.
   *
   * @returns Estado interno coherente para despublicación.
   */
  static getUnpublishedState(): UnpublishedProductState {
    return {
      esPublicable: false,
      estadoDisponibilidad: EstadoDisponibilidadEnum.NO_PUBLICADO,
    };
  }
}
