import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';

import { CategoriaEntity } from '../categorias/entities/categoria.entity';
import { ProductosController } from './controllers/productos.controller';
import { ProductoEntity } from './entities/producto.entity';
import { ProductoRepository } from './repositories/producto.repository';
import { ProductosService } from './services/productos.service';
import { ActualizarProductoUseCase } from './use-cases/actualizar-producto.use-case';
import { CambiarEstadoProductoUseCase } from './use-cases/cambiar-estado-producto.use-case';
import { CrearProductoUseCase } from './use-cases/crear-producto.use-case';
import { ListarProductosUseCase } from './use-cases/listar-productos.use-case';
import { ObtenerProductoUseCase } from './use-cases/obtener-producto.use-case';
import { ProductoRulesValidator } from './validation/producto-rules.validator';

/**
 * Módulo del dominio de productos administrativos de farmacia.
 *
 * Responsabilidades:
 * - crear productos;
 * - listar productos con paginación y filtros;
 * - obtener un producto puntual;
 * - actualizar datos base del producto;
 * - cambiar el estado lógico del producto.
 */
@Module({
  imports: [TypeOrmModule.forFeature([ProductoEntity, CategoriaEntity])],
  controllers: [ProductosController],
  providers: [
    ProductoRepository,
    ProductoRulesValidator,
    ProductosService,
    CrearProductoUseCase,
    ListarProductosUseCase,
    ObtenerProductoUseCase,
    ActualizarProductoUseCase,
    CambiarEstadoProductoUseCase,
  ],
  exports: [ProductosService, ProductoRepository],
})
export class ProductosModule {}
