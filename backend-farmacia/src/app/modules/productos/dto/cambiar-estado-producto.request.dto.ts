import { ApiProperty } from '@nestjs/swagger';
import { IsEnum } from 'class-validator';

import { EstadoProductoEnum } from '../../../common/enums/estado-producto.enum';

/**
 * DTO de entrada para cambiar el estado lógico del producto.
 */
export class CambiarEstadoProductoRequestDto {
  /**
   * Nuevo estado lógico del producto.
   */
  @ApiProperty({
    description: 'Nuevo estado lógico del producto.',
    enum: EstadoProductoEnum,
    example: EstadoProductoEnum.INACTIVO,
  })
  @IsEnum(EstadoProductoEnum)
  estadoProducto!: EstadoProductoEnum;
}
