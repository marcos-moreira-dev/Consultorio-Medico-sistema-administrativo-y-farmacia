import { ApiPropertyOptional } from '@nestjs/swagger';
import { IsOptional, IsString, MaxLength, MinLength } from 'class-validator';

/**
 * DTO de entrada para actualizar una categoría.
 *
 * Todos los campos son opcionales, pero al menos uno debe venir informado.
 * Esa validación de negocio se resuelve en la capa de reglas del módulo.
 */
export class ActualizarCategoriaRequestDto {
  /**
   * Nombre visible de la categoría.
   */
  @ApiPropertyOptional({
    description: 'Nombre visible de la categoría.',
    example: 'Analgésicos',
    maxLength: 100,
  })
  @IsOptional()
  @IsString()
  @MinLength(2)
  @MaxLength(100)
  nombreCategoria?: string;

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
  descripcionBreve?: string | null;
}
