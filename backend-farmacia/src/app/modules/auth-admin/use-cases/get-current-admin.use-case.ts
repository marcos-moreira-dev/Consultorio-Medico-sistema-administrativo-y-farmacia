import { Injectable } from '@nestjs/common';

import { AuthenticatedAdminType } from '../../../security/types/authenticated-admin.type';
import { CurrentAdminResponseDto } from '../dto/current-admin.response.dto';
import { AuthAdminService } from '../services/auth-admin.service';

/**
 * Caso de uso para recuperar el contexto resumido del admin autenticado actual.
 */
@Injectable()
export class GetCurrentAdminUseCase {
  /**
   * Crea el caso de uso.
   *
   * @param authAdminService Servicio de autenticación administrativa.
   */
  constructor(private readonly authAdminService: AuthAdminService) {}

  /**
   * Ejecuta la recuperación de la sesión actual.
   *
   * @param authenticatedAdmin Contexto autenticado inyectado por JWT.
   * @returns DTO resumido del admin actual.
   */
  async execute(
    authenticatedAdmin: AuthenticatedAdminType,
  ): Promise<CurrentAdminResponseDto> {
    return this.authAdminService.obtenerSesionActual(authenticatedAdmin);
  }
}
