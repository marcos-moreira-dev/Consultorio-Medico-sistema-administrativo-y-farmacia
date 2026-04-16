import { ApiProperty, ApiPropertyOptional } from '@nestjs/swagger';

/**
 * DTO de salida de un recurso de media.
 */
export class MediaResponseDto {
  /**
   * Identificador principal del recurso de media.
   */
  @ApiProperty({
    description: 'Identificador principal del recurso de media.',
    example: 10,
  })
  mediaRecursoId!: number;

  /**
   * Identificador del producto asociado, si existe.
   */
  @ApiPropertyOptional({
    description: 'Identificador del producto asociado, si existe.',
    example: 25,
  })
  productoId?: number | null;

  /**
   * Tipo de recurso de media.
   */
  @ApiProperty({
    description: 'Tipo de recurso de media.',
    example: 'IMAGEN_PRODUCTO',
  })
  tipoRecurso!: string;

  /**
   * Nombre original del archivo.
   */
  @ApiProperty({
    description: 'Nombre original del archivo.',
    example: 'paracetamol-frontal.png',
  })
  nombreOriginal!: string;

  /**
   * Nombre físico generado para almacenamiento.
   */
  @ApiProperty({
    description: 'Nombre físico generado para almacenamiento.',
    example: '6cfdd4eb-b96f-49f1-8c27-a77ef76fc8df.png',
  })
  nombreArchivo!: string;

  /**
   * MIME type detectado del archivo.
   */
  @ApiProperty({
    description: 'MIME type detectado del archivo.',
    example: 'image/png',
  })
  mimeType!: string;

  /**
   * Extensión normalizada del archivo.
   */
  @ApiProperty({
    description: 'Extensión normalizada del archivo.',
    example: 'png',
  })
  extension!: string;

  /**
   * Tamaño del archivo en bytes.
   */
  @ApiProperty({
    description: 'Tamaño del archivo en bytes.',
    example: 245120,
  })
  tamanoBytes!: number;

  /**
   * Ruta relativa del archivo dentro del storage.
   */
  @ApiProperty({
    description: 'Ruta relativa del archivo dentro del storage.',
    example: 'productos/imagenes/2026/04/6cfdd4eb-b96f-49f1-8c27-a77ef76fc8df.png',
  })
  rutaRelativa!: string;

  /**
   * URL pública calculada del recurso.
   */
  @ApiProperty({
    description: 'URL pública calculada del recurso.',
    example: 'http://localhost:3001/media/productos/imagenes/2026/04/6cfdd4eb-b96f-49f1-8c27-a77ef76fc8df.png',
  })
  urlPublica!: string;

  /**
   * Fecha de creación.
   */
  @ApiProperty({
    description: 'Fecha de creación en formato ISO-8601.',
    example: '2026-04-08T15:30:00.000Z',
  })
  fechaCreacion!: string;

  /**
   * Fecha de última actualización.
   */
  @ApiProperty({
    description: 'Fecha de última actualización en formato ISO-8601.',
    example: '2026-04-08T15:30:00.000Z',
  })
  fechaActualizacion!: string;
}
