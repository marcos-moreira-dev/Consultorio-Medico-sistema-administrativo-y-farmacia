import { ApiProperty } from '@nestjs/swagger';

/**
 * DTO de salida con el estado actual de publicación y disponibilidad del producto.
 */
export class EstadoPublicacionResponseDto {
  /**
   * Identificador principal del producto.
   */
  @ApiProperty({
    description: 'Identificador principal del producto.',
    example: 1,
  })
  productoId!: number;

  /**
   * Estado lógico del producto.
   */
  @ApiProperty({
    description: 'Estado lógico del producto.',
    example: 'ACTIVO',
  })
  estadoProducto!: string;

  /**
   * Indica si el producto es publicable o está publicado.
   */
  @ApiProperty({
    description: 'Indica si el producto es publicable o está publicado.',
    example: true,
  })
  esPublicable!: boolean;

  /**
   * Estado de disponibilidad operativa del producto.
   */
  @ApiProperty({
    description: 'Estado de disponibilidad operativa del producto.',
    example: 'DISPONIBLE',
  })
  estadoDisponibilidad!: string;

  /**
   * Indica si el producto es visible actualmente en la superficie pública.
   */
  @ApiProperty({
    description: 'Indica si el producto es visible actualmente en la superficie pública.',
    example: true,
  })
  estaPublicadoPublicamente!: boolean;

  /**
   * Indica si, por su estado lógico, el producto podría publicarse.
   */
  @ApiProperty({
    description: 'Indica si el producto podría publicarse según su estado lógico.',
    example: true,
  })
  puedePublicarse!: boolean;

  /**
   * Fecha de última actualización del producto.
   */
  @ApiProperty({
    description: 'Fecha de última actualización en formato ISO-8601.',
    example: '2026-04-08T15:30:00.000Z',
  })
  fechaActualizacion!: string;
}
