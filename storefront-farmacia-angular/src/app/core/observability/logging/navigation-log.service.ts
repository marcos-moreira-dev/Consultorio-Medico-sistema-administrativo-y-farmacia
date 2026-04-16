import { Injectable, inject } from '@angular/core';

import { LoggerService } from './logger.service';

/**
 * Centraliza eventos simples de navegación para no dispersar logs de rutas en componentes.
 */
@Injectable({
  providedIn: 'root',
})
export class NavigationLogService {
  private readonly logger = inject(LoggerService);

  logNavigation(destination: string, payload?: Record<string, unknown>): void {
    this.logger.info(`navigation:${destination}`, payload);
  }
}
