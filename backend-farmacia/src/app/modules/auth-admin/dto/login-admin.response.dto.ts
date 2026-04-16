import { ApiProperty } from '@nestjs/swagger';

import { RolAdminEnum } from '../../../common/enums/rol-admin.enum';
import { CurrentAdminResponseDto } from './current-admin.response.dto';

/**
 * DTO de salida para inicio de sesión administrativo.
 */
export class LoginAdminResponseDto {
  /**
   * Token JWT de acceso.
   */
  @ApiProperty({
    description: 'Token JWT de acceso.',
    example: 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...',
  })
  accessToken!: string;

  /**
   * Tipo de token emitido.
   */
  @ApiProperty({
    description: 'Tipo de token emitido.',
    example: 'Bearer',
  })
  tokenType!: string;

  /**
   * Duración del token expresada en segundos.
   */
  @ApiProperty({
    description: 'Duración del token expresada en segundos.',
    example: 86400,
  })
  expiresInSeconds!: number;

  /**
   * Rol principal del admin autenticado.
   */
  @ApiProperty({
    description: 'Rol principal del admin autenticado.',
    enum: RolAdminEnum,
    example: RolAdminEnum.ADMIN_FARMACIA,
  })
  rolPrincipal!: RolAdminEnum;

  /**
   * Resumen del usuario admin autenticado.
   */
  @ApiProperty({
    description: 'Resumen del usuario admin autenticado.',
    type: () => CurrentAdminResponseDto,
  })
  usuario!: CurrentAdminResponseDto;
}
