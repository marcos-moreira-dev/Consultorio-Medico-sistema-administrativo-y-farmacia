import { Injectable } from '@nestjs/common';

import { EstadoPublicacionResponseDto } from '../dto/estado-publicacion.response.dto';
import { PublicarProductoRequestDto } from '../dto/publicar-producto.request.dto';
import { DisponibilidadPublicacionService } from '../services/disponibilidad-publicacion.service';

/**
 * Caso de uso para publicar un producto.
 */
@Injectable()
export class PublicarProductoUseCase {
  /**
   * Crea el caso de uso.
   *
   * @param disponibilidadPublicacionService Servicio del módulo.
   */
  constructor(
    private readonly disponibilidadPublicacionService: DisponibilidadPublicacionService,
  ) {}

  /**
   * Ejecuta la publicación del producto.
   *
   * @param productoId Identificador del producto.
   * @param request DTO con el estado inicial visible.
   * @returns Estado resultante de publicación.
   */
  async execute(
    productoId: number,
    request: PublicarProductoRequestDto,
  ): Promise<EstadoPublicacionResponseDto> {
    return this.disponibilidadPublicacionService.publicar(productoId, request);
  }
}
