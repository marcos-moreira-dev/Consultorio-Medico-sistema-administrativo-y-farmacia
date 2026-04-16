import { ApiProperty } from '@nestjs/swagger';
import { IsInt, Min } from 'class-validator';

/**
 * DTO de entrada para reemplazar la imagen asociada de un producto.
 */
export class ReemplazarImagenProductoRequestDto {
  /**
   * Identificador del nuevo recurso de media a asociar.
   */
  @ApiProperty({
    description: 'Identificador del nuevo recurso de media a asociar.',
    example: 11,
  })
  @IsInt()
  @Min(1)
  mediaRecursoId!: number;
}
