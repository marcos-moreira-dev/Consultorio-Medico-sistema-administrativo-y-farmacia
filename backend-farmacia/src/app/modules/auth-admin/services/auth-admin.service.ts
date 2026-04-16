import { Injectable, UnauthorizedException } from '@nestjs/common';
import { JwtService } from '@nestjs/jwt';
import { ConfigService } from '@nestjs/config';
import { compare } from 'bcrypt';

import { AuthenticatedAdminType } from '../../../security/types/authenticated-admin.type';
import { AuthAdminMapper } from '../mappers/auth-admin.mapper';
import { CurrentAdminResponseDto } from '../dto/current-admin.response.dto';
import { LoginAdminResponseDto } from '../dto/login-admin.response.dto';
import { UsuarioAdminRepository } from '../repositories/usuario-admin.repository';

/**
 * Servicio de aplicación del módulo de autenticación administrativa.
 */
@Injectable()
export class AuthAdminService {
  /**
   * Crea el servicio de autenticación administrativa.
   *
   * @param usuarioAdminRepository Repositorio de usuarios admin.
   * @param jwtService Servicio JWT de Nest.
   * @param configService Servicio de configuración global.
   */
  constructor(
    private readonly usuarioAdminRepository: UsuarioAdminRepository,
    private readonly jwtService: JwtService,
    private readonly configService: ConfigService,
  ) {}

  /**
   * Autentica credenciales administrativas y emite un token JWT.
   *
   * @param username Username del admin.
   * @param password Password en texto plano enviada por el cliente.
   * @returns Respuesta completa de login.
   */
  async login(username: string, password: string): Promise<LoginAdminResponseDto> {
    const usuarioAdmin = await this.usuarioAdminRepository.findByUsername(username);

    if (!usuarioAdmin || !usuarioAdmin.isActivo()) {
      throw new UnauthorizedException('Credenciales inválidas.');
    }

    const passwordIsValid = await compare(password, usuarioAdmin.passwordHash);

    if (!passwordIsValid) {
      throw new UnauthorizedException('Credenciales inválidas.');
    }

    const roles = usuarioAdmin.getRoles();
    const payload = {
      sub: usuarioAdmin.usuarioAdminId,
      adminId: usuarioAdmin.usuarioAdminId,
      username: usuarioAdmin.username,
      email: usuarioAdmin.email ?? undefined,
      roles,
    };

    const accessToken = await this.jwtService.signAsync(payload);
    const currentAdmin = AuthAdminMapper.toCurrentAdminResponseFromEntity(usuarioAdmin);

    return AuthAdminMapper.toLoginResponse(
      accessToken,
      this.resolveExpiresInSeconds(),
      currentAdmin,
    );
  }

  /**
   * Recupera la sesión actual validando contra persistencia.
   *
   * Esto permite detectar admins eliminados o desactivados después de emitido
   * el token.
   *
   * @param authenticatedAdmin Contexto autenticado reconstruido desde JWT.
   * @returns DTO resumido del admin actual.
   */
  async obtenerSesionActual(
    authenticatedAdmin: AuthenticatedAdminType,
  ): Promise<CurrentAdminResponseDto> {
    const usuarioAdmin = await this.usuarioAdminRepository.findById(
      authenticatedAdmin.adminId,
    );

    if (!usuarioAdmin || !usuarioAdmin.isActivo()) {
      throw new UnauthorizedException(
        'La sesión ya no es válida para este usuario administrativo.',
      );
    }

    return AuthAdminMapper.toCurrentAdminResponseFromEntity(usuarioAdmin);
  }

  /**
   * Resuelve la duración efectiva del token en segundos.
   *
   * Soporta formatos típicos como:
   * - 900
   * - 900s
   * - 15m
   * - 12h
   * - 1d
   *
   * @returns Duración del token en segundos.
   */
  private resolveExpiresInSeconds(): number {
    const rawValue = this.configService.get<string | number>('jwt.expiresIn', '1d');

    if (typeof rawValue === 'number' && Number.isFinite(rawValue)) {
      return rawValue;
    }

    const normalized = String(rawValue).trim().toLowerCase();

    if (/^\d+$/.test(normalized)) {
      return Number(normalized);
    }

    const match = normalized.match(/^(\d+)(s|m|h|d)$/);

    if (!match) {
      return 86400;
    }

    const amount = Number(match[1]);
    const unit = match[2];

    switch (unit) {
      case 's':
        return amount;
      case 'm':
        return amount * 60;
      case 'h':
        return amount * 60 * 60;
      case 'd':
        return amount * 60 * 60 * 24;
      default:
        return 86400;
    }
  }
}
