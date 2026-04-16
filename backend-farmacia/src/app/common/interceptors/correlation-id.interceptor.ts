import {
  CallHandler,
  ExecutionContext,
  Injectable,
  NestInterceptor,
} from '@nestjs/common';
import { Request, Response } from 'express';
import { Observable } from 'rxjs';

import { AppConstants } from '../constants/app.constants';
import { CorrelationIdUtil } from '../utils/correlation-id.util';

/**
 * Tipo de request HTTP enriquecido con correlationId.
 */
type RequestWithCorrelationId = Request & {
  correlationId?: string;
};

/**
 * Interceptor que garantiza la presencia de un correlationId por solicitud.
 *
 * Si el cliente envía un `x-correlation-id` válido, se reutiliza.
 * En caso contrario, se genera uno nuevo.
 */
@Injectable()
export class CorrelationIdInterceptor implements NestInterceptor {
  /**
   * Inyecta y propaga el correlationId dentro del request/response.
   *
   * @param context Contexto de ejecución.
   * @param next Siguiente manejador de la cadena.
   * @returns Flujo observable sin alterar el cuerpo.
   */
  intercept(context: ExecutionContext, next: CallHandler): Observable<unknown> {
    const httpContext = context.switchToHttp();
    const request = httpContext.getRequest<RequestWithCorrelationId>();
    const response = httpContext.getResponse<Response>();

    const incomingCorrelationId = request.headers[AppConstants.HEADERS.CORRELATION_ID];
    const correlationId = CorrelationIdUtil.resolve(incomingCorrelationId);

    request.correlationId = correlationId;
    response.setHeader(AppConstants.HEADERS.CORRELATION_ID, correlationId);

    return next.handle();
  }
}
