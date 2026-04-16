import { Injectable } from '@nestjs/common';

import { MediaResponseDto } from '../dto/media.response.dto';
import { ReemplazarImagenProductoRequestDto } from '../dto/reemplazar-imagen-producto.request.dto';
import { MediaService } from '../services/media.service';

/**
 * Caso de uso para reemplazar la imagen actual de un producto.
 */
@Injectable()
export class ReemplazarImagenProductoUseCase {
  /**
   * Crea el caso de uso.
   *
   * @param mediaService Servicio del módulo de media.
   */
  constructor(private readonly mediaService: MediaService) {}

  /**
   * Ejecuta el reemplazo de la imagen del producto.
   *
   * @param productoId Identificador del producto.
   * @param request DTO con el nuevo recurso.
   * @returns Recurso asociado tras el reemplazo.
   */
  async execute(
    productoId: number,
    request: ReemplazarImagenProductoRequestDto,
  ): Promise<MediaResponseDto> {
    return this.mediaService.reemplazarImagenProducto(productoId, request);
  }
}
