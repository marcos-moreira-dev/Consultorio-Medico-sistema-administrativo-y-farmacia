import { randomUUID } from 'crypto';

/**
 * Utilidad para normalizar y generar correlation IDs.
 */
export class CorrelationIdUtil {
  /**
   * Patrón conservador para correlation IDs externos.
   *
   * Se aceptan caracteres seguros para encabezados y logs.
   */
  private static readonly SAFE_PATTERN = /^[A-Za-z0-9._:-]{8,128}$/;

  private constructor() {}

  /**
   * Genera un correlationId nuevo.
   *
   * @returns CorrelationId generado localmente.
   */
  static generate(): string {
    return randomUUID();
  }

  /**
   * Normaliza un valor externo a un correlationId válido.
   *
   * @param value Valor recibido desde headers u otras fuentes.
   * @returns CorrelationId normalizado o null si no es aceptable.
   */
  static normalize(value: unknown): string | null {
    const rawValue = Array.isArray(value) ? value[0] : value;

    if (typeof rawValue !== 'string') {
      return null;
    }

    const normalized = rawValue.trim();

    if (!normalized) {
      return null;
    }

    if (!this.SAFE_PATTERN.test(normalized)) {
      return null;
    }

    return normalized;
  }

  /**
   * Resuelve un correlationId usable.
   *
   * Si el valor entrante es válido, lo reutiliza. En caso contrario,
   * genera uno nuevo.
   *
   * @param value Valor externo opcional.
   * @returns CorrelationId final.
   */
  static resolve(value?: unknown): string {
    return this.normalize(value) ?? this.generate();
  }
}
