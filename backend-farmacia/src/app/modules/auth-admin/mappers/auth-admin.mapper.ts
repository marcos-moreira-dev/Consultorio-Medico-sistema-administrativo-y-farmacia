import { RolAdminEnum } from '../../../common/enums/rol-admin.enum';
import { AuthenticatedAdminType } from '../../../security/types/authenticated-admin.type';
import { CurrentAdminResponseDto } from '../dto/current-admin.response.dto';
import { LoginAdminResponseDto } from '../dto/login-admin.response.dto';
import { UsuarioAdminEntity } from '../entities/usuario-admin.entity';

/**
 * Mapper del módulo de autenticación administrativa.
 */
export class AuthAdminMapper {
  private constructor() {}

  /**
   * Convierte una entidad de usuario admin a DTO de sesión actual.
   *
   * @param entity Entidad persistida del admin.
   * @returns DTO resumido del admin actual.
   */
  static toCurrentAdminResponseFromEntity(
    entity: UsuarioAdminEntity,
  ): CurrentAdminResponseDto {
    const roles = entity.getRoles();
    const rolPrincipal = roles[0] ?? RolAdminEnum.ADMIN_FARMACIA;

    return {
      adminId: entity.usuarioAdminId,
      username: entity.username,
      email: entity.email ?? undefined,
      rolPrincipal,
      roles,
    };
  }

  /**
   * Convierte un admin autenticado desde JWT a DTO resumido.
   *
   * @param authenticatedAdmin Admin autenticado.
   * @returns DTO resumido del admin actual.
   */
  static toCurrentAdminResponseFromAuthenticatedAdmin(
    authenticatedAdmin: AuthenticatedAdminType,
  ): CurrentAdminResponseDto {
    const roles = Array.isArray(authenticatedAdmin.roles)
      ? authenticatedAdmin.roles.filter((role) => !!role)
      : [];

    const rolPrincipal =
      (roles[0] as RolAdminEnum | undefined) ?? RolAdminEnum.ADMIN_FARMACIA;

    return {
      adminId: authenticatedAdmin.adminId,
      username: authenticatedAdmin.username,
      email: authenticatedAdmin.email,
      rolPrincipal,
      roles,
    };
  }

  /**
   * Construye la respuesta de login administrativo.
   *
   * @param accessToken Token JWT emitido.
   * @param expiresInSeconds Duración del token en segundos.
   * @param usuario DTO resumido del admin autenticado.
   * @returns DTO de respuesta de login.
   */
  static toLoginResponse(
    accessToken: string,
    expiresInSeconds: number,
    usuario: CurrentAdminResponseDto,
  ): LoginAdminResponseDto {
    return {
      accessToken,
      tokenType: 'Bearer',
      expiresInSeconds,
      rolPrincipal: usuario.rolPrincipal,
      usuario,
    };
  }
}
