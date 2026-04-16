import { HttpErrorResponse } from '@angular/common/http';

import { ErrorResponseDto } from '../http/dto/common/error-response.dto';
import { AppError } from './app-error';
import { RemoteApiError } from './remote-api-error';

/**
 * Mapper centralizado que transforma errores HTTP en errores de dominio UI.
 *
 * <p>Este mapper unifica el tratamiento de errores de toda la aplicación:
 * cualquier error que llegue desde la capa HTTP ({@code HttpErrorResponse})
 * se convierte en {@link RemoteApiError} con código, correlationId y detalles
 * extraídos de la respuesta del backend. Los errores que ya son {@link AppError}
 * se re-lanzan sin modificar para evitar pérdida de contexto.</p>
 *
 * <p>El fallback genérico ("No fue posible completar la solicitud") se usa
 * cuando el backend no devuelve un body estructurado o la conexión falla
 * completamente (servidor caído, timeout, offline). Esto evita que el usuario
 * vea mensajes crípticos como "Unknown Error" o "Http failure response".</p>
 */
export class ErrorMapper {
  static fromUnknown(error: unknown): AppError {
    if (error instanceof AppError) {
      return error;
    }

    if (error instanceof HttpErrorResponse) {
      const body = error.error as ErrorResponseDto | undefined;
      const message =
        body?.error?.message ??
        body?.message ??
        'No fue posible completar la solicitud. Intenta nuevamente.';
      const code = body?.error?.code ?? `HTTP_${error.status || 0}`;
      const correlationId = body?.correlationId;

      return new RemoteApiError(message, code, correlationId, body?.error?.details);
    }

    if (error instanceof Error) {
      return new AppError(error.message);
    }

    return new AppError('Ocurrió un error inesperado.');
  }
}
