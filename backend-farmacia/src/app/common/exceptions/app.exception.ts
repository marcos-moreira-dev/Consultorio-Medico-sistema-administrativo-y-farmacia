import { HttpException, HttpStatus } from '@nestjs/common';

/**
 * Opciones para construir una excepción de aplicación consistente.
 */
export interface AppExceptionOptions {
  /**
   * Mensaje principal del error.
   */
  message: string;

  /**
   * Código HTTP asociado.
   *
   * Por defecto se usa 400.
   */
  status?: HttpStatus;

  /**
   * Código interno/estable de negocio.
   */
  code?: string;

  /**
   * Detalles adicionales seguros para exponer.
   */
  details?: unknown;

  /**
   * Etiqueta corta de error.
   *
   * Por defecto se deriva del HttpStatus.
   */
  error?: string;
}

/**
 * Excepción base de aplicación para respuestas de error consistentes.
 *
 * La idea es que el resto de excepciones del proyecto hereden de esta clase
 * y definan solo su semántica propia.
 */
export class AppException extends HttpException {
  /**
   * Crea una nueva excepción de aplicación.
   *
   * @param options Opciones de construcción del error.
   */
  constructor(options: AppExceptionOptions) {
    const status = options.status ?? HttpStatus.BAD_REQUEST;

    super(
      {
        ok: false,
        message: options.message,
        error: options.error ?? HttpStatus[status] ?? 'Error',
        code: options.code,
        details: options.details,
      },
      status,
    );
  }
}
