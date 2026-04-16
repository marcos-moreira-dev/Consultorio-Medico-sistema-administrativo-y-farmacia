import { ApiPropertyOptional } from '@nestjs/swagger';
import { Type } from 'class-transformer';
import {
  IsEnum,
  IsIn,
  IsInt,
  IsNumber,
  IsOptional,
  IsString,
  Max,
  MaxLength,
  Min,
} from 'class-validator';

import { SortDirectionEnum } from '../../../common/enums/sort-direction.enum';

/**
 * DTO de query para listado y búsqueda del catálogo público.
 */
export class BuscarCatalogoQueryDto {
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
   * Cantidad máxima de elementos por página.
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
   * Término libre de búsqueda.
   */
  @ApiPropertyOptional({
    description:
      'Término libre de búsqueda aplicado sobre nombre, presentación, descripción y categoría.',
    example: 'paracetamol',
    maxLength: 100,
  })
  @IsOptional()
  @IsString()
  @MaxLength(100)
  q?: string;

  /**
   * Filtro por categoría.
   */
  @ApiPropertyOptional({
    description: 'Filtra por identificador de categoría.',
    example: 1,
  })
  @IsOptional()
  @Type(() => Number)
  @IsNumber({ maxDecimalPlaces: 0 })
  @Min(1)
  categoriaId?: number;

  /**
   * Campo de ordenamiento.
   */
  @ApiPropertyOptional({
    description: 'Campo por el que se ordena el catálogo.',
    enum: ['nombreProducto', 'precioVisible', 'fechaActualizacion'],
    example: 'nombreProducto',
  })
  @IsOptional()
  @IsIn(['nombreProducto', 'precioVisible', 'fechaActualizacion'])
  sortBy?: 'nombreProducto' | 'precioVisible' | 'fechaActualizacion';

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
