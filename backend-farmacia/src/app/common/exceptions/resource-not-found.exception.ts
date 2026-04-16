import { HttpStatus } from '@nestjs/common';

import { AppException } from './app.exception';

/**
 * Excepción para recursos inexistentes.
 */
export class ResourceNotFoundException extends AppException {
  /**
   * Crea una excepción por recurso no encontrado.
   *
   * @param message Mensaje principal del error.
   * @param code Código opcional de negocio.
   * @param details Detalles opcionales.
   */
  constructor(message = 'No se encontró el recurso solicitado.', code = 'RESOURCE_NOT_FOUND', details?: unknown) {
    super({
      message,
      code,
      details,
      status: HttpStatus.NOT_FOUND,
      error: 'Not Found',
    });
  }
}
