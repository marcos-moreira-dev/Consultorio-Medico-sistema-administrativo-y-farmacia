import { Injectable, inject } from '@angular/core';

import { LoggerService } from './logger.service';

/**
 * Registra interacciones UI ligeras del storefront sin imponer una capa de analítica pesada.
 */
@Injectable({
  providedIn: 'root',
})
export class UiEventLogService {
  private readonly logger = inject(LoggerService);

  logInteraction(eventName: string, payload?: Record<string, unknown>): void {
    this.logger.info(`ui:${eventName}`, payload);
  }
}
