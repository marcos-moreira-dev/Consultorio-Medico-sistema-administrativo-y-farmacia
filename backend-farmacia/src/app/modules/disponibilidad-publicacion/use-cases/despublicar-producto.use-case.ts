import { Injectable } from '@nestjs/common';

import { DespublicarProductoRequestDto } from '../dto/despublicar-producto.request.dto';
import { EstadoPublicacionResponseDto } from '../dto/estado-publicacion.response.dto';
import { DisponibilidadPublicacionService } from '../services/disponibilidad-publicacion.service';

/**
 * Caso de uso para despublicar un producto.
 */
@Injectable()
export class DespublicarProductoUseCase {
  /**
   * Crea el caso de uso.
   *
   * @param disponibilidadPublicacionService Servicio del módulo.
   */
  constructor(
    private readonly disponibilidadPublicacionService: DisponibilidadPublicacionService,
  ) {}

  /**
   * Ejecuta la despublicación del producto.
   *
   * @param productoId Identificador del producto.
   * @param request DTO opcional con motivo operativo.
   * @returns Estado resultante de publicación.
   */
  async execute(
    productoId: number,
    request: DespublicarProductoRequestDto,
  ): Promise<EstadoPublicacionResponseDto> {
    return this.disponibilidadPublicacionService.despublicar(productoId, request);
  }
}
