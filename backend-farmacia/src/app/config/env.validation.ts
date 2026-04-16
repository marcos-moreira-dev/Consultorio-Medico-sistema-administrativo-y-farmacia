/**
 * Determina si un valor está ausente o vacío.
 *
 * @param value Valor a inspeccionar.
 * @returns true cuando el valor es nulo, indefinido o cadena vacía.
 */
function isMissing(value: unknown): boolean {
  return value === undefined || value === null || String(value).trim() === '';
}

/**
 * Valida que una variable exista.
 *
 * @param config Configuración cruda cargada desde variables de entorno.
 * @param key Nombre de la variable.
 */
function assertRequired(config: Record<string, unknown>, key: string): void {
  if (isMissing(config[key])) {
    throw new Error(`Falta la variable de entorno requerida: ${key}`);
  }
}

/**
 * Valida que una variable, si existe, sea un entero válido.
 *
 * @param config Configuración cruda cargada desde variables de entorno.
 * @param key Nombre de la variable.
 */
function assertInteger(config: Record<string, unknown>, key: string): void {
  const value = config[key];

  if (isMissing(value)) {
    return;
  }

  const parsed = Number(value);

  if (!Number.isInteger(parsed)) {
    throw new Error(`La variable de entorno ${key} debe ser un entero válido`);
  }
}

/**
 * Valida que una variable, si existe, sea un booleano textual válido.
 *
 * Valores aceptados: true, false.
 *
 * @param config Configuración cruda cargada desde variables de entorno.
 * @param key Nombre de la variable.
 */
function assertBooleanString(config: Record<string, unknown>, key: string): void {
  const value = config[key];

  if (isMissing(value)) {
    return;
  }

  const normalized = String(value).trim().toLowerCase();

  if (normalized !== 'true' && normalized !== 'false') {
    throw new Error(`La variable de entorno ${key} debe ser "true" o "false"`);
  }
}

/**
 * Valida variables de entorno mínimas del backend.
 *
 * Esta función no transforma los valores. Solo asegura presencia y formato básico
 * para evitar arranques ambiguos o difíciles de depurar.
 *
 * @param config Configuración cruda cargada por Nest.
 * @returns La misma configuración cuando supera la validación.
 */
export function validateEnv(config: Record<string, unknown>): Record<string, unknown> {
  const requiredKeys = [
    'APP_PORT',
    'JWT_SECRET',
    'DB_HOST',
    'DB_PORT',
    'DB_NAME',
    'DB_USER',
    'DB_PASSWORD',
  ];

  requiredKeys.forEach((key) => assertRequired(config, key));

  ['APP_PORT', 'DB_PORT', 'MAX_FILE_SIZE_MB'].forEach((key) => {
    assertInteger(config, key);
  });

  ['DB_SYNC', 'DB_LOGGING'].forEach((key) => {
    assertBooleanString(config, key);
  });

  return config;
}
