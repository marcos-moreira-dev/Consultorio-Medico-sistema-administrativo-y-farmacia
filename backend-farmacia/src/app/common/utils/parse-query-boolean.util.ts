/**
 * Convierte un valor de query string a booleano opcional.
 *
 * Casos aceptados:
 * - true / false
 * - 1 / 0
 * - "true" / "false"
 * - "1" / "0"
 *
 * Si el valor es vacío, retorna undefined.
 * Si el valor no es interpretable, retorna el valor original para que
 * class-validator pueda marcarlo como inválido.
 *
 * @param value Valor crudo recibido desde query string.
 * @returns Booleano normalizado, undefined o el valor original.
 */
export function parseOptionalQueryBoolean(
  value: unknown,
): boolean | undefined | unknown {
  if (value === undefined || value === null) {
    return undefined;
  }

  if (typeof value === 'boolean') {
    return value;
  }

  if (typeof value === 'number') {
    if (value === 1) {
      return true;
    }

    if (value === 0) {
      return false;
    }

    return value;
  }

  if (typeof value !== 'string') {
    return value;
  }

  const normalized = value.trim().toLowerCase();

  if (!normalized) {
    return undefined;
  }

  if (normalized === 'true' || normalized === '1') {
    return true;
  }

  if (normalized === 'false' || normalized === '0') {
    return false;
  }

  return value;
}
