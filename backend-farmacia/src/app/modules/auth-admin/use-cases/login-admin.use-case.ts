import { Injectable } from '@nestjs/common';

import { LoginAdminRequestDto } from '../dto/login-admin.request.dto';
import { LoginAdminResponseDto } from '../dto/login-admin.response.dto';
import { AuthAdminService } from '../services/auth-admin.service';

/**
 * Caso de uso para inicio de sesión administrativo.
 */
@Injectable()
export class LoginAdminUseCase {
  /**
   * Crea el caso de uso.
   *
   * @param authAdminService Servicio de autenticación administrativa.
   */
  constructor(private readonly authAdminService: AuthAdminService) {}

  /**
   * Ejecuta el flujo de login admin.
   *
   * @param request DTO con credenciales de acceso.
   * @returns Respuesta con token y contexto resumido del admin.
   */
  async execute(request: LoginAdminRequestDto): Promise<LoginAdminResponseDto> {
    return this.authAdminService.login(request.username, request.password);
  }
}
