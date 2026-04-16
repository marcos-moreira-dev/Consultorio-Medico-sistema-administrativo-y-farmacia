import {
  CanActivate,
  ExecutionContext,
  ForbiddenException,
  Injectable,
} from '@nestjs/common';
import { Reflector } from '@nestjs/core';

import { IS_PUBLIC_KEY } from '../decorators/public.decorator';
import { ROLES_KEY } from '../decorators/roles.decorator';
import { AuthenticatedAdminType } from '../types/authenticated-admin.type';

/**
 * Tipo de request HTTP enriquecido con el admin autenticado.
 */
type RequestWithAuthenticatedAdmin = {
  user?: AuthenticatedAdminType;
};

/**
 * Guard para verificar roles del admin autenticado.
 *
 * Reglas:
 * - Si la ruta es pública, deja pasar.
 * - Si la ruta no declara roles, deja pasar y asume que basta con estar autenticado.
 * - Si declara roles, el admin debe tener al menos uno coincidente.
 */
@Injectable()
export class RolesGuard implements CanActivate {
  /**
   * Crea una instancia del guard.
   *
   * @param reflector Utilidad para leer metadata de Nest.
   */
  constructor(private readonly reflector: Reflector) {}

  /**
   * Determina si la ruta puede continuar según roles requeridos.
   *
   * @param context Contexto de ejecución actual.
   * @returns true cuando la solicitud supera la validación de roles.
   */
  canActivate(context: ExecutionContext): boolean {
    const isPublic = this.reflector.getAllAndOverride<boolean>(IS_PUBLIC_KEY, [
      context.getHandler(),
      context.getClass(),
    ]);

    if (isPublic) {
      return true;
    }

    const requiredRoles = this.reflector.getAllAndOverride<string[]>(ROLES_KEY, [
      context.getHandler(),
      context.getClass(),
    ]);

    if (!requiredRoles || requiredRoles.length === 0) {
      return true;
    }

    const request = context.switchToHttp().getRequest<RequestWithAuthenticatedAdmin>();
    const admin = request.user;

    if (!admin) {
      throw new ForbiddenException('No existe un admin autenticado para validar roles.');
    }

    const adminRoles = Array.isArray(admin.roles) ? admin.roles : [];

    const hasAtLeastOneRole = requiredRoles.some((requiredRole) =>
      adminRoles.includes(requiredRole),
    );

    if (!hasAtLeastOneRole) {
      throw new ForbiddenException('No tienes permisos para realizar esta operación.');
    }

    return true;
  }
}
