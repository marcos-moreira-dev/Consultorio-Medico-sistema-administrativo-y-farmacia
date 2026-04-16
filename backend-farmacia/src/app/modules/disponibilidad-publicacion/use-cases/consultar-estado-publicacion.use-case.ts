import { Injectable } from '@nestjs/common';

import { EstadoPublicacionResponseDto } from '../dto/estado-publicacion.response.dto';
import { DisponibilidadPublicacionService } from '../services/disponibilidad-publicacion.service';

/**
 * Caso de uso para consultar el estado de publicación de un producto.
 */
@Injectable()
export class ConsultarEstadoPublicacionUseCase {
  /**
   * Crea el caso de uso.
   *
   * @param disponibilidadPublicacionService Servicio del módulo.
   */
  constructor(
    private readonly disponibilidadPublicacionService: DisponibilidadPublicacionService,
  ) {}

  /**
   * Ejecuta la consulta del estado de publicación.
   *
   * @param productoId Identificador del producto.
   * @returns Estado actual de publicación.
   */
  async execute(productoId: number): Promise<EstadoPublicacionResponseDto> {
    return this.disponibilidadPublicacionService.consultarEstado(productoId);
  }
}
