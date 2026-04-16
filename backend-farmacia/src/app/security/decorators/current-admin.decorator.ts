import { createParamDecorator, ExecutionContext } from '@nestjs/common';

import { AuthenticatedAdminType } from '../types/authenticated-admin.type';

/**
 * Tipo de request HTTP enriquecido con el admin autenticado.
 */
type RequestWithAuthenticatedAdmin = {
  user?: AuthenticatedAdminType;
};

/**
 * Decorador para obtener el admin autenticado desde el request actual.
 *
 * Uso típico:
 * `@CurrentAdmin() admin: AuthenticatedAdminType`
 *
 * También permite acceder a una propiedad concreta:
 * `@CurrentAdmin('adminId') adminId: number`
 */
export const CurrentAdmin = createParamDecorator(
  (
    property: keyof AuthenticatedAdminType | undefined,
    ctx: ExecutionContext,
  ): AuthenticatedAdminType | AuthenticatedAdminType[keyof AuthenticatedAdminType] | undefined => {
    const request = ctx.switchToHttp().getRequest<RequestWithAuthenticatedAdmin>();
    const admin = request.user;

    if (!admin) {
      return undefined;
    }

    if (!property) {
      return admin;
    }

    return admin[property];
  },
);
