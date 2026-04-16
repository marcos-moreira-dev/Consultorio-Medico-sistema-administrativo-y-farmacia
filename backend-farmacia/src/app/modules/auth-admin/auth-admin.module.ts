import { Module } from '@nestjs/common';
import { APP_GUARD } from '@nestjs/core';
import { JwtModule } from '@nestjs/jwt';
import type { StringValue } from 'ms';
import { PassportModule } from '@nestjs/passport';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { TypeOrmModule } from '@nestjs/typeorm';

import { JwtAuthGuard } from '../../security/guards/jwt-auth.guard';
import { RolesGuard } from '../../security/guards/roles.guard';
import { JwtStrategy } from '../../security/strategies/jwt.strategy';
import { AuthAdminController } from './controllers/auth-admin.controller';
import { UsuarioAdminEntity } from './entities/usuario-admin.entity';
import { UsuarioAdminRepository } from './repositories/usuario-admin.repository';
import { AuthAdminService } from './services/auth-admin.service';
import { GetCurrentAdminUseCase } from './use-cases/get-current-admin.use-case';
import { LoginAdminUseCase } from './use-cases/login-admin.use-case';

/**
 * Módulo de autenticación administrativa de farmacia.
 *
 * Responsabilidades:
 * - exponer endpoints de login admin y sesión actual;
 * - emitir JWT para la superficie administrativa;
 * - registrar la estrategia JWT;
 * - activar guards globales de autenticación y autorización.
 */
@Module({
  imports: [
    ConfigModule,
    PassportModule.register({
      defaultStrategy: 'jwt',
    }),
    JwtModule.registerAsync({
      inject: [ConfigService],
      useFactory: (configService: ConfigService) => ({
        secret: configService.get<string>('jwt.secret', 'CAMBIAR_EN_ENTORNO_REAL'),
        signOptions: {
          expiresIn: configService.get<string>('jwt.expiresIn', '1d') as StringValue,
        },
      }),
    }),
    TypeOrmModule.forFeature([UsuarioAdminEntity]),
  ],
  controllers: [AuthAdminController],
  providers: [
    UsuarioAdminRepository,
    AuthAdminService,
    LoginAdminUseCase,
    GetCurrentAdminUseCase,
    JwtStrategy,
    {
      provide: APP_GUARD,
      useClass: JwtAuthGuard,
    },
    {
      provide: APP_GUARD,
      useClass: RolesGuard,
    },
  ],
  exports: [AuthAdminService, UsuarioAdminRepository],
})
export class AuthAdminModule {}
