import { Injectable } from '@nestjs/common';
import { DataSource } from 'typeorm';

/**
 * Servicio mínimo de health para Docker local y diagnósticos rápidos.
 */
@Injectable()
export class HealthService {
  constructor(private readonly dataSource: DataSource) {}

  async check(): Promise<{ status: string; database: string; timestamp: string }> {
    await this.dataSource.query('SELECT 1');
    return {
      status: 'ok',
      database: 'reachable',
      timestamp: new Date().toISOString(),
    };
  }
}
