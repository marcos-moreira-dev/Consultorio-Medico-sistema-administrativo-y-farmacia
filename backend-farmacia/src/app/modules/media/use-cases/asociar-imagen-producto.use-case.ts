import { Injectable } from '@nestjs/common';

import { AsociarImagenProductoRequestDto } from '../dto/asociar-imagen-producto.request.dto';
import { MediaResponseDto } from '../dto/media.response.dto';
import { MediaService } from '../services/media.service';

/**
 * Caso de uso para asociar una imagen previamente subida a un producto.
 */
@Injectable()
export class AsociarImagenProductoUseCase {
  /**
   * Crea el caso de uso.
   *
   * @param mediaService Servicio del módulo de media.
   */
  constructor(private readonly mediaService: MediaService) {}

  /**
   * Ejecuta la asociación de la imagen al producto.
   *
   * @param productoId Identificador del producto.
   * @param request DTO con el recurso de media.
   * @returns Recurso asociado al producto.
   */
  async execute(
    productoId: number,
    request: AsociarImagenProductoRequestDto,
  ): Promise<MediaResponseDto> {
    return this.mediaService.asociarImagenProducto(productoId, request);
  }
}
