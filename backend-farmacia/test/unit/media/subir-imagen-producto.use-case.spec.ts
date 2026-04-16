import { SubirImagenProductoUseCase } from '../../../src/app/modules/media/use-cases/subir-imagen-producto.use-case';
import { MediaService } from '../../../src/app/modules/media/services/media.service';
import { TipoRecursoMediaEnum } from '../../../src/app/common/enums/tipo-recurso-media.enum';

import { UploadedFileType } from '../../../src/app/common/types/uploaded-file.type';
describe('SubirImagenProductoUseCase', () => {
  let useCase: SubirImagenProductoUseCase;
  let mediaService: jest.Mocked<MediaService>;

  beforeEach(() => {
    mediaService = {
      subirImagenProducto: jest.fn(),
    } as unknown as jest.Mocked<MediaService>;

    useCase = new SubirImagenProductoUseCase(mediaService);
  });

  it('debe delegar la subida al servicio y devolver su resultado', async () => {
    const file = {
      originalname: 'paracetamol.png',
      mimetype: 'image/png',
      size: 245120,
      buffer: Buffer.from('fake-image'),
    } as UploadedFileType;

    const expectedResponse = {
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
      fechaCreacion: '2026-04-08T15:30:00.000Z',
      fechaActualizacion: '2026-04-08T15:30:00.000Z',
    };

    mediaService.subirImagenProducto.mockResolvedValue(expectedResponse);

    const result = await useCase.execute(file);

    expect(mediaService.subirImagenProducto).toHaveBeenCalledTimes(1);
    expect(mediaService.subirImagenProducto).toHaveBeenCalledWith(file);
    expect(result).toEqual(expectedResponse);
  });
});
