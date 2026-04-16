import { ApiProperty } from '@nestjs/swagger';
import { IsIn } from 'class-validator';

import { EstadoDisponibilidadEnum } from '../../../common/enums/estado-disponibilidad.enum';

/**
 * Estados de disponibilidad que sí son visibles en la superficie pública.
 */
export const ESTADOS_DISPONIBILIDAD_PUBLICA = [
  EstadoDisponibilidadEnum.DISPONIBLE,
  EstadoDisponibilidadEnum.AGOTADO,
] as const;

/**
 * DTO de entrada para actualizar la disponibilidad pública de un producto.
 */
export class ActualizarDisponibilidadRequestDto {
  /**
   * Nuevo estado de disponibilidad operativa.
   */
  @ApiProperty({
    description: 'Nuevo estado de disponibilidad operativa.',
    enum: ESTADOS_DISPONIBILIDAD_PUBLICA,
    example: EstadoDisponibilidadEnum.AGOTADO,
  })
  @IsIn(ESTADOS_DISPONIBILIDAD_PUBLICA)
  estadoDisponibilidad!: EstadoDisponibilidadEnum.DISPONIBLE | EstadoDisponibilidadEnum.AGOTADO;
}
