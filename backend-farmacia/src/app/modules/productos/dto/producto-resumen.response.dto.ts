import { ApiProperty, ApiPropertyOptional } from '@nestjs/swagger';

/**
 * DTO resumen de producto, útil para listados administrativos.
 */
export class ProductoResumenResponseDto {
  /**
   * Identificador principal del producto.
   */
  @ApiProperty({
    description: 'Identificador principal del producto.',
    example: 1,
  })
  productoId!: number;

  /**
   * Identificador de la categoría asociada.
   */
  @ApiProperty({
    description: 'Identificador de la categoría asociada.',
    example: 1,
  })
  categoriaId!: number;

  /**
   * Nombre de la categoría asociada.
   */
  @ApiPropertyOptional({
    description: 'Nombre de la categoría asociada.',
    example: 'Analgésicos',
  })
  nombreCategoria?: string;

  /**
   * Nombre visible del producto.
   */
  @ApiProperty({
    description: 'Nombre visible del producto.',
    example: 'Paracetamol',
  })
  nombreProducto!: string;

  /**
   * Presentación comercial del producto.
   */
  @ApiProperty({
    description: 'Presentación comercial del producto.',
    example: 'Caja x 20 tabletas',
  })
  presentacion!: string;

  /**
   * Precio visible al público.
   */
  @ApiProperty({
    description: 'Precio visible al público.',
    example: 3.5,
  })
  precioVisible!: number;

  /**
   * Estado lógico del producto.
   */
  @ApiProperty({
    description: 'Estado lógico del producto.',
    example: 'ACTIVO',
  })
  estadoProducto!: string;

  /**
   * Indica si el producto es publicable o está publicado para superficie pública.
   */
  @ApiProperty({
    description: 'Indica si el producto es publicable o está publicado.',
    example: false,
  })
  esPublicable!: boolean;

  /**
   * Estado de disponibilidad operativa del producto.
   */
  @ApiProperty({
    description: 'Estado de disponibilidad operativa del producto.',
    example: 'NO_PUBLICADO',
  })
  estadoDisponibilidad!: string;

  /**
   * Fecha de última actualización.
   */
  @ApiProperty({
    description: 'Fecha de última actualización en formato ISO-8601.',
    example: '2026-04-08T15:30:00.000Z',
  })
  fechaActualizacion!: string;
}
