import { AppConstants } from '../constants/app.constants';

/**
 * Utilidad para construir y validar slugs simples.
 */
export class SlugUtil {
  private constructor() {}

  /**
   * Convierte un texto libre en un slug estable.
   *
   * Reglas:
   * - elimina acentos;
   * - pasa a minúsculas;
   * - reemplaza separadores por guiones;
   * - elimina caracteres no alfanuméricos;
   * - recorta longitud máxima.
   *
   * @param value Texto de entrada.
   * @returns Slug normalizado.
   */
  static toSlug(value: string): string {
    const normalized = value
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '')
      .toLowerCase()
      .trim()
      .replace(/['"]/g, '')
      .replace(/[^a-z0-9]+/g, '-')
      .replace(/^-+|-+$/g, '');

    return normalized.slice(0, AppConstants.SLUG.MAX_LENGTH).replace(/-+$/g, '');
  }

  /**
   * Determina si un texto ya es un slug válido.
   *
   * @param value Texto a inspeccionar.
   * @returns true si cumple el patrón esperado.
   */
  static isSlug(value: string): boolean {
    return /^[a-z0-9]+(?:-[a-z0-9]+)*$/.test(value);
  }
}
