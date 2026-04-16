import { ApiProperty, ApiPropertyOptional } from '@nestjs/swagger';

/**
 * DTO de salida de una categoría administrativa.
 */
export class CategoriaResponseDto {
  /**
   * Identificador principal de la categoría.
   */
  @ApiProperty({
    description: 'Identificador principal de la categoría.',
    example: 1,
  })
  categoriaId!: number;

  /**
   * Nombre visible de la categoría.
   */
  @ApiProperty({
    description: 'Nombre visible de la categoría.',
    example: 'Analgésicos',
  })
  nombreCategoria!: string;

  /**
   * Descripción breve opcional.
   */
  @ApiPropertyOptional({
    description: 'Descripción breve opcional.',
    example: 'Medicamentos para dolor y fiebre.',
  })
  descripcionBreve?: string | null;

  /**
   * Marca de creación en formato ISO-8601.
   */
  @ApiProperty({
    description: 'Marca de creación en formato ISO-8601.',
    example: '2026-04-08T15:30:00.000Z',
  })
  fechaCreacion!: string;

  /**
   * Marca de última actualización en formato ISO-8601.
   */
  @ApiProperty({
    description: 'Marca de última actualización en formato ISO-8601.',
    example: '2026-04-08T15:30:00.000Z',
  })
  fechaActualizacion!: string;
}
