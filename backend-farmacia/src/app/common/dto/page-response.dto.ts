import { ApiProperty, ApiPropertyOptional } from '@nestjs/swagger';

import { PageMetaDto } from './page-meta.dto';

/**
 * DTO estándar para respuestas paginadas.
 *
 * @typeParam TItem Tipo de cada elemento de la colección devuelta.
 */
export class PageResponseDto<TItem = unknown> {
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
    example: 'Consulta paginada realizada correctamente.',
  })
  message?: string;

  /**
   * Colección paginada resultante.
   */
  @ApiProperty({
    description: 'Colección paginada resultante.',
    type: [Object],
  })
  data!: TItem[];

  /**
   * Metadatos de paginación.
   */
  @ApiProperty({
    description: 'Metadatos de paginación.',
    type: () => PageMetaDto,
  })
  meta!: PageMetaDto;

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
