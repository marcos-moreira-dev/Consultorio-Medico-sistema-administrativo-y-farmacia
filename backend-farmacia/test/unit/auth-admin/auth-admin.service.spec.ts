import { UnauthorizedException } from '@nestjs/common';
import { JwtService } from '@nestjs/jwt';
import { ConfigService } from '@nestjs/config';

import { RolAdminEnum } from '../../../src/app/common/enums/rol-admin.enum';
import { AuthAdminService } from '../../../src/app/modules/auth-admin/services/auth-admin.service';
import { UsuarioAdminRepository } from '../../../src/app/modules/auth-admin/repositories/usuario-admin.repository';

jest.mock('bcrypt', () => ({
  compare: jest.fn(),
}));

import { compare } from 'bcrypt';

describe('AuthAdminService', () => {
  let service: AuthAdminService;
  let usuarioAdminRepository: jest.Mocked<UsuarioAdminRepository>;
  let jwtService: jest.Mocked<JwtService>;
  let configService: jest.Mocked<ConfigService>;

  beforeEach(() => {
    usuarioAdminRepository = {
      findByUsername: jest.fn(),
      findById: jest.fn(),
    } as unknown as jest.Mocked<UsuarioAdminRepository>;

    jwtService = {
      signAsync: jest.fn(),
    } as unknown as jest.Mocked<JwtService>;

    configService = {
      get: jest.fn((key: string, defaultValue?: unknown) => {
        if (key === 'jwt.expiresIn') {
          return '1d';
        }

        return defaultValue;
      }),
    } as unknown as jest.Mocked<ConfigService>;

    service = new AuthAdminService(
      usuarioAdminRepository,
      jwtService,
      configService,
    );

    jest.clearAllMocks();
  });

  it('debe autenticar credenciales válidas y emitir token', async () => {
    usuarioAdminRepository.findByUsername.mockResolvedValue({
      usuarioAdminId: 1,
      username: 'admin.farmacia',
      passwordHash: 'bcrypt-hash',
      email: 'admin@farmacia.local',
      estado: 'ACTIVO',
      rolCodigo: RolAdminEnum.ADMIN_FARMACIA,
      isActivo: () => true,
      getRoles: () => [RolAdminEnum.ADMIN_FARMACIA],
    } as any);

    (compare as jest.Mock).mockResolvedValue(true);
    jwtService.signAsync.mockResolvedValue('jwt-token-demo');

    const result = await service.login('admin.farmacia', 'Admin123*');

    expect(usuarioAdminRepository.findByUsername).toHaveBeenCalledWith('admin.farmacia');
    expect(compare).toHaveBeenCalledWith('Admin123*', 'bcrypt-hash');
    expect(jwtService.signAsync).toHaveBeenCalledTimes(1);
    expect(result.accessToken).toBe('jwt-token-demo');
    expect(result.usuario.username).toBe('admin.farmacia');
    expect(result.rolPrincipal).toBe(RolAdminEnum.ADMIN_FARMACIA);
    expect(result.expiresInSeconds).toBe(86400);
  });

  it('debe rechazar credenciales cuando el usuario no existe', async () => {
    usuarioAdminRepository.findByUsername.mockResolvedValue(null);

    await expect(
      service.login('fantasma', 'clave-invalida'),
    ).rejects.toBeInstanceOf(UnauthorizedException);
  });

  it('debe rechazar credenciales cuando la contraseña no coincide', async () => {
    usuarioAdminRepository.findByUsername.mockResolvedValue({
      usuarioAdminId: 1,
      username: 'admin.farmacia',
      passwordHash: 'bcrypt-hash',
      email: 'admin@farmacia.local',
      estado: 'ACTIVO',
      rolCodigo: RolAdminEnum.ADMIN_FARMACIA,
      isActivo: () => true,
      getRoles: () => [RolAdminEnum.ADMIN_FARMACIA],
    } as any);

    (compare as jest.Mock).mockResolvedValue(false);

    await expect(
      service.login('admin.farmacia', 'mal'),
    ).rejects.toBeInstanceOf(UnauthorizedException);
  });

  it('debe recuperar la sesión actual validando contra persistencia', async () => {
    usuarioAdminRepository.findById.mockResolvedValue({
      usuarioAdminId: 1,
      username: 'admin.farmacia',
      passwordHash: 'bcrypt-hash',
      email: 'admin@farmacia.local',
      estado: 'ACTIVO',
      rolCodigo: RolAdminEnum.ADMIN_FARMACIA,
      isActivo: () => true,
      getRoles: () => [RolAdminEnum.ADMIN_FARMACIA],
    } as any);

    const result = await service.obtenerSesionActual({
      adminId: 1,
      username: 'admin.farmacia',
      email: 'admin@farmacia.local',
      roles: [RolAdminEnum.ADMIN_FARMACIA],
    });

    expect(usuarioAdminRepository.findById).toHaveBeenCalledWith(1);
    expect(result.adminId).toBe(1);
    expect(result.username).toBe('admin.farmacia');
    expect(result.rolPrincipal).toBe(RolAdminEnum.ADMIN_FARMACIA);
  });

  it('debe rechazar la sesión actual cuando el admin ya no existe o no está activo', async () => {
    usuarioAdminRepository.findById.mockResolvedValue(null);

    await expect(
      service.obtenerSesionActual({
        adminId: 99,
        username: 'admin.fantasma',
        roles: [RolAdminEnum.ADMIN_FARMACIA],
      }),
    ).rejects.toBeInstanceOf(UnauthorizedException);
  });
});
