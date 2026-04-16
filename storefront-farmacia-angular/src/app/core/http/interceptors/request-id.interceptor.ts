import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';

import { TraceContextService } from '../../observability/tracing/trace-context.service';

export const requestIdInterceptor: HttpInterceptorFn = (req, next) => {
  const traceContextService = inject(TraceContextService);
  const requestId = traceContextService.createRequestId();

  return next(
    req.clone({
      setHeaders: {
        'X-Request-Id': requestId,
      },
    }),
  );
};
