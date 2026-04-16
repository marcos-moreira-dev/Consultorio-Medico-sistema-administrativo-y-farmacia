import { DocumentBuilder } from '@nestjs/swagger';

/**
 * Construye la configuración base de Swagger/OpenAPI para el backend.
 *
 * @returns Configuración lista para createDocument.
 */
export function buildSwaggerConfig() {
  return new DocumentBuilder()
    .setTitle('Backend Farmacia')
    .setDescription('API administrativa y pública de farmacia')
    .setVersion('1.0.0')
    .addBearerAuth()
    .build();
}
