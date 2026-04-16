/**
 * Constantes compartidas de aplicación.
 *
 * Estas constantes agrupan valores transversales que el backend reutiliza
 * en respuestas, paginación, encabezados HTTP y utilidades comunes.
 */
export const AppConstants = Object.freeze({
  APP_NAME: 'backend-farmacia',
  APP_VERSION: '1.0.0',
  HEADERS: Object.freeze({
    CORRELATION_ID: 'x-correlation-id',
  }),
  PAGINATION: Object.freeze({
    DEFAULT_PAGE: 1,
    DEFAULT_LIMIT: 20,
    MAX_LIMIT: 100,
  }),
  SORT: Object.freeze({
    DEFAULT_DIRECTION: 'ASC',
  }),
  SLUG: Object.freeze({
    MAX_LENGTH: 120,
  }),
});
