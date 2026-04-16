import { ApiProperty } from '@nestjs/swagger';
import { IsInt, Min } from 'class-validator';

/**
 * DTO de entrada para asociar una imagen previamente subida a un producto.
 */
export class AsociarImagenProductoRequestDto {
  /**
   * Identificador del recurso de media a asociar.
   */
  @ApiProperty({
    description: 'Identificador del recurso de media a asociar.',
    example: 10,
  })
  @IsInt()
  @Min(1)
  mediaRecursoId!: number;
}
