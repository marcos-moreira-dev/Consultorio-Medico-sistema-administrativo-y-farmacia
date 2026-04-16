import { SetMetadata } from '@nestjs/common';

/**
 * Clave de metadata para marcar rutas públicas.
 */
export const IS_PUBLIC_KEY = 'isPublic';

/**
 * Decorador para indicar que una ruta o controlador es público.
 *
 * Las rutas públicas no deben exigir autenticación JWT.
 */
export const Public = (): MethodDecorator & ClassDecorator =>
  SetMetadata(IS_PUBLIC_KEY, true);
