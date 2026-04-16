import { UnauthorizedException } from '@nestjs/common';

import { RolAdminEnum } from '../../../src/app/common/enums/rol-admin.enum';
import { JwtStrategy } from '../../../src/app/security/strategies/jwt.strategy';

describe('JwtStrategy', () => {
  let strategy: JwtStrategy;
  let configService: { get: jest.Mock };

  beforeEach(() => {
    configService = {
      get: jest.fn((key: string, defaultValue?: unknown) => {
        if (key === 'jwt.secret') {
          return 'secret-test';
        }

        return defaultValue;
      }),
    };

    strategy = new JwtStrategy(configService as any);
  });

  it('debe normalizar correctamente un payload válido', () => {
    const result = strategy.validate({
      sub: 1,
      username: 'admin.farmacia',
      email: 'admin@farmacia.local',
      roles: [RolAdminEnum.ADMIN_FARMACIA],
    });

    expect(result).toEqual({
      adminId: 1,
      username: 'admin.farmacia',
      email: 'admin@farmacia.local',
      roles: [RolAdminEnum.ADMIN_FARMACIA],
      raw: {
        sub: 1,
        username: 'admin.farmacia',
        email: 'admin@farmacia.local',
        roles: [RolAdminEnum.ADMIN_FARMACIA],
      },
    });
  });

  it('debe usar adminId si viene explícito en el payload', () => {
    const result = strategy.validate({
      sub: 99,
      adminId: 5,
      username: 'otro.admin',
      roles: [RolAdminEnum.ADMIN_FARMACIA],
    });

    expect(result.adminId).toBe(5);
    expect(result.username).toBe('otro.admin');
  });

  it('debe asignar username por defecto si no viene uno válido', () => {
    const result = strategy.validate({
      adminId: 7,
      roles: [RolAdminEnum.ADMIN_FARMACIA],
    });

    expect(result.adminId).toBe(7);
    expect(result.username).toBe('admin-7');
  });

  it('debe asignar rol por defecto si no vienen roles', () => {
    const result = strategy.validate({
      adminId: 3,
      username: 'admin.simple',
    });

    expect(result.roles).toEqual([RolAdminEnum.ADMIN_FARMACIA]);
  });

  it('debe lanzar error si el token no contiene un adminId válido', () => {
    expect(() =>
      strategy.validate({
        username: 'sin-id',
        roles: [RolAdminEnum.ADMIN_FARMACIA],
      }),
    ).toThrow(UnauthorizedException);

    expect(() =>
      strategy.validate({
        adminId: 'abc',
        username: 'mal-id',
        roles: [RolAdminEnum.ADMIN_FARMACIA],
      }),
    ).toThrow(UnauthorizedException);
  });
});
