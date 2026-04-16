import { Module } from '@nestjs/common';

import { ProductosModule } from '../productos/productos.module';
import { DisponibilidadPublicacionController } from './controllers/disponibilidad-publicacion.controller';
import { DisponibilidadPublicacionService } from './services/disponibilidad-publicacion.service';
import { ActualizarDisponibilidadUseCase } from './use-cases/actualizar-disponibilidad.use-case';
import { ConsultarEstadoPublicacionUseCase } from './use-cases/consultar-estado-publicacion.use-case';
import { DespublicarProductoUseCase } from './use-cases/despublicar-producto.use-case';
import { PublicarProductoUseCase } from './use-cases/publicar-producto.use-case';
import { PublicacionRulesValidator } from './validation/publicacion-rules.validator';

/**
 * Módulo encargado de la semántica de publicación y disponibilidad.
 *
 * Este módulo gobierna la parte más delicada del dominio:
 * - publicación pública del producto;
 * - despublicación;
 * - disponibilidad operativa visible;
 * - consulta del estado de publicación.
 */
@Module({
  imports: [ProductosModule],
  controllers: [DisponibilidadPublicacionController],
  providers: [
    PublicacionRulesValidator,
    DisponibilidadPublicacionService,
    ConsultarEstadoPublicacionUseCase,
    PublicarProductoUseCase,
    DespublicarProductoUseCase,
    ActualizarDisponibilidadUseCase,
  ],
  exports: [DisponibilidadPublicacionService],
})
export class DisponibilidadPublicacionModule {}
