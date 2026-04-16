import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';

import { CategoriasController } from './controllers/categorias.controller';
import { CategoriaEntity } from './entities/categoria.entity';
import { CategoriaRepository } from './repositories/categoria.repository';
import { CategoriasService } from './services/categorias.service';
import { ActualizarCategoriaUseCase } from './use-cases/actualizar-categoria.use-case';
import { CrearCategoriaUseCase } from './use-cases/crear-categoria.use-case';
import { ListarCategoriasUseCase } from './use-cases/listar-categorias.use-case';
import { ObtenerCategoriaUseCase } from './use-cases/obtener-categoria.use-case';
import { CategoriaRulesValidator } from './validation/categoria-rules.validator';

/**
 * Módulo del dominio de categorías administrativas de farmacia.
 *
 * Responsabilidades:
 * - crear categorías;
 * - listar categorías con paginación;
 * - obtener una categoría puntual;
 * - actualizar categorías existentes.
 */
@Module({
  imports: [TypeOrmModule.forFeature([CategoriaEntity])],
  controllers: [CategoriasController],
  providers: [
    CategoriaRepository,
    CategoriaRulesValidator,
    CategoriasService,
    CrearCategoriaUseCase,
    ListarCategoriasUseCase,
    ObtenerCategoriaUseCase,
    ActualizarCategoriaUseCase,
  ],
  exports: [CategoriasService, CategoriaRepository],
})
export class CategoriasModule {}
