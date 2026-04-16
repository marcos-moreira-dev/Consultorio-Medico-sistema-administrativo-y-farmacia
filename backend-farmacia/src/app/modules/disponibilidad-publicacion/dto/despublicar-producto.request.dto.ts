import { ApiPropertyOptional } from '@nestjs/swagger';
import { IsOptional, IsString, MaxLength } from 'class-validator';

/**
 * DTO de entrada para despublicar un producto.
 *
 * El motivo no se persiste todavía. Se deja como extensión natural para
 * auditoría o trazabilidad futura.
 */
export class DespublicarProductoRequestDto {
  /**
   * Motivo operativo opcional de la despublicación.
   */
  @ApiPropertyOptional({
    description: 'Motivo operativo opcional de la despublicación.',
    example: 'Producto retirado temporalmente del catálogo.',
    maxLength: 255,
  })
  @IsOptional()
  @IsString()
  @MaxLength(255)
  motivoOperativo?: string;
}
