import { ApiProperty, ApiPropertyOptional } from '@nestjs/swagger';
import {
  IsNotEmpty,
  IsNumber,
  IsPositive,
  IsString,
  MaxLength,
  Min,
  MinLength,
  IsOptional,
} from 'class-validator';

/**
 * DTO de entrada para crear un producto.
 *
 * El producto nace en estado interno coherente:
 * - estado_producto = ACTIVO
 * - es_publicable = false
 * - estado_disponibilidad = NO_PUBLICADO
 */
export class CrearProductoRequestDto {
  /**
   * Identificador de la categoría del producto.
   */
  @ApiProperty({
    description: 'Identificador de la categoría del producto.',
    example: 1,
  })
  @IsNumber({ maxDecimalPlaces: 0 })
  @Min(1)
  categoriaId!: number;

  /**
   * Nombre visible del producto.
   */
  @ApiProperty({
    description: 'Nombre visible del producto.',
    example: 'Paracetamol',
    maxLength: 150,
  })
  @IsString()
  @IsNotEmpty()
  @MinLength(2)
  @MaxLength(150)
  nombreProducto!: string;

  /**
   * Presentación comercial del producto.
   */
  @ApiProperty({
    description: 'Presentación comercial del producto.',
    example: 'Caja x 20 tabletas',
    maxLength: 100,
  })
  @IsString()
  @IsNotEmpty()
  @MinLength(2)
  @MaxLength(100)
  presentacion!: string;

  /**
   * Descripción breve opcional.
   */
  @ApiPropertyOptional({
    description: 'Descripción breve opcional del producto.',
    example: 'Analgésico y antipirético de uso común.',
    maxLength: 300,
  })
  @IsOptional()
  @IsString()
  @MaxLength(300)
  descripcionBreve?: string;

  /**
   * Precio visible al público.
   */
  @ApiProperty({
    description: 'Precio visible al público.',
    example: 3.5,
  })
  @IsNumber({ maxDecimalPlaces: 2 })
  @IsPositive()
  precioVisible!: number;
}
