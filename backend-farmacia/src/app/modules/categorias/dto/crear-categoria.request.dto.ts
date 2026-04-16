import { ApiProperty, ApiPropertyOptional } from '@nestjs/swagger';
import { IsNotEmpty, IsOptional, IsString, MaxLength, MinLength } from 'class-validator';

/**
 * DTO de entrada para crear una categoría.
 */
export class CrearCategoriaRequestDto {
  /**
   * Nombre visible de la categoría.
   */
  @ApiProperty({
    description: 'Nombre visible de la categoría.',
    example: 'Analgésicos',
    maxLength: 100,
  })
  @IsString()
  @IsNotEmpty()
  @MinLength(2)
  @MaxLength(100)
  nombreCategoria!: string;

  /**
   * Descripción breve opcional de la categoría.
   */
  @ApiPropertyOptional({
    description: 'Descripción breve opcional de la categoría.',
    example: 'Medicamentos para dolor y fiebre.',
    maxLength: 200,
  })
  @IsOptional()
  @IsString()
  @MaxLength(200)
  descripcionBreve?: string;
}
