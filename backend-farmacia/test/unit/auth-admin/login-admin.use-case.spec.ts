import { LoginAdminUseCase } from '../../../src/app/modules/auth-admin/use-cases/login-admin.use-case';
import { AuthAdminService } from '../../../src/app/modules/auth-admin/services/auth-admin.service';
import { RolAdminEnum } from '../../../src/app/common/enums/rol-admin.enum';

describe('LoginAdminUseCase', () => {
  let useCase: LoginAdminUseCase;
  let authAdminService: jest.Mocked<AuthAdminService>;

  beforeEach(() => {
    authAdminService = {
      login: jest.fn(),
    } as unknown as jest.Mocked<AuthAdminService>;

    useCase = new LoginAdminUseCase(authAdminService);
  });

  it('debe delegar el login al servicio y devolver su resultado', async () => {
    const request = {
      username: 'admin.farmacia',
      password: 'Admin123*',
    };

    const expectedResponse = {
      accessToken: 'jwt-token-demo',
      tokenType: 'Bearer',
      expiresInSeconds: 86400,
      rolPrincipal: RolAdminEnum.ADMIN_FARMACIA,
      usuario: {
        adminId: 1,
        username: 'admin.farmacia',
        email: 'admin@farmacia.local',
        rolPrincipal: RolAdminEnum.ADMIN_FARMACIA,
        roles: [RolAdminEnum.ADMIN_FARMACIA],
      },
    };

    authAdminService.login.mockResolvedValue(expectedResponse);

    const result = await useCase.execute(request);

    expect(authAdminService.login).toHaveBeenCalledTimes(1);
    expect(authAdminService.login).toHaveBeenCalledWith(
      request.username,
      request.password,
    );
    expect(result).toEqual(expectedResponse);
  });
});
