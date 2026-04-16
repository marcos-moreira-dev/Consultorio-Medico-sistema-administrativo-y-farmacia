import { ApiPropertyOptional } from '@nestjs/swagger';
import { Type } from 'class-transformer';
import {
  IsEnum,
  IsIn,
  IsInt,
  IsOptional,
  IsString,
  Max,
  MaxLength,
  Min,
} from 'class-validator';

import { SortDirectionEnum } from '../../../common/enums/sort-direction.enum';

/**
 * DTO de query para listado paginado de categorías.
 */
export class ListarCategoriasQueryDto {
  /**
   * Número de página.
   */
  @ApiPropertyOptional({
    description: 'Número de página.',
    example: 1,
    default: 1,
  })
  @IsOptional()
  @Type(() => Number)
  @IsInt()
  @Min(1)
  page?: number;

  /**
   * Tamaño de página.
   */
  @ApiPropertyOptional({
    description: 'Cantidad máxima de elementos por página.',
    example: 20,
    default: 20,
  })
  @IsOptional()
  @Type(() => Number)
  @IsInt()
  @Min(1)
  @Max(100)
  limit?: number;

  /**
   * Término de búsqueda libre.
   */
  @ApiPropertyOptional({
    description: 'Término libre de búsqueda aplicado sobre nombre y descripción.',
    example: 'analg',
    maxLength: 100,
  })
  @IsOptional()
  @IsString()
  @MaxLength(100)
  q?: string;

  /**
   * Campo de ordenamiento.
   */
  @ApiPropertyOptional({
    description: 'Campo por el que se ordena la colección.',
    example: 'nombreCategoria',
    enum: ['nombreCategoria', 'fechaCreacion', 'fechaActualizacion'],
  })
  @IsOptional()
  @IsIn(['nombreCategoria', 'fechaCreacion', 'fechaActualizacion'])
  sortBy?: 'nombreCategoria' | 'fechaCreacion' | 'fechaActualizacion';

  /**
   * Dirección del ordenamiento.
   */
  @ApiPropertyOptional({
    description: 'Dirección del ordenamiento.',
    enum: SortDirectionEnum,
    example: SortDirectionEnum.ASC,
  })
  @IsOptional()
  @IsEnum(SortDirectionEnum)
  sortDirection?: SortDirectionEnum;
}
