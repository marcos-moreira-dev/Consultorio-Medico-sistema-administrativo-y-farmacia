import { MediaResponseDto } from '../dto/media.response.dto';
import { MediaRecursoEntity } from '../entities/media-recurso.entity';

/**
 * Mapper del módulo de media.
 */
export class MediaMapper {
  private constructor() {}

  /**
   * Convierte una entidad de media a DTO de salida.
   *
   * @param entity Entidad de media.
   * @returns DTO serializable del recurso.
   */
  static toResponseDto(entity: MediaRecursoEntity): MediaResponseDto {
    return {
      mediaRecursoId: entity.mediaRecursoId,
      productoId: entity.productoId ?? null,
      tipoRecurso: entity.tipoRecurso,
      nombreOriginal: entity.nombreOriginal,
      nombreArchivo: entity.nombreArchivo,
      mimeType: entity.mimeType,
      extension: entity.extension,
      tamanoBytes: entity.tamanoBytes,
      rutaRelativa: entity.rutaRelativa,
      urlPublica: entity.urlPublica,
      fechaCreacion: entity.fechaCreacion.toISOString(),
      fechaActualizacion: entity.fechaActualizacion.toISOString(),
    };
  }
}
