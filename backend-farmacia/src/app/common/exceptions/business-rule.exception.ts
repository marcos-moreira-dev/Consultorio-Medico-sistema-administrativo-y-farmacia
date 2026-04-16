import { HttpStatus } from '@nestjs/common';

import { AppException } from './app.exception';

/**
 * Excepción para reglas de negocio violadas.
 *
 * Se usa cuando la solicitud es sintácticamente válida, pero contradice una
 * restricción del dominio o un flujo permitido por el sistema.
 */
export class BusinessRuleException extends AppException {
  /**
   * Crea una excepción por violación de regla de negocio.
   *
   * @param message Mensaje principal del error.
   * @param code Código opcional de negocio.
   * @param details Detalles opcionales.
   */
  constructor(message = 'Se violó una regla de negocio.', code = 'BUSINESS_RULE_VIOLATION', details?: unknown) {
    super({
      message,
      code,
      details,
      status: HttpStatus.UNPROCESSABLE_ENTITY,
      error: 'Business Rule Violation',
    });
  }
}
