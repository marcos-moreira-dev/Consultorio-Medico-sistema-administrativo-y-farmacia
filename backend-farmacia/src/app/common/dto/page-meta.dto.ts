import { ApiProperty, ApiPropertyOptional } from '@nestjs/swagger';

import { SortDirectionEnum } from '../enums/sort-direction.enum';

/**
 * DTO con metadatos de paginación.
 */
export class PageMetaDto {
  /**
   * Página actual.
   */
  @ApiProperty({
    description: 'Página actual.',
    example: 1,
  })
  page!: number;

  /**
   * Cantidad de elementos por página.
   */
  @ApiProperty({
    description: 'Cantidad de elementos por página.',
    example: 20,
  })
  limit!: number;

  /**
   * Total de elementos existentes.
   */
  @ApiProperty({
    description: 'Total de elementos existentes.',
    example: 53,
  })
  totalItems!: number;

  /**
   * Total de páginas disponibles.
   */
  @ApiProperty({
    description: 'Total de páginas disponibles.',
    example: 3,
  })
  totalPages!: number;

  /**
   * Indica si existe una página anterior.
   */
  @ApiProperty({
    description: 'Indica si existe una página anterior.',
    example: false,
  })
  hasPreviousPage!: boolean;

  /**
   * Indica si existe una página siguiente.
   */
  @ApiProperty({
    description: 'Indica si existe una página siguiente.',
    example: true,
  })
  hasNextPage!: boolean;

  /**
   * Campo por el que se ordenó la colección.
   */
  @ApiPropertyOptional({
    description: 'Campo por el que se ordenó la colección.',
    example: 'nombreProducto',
  })
  sortBy?: string;

  /**
   * Dirección del ordenamiento.
   */
  @ApiPropertyOptional({
    description: 'Dirección del ordenamiento.',
    enum: SortDirectionEnum,
    example: SortDirectionEnum.ASC,
  })
  sortDirection?: SortDirectionEnum;

  /**
   * Término libre de búsqueda aplicado.
   */
  @ApiPropertyOptional({
    description: 'Término libre de búsqueda aplicado.',
    example: 'paracetamol',
  })
  query?: string;
}
