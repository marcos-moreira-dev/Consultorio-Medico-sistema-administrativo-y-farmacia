import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';

import { TraceContextService } from '../../observability/tracing/trace-context.service';

/**
 * Interceptor que inyecta headers de trazabilidad en cada request HTTP.
 *
 * <p>Añade {@code X-Trace-Id} y {@code X-Session-Id} a todas las peticiones
 * salientes, permitiendo que el backend correlacione logs, auditoría y
 * diagnósticos con la sesión del usuario actual.</p>
 *
 * <p>Este interceptor es el primero en la cadena (antes de request-id y
 * loading) para que el traceId se genere antes que cualquier otro identificador,
 * sirviendo como ID raíz de la operación completa.</p>
 *
 * <p>Los headers se envían aunque el backend no los use todavía: están
 * preparados para integración futura con OpenTelemetry, logs estructurados
 * y dashboards de observabilidad.</p>
 */
export const traceContextInterceptor: HttpInterceptorFn = (req, next) => {
  const traceContextService = inject(TraceContextService);

  return next(
    req.clone({
      setHeaders: {
        'X-Trace-Id': traceContextService.createTraceId(),
        'X-Session-Id': traceContextService.getSessionId(),
      },
    }),
  );
};
