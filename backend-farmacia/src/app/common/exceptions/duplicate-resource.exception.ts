import { HttpStatus } from '@nestjs/common';

import { AppException } from './app.exception';

/**
 * Excepción para intentos de crear o registrar recursos duplicados.
 */
export class DuplicateResourceException extends AppException {
  /**
   * Crea una excepción por recurso duplicado.
   *
   * @param message Mensaje principal del error.
   * @param code Código opcional de negocio.
   * @param details Detalles opcionales.
   */
  constructor(message = 'El recurso ya existe.', code = 'DUPLICATE_RESOURCE', details?: unknown) {
    super({
      message,
      code,
      details,
      status: HttpStatus.CONFLICT,
      error: 'Duplicate Resource',
    });
  }
}
