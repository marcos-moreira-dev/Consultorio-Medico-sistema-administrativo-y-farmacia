import { Controller, Get } from '@nestjs/common';
import { ApiOkResponse, ApiTags } from '@nestjs/swagger';

import { Public } from '../../security/decorators/public.decorator';
import { HealthService } from './health.service';

/**
 * Controlador público de salud operativa.
 */
@ApiTags('health')
@Controller('health')
export class HealthController {
  constructor(private readonly healthService: HealthService) {}

  /**
   * Verifica que la app y la base respondan.
   */
  @Public()
  @Get()
  @ApiOkResponse({
    description: 'Estado básico del backend y de la base.',
    schema: {
      example: {
        ok: true,
        data: { status: 'ok', database: 'reachable', timestamp: '2026-04-09T21:55:32.490Z' },
      },
    },
  })
  async getHealth() {
    return this.healthService.check();
  }
}
