/**
 * Contrato mínimo del archivo multipart que realmente usa el backend.
 *
 * Se define explícitamente para evitar depender de la ampliación global
 * `Express.Multer.File`, que puede variar según la versión de tipos de Express.
 */
export interface UploadedFileType {
  /** Nombre original recibido del cliente. */
  originalname: string;

  /** MIME type detectado por Nest/Multer. */
  mimetype: string;

  /** Tamaño del archivo en bytes. */
  size: number;

  /** Contenido cargado en memoria. */
  buffer: Buffer;
}
