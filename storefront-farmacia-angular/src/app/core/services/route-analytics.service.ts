import { Injectable, inject } from '@angular/core';

import { LoggerService } from '../observability/logging/logger.service';

@Injectable({
  providedIn: 'root',
})
export class RouteAnalyticsService {
  private readonly logger = inject(LoggerService);

  trackView(eventName: string, payload?: Record<string, unknown>): void {
    this.logger.debug(`Analytics view: ${eventName}`, payload ?? {});
  }
}
