import { ApiProperty, ApiPropertyOptional } from '@nestjs/swagger';

import { RolAdminEnum } from '../../../common/enums/rol-admin.enum';

/**
 * DTO de salida con el contexto mínimo del admin autenticado.
 */
export class CurrentAdminResponseDto {
  /**
   * Identificador del admin autenticado.
   */
  @ApiProperty({
    description: 'Identificador del admin autenticado.',
    example: 1,
  })
  adminId!: number;

  /**
   * Username del admin autenticado.
   */
  @ApiProperty({
    description: 'Username del admin autenticado.',
    example: 'admin.farmacia',
  })
  username!: string;

  /**
   * Correo opcional del admin autenticado.
   */
  @ApiPropertyOptional({
    description: 'Correo opcional del admin autenticado.',
    example: 'admin@farmacia.local',
  })
  email?: string;

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
   * Roles efectivos del admin autenticado.
   */
  @ApiProperty({
    description: 'Roles efectivos del admin autenticado.',
    type: [String],
    example: [RolAdminEnum.ADMIN_FARMACIA],
  })
  roles!: string[];
}
