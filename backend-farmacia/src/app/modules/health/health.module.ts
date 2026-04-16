import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';

import { HealthController } from './health.controller';
import { HealthService } from './health.service';

/**
 * Módulo de verificación de salud operativa del backend.
 */
@Module({
  imports: [TypeOrmModule.forFeature([])],
  controllers: [HealthController],
  providers: [HealthService],
})
export class HealthModule {}
