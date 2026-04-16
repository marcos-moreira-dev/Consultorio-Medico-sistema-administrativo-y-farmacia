import { Module } from '@nestjs/common';
import { ConfigModule } from '@nestjs/config';

import configuration from './config/configuration';
import { validateEnv } from './config/env.validation';
import { DatabaseModule } from './database/database.module';
import { AuthAdminModule } from './modules/auth-admin/auth-admin.module';
import { CatalogoPublicoModule } from './modules/catalogo-publico/catalogo-publico.module';
import { CategoriasModule } from './modules/categorias/categorias.module';
import { DisponibilidadPublicacionModule } from './modules/disponibilidad-publicacion/disponibilidad-publicacion.module';
import { MediaModule } from './modules/media/media.module';
import { HealthModule } from './modules/health/health.module';
import { ProductosModule } from './modules/productos/productos.module';
import { DemoModule } from './modules/demo/demo.module';

/**
 * Módulo raíz de la aplicación.
 *
 * <p>El DemoModule se importa aquí pero su {@code DemoBootstrapService}
 * verifica internamente el perfil (NODE_ENV) antes de ejecutar seeds,
 * por lo que en producción no inserta datos de prueba.</p>
 */
@Module({
  imports: [
    ConfigModule.forRoot({
      isGlobal: true,
      cache: true,
      load: [configuration],
      validate: validateEnv,
      envFilePath: ['.env.local', '.env'],
    }),
    DatabaseModule,
    AuthAdminModule,
    CategoriasModule,
    ProductosModule,
    MediaModule,
    CatalogoPublicoModule,
    DisponibilidadPublicacionModule,
    HealthModule,
    DemoModule,
  ],
})
export class AppModule {}
