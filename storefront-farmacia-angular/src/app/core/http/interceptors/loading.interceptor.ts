import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { finalize } from 'rxjs';

import { LoggerService } from '../../observability/logging/logger.service';
import { LoadingStateService } from '../../services/loading-state.service';

export const loadingInterceptor: HttpInterceptorFn = (req, next) => {
  const logger = inject(LoggerService);
  const loadingStateService = inject(LoadingStateService);
  const startedAt = performance.now();

  loadingStateService.startRequest();

  return next(req).pipe(
    finalize(() => {
      const elapsed = Math.round(performance.now() - startedAt);

      loadingStateService.finishRequest();

      if (elapsed >= 600) {
        logger.info(`Solicitud lenta: ${req.method} ${req.urlWithParams}`, {
          elapsedMs: elapsed,
        });
      }
    }),
  );
};
