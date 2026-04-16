import { TipoRecursoMediaEnum } from '../../../src/app/common/enums/tipo-recurso-media.enum';
import { ResourceNotFoundException } from '../../../src/app/common/exceptions/resource-not-found.exception';
import { MediaService } from '../../../src/app/modules/media/services/media.service';
import { MediaRecursoRepository } from '../../../src/app/modules/media/repositories/media-recurso.repository';
import { FilesystemStorageProvider } from '../../../src/app/modules/media/storage/filesystem-storage.provider';
import { MediaFileValidator } from '../../../src/app/modules/media/validation/media-file.validator';

import { UploadedFileType } from '../../../src/app/common/types/uploaded-file.type';
describe('MediaService', () => {
  let service: MediaService;
  let mediaRecursoRepository: jest.Mocked<MediaRecursoRepository>;
  let productoOrmRepository: { findOne: jest.Mock };
  let filesystemStorageProvider: jest.Mocked<FilesystemStorageProvider>;
  let mediaFileValidator: jest.Mocked<MediaFileValidator>;

  beforeEach(() => {
    mediaRecursoRepository = {
      createAndSave: jest.fn(),
      save: jest.fn(),
      findById: jest.fn(),
      findByProductoId: jest.fn(),
    } as unknown as jest.Mocked<MediaRecursoRepository>;

    productoOrmRepository = {
      findOne: jest.fn(),
    };

    filesystemStorageProvider = {
      saveProductImage: jest.fn(),
    } as unknown as jest.Mocked<FilesystemStorageProvider>;

    mediaFileValidator = {
      validateImageFile: jest.fn(),
      ensureCanAssociateMediaToProducto: jest.fn(),
      ensureCanReplaceMediaOfProducto: jest.fn(),
    } as unknown as jest.Mocked<MediaFileValidator>;

    service = new MediaService(
      mediaRecursoRepository,
      productoOrmRepository as any,
      filesystemStorageProvider,
      mediaFileValidator,
    );
  });

  it('debe subir una imagen de producto y persistir el recurso de media', async () => {
    const file = {
      originalname: 'paracetamol.png',
      mimetype: 'image/png',
      size: 245120,
      buffer: Buffer.from('fake-image'),
    } as UploadedFileType;

    filesystemStorageProvider.saveProductImage.mockResolvedValue({
      nombreOriginal: 'paracetamol.png',
      nombreArchivo: 'uuid-demo.png',
      mimeType: 'image/png',
      extension: 'png',
      tamanoBytes: 245120,
      rutaRelativa: 'productos/imagenes/2026/04/uuid-demo.png',
      urlPublica: 'http://localhost:3001/media/productos/imagenes/2026/04/uuid-demo.png',
    });

    mediaRecursoRepository.createAndSave.mockResolvedValue({
      mediaRecursoId: 10,
      productoId: null,
      tipoRecurso: TipoRecursoMediaEnum.IMAGEN_PRODUCTO,
      nombreOriginal: 'paracetamol.png',
      nombreArchivo: 'uuid-demo.png',
      mimeType: 'image/png',
      extension: 'png',
      tamanoBytes: 245120,
      rutaRelativa: 'productos/imagenes/2026/04/uuid-demo.png',
      urlPublica: 'http://localhost:3001/media/productos/imagenes/2026/04/uuid-demo.png',
      fechaCreacion: new Date('2026-04-08T15:30:00.000Z'),
      fechaActualizacion: new Date('2026-04-08T15:30:00.000Z'),
    } as any);

    const result = await service.subirImagenProducto(file);

    expect(mediaFileValidator.validateImageFile).toHaveBeenCalledWith(file);
    expect(filesystemStorageProvider.saveProductImage).toHaveBeenCalledWith(file);
    expect(mediaRecursoRepository.createAndSave).toHaveBeenCalledWith(
      expect.objectContaining({
        productoId: null,
        tipoRecurso: TipoRecursoMediaEnum.IMAGEN_PRODUCTO,
      }),
    );
    expect(result.mediaRecursoId).toBe(10);
  });

  it('debe asociar una imagen subida a un producto', async () => {
    productoOrmRepository.findOne.mockResolvedValue({
      productoId: 1,
      estadoProducto: 'ACTIVO',
    });

    mediaRecursoRepository.findById.mockResolvedValue({
      mediaRecursoId: 10,
      productoId: null,
      tipoRecurso: TipoRecursoMediaEnum.IMAGEN_PRODUCTO,
      nombreOriginal: 'paracetamol.png',
      nombreArchivo: 'uuid-demo.png',
      mimeType: 'image/png',
      extension: 'png',
      tamanoBytes: 245120,
      rutaRelativa: 'productos/imagenes/2026/04/uuid-demo.png',
      urlPublica: 'http://localhost:3001/media/productos/imagenes/2026/04/uuid-demo.png',
      fechaCreacion: new Date('2026-04-08T15:30:00.000Z'),
      fechaActualizacion: new Date('2026-04-08T15:30:00.000Z'),
    } as any);

    mediaRecursoRepository.findByProductoId.mockResolvedValue(null);

    mediaRecursoRepository.save.mockResolvedValue({
      mediaRecursoId: 10,
      productoId: 1,
      tipoRecurso: TipoRecursoMediaEnum.IMAGEN_PRODUCTO,
      nombreOriginal: 'paracetamol.png',
      nombreArchivo: 'uuid-demo.png',
      mimeType: 'image/png',
      extension: 'png',
      tamanoBytes: 245120,
      rutaRelativa: 'productos/imagenes/2026/04/uuid-demo.png',
      urlPublica: 'http://localhost:3001/media/productos/imagenes/2026/04/uuid-demo.png',
      fechaCreacion: new Date('2026-04-08T15:30:00.000Z'),
      fechaActualizacion: new Date('2026-04-08T16:00:00.000Z'),
    } as any);

    mediaRecursoRepository.findById
      .mockResolvedValueOnce({
        mediaRecursoId: 10,
        productoId: null,
        tipoRecurso: TipoRecursoMediaEnum.IMAGEN_PRODUCTO,
        nombreOriginal: 'paracetamol.png',
        nombreArchivo: 'uuid-demo.png',
        mimeType: 'image/png',
        extension: 'png',
        tamanoBytes: 245120,
        rutaRelativa: 'productos/imagenes/2026/04/uuid-demo.png',
        urlPublica: 'http://localhost:3001/media/productos/imagenes/2026/04/uuid-demo.png',
        fechaCreacion: new Date('2026-04-08T15:30:00.000Z'),
        fechaActualizacion: new Date('2026-04-08T15:30:00.000Z'),
      } as any)
      .mockResolvedValueOnce({
        mediaRecursoId: 10,
        productoId: 1,
        tipoRecurso: TipoRecursoMediaEnum.IMAGEN_PRODUCTO,
        nombreOriginal: 'paracetamol.png',
        nombreArchivo: 'uuid-demo.png',
        mimeType: 'image/png',
        extension: 'png',
        tamanoBytes: 245120,
        rutaRelativa: 'productos/imagenes/2026/04/uuid-demo.png',
        urlPublica: 'http://localhost:3001/media/productos/imagenes/2026/04/uuid-demo.png',
        fechaCreacion: new Date('2026-04-08T15:30:00.000Z'),
        fechaActualizacion: new Date('2026-04-08T16:00:00.000Z'),
      } as any);

    const result = await service.asociarImagenProducto(1, {
      mediaRecursoId: 10,
    });

    expect(mediaFileValidator.ensureCanAssociateMediaToProducto).toHaveBeenCalledTimes(1);
    expect(mediaRecursoRepository.save).toHaveBeenCalledWith(
      expect.objectContaining({
        productoId: 1,
      }),
    );
    expect(result.productoId).toBe(1);
  });

  it('debe lanzar error si el producto no existe al asociar media', async () => {
    productoOrmRepository.findOne.mockResolvedValue(null);

    await expect(
      service.asociarImagenProducto(999, { mediaRecursoId: 10 }),
    ).rejects.toBeInstanceOf(ResourceNotFoundException);
  });
});
