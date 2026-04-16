import { Injectable } from '@nestjs/common';

import { UploadedFileType } from '../../../common/types/uploaded-file.type';
import { MediaResponseDto } from '../dto/media.response.dto';
import { MediaService } from '../services/media.service';

/**
 * Caso de uso para subir una imagen de producto.
 */
@Injectable()
export class SubirImagenProductoUseCase {
  /**
   * Crea el caso de uso.
   *
   * @param mediaService Servicio del módulo de media.
   */
  constructor(private readonly mediaService: MediaService) {}

  /**
   * Ejecuta la subida física y el registro del recurso de media.
   *
   * @param file Archivo multipart recibido.
   * @returns Recurso de media persistido.
   */
  async execute(file: UploadedFileType): Promise<MediaResponseDto> {
    return this.mediaService.subirImagenProducto(file);
  }
}
