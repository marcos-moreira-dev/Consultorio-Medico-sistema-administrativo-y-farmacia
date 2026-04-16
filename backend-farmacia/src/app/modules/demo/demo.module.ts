import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';

import { UsuarioAdminEntity } from '../auth-admin/entities/usuario-admin.entity';
import { CategoriaEntity } from '../categorias/entities/categoria.entity';
import { ProductoEntity } from '../productos/entities/producto.entity';
import { DemoBootstrapService } from './demo-bootstrap.service';

@Module({
  imports: [TypeOrmModule.forFeature([CategoriaEntity, ProductoEntity, UsuarioAdminEntity])],
  providers: [DemoBootstrapService],
})
export class DemoModule {}
