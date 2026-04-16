import { ApiProperty } from '@nestjs/swagger';
import { IsNotEmpty, IsString, MaxLength, MinLength } from 'class-validator';

/**
 * DTO de entrada para inicio de sesión administrativo.
 */
export class LoginAdminRequestDto {
  /**
   * Username del admin.
   */
  @ApiProperty({
    description: 'Username del usuario admin.',
    example: 'admin.farmacia',
  })
  @IsString()
  @IsNotEmpty()
  @MinLength(3)
  @MaxLength(100)
  username!: string;

  /**
   * Password del admin.
   */
  @ApiProperty({
    description: 'Password del usuario admin.',
    example: 'Admin123*',
  })
  @IsString()
  @IsNotEmpty()
  @MinLength(6)
  @MaxLength(100)
  password!: string;
}
