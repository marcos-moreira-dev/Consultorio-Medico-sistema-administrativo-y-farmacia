import { Body, Controller, Get, Post } from '@nestjs/common';
import {
  ApiBearerAuth,
  ApiOkResponse,
  ApiOperation,
  ApiTags,
} from '@nestjs/swagger';

import { CurrentAdmin } from '../../../security/decorators/current-admin.decorator';
import { Public } from '../../../security/decorators/public.decorator';
import { AuthenticatedAdminType } from '../../../security/types/authenticated-admin.type';
import { CurrentAdminResponseDto } from '../dto/current-admin.response.dto';
import { LoginAdminRequestDto } from '../dto/login-admin.request.dto';
import { LoginAdminResponseDto } from '../dto/login-admin.response.dto';
import { GetCurrentAdminUseCase } from '../use-cases/get-current-admin.use-case';
import { LoginAdminUseCase } from '../use-cases/login-admin.use-case';

/**
 * Controlador HTTP de la superficie administrativa de autenticación.
 *
 * <p>Expone dos endpoints: {@code POST /admin/auth/login} para iniciar sesión
 * (público, sin token) y {@code GET /admin/auth/me} para verificar la sesión
 * actual (requiere JWT). El login es el único endpoint público de toda la
 * superficie administrativa; todos los demás requieren rol
 * {@code ADMIN_FARMACIA} y token JWT válido.</p>
 *
 * <p>La respuesta de login incluye el token JWT, su expiración en segundos
 * y un resumen del usuario autenticado. El endpoint {@code /me} consulta
 * la persistencia para detectar si el admin fue eliminado o desactivado
 * después de emitido el token, invalidando sesiones huérfanas.</p>
 *
 * @see AuthAdminService para la lógica de autenticación BCrypt + JWT
 */
@ApiTags('Admin Auth')
@Controller('admin/auth')
export class AuthAdminController {
  /**
   * Crea el controlador de autenticación administrativa.
   *
   * @param loginAdminUseCase Caso de uso de login admin.
   * @param getCurrentAdminUseCase Caso de uso de sesión actual.
   */
  constructor(
    private readonly loginAdminUseCase: LoginAdminUseCase,
    private readonly getCurrentAdminUseCase: GetCurrentAdminUseCase,
  ) {}

  /**
   * Inicia sesión de un usuario administrativo.
   *
   * @param request DTO con credenciales de acceso.
   * @returns Respuesta con token y contexto resumido del admin.
   */
  @Public()
  @Post('login')
  @ApiOperation({
    summary: 'Iniciar sesión administrativa',
    description: 'Autentica un usuario admin válido y emite un JWT.',
  })
  @ApiOkResponse({
    description: 'Inicio de sesión exitoso.',
    type: LoginAdminResponseDto,
  })
  async login(@Body() request: LoginAdminRequestDto) {
    const data = await this.loginAdminUseCase.execute(request);

    return {
      message: 'Inicio de sesión exitoso.',
      data,
    };
  }

  /**
   * Recupera el contexto mínimo del admin autenticado actual.
   *
   * @param admin Admin autenticado reconstruido desde JWT.
   * @returns Resumen del admin autenticado actual.
   */
  @Get('me')
  @ApiBearerAuth()
  @ApiOperation({
    summary: 'Consultar admin autenticado actual',
    description: 'Devuelve el contexto mínimo del usuario admin autenticado.',
  })
  @ApiOkResponse({
    description: 'Consulta exitosa de sesión actual.',
    type: CurrentAdminResponseDto,
  })
  async getCurrentAdmin(
    @CurrentAdmin() admin: AuthenticatedAdminType,
  ) {
    const data = await this.getCurrentAdminUseCase.execute(admin);

    return {
      data,
    };
  }
}
