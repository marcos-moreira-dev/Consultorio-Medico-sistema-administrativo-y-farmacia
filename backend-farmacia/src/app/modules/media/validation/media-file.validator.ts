import { Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';

import { TipoRecursoMediaEnum } from '../../../common/enums/tipo-recurso-media.enum';
import { BusinessRuleException } from '../../../common/exceptions/business-rule.exception';
import { UploadedFileType } from '../../../common/types/uploaded-file.type';
import { ProductoEntity } from '../../productos/entities/producto.entity';
import { MediaRecursoEntity } from '../entities/media-recurso.entity';

/**
 * Validador de archivos y reglas de negocio del módulo de media.
 */
@Injectable()
export class MediaFileValidator {
  /**
   * Tipos MIME permitidos para imágenes de producto.
   */
  private static readonly ALLOWED_IMAGE_MIME_TYPES = new Set([
    'image/jpeg',
    'image/png',
    'image/webp',
  ]);

  /**
   * Crea el validador del módulo.
   *
   * @param configService Servicio de configuración global.
   */
  constructor(private readonly configService: ConfigService) {}

  /**
   * Valida un archivo de imagen recibido por multipart.
   *
   * @param file Archivo recibido.
   */
  validateImageFile(file?: UploadedFileType): void {
    if (!file) {
      throw new BusinessRuleException(
        'Debes adjuntar un archivo de imagen.',
        'ARCHIVO_MEDIA_REQUERIDO',
      );
    }

    if (!file.buffer || file.buffer.length === 0) {
      throw new BusinessRuleException(
        'El archivo recibido está vacío o no pudo cargarse en memoria.',
        'ARCHIVO_MEDIA_INVALIDO',
      );
    }

    if (!MediaFileValidator.ALLOWED_IMAGE_MIME_TYPES.has(file.mimetype)) {
      throw new BusinessRuleException(
        'El tipo de archivo no está permitido. Solo se aceptan JPG, PNG o WEBP.',
        'TIPO_ARCHIVO_NO_PERMITIDO',
      );
    }

    const maxFileSizeMb = this.configService.get<number>('storage.maxFileSizeMb', 5);
    const maxFileSizeBytes = maxFileSizeMb * 1024 * 1024;

    if (file.size > maxFileSizeBytes) {
      throw new BusinessRuleException(
        `El archivo supera el tamaño máximo permitido de ${maxFileSizeMb} MB.`,
        'ARCHIVO_EXCEDE_TAMANIO_MAXIMO',
      );
    }
  }

  /**
   * Valida que un recurso de media pueda asociarse a un producto.
   *
   * @param producto Producto destino.
   * @param media Recurso de media a asociar.
   * @param currentMedia Imagen actualmente asociada al producto, si existe.
   */
  ensureCanAssociateMediaToProducto(
    producto: ProductoEntity,
    media: MediaRecursoEntity,
    currentMedia: MediaRecursoEntity | null,
  ): void {
    this.ensureProductoActivo(producto);
    this.ensureIsProductImage(media);

    if (media.productoId && media.productoId !== producto.productoId) {
      throw new BusinessRuleException(
        'El recurso de media ya está asociado a otro producto.',
        'MEDIA_YA_ASOCIADA_A_OTRO_PRODUCTO',
      );
    }

    if (media.productoId === producto.productoId) {
      throw new BusinessRuleException(
        'La imagen indicada ya está asociada a este producto.',
        'MEDIA_YA_ASOCIADA_AL_PRODUCTO',
      );
    }

    if (currentMedia) {
      throw new BusinessRuleException(
        'El producto ya tiene una imagen asociada. Usa el flujo de reemplazo.',
        'PRODUCTO_YA_TIENE_IMAGEN',
      );
    }
  }

  /**
   * Valida que un recurso de media pueda reemplazar la imagen actual del producto.
   *
   * @param producto Producto destino.
   * @param nextMedia Nuevo recurso de media.
   * @param currentMedia Imagen actual asociada al producto, si existe.
   */
  ensureCanReplaceMediaOfProducto(
    producto: ProductoEntity,
    nextMedia: MediaRecursoEntity,
    currentMedia: MediaRecursoEntity | null,
  ): void {
    this.ensureProductoActivo(producto);
    this.ensureIsProductImage(nextMedia);

    if (!currentMedia) {
      throw new BusinessRuleException(
        'El producto no tiene una imagen actual para reemplazar. Usa el flujo de asociación.',
        'PRODUCTO_SIN_IMAGEN_ACTUAL',
      );
    }

    if (nextMedia.productoId && nextMedia.productoId !== producto.productoId) {
      throw new BusinessRuleException(
        'La nueva imagen indicada ya está asociada a otro producto.',
        'MEDIA_YA_ASOCIADA_A_OTRO_PRODUCTO',
      );
    }

    if (currentMedia.mediaRecursoId === nextMedia.mediaRecursoId) {
      throw new BusinessRuleException(
        'La imagen indicada ya es la imagen actual del producto.',
        'REEMPLAZO_SIN_CAMBIO',
      );
    }
  }

  /**
   * Verifica que el producto esté activo.
   *
   * @param producto Producto a inspeccionar.
   */
  private ensureProductoActivo(producto: ProductoEntity): void {
    const estadoProducto = String(producto.estadoProducto).trim().toUpperCase();

    if (estadoProducto !== 'ACTIVO') {
      throw new BusinessRuleException(
        'Solo se pueden manipular imágenes de productos ACTIVO.',
        'MEDIA_REQUIERE_PRODUCTO_ACTIVO',
      );
    }
  }

  /**
   * Verifica que el recurso de media corresponda a imagen de producto.
   *
   * @param media Recurso de media a inspeccionar.
   */
  private ensureIsProductImage(media: MediaRecursoEntity): void {
    const tipoRecurso = String(media.tipoRecurso).trim().toUpperCase();

    if (tipoRecurso !== TipoRecursoMediaEnum.IMAGEN_PRODUCTO) {
      throw new BusinessRuleException(
        'El recurso de media indicado no corresponde a una imagen de producto.',
        'TIPO_MEDIA_INVALIDO',
      );
    }
  }
}
