import { Injectable } from '@nestjs/common';

import { ActualizarDisponibilidadRequestDto } from '../dto/actualizar-disponibilidad.request.dto';
import { EstadoPublicacionResponseDto } from '../dto/estado-publicacion.response.dto';
import { DisponibilidadPublicacionService } from '../services/disponibilidad-publicacion.service';

/**
 * Caso de uso para actualizar la disponibilidad operativa del producto.
 */
@Injectable()
export class ActualizarDisponibilidadUseCase {
  /**
   * Crea el caso de uso.
   *
   * @param disponibilidadPublicacionService Servicio del módulo.
   */
  constructor(
    private readonly disponibilidadPublicacionService: DisponibilidadPublicacionService,
  ) {}

  /**
   * Ejecuta la actualización de disponibilidad.
   *
   * @param productoId Identificador del producto.
   * @param request DTO con el nuevo estado de disponibilidad.
   * @returns Estado resultante de publicación.
   */
  async execute(
    productoId: number,
    request: ActualizarDisponibilidadRequestDto,
  ): Promise<EstadoPublicacionResponseDto> {
    return this.disponibilidadPublicacionService.actualizarDisponibilidad(
      productoId,
      request,
    );
  }
}
