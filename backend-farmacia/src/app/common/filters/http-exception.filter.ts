import {
  ArgumentsHost,
  Catch,
  ExceptionFilter,
  HttpException,
  HttpStatus,
} from '@nestjs/common';
import { Request, Response } from 'express';

import { AppConstants } from '../constants/app.constants';
import { ErrorResponseDto } from '../dto/error-response.dto';

/**
 * Tipo de request HTTP enriquecido con correlationId.
 */
type RequestWithCorrelationId = Request & {
  correlationId?: string;
};

/**
 * Filtro global para transformar errores en una respuesta uniforme.
 *
 * Aunque el nombre se centre en HttpException, este filtro también captura
 * errores no controlados y los traduce a un formato estándar.
 */
@Catch()
export class HttpExceptionFilter implements ExceptionFilter {
  /**
   * Intercepta una excepción y escribe una respuesta HTTP estándar.
   *
   * @param exception Excepción capturada.
   * @param host Host de ejecución de Nest.
   */
  catch(exception: unknown, host: ArgumentsHost): void {
    const httpContext = host.switchToHttp();
    const request = httpContext.getRequest<RequestWithCorrelationId>();
    const response = httpContext.getResponse<Response<ErrorResponseDto>>();

    const status = this.resolveStatus(exception);
    const payload = this.buildPayload(exception, request, status);

    response.status(status).json(payload);
  }

  /**
   * Resuelve el código HTTP apropiado para la excepción capturada.
   *
   * @param exception Excepción capturada.
   * @returns Código HTTP resultante.
   */
  private resolveStatus(exception: unknown): number {
    if (exception instanceof HttpException) {
      return exception.getStatus();
    }

    return HttpStatus.INTERNAL_SERVER_ERROR;
  }

  /**
   * Construye el cuerpo uniforme de error.
   *
   * @param exception Excepción capturada.
   * @param request Request actual.
   * @param status Código HTTP final.
   * @returns DTO de error serializable.
   */
  private buildPayload(
    exception: unknown,
    request: RequestWithCorrelationId,
    status: number,
  ): ErrorResponseDto {
    const fallbackError = HttpStatus[status] ?? 'Error';

    let message = 'Ocurrió un error inesperado.';
    let error = fallbackError;
    let code: string | undefined;
    let details: unknown;

    if (exception instanceof HttpException) {
      const exceptionResponse = exception.getResponse();

      if (typeof exceptionResponse === 'string') {
        message = exceptionResponse;
      } else if (this.isRecord(exceptionResponse)) {
        const responseMessage = exceptionResponse.message;
        const responseError = exceptionResponse.error;
        const responseCode = exceptionResponse.code;
        const responseDetails = exceptionResponse.details;

        if (Array.isArray(responseMessage)) {
          message = responseMessage.join(', ');
          details = responseDetails ?? responseMessage;
        } else if (typeof responseMessage === 'string') {
          message = responseMessage;
          details = responseDetails;
        }

        if (typeof responseError === 'string' && responseError.trim().length > 0) {
          error = responseError;
        }

        if (typeof responseCode === 'string' && responseCode.trim().length > 0) {
          code = responseCode;
        }
      }
    } else if (exception instanceof Error) {
      message = exception.message || message;
      error = exception.name || fallbackError;
    }

    return {
      ok: false,
      statusCode: status,
      error,
      message,
      code,
      details,
      path: request.originalUrl ?? request.url,
      correlationId:
        request.correlationId ??
        this.extractCorrelationIdFromHeader(request.headers[AppConstants.HEADERS.CORRELATION_ID]),
      timestamp: new Date().toISOString(),
    };
  }

  /**
   * Verifica si un valor es un objeto indexable.
   *
   * @param value Valor a inspeccionar.
   * @returns true si el valor es un registro simple.
   */
  private isRecord(value: unknown): value is Record<string, unknown> {
    return typeof value === 'object' && value !== null;
  }

  /**
   * Extrae un correlationId válido desde el encabezado HTTP.
   *
   * @param headerValue Valor del encabezado recibido.
   * @returns El correlationId normalizado o undefined.
   */
  private extractCorrelationIdFromHeader(headerValue: string | string[] | undefined): string | undefined {
    if (Array.isArray(headerValue)) {
      return headerValue[0];
    }

    return headerValue;
  }
}
