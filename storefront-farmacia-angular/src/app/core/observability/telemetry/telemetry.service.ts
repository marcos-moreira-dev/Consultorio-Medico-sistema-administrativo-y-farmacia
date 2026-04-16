import { Injectable, inject } from '@angular/core';

import { LoggerService } from '../logging/logger.service';

/**
 * Punto de entrada mínimo para telemetría del storefront.
 * En V1 funciona como fachada estable mientras se decide una integración más profunda.
 */
@Injectable({
  providedIn: 'root',
})
export class TelemetryService {
  private readonly logger = inject(LoggerService);

  record(eventName: string, payload?: Record<string, unknown>): void {
    this.logger.debug(`telemetry:${eventName}`, payload);
  }
}
