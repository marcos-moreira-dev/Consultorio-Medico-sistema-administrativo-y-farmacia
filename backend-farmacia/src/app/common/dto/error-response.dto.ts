import { ApiProperty, ApiPropertyOptional } from '@nestjs/swagger';

/**
 * DTO estándar para respuestas de error del backend.
 */
export class ErrorResponseDto {
  /**
   * Indica si la operación fue exitosa.
   *
   * En respuestas de error siempre será false.
   */
  @ApiProperty({
    description: 'Indica si la operación fue exitosa.',
    example: false,
  })
  ok!: false;

  /**
   * Código HTTP de la respuesta.
   */
  @ApiProperty({
    description: 'Código HTTP asociado al error.',
    example: 404,
  })
  statusCode!: number;

  /**
   * Tipo corto de error.
   */
  @ApiProperty({
    description: 'Tipo corto de error.',
    example: 'Not Found',
  })
  error!: string;

  /**
   * Mensaje principal del error.
   */
  @ApiProperty({
    description: 'Mensaje principal del error.',
    example: 'No se encontró el recurso solicitado.',
  })
  message!: string;

  /**
   * Código de negocio opcional para clasificar errores de forma estable.
   */
  @ApiPropertyOptional({
    description: 'Código de negocio opcional del error.',
    example: 'RESOURCE_NOT_FOUND',
  })
  code?: string;

  /**
   * Detalles opcionales del error.
   *
   * Puede contener listas de validación, contexto técnico seguro o metadatos.
   */
  @ApiPropertyOptional({
    description: 'Detalles opcionales del error.',
    type: Object,
  })
  details?: unknown;

  /**
   * Ruta HTTP que originó el error.
   */
  @ApiProperty({
    description: 'Ruta de la solicitud que originó el error.',
    example: '/api/v1/productos/999',
  })
  path!: string;

  /**
   * Identificador de correlación para trazabilidad.
   */
  @ApiPropertyOptional({
    description: 'Identificador de correlación de la solicitud.',
    example: '5d0672fd-d7bc-4b2b-a7d8-a0f1d4fa2f1c',
  })
  correlationId?: string;

  /**
   * Marca temporal en formato ISO-8601.
   */
  @ApiProperty({
    description: 'Marca temporal de la respuesta en formato ISO-8601.',
    example: '2026-04-08T15:30:00.000Z',
  })
  timestamp!: string;
}
