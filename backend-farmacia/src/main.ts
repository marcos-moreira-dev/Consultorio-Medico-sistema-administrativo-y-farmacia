import 'reflect-metadata';

import { ValidationPipe } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { NestFactory } from '@nestjs/core';
import { SwaggerModule } from '@nestjs/swagger';
import { static as expressStatic } from 'express';
import { resolve } from 'path';

import { AppModule } from './app/app.module';
import { HttpExceptionFilter } from './app/common/filters/http-exception.filter';
import { ApiResponseInterceptor } from './app/common/interceptors/api-response.interceptor';
import { CorrelationIdInterceptor } from './app/common/interceptors/correlation-id.interceptor';
import { buildSwaggerConfig } from './app/config/swagger.config';

/**
 * Convierte la variable de entorno de orígenes CORS a un valor usable por Nest.
 *
 * Reglas:
 * - Si no hay valor, permite todos los orígenes en local.
 * - Si el valor es "*", permite todos los orígenes.
 * - Si hay una lista separada por comas, la convierte a arreglo limpio.
 *
 * @param rawOrigins Valor crudo de CORS_ALLOWED_ORIGINS.
 * @returns Configuración de origin para enableCors.
 */
function parseCorsOrigins(rawOrigins?: string): true | string[] {
  if (!rawOrigins) {
    return true;
  }

  const normalized = rawOrigins.trim();

  if (normalized === '*') {
    return true;
  }

  const origins = normalized
    .split(',')
    .map((origin) => origin.trim())
    .filter((origin) => origin.length > 0);

  return origins.length > 0 ? origins : true;
}

/**
 * Bootstrap principal del backend de farmacia.
 */
async function bootstrap(): Promise<void> {
  const app = await NestFactory.create(AppModule);
  const configService = app.get(ConfigService);

  const apiPrefix = configService.get<string>('app.apiPrefix', 'api/v1');
  const swaggerPath = configService.get<string>('app.swaggerPath', 'docs');
  const port = configService.get<number>('app.port', 3001);
  const corsAllowedOrigins = configService.get<string>('app.corsAllowedOrigins');
  const storageRoot = configService.get<string>('storage.root', './storage');

  app.enableCors({
    origin: parseCorsOrigins(corsAllowedOrigins),
    credentials: true,
  });

  app.use('/media', expressStatic(resolve(process.cwd(), storageRoot)));

  app.setGlobalPrefix(apiPrefix);

  app.useGlobalPipes(
    new ValidationPipe({
      whitelist: true,
      transform: true,
      forbidNonWhitelisted: true,
    }),
  );

  app.useGlobalInterceptors(
    new CorrelationIdInterceptor(),
    new ApiResponseInterceptor(),
  );

  app.useGlobalFilters(new HttpExceptionFilter());

  const document = SwaggerModule.createDocument(app, buildSwaggerConfig());
  SwaggerModule.setup(swaggerPath, app, document);

  await app.listen(port, '0.0.0.0');
}

void bootstrap();
