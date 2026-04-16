import { Injectable, UnauthorizedException } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { PassportStrategy } from '@nestjs/passport';
import { ExtractJwt, Strategy } from 'passport-jwt';

import { RolAdminEnum } from '../../common/enums/rol-admin.enum';
import { AuthenticatedAdminType } from '../types/authenticated-admin.type';

/**
 * Payload mínimo esperado dentro del JWT del admin.
 */
interface JwtAdminPayload {
  /**
   * Identificador principal del sujeto.
   */
  sub?: number | string;

  /**
   * Identificador explícito del admin.
   */
  adminId?: number | string;

  /**
   * Username o nombre corto del admin.
   */
  username?: string;

  /**
   * Correo opcional del admin.
   */
  email?: string;

  /**
   * Roles asociados al admin.
   */
  roles?: string[];

  /**
   * Permite propiedades adicionales futuras.
   */
  [key: string]: unknown;
}

/**
 * Estrategia JWT para autenticación de administradores.
 *
 * Por ahora valida la firma del token y normaliza un objeto de admin autenticado
 * a partir del payload. Más adelante, si el proyecto lo necesita, aquí se puede
 * consultar la base de datos para verificar estado del admin.
 */
@Injectable()
export class JwtStrategy extends PassportStrategy(Strategy) {
  /**
   * Crea la estrategia JWT.
   *
   * @param configService Servicio de configuración global.
   */
  constructor(private readonly configService: ConfigService) {
    super({
      jwtFromRequest: ExtractJwt.fromAuthHeaderAsBearerToken(),
      ignoreExpiration: false,
      secretOrKey: configService.get<string>('jwt.secret', 'CAMBIAR_EN_ENTORNO_REAL'),
    });
  }

  /**
   * Normaliza el payload del token a la forma usada por el backend.
   *
   * @param payload Payload JWT ya validado criptográficamente.
   * @returns Admin autenticado normalizado.
   */
  validate(payload: JwtAdminPayload): AuthenticatedAdminType {
    const resolvedAdminId = Number(payload.adminId ?? payload.sub);

    if (!Number.isInteger(resolvedAdminId) || resolvedAdminId < 1) {
      throw new UnauthorizedException('El token no contiene un adminId válido.');
    }

    const username =
      typeof payload.username === 'string' && payload.username.trim().length > 0
        ? payload.username.trim()
        : `admin-${resolvedAdminId}`;

    const email =
      typeof payload.email === 'string' && payload.email.trim().length > 0
        ? payload.email.trim()
        : undefined;

    const roles =
      Array.isArray(payload.roles) && payload.roles.length > 0
        ? payload.roles.filter((role): role is string => typeof role === 'string' && role.trim().length > 0)
        : [RolAdminEnum.ADMIN_FARMACIA];

    return {
      adminId: resolvedAdminId,
      username,
      email,
      roles,
      raw: payload as Record<string, unknown>,
    };
  }
}
