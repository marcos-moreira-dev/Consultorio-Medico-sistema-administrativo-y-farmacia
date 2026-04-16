import { SetMetadata } from '@nestjs/common';

/**
 * Clave de metadata para roles requeridos.
 */
export const ROLES_KEY = 'roles';

/**
 * Decorador para declarar los roles requeridos por una ruta o controlador.
 *
 * @param roles Lista de roles aceptados.
 */
export const Roles = (...roles: string[]): MethodDecorator & ClassDecorator =>
  SetMetadata(ROLES_KEY, roles);
