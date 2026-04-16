import {
  ExecutionContext,
  Injectable,
  UnauthorizedException,
} from '@nestjs/common';
import { Reflector } from '@nestjs/core';
import { AuthGuard } from '@nestjs/passport';

import { IS_PUBLIC_KEY } from '../decorators/public.decorator';

/**
 * Guard JWT para proteger rutas privadas.
 *
 * Reglas:
 * - Si una ruta/controlador es público, deja pasar.
 * - Si no es público, delega la validación a Passport con estrategia `jwt`.
 */
@Injectable()
export class JwtAuthGuard extends AuthGuard('jwt') {
  /**
   * Crea una instancia del guard.
   *
   * @param reflector Utilidad para leer metadata de Nest.
   */
  constructor(private readonly reflector: Reflector) {
    super();
  }

  /**
   * Determina si la ruta puede continuar.
   *
   * @param context Contexto de ejecución actual.
   * @returns true cuando la ruta es pública o el JWT resulta válido.
   */
  override canActivate(context: ExecutionContext) {
    const isPublic = this.reflector.getAllAndOverride<boolean>(IS_PUBLIC_KEY, [
      context.getHandler(),
      context.getClass(),
    ]);

    if (isPublic) {
      return true;
    }

    return super.canActivate(context);
  }

  /**
   * Maneja el resultado final de Passport.
   *
   * @param error Error interno de Passport.
   * @param user Usuario resuelto por la estrategia.
   * @returns Usuario autenticado.
   */
  override handleRequest<TUser = unknown>(error: unknown, user: TUser | false | null): TUser {
    if (error || !user) {
      throw new UnauthorizedException('No autenticado o token inválido.');
    }

    return user;
  }
}
