import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';

import { TipoRecursoMediaEnum } from '../../../common/enums/tipo-recurso-media.enum';
import { ResourceNotFoundException } from '../../../common/exceptions/resource-not-found.exception';
import { UploadedFileType } from '../../../common/types/uploaded-file.type';
import { ProductoEntity } from '../../productos/entities/producto.entity';
import { MediaMapper } from '../mappers/media.mapper';
import { AsociarImagenProductoRequestDto } from '../dto/asociar-imagen-producto.request.dto';
import { MediaResponseDto } from '../dto/media.response.dto';
import { ReemplazarImagenProductoRequestDto } from '../dto/reemplazar-imagen-producto.request.dto';
import { MediaRecursoEntity } from '../entities/media-recurso.entity';
import { MediaRecursoRepository } from '../repositories/media-recurso.repository';
import { FilesystemStorageProvider } from '../storage/filesystem-storage.provider';
import { MediaFileValidator } from '../validation/media-file.validator';

/**
 * Servicio de aplicación para recursos de media.
 */
@Injectable()
export class MediaService {
  /**
   * Crea el servicio del módulo de media.
   *
   * @param mediaRecursoRepository Repositorio de recursos de media.
   * @param productoOrmRepository Repositorio ORM de productos.
   * @param filesystemStorageProvider Proveedor de almacenamiento físico.
   * @param mediaFileValidator Validador de archivos y reglas del módulo.
   */
  constructor(
    private readonly mediaRecursoRepository: MediaRecursoRepository,
    @InjectRepository(ProductoEntity)
    private readonly productoOrmRepository: Repository<ProductoEntity>,
    private readonly filesystemStorageProvider: FilesystemStorageProvider,
    private readonly mediaFileValidator: MediaFileValidator,
  ) {}

  /**
   * Sube físicamente una imagen de producto y registra su recurso de media.
   *
   * El recurso se crea inicialmente sin asociación a producto.
   *
   * @param file Archivo multipart recibido.
   * @returns Recurso de media persistido.
   */
  async subirImagenProducto(file: UploadedFileType): Promise<MediaResponseDto> {
    this.mediaFileValidator.validateImageFile(file);

    const storedFile = await this.filesystemStorageProvider.saveProductImage(file);

    const media = await this.mediaRecursoRepository.createAndSave({
      productoId: null,
      tipoRecurso: TipoRecursoMediaEnum.IMAGEN_PRODUCTO,
      nombreOriginal: storedFile.nombreOriginal,
      nombreArchivo: storedFile.nombreArchivo,
      mimeType: storedFile.mimeType,
      extension: storedFile.extension,
      tamanoBytes: storedFile.tamanoBytes,
      rutaRelativa: storedFile.rutaRelativa,
      urlPublica: storedFile.urlPublica,
    });

    return MediaMapper.toResponseDto(media);
  }

  /**
   * Asocia una imagen previamente subida a un producto sin imagen actual.
   *
   * @param productoId Identificador del producto.
   * @param request DTO con el recurso de media a asociar.
   * @returns Recurso asociado al producto.
   */
  async asociarImagenProducto(
    productoId: number,
    request: AsociarImagenProductoRequestDto,
  ): Promise<MediaResponseDto> {
    const producto = await this.findProductoOrFail(productoId);
    const media = await this.findMediaOrFail(request.mediaRecursoId);
    const currentMedia = await this.mediaRecursoRepository.findByProductoId(productoId);

    this.mediaFileValidator.ensureCanAssociateMediaToProducto(producto, media, currentMedia);

    media.productoId = producto.productoId;

    const updated = await this.mediaRecursoRepository.save(media);
    const refreshed = await this.mediaRecursoRepository.findById(updated.mediaRecursoId);

    return MediaMapper.toResponseDto(refreshed ?? updated);
  }

  /**
   * Reemplaza la imagen actualmente asociada a un producto.
   *
   * El recurso anterior se desasocia, pero no se elimina físicamente.
   *
   * @param productoId Identificador del producto.
   * @param request DTO con el nuevo recurso.
   * @returns Recurso asociado tras el reemplazo.
   */
  async reemplazarImagenProducto(
    productoId: number,
    request: ReemplazarImagenProductoRequestDto,
  ): Promise<MediaResponseDto> {
    const producto = await this.findProductoOrFail(productoId);
    const nextMedia = await this.findMediaOrFail(request.mediaRecursoId);
    const currentMedia = await this.mediaRecursoRepository.findByProductoId(productoId);

    this.mediaFileValidator.ensureCanReplaceMediaOfProducto(producto, nextMedia, currentMedia);

    if (currentMedia && currentMedia.mediaRecursoId !== nextMedia.mediaRecursoId) {
      currentMedia.productoId = null;
      await this.mediaRecursoRepository.save(currentMedia);
    }

    nextMedia.productoId = producto.productoId;

    const updated = await this.mediaRecursoRepository.save(nextMedia);
    const refreshed = await this.mediaRecursoRepository.findById(updated.mediaRecursoId);

    return MediaMapper.toResponseDto(refreshed ?? updated);
  }

  /**
   * Busca un producto y falla si no existe.
   *
   * @param productoId Identificador del producto.
   * @returns Producto encontrado.
   */
  private async findProductoOrFail(productoId: number): Promise<ProductoEntity> {
    const producto = await this.productoOrmRepository.findOne({
      where: {
        productoId,
      },
    });

    if (!producto) {
      throw new ResourceNotFoundException(
        'No existe el producto indicado para la operación de media.',
        'PRODUCTO_NO_ENCONTRADO',
      );
    }

    return producto;
  }

  /**
   * Busca un recurso de media y falla si no existe.
   *
   * @param mediaRecursoId Identificador principal del recurso.
   * @returns Recurso de media encontrado.
   */
  private async findMediaOrFail(mediaRecursoId: number): Promise<MediaRecursoEntity> {
    const media = await this.mediaRecursoRepository.findById(mediaRecursoId);

    if (!media) {
      throw new ResourceNotFoundException(
        'No existe el recurso de media indicado.',
        'MEDIA_RECURSO_NO_ENCONTRADO',
      );
    }

    return media;
  }
}
