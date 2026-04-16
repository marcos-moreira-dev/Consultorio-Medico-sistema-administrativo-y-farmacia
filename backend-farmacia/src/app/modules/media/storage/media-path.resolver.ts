import { Injectable } from '@nestjs/common';
import { join } from 'path';

/**
 * Resolutor de rutas y URLs para recursos de media.
 */
@Injectable()
export class MediaPathResolver {
  /**
   * Construye la ruta relativa de una imagen de producto.
   *
   * Se usa una partición por año y mes para mantener el storage más ordenado.
   *
   * @param nombreArchivo Nombre físico del archivo.
   * @returns Ruta relativa dentro del storage.
   */
  resolveProductImageRelativePath(nombreArchivo: string): string {
    const now = new Date();
    const year = String(now.getUTCFullYear());
    const month = String(now.getUTCMonth() + 1).padStart(2, '0');

    return `productos/imagenes/${year}/${month}/${nombreArchivo}`;
  }

  /**
   * Resuelve la ruta absoluta a partir del storage root y la ruta relativa.
   *
   * @param storageRoot Directorio raíz del storage.
   * @param rutaRelativa Ruta relativa del archivo.
   * @returns Ruta absoluta en el filesystem.
   */
  resolveAbsoluteStoragePath(storageRoot: string, rutaRelativa: string): string {
    return join(storageRoot, rutaRelativa);
  }

  /**
   * Construye la URL pública del recurso a partir de la base pública de media
   * y la ruta relativa.
   *
   * Tolerancia intencional:
   * - si la base ya termina en `/media`, no la duplica;
   * - si la base es la URL pública del backend sin `/media`, la añade.
   *
   * @param mediaBaseUrl URL base pública de media o del backend.
   * @param rutaRelativa Ruta relativa del archivo.
   * @returns URL pública calculada del recurso.
   */
  buildPublicUrl(mediaBaseUrl: string, rutaRelativa: string): string {
    const normalizedBase = mediaBaseUrl.replace(/\\+$/g, '').replace(/\/+$/g, '');
    const normalizedRelative = rutaRelativa.replace(/^\\+/, '').replace(/^\/+/, '');
    const baseWithMedia = /\/media$/i.test(normalizedBase)
      ? normalizedBase
      : `${normalizedBase}/media`;

    return `${baseWithMedia}/${normalizedRelative}`.replace(/\\/g, '/');
  }
}
