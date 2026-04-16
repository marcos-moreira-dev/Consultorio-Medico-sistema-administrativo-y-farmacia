import { ApiProperty, ApiPropertyOptional } from '@nestjs/swagger';

/**
 * DTO base para respuestas exitosas del backend.
 *
 * @typeParam TData Tipo de la carga útil principal de la respuesta.
 */
export class ApiResponseDto<TData = unknown> {
  /**
   * Indica si la operación fue exitosa.
   */
  @ApiProperty({
    description: 'Indica si la operación fue exitosa.',
    example: true,
  })
  ok!: boolean;

  /**
   * Mensaje descriptivo opcional.
   */
  @ApiPropertyOptional({
    description: 'Mensaje descriptivo opcional de la operación.',
    example: 'Operación realizada correctamente.',
  })
  message?: string;

  /**
   * Carga útil principal de la respuesta.
   */
  @ApiPropertyOptional({
    description: 'Carga útil principal de la respuesta.',
    type: Object,
  })
  data?: TData;

  /**
   * Metadatos adicionales de la respuesta.
   */
  @ApiPropertyOptional({
    description: 'Metadatos adicionales de la respuesta.',
    type: Object,
  })
  meta?: Record<string, unknown>;

  /**
   * Identificador de correlación para rastreo.
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
