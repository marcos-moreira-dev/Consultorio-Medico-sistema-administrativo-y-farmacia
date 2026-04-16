import { ApiProperty, ApiPropertyOptional } from '@nestjs/swagger';

/**
 * DTO detallado de producto visible en catálogo público.
 */
export class ProductoPublicoDetalleResponseDto {
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
   * Descripción breve opcional.
   */
  @ApiPropertyOptional({
    description: 'Descripción breve opcional del producto.',
    example: 'Analgésico y antipirético de uso común.',
  })
  descripcionBreve?: string | null;

  /**
   * Precio visible al público.
   */
  @ApiProperty({
    description: 'Precio visible al público.',
    example: 3.5,
  })
  precioVisible!: number;

  /**
   * Estado de disponibilidad pública del producto.
   */
  @ApiProperty({
    description: 'Estado de disponibilidad pública del producto.',
    example: 'AGOTADO',
  })
  estadoDisponibilidad!: string;

  /**
   * Indica si el producto está disponible públicamente.
   */
  @ApiProperty({
    description: 'Indica si el producto está disponible públicamente.',
    example: false,
  })
  disponible!: boolean;

  /**
   * URL pública de la imagen principal del producto.
   */
  @ApiPropertyOptional({
    description: 'URL pública de la imagen principal del producto.',
    example: 'http://localhost:3001/media/productos/imagenes/2026/04/uuid-demo.png',
  })
  imagenUrl?: string | null;

  /**
   * Fecha de última actualización del producto.
   */
  @ApiProperty({
    description: 'Fecha de última actualización en formato ISO-8601.',
    example: '2026-04-08T15:30:00.000Z',
  })
  fechaActualizacion!: string;
}
