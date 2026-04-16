import { ApiPropertyOptional } from '@nestjs/swagger';
import { Transform, Type } from 'class-transformer';
import {
  IsBoolean,
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

import { EstadoDisponibilidadEnum } from '../../../common/enums/estado-disponibilidad.enum';
import { EstadoProductoEnum } from '../../../common/enums/estado-producto.enum';
import { SortDirectionEnum } from '../../../common/enums/sort-direction.enum';
import { parseOptionalQueryBoolean } from '../../../common/utils/parse-query-boolean.util';

/**
 * Estados de disponibilidad permitidos en filtros.
 */
export const ESTADOS_DISPONIBILIDAD = [
  EstadoDisponibilidadEnum.DISPONIBLE,
  EstadoDisponibilidadEnum.AGOTADO,
  EstadoDisponibilidadEnum.NO_PUBLICADO,
] as const;

/**
 * Estados lógicos permitidos para filtros de producto.
 */
export const ESTADOS_PRODUCTO = [
  EstadoProductoEnum.ACTIVO,
  EstadoProductoEnum.INACTIVO,
] as const;

/**
 * DTO de query para listado paginado de productos.
 */
export class ListarProductosQueryDto {
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
   * Filtro por estado lógico.
   */
  @ApiPropertyOptional({
    description: 'Filtra por estado lógico del producto.',
    enum: EstadoProductoEnum,
    example: EstadoProductoEnum.ACTIVO,
  })
  @IsOptional()
  @IsEnum(EstadoProductoEnum)
  estadoProducto?: EstadoProductoEnum;

  /**
   * Filtro por bandera de publicación.
   */
  @ApiPropertyOptional({
    description: 'Filtra por bandera de publicación.',
    example: true,
  })
  @IsOptional()
  @Transform(({ value }) => parseOptionalQueryBoolean(value))
  @IsBoolean()
  esPublicable?: boolean;

  /**
   * Filtro por estado de disponibilidad.
   */
  @ApiPropertyOptional({
    description: 'Filtra por estado de disponibilidad.',
    enum: EstadoDisponibilidadEnum,
    example: EstadoDisponibilidadEnum.DISPONIBLE,
  })
  @IsOptional()
  @IsEnum(EstadoDisponibilidadEnum)
  estadoDisponibilidad?: EstadoDisponibilidadEnum;

  /**
   * Campo de ordenamiento.
   */
  @ApiPropertyOptional({
    description: 'Campo por el que se ordena la colección.',
    example: 'nombreProducto',
    enum: [
      'nombreProducto',
      'precioVisible',
      'fechaCreacion',
      'fechaActualizacion',
    ],
  })
  @IsOptional()
  @IsIn(['nombreProducto', 'precioVisible', 'fechaCreacion', 'fechaActualizacion'])
  sortBy?: 'nombreProducto' | 'precioVisible' | 'fechaCreacion' | 'fechaActualizacion';

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
