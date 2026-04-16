import { Module } from '@nestjs/common';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { MulterModule } from '@nestjs/platform-express';
import { TypeOrmModule } from '@nestjs/typeorm';
import { memoryStorage } from 'multer';

import { ProductoEntity } from '../productos/entities/producto.entity';
import { MediaController } from './controllers/media.controller';
import { MediaRecursoEntity } from './entities/media-recurso.entity';
import { MediaRecursoRepository } from './repositories/media-recurso.repository';
import { MediaService } from './services/media.service';
import { FilesystemStorageProvider } from './storage/filesystem-storage.provider';
import { MediaPathResolver } from './storage/media-path.resolver';
import { AsociarImagenProductoUseCase } from './use-cases/asociar-imagen-producto.use-case';
import { ReemplazarImagenProductoUseCase } from './use-cases/reemplazar-imagen-producto.use-case';
import { SubirImagenProductoUseCase } from './use-cases/subir-imagen-producto.use-case';
import { MediaFileValidator } from './validation/media-file.validator';

/**
 * Módulo de media para recursos de farmacia.
 *
 * Responsabilidades:
 * - subir archivos de imagen de producto;
 * - asociar una imagen subida a un producto;
 * - reemplazar la imagen asociada de un producto.
 *
 * Decisión operativa relevante:
 * - se fuerza `memoryStorage` para que `file.buffer` exista de forma explícita;
 * - se centraliza el límite de tamaño de carga desde configuración.
 */
@Module({
  imports: [
    ConfigModule,
    MulterModule.registerAsync({
      inject: [ConfigService],
      useFactory: (configService: ConfigService) => {
        const maxFileSizeMb = configService.get<number>('storage.maxFileSizeMb', 5);

        return {
          storage: memoryStorage(),
          limits: {
            fileSize: maxFileSizeMb * 1024 * 1024,
            files: 1,
          },
        };
      },
    }),
    TypeOrmModule.forFeature([MediaRecursoEntity, ProductoEntity]),
  ],
  controllers: [MediaController],
  providers: [
    MediaRecursoRepository,
    MediaPathResolver,
    FilesystemStorageProvider,
    MediaFileValidator,
    MediaService,
    SubirImagenProductoUseCase,
    AsociarImagenProductoUseCase,
    ReemplazarImagenProductoUseCase,
  ],
  exports: [MediaService],
})
export class MediaModule {}
