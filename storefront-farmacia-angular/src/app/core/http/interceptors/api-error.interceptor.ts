import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';

import { ErrorMapper } from '../../errors/error-mapper';
import { LoggerService } from '../../observability/logging/logger.service';

/**
 * Interceptor que captura errores HTTP y los transforma en errores de dominio.
 *
 * <p>Este interceptor es el último de la cadena (va después de loading y
 * trace-context) para que pueda capturar cualquier error que los anteriores
 * no hayan manejado. Convierte {@code HttpErrorResponse} en {@link AppError}
 * usando {@link ErrorMapper}, registra el error en el logger con contexto
 * (código, correlationId, mensaje) y re-lanza el error mapeado para que
 * los componentes lo muestren al usuario.</p>
 *
 * <p>El logging incluye el {@code correlationId} del backend para facilitar
 * la correlación entre logs del frontend y del backend cuando un usuario
 * reporta un problema.</p>
 */
export const apiErrorInterceptor: HttpInterceptorFn = (req, next) => {
  const logger = inject(LoggerService);

  return next(req).pipe(
    catchError((error) => {
      const mappedError = ErrorMapper.fromUnknown(error);

      logger.error(`Fallo HTTP en ${req.method} ${req.urlWithParams}`, {
        code: mappedError.code,
        correlationId: mappedError.correlationId,
        message: mappedError.message,
      });

      return throwError(() => mappedError);
    }),
  );
};
