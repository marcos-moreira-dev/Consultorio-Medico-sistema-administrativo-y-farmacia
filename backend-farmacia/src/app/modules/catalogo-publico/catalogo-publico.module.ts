import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';

import { CategoriaEntity } from '../categorias/entities/categoria.entity';
import { MediaRecursoEntity } from '../media/entities/media-recurso.entity';
import { ProductoEntity } from '../productos/entities/producto.entity';
import { CatalogoPublicoController } from './controllers/catalogo-publico.controller';
import { CatalogoPublicoService } from './services/catalogo-publico.service';
import { BuscarCatalogoPublicoUseCase } from './use-cases/buscar-catalogo-publico.use-case';
import { ListarCatalogoPublicoUseCase } from './use-cases/listar-catalogo-publico.use-case';
import { ListarCategoriasPublicasUseCase } from './use-cases/listar-categorias-publicas.use-case';
import { ObtenerProductoPublicoUseCase } from './use-cases/obtener-producto-publico.use-case';

/**
 * Módulo del catálogo público de farmacia.
 *
 * Responsabilidades:
 * - listar productos visibles al público;
 * - buscar productos publicados;
 * - obtener el detalle público de un producto visible;
 * - listar categorías visibles públicamente.
 *
 * Nota: {@code CategoriaEntity} se registra aquí porque el servicio de
 * catálogo hace joins explícitos entre producto y categoría mediante
 * query builder. Sin esta declaración, TypeORM no resuelve la relación
 * dentro de este módulo y lanza "Cannot read properties of undefined
 * (reading 'databaseName')" al construir la consulta.
 */
@Module({
  imports: [TypeOrmModule.forFeature([ProductoEntity, CategoriaEntity, MediaRecursoEntity])],
  controllers: [CatalogoPublicoController],
  providers: [
    CatalogoPublicoService,
    ListarCatalogoPublicoUseCase,
    BuscarCatalogoPublicoUseCase,
    ObtenerProductoPublicoUseCase,
    ListarCategoriasPublicasUseCase,
  ],
  exports: [CatalogoPublicoService],
})
export class CatalogoPublicoModule {}
