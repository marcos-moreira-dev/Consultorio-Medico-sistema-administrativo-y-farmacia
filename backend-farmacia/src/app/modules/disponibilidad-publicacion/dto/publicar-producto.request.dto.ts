import { ApiPropertyOptional } from '@nestjs/swagger';
import { IsIn, IsOptional, IsString } from 'class-validator';

import { ESTADOS_DISPONIBILIDAD_PUBLICA } from './actualizar-disponibilidad.request.dto';

/**
 * DTO de entrada para publicar un producto.
 */
export class PublicarProductoRequestDto {
  /**
   * Estado inicial de disponibilidad visible al publicar.
   *
   * Si no se envía, el sistema usa `DISPONIBLE`.
   */
  @ApiPropertyOptional({
    description:
      'Estado inicial de disponibilidad visible al publicar. Si no se envía, se usa DISPONIBLE.',
    enum: ESTADOS_DISPONIBILIDAD_PUBLICA,
    example: 'DISPONIBLE',
    default: 'DISPONIBLE',
  })
  @IsOptional()
  @IsString()
  @IsIn(ESTADOS_DISPONIBILIDAD_PUBLICA)
  estadoDisponibilidadInicial?: (typeof ESTADOS_DISPONIBILIDAD_PUBLICA)[number];
}
