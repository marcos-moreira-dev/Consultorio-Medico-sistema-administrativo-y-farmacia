import { ApiPropertyOptional } from '@nestjs/swagger';
import {
  IsNumber,
  IsOptional,
  IsPositive,
  IsString,
  MaxLength,
  Min,
  MinLength,
} from 'class-validator';

/**
 * DTO de entrada para actualizar un producto.
 *
 * Todos los campos son opcionales. La validación de que exista al menos un
 * cambio efectivo se resuelve en la capa de reglas del módulo.
 */
export class ActualizarProductoRequestDto {
  /**
   * Identificador de la categoría del producto.
   */
  @ApiPropertyOptional({
    description: 'Identificador de la categoría del producto.',
    example: 1,
  })
  @IsOptional()
  @IsNumber({ maxDecimalPlaces: 0 })
  @Min(1)
  categoriaId?: number;

  /**
   * Nombre visible del producto.
   */
  @ApiPropertyOptional({
    description: 'Nombre visible del producto.',
    example: 'Paracetamol',
    maxLength: 150,
  })
  @IsOptional()
  @IsString()
  @MinLength(2)
  @MaxLength(150)
  nombreProducto?: string;

  /**
   * Presentación comercial del producto.
   */
  @ApiPropertyOptional({
    description: 'Presentación comercial del producto.',
    example: 'Caja x 20 tabletas',
    maxLength: 100,
  })
  @IsOptional()
  @IsString()
  @MinLength(2)
  @MaxLength(100)
  presentacion?: string;

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
  descripcionBreve?: string | null;

  /**
   * Precio visible al público.
   */
  @ApiPropertyOptional({
    description: 'Precio visible al público.',
    example: 3.5,
  })
  @IsOptional()
  @IsNumber({ maxDecimalPlaces: 2 })
  @IsPositive()
  precioVisible?: number;
}
