import { ApiProperty } from '@nestjs/swagger';

/**
 * DTO de salida para categorías visibles en el catálogo público.
 */
export class CategoriaPublicaResponseDto {
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
   * Cantidad de productos visibles públicamente dentro de la categoría.
   */
  @ApiProperty({
    description: 'Cantidad de productos visibles públicamente dentro de la categoría.',
    example: 12,
  })
  cantidadProductosVisibles!: number;
}
