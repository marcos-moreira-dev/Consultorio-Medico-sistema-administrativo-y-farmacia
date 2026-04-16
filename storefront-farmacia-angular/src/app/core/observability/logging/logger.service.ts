import { Injectable } from '@angular/core';

import { storefrontAppConfig } from '../../config/app.config';

@Injectable({
  providedIn: 'root',
})
export class LoggerService {
  private readonly scope = storefrontAppConfig.technicalName;

  debug(message: string, payload?: unknown): void {
    console.debug(this.formatMessage('debug', message), payload ?? '');
  }

  info(message: string, payload?: unknown): void {
    console.info(this.formatMessage('info', message), payload ?? '');
  }

  warn(message: string, payload?: unknown): void {
    console.warn(this.formatMessage('warn', message), payload ?? '');
  }

  error(message: string, payload?: unknown): void {
    console.error(this.formatMessage('error', message), payload ?? '');
  }

  private formatMessage(level: 'debug' | 'info' | 'warn' | 'error', message: string): string {
    return `[${this.scope}] [${level}] ${message}`;
  }
}
