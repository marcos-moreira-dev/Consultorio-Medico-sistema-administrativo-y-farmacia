import {
  CallHandler,
  ExecutionContext,
  Injectable,
  NestInterceptor,
} from '@nestjs/common';
import { Request } from 'express';
import { Observable, map } from 'rxjs';

import { ApiResponseDto } from '../dto/api-response.dto';

/**
 * Tipo de request HTTP enriquecido con correlationId.
 */
type RequestWithCorrelationId = Request & {
  correlationId?: string;
};

/**
 * Interceptor que envuelve respuestas exitosas en un formato estándar.
 *
 * Reglas:
 * - Si la respuesta ya viene estandarizada, la completa con correlationId
 *   y timestamp si faltan.
 * - Si el controlador retorna un objeto con `data`, `meta` o `message`,
 *   se respeta esa intención y se normaliza.
 * - Si el controlador retorna cualquier otro valor, se envuelve como `data`.
 */
@Injectable()
export class ApiResponseInterceptor implements NestInterceptor {
  /**
   * Intercepta la respuesta del controlador y la normaliza.
   *
   * @param context Contexto de ejecución.
   * @param next Siguiente manejador de la cadena.
   * @returns Observable con la respuesta ya normalizada.
   */
  intercept(context: ExecutionContext, next: CallHandler): Observable<unknown> {
    const request = context.switchToHttp().getRequest<RequestWithCorrelationId>();
    const correlationId = request?.correlationId;

    return next.handle().pipe(
      map((body: unknown) => this.buildResponse(body, correlationId)),
    );
  }

  /**
   * Construye una respuesta estándar a partir del cuerpo devuelto por el controlador.
   *
   * @param body Cuerpo crudo devuelto por el controlador.
   * @param correlationId CorrelationId de la solicitud actual.
   * @returns Respuesta estándar serializable.
   */
  private buildResponse(body: unknown, correlationId?: string): ApiResponseDto {
    const timestamp = new Date().toISOString();

    if (this.isStandardEnvelope(body)) {
      return {
        ...body,
        correlationId: body.correlationId ?? correlationId,
        timestamp: body.timestamp ?? timestamp,
      };
    }

    if (this.isRecord(body) && ('data' in body || 'meta' in body || 'message' in body)) {
      return {
        ok: true,
        message: typeof body.message === 'string' ? body.message : undefined,
        data: 'data' in body ? body.data : undefined,
        meta: this.isRecord(body.meta) ? body.meta : undefined,
        correlationId,
        timestamp,
      };
    }

    return {
      ok: true,
      data: body ?? null,
      correlationId,
      timestamp,
    };
  }

  /**
   * Determina si un valor ya es una respuesta estándar.
   *
   * @param value Valor a inspeccionar.
   * @returns true si el valor ya tiene forma de sobre estándar.
   */
  private isStandardEnvelope(value: unknown): value is ApiResponseDto {
    if (!this.isRecord(value)) {
      return false;
    }

    return typeof value.ok === 'boolean';
  }

  /**
   * Determina si un valor es un objeto indexable.
   *
   * @param value Valor a inspeccionar.
   * @returns true si el valor es un registro simple.
   */
  private isRecord(value: unknown): value is Record<string, any> {
    return typeof value === 'object' && value !== null;
  }
}
