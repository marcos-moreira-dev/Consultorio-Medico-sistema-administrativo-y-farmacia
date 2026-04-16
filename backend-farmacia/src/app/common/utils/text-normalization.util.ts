import { BusinessRuleException } from '../exceptions/business-rule.exception';

/**
 * Utilidades compartidas para normalización de texto.
 */
export class TextNormalizationUtil {
  private constructor() {}

  /**
   * Colapsa espacios y recorta extremos.
   *
   * @param value Texto de entrada.
   * @returns Texto normalizado.
   */
  static normalize(value: string): string {
    return value.trim().replace(/\s+/g, ' ');
  }

  /**
   * Normaliza un texto obligatorio.
   *
   * @param value Texto de entrada.
   * @param message Mensaje de error en caso de quedar vacío.
   * @param code Código de negocio opcional.
   * @returns Texto normalizado.
   */
  static normalizeRequired(
    value: string,
    message = 'El texto obligatorio no puede quedar vacío.',
    code = 'TEXTO_REQUERIDO_INVALIDO',
  ): string {
    const normalized = this.normalize(value);

    if (!normalized) {
      throw new BusinessRuleException(message, code);
    }

    return normalized;
  }

  /**
   * Normaliza un texto opcional.
   *
   * @param value Texto opcional.
   * @returns Texto normalizado, null o undefined según el caso.
   */
  static normalizeOptional(value?: string): string | null | undefined {
    if (value === undefined) {
      return undefined;
    }

    const normalized = this.normalize(value);

    return normalized.length > 0 ? normalized : null;
  }
}
