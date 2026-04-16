import { TipoRecursoMediaEnum } from '../../../src/app/common/enums/tipo-recurso-media.enum';
import { BusinessRuleException } from '../../../src/app/common/exceptions/business-rule.exception';
import { MediaFileValidator } from '../../../src/app/modules/media/validation/media-file.validator';

import { UploadedFileType } from '../../../src/app/common/types/uploaded-file.type';
describe('MediaFileValidator', () => {
  let validator: MediaFileValidator;
  let configService: { get: jest.Mock };

  beforeEach(() => {
    configService = {
      get: jest.fn((key: string, defaultValue?: unknown) => {
        if (key === 'storage.maxFileSizeMb') {
          return 5;
        }

        return defaultValue;
      }),
    };

    validator = new MediaFileValidator(configService as any);
  });

  it('debe aceptar una imagen válida', () => {
    expect(() =>
      validator.validateImageFile({
        originalname: 'paracetamol.png',
        mimetype: 'image/png',
        size: 1024,
        buffer: Buffer.from('fake-image'),
      } as UploadedFileType),
    ).not.toThrow();
  });

  it('debe rechazar cuando no se envía archivo', () => {
    expect(() => validator.validateImageFile(undefined)).toThrow(BusinessRuleException);
  });

  it('debe rechazar tipo MIME no permitido', () => {
    expect(() =>
      validator.validateImageFile({
        originalname: 'archivo.pdf',
        mimetype: 'application/pdf',
        size: 1024,
        buffer: Buffer.from('fake-pdf'),
      } as UploadedFileType),
    ).toThrow(BusinessRuleException);
  });

  it('debe rechazar archivo demasiado grande', () => {
    expect(() =>
      validator.validateImageFile({
        originalname: 'gigante.png',
        mimetype: 'image/png',
        size: 6 * 1024 * 1024,
        buffer: Buffer.from('fake-large-image'),
      } as UploadedFileType),
    ).toThrow(BusinessRuleException);
  });

  it('debe rechazar asociación si la media ya pertenece a otro producto', () => {
    expect(() =>
      validator.ensureCanAssociateMediaToProducto(
        {
          productoId: 1,
          estadoProducto: 'ACTIVO',
        } as any,
        {
          mediaRecursoId: 10,
          productoId: 2,
          tipoRecurso: TipoRecursoMediaEnum.IMAGEN_PRODUCTO,
        } as any,
        null,
      ),
    ).toThrow(BusinessRuleException);
  });

  it('debe rechazar reemplazo cuando el producto no tiene imagen actual', () => {
    expect(() =>
      validator.ensureCanReplaceMediaOfProducto(
        {
          productoId: 1,
          estadoProducto: 'ACTIVO',
        } as any,
        {
          mediaRecursoId: 11,
          productoId: null,
          tipoRecurso: TipoRecursoMediaEnum.IMAGEN_PRODUCTO,
        } as any,
        null,
      ),
    ).toThrow(BusinessRuleException);
  });
});
