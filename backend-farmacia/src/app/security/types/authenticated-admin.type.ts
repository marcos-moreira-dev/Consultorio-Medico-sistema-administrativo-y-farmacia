/**
 * Tipo base del admin autenticado disponible en `request.user`.
 *
 * Esta forma intenta ser estable y suficientemente pequeña para no acoplar toda
 * la aplicación al payload completo del JWT.
 */
export type AuthenticatedAdminType = {
  /**
   * Identificador del admin autenticado.
   */
  adminId: number;

  /**
   * Nombre corto o username del admin.
   */
  username: string;

  /**
   * Correo opcional del admin.
   */
  email?: string;

  /**
   * Roles efectivos del admin autenticado.
   */
  roles: string[];

  /**
   * Payload crudo del token, conservado por depuración o uso futuro.
   */
  raw?: Record<string, unknown>;
};
