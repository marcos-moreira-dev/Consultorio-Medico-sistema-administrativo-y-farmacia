import { Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { mkdir, unlink, writeFile } from 'fs/promises';
import { dirname, extname } from 'path';
import { randomUUID } from 'crypto';

import { UploadedFileType } from '../../../common/types/uploaded-file.type';
import { MediaPathResolver } from './media-path.resolver';

/**
 * Resultado de almacenamiento físico de un archivo de media.
 */
export interface StoredMediaFileResult {
  /** Nombre original recibido del cliente. */
  nombreOriginal: string;

  /** Nombre físico generado para el archivo. */
  nombreArchivo: string;

  /** MIME type detectado del archivo. */
  mimeType: string;

  /** Extensión normalizada del archivo. */
  extension: string;

  /** Tamaño del archivo en bytes. */
  tamanoBytes: number;

  /** Ruta relativa persistible dentro del storage. */
  rutaRelativa: string;

  /** URL pública calculada del recurso. */
  urlPublica: string;
}

/**
 * Proveedor sencillo de almacenamiento en filesystem local.
 */
@Injectable()
export class FilesystemStorageProvider {
  /**
   * Crea el proveedor.
   *
   * @param configService Configuración global del backend.
   * @param mediaPathResolver Resolutor centralizado de rutas y URLs.
   */
  constructor(
    private readonly configService: ConfigService,
    private readonly mediaPathResolver: MediaPathResolver,
  ) {}

  /**
   * Guarda físicamente una imagen de producto dentro del storage local.
   *
   * @param file Archivo recibido por multipart.
   * @returns Resultado serializable del archivo almacenado.
   */
  async saveProductImage(file: UploadedFileType): Promise<StoredMediaFileResult> {
    const extension = this.resolveExtension(file);
    const nombreArchivo = `${randomUUID()}.${extension}`;
    const rutaRelativa = this.mediaPathResolver.resolveProductImageRelativePath(nombreArchivo);

    const storageRoot = this.configService.get<string>('storage.root', './storage');
    const absolutePath = this.mediaPathResolver.resolveAbsoluteStoragePath(
      storageRoot,
      rutaRelativa,
    );

    await mkdir(dirname(absolutePath), { recursive: true });
    await writeFile(absolutePath, file.buffer);

    const mediaBaseUrl = this.configService.get<string>(
      'storage.mediaBaseUrl',
      'http://localhost:3001/media',
    );

    return {
      nombreOriginal: file.originalname,
      nombreArchivo,
      mimeType: file.mimetype,
      extension,
      tamanoBytes: file.size,
      rutaRelativa,
      urlPublica: this.mediaPathResolver.buildPublicUrl(mediaBaseUrl, rutaRelativa),
    };
  }

  /**
   * Elimina físicamente un archivo si existe en disco.
   *
   * @param rutaRelativa Ruta relativa persistida del recurso.
   */
  async deleteByRelativePath(rutaRelativa: string): Promise<void> {
    const storageRoot = this.configService.get<string>('storage.root', './storage');
    const absolutePath = this.mediaPathResolver.resolveAbsoluteStoragePath(
      storageRoot,
      rutaRelativa,
    );

    await unlink(absolutePath).catch(() => undefined);
  }

  /**
   * Determina la extensión final de almacenamiento.
   *
   * Primero intenta usar la extensión del nombre original y, si no existe,
   * cae a una resolución por MIME type.
   *
   * @param file Archivo recibido por multipart.
   * @returns Extensión final sin punto.
   */
  private resolveExtension(file: UploadedFileType): string {
    const originalExtension = extname(file.originalname).replace('.', '').trim().toLowerCase();

    if (originalExtension) {
      return originalExtension;
    }

    const mimeMap: Record<string, string> = {
      'image/jpeg': 'jpg',
      'image/jpg': 'jpg',
      'image/png': 'png',
      'image/webp': 'webp',
    };

    return mimeMap[file.mimetype] ?? 'bin';
  }
}
