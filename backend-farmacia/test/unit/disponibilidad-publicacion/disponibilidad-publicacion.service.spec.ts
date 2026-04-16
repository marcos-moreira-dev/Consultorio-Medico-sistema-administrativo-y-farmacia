import { EstadoDisponibilidadEnum } from '../../../src/app/common/enums/estado-disponibilidad.enum';
import { EstadoProductoEnum } from '../../../src/app/common/enums/estado-producto.enum';
import { ResourceNotFoundException } from '../../../src/app/common/exceptions/resource-not-found.exception';
import { DisponibilidadPublicacionService } from '../../../src/app/modules/disponibilidad-publicacion/services/disponibilidad-publicacion.service';
import { ProductoRepository } from '../../../src/app/modules/productos/repositories/producto.repository';
import { PublicacionRulesValidator } from '../../../src/app/modules/disponibilidad-publicacion/validation/publicacion-rules.validator';

describe('DisponibilidadPublicacionService', () => {
  let service: DisponibilidadPublicacionService;
  let productoRepository: jest.Mocked<ProductoRepository>;
  let publicacionRulesValidator: jest.Mocked<PublicacionRulesValidator>;

  beforeEach(() => {
    productoRepository = {
      findById: jest.fn(),
      save: jest.fn(),
    } as unknown as jest.Mocked<ProductoRepository>;

    publicacionRulesValidator = {
      ensureCanPublish: jest.fn(),
      ensureCanUnpublish: jest.fn(),
      ensureCanUpdateDisponibilidad: jest.fn(),
    } as unknown as jest.Mocked<PublicacionRulesValidator>;

    service = new DisponibilidadPublicacionService(
      productoRepository,
      publicacionRulesValidator,
    );
  });

  it('debe publicar un producto con DISPONIBLE por defecto', async () => {
    productoRepository.findById
      .mockResolvedValueOnce({
        productoId: 1,
        estadoProducto: EstadoProductoEnum.ACTIVO,
        esPublicable: false,
        estadoDisponibilidad: EstadoDisponibilidadEnum.NO_PUBLICADO,
        fechaActualizacion: new Date('2026-04-08T15:30:00.000Z'),
      } as any)
      .mockResolvedValueOnce({
        productoId: 1,
        estadoProducto: EstadoProductoEnum.ACTIVO,
        esPublicable: true,
        estadoDisponibilidad: EstadoDisponibilidadEnum.DISPONIBLE,
        fechaActualizacion: new Date('2026-04-08T16:00:00.000Z'),
      } as any);

    productoRepository.save.mockResolvedValue({
      productoId: 1,
      estadoProducto: EstadoProductoEnum.ACTIVO,
      esPublicable: true,
      estadoDisponibilidad: EstadoDisponibilidadEnum.DISPONIBLE,
      fechaActualizacion: new Date('2026-04-08T16:00:00.000Z'),
    } as any);

    const result = await service.publicar(1, {});

    expect(publicacionRulesValidator.ensureCanPublish).toHaveBeenCalledWith(
      expect.objectContaining({
        productoId: 1,
      }),
      EstadoDisponibilidadEnum.DISPONIBLE,
    );
    expect(productoRepository.save).toHaveBeenCalledWith(
      expect.objectContaining({
        esPublicable: true,
        estadoDisponibilidad: EstadoDisponibilidadEnum.DISPONIBLE,
      }),
    );
    expect(result.estaPublicadoPublicamente).toBe(true);
  });

  it('debe despublicar un producto forzando NO_PUBLICADO', async () => {
    productoRepository.findById
      .mockResolvedValueOnce({
        productoId: 1,
        estadoProducto: EstadoProductoEnum.ACTIVO,
        esPublicable: true,
        estadoDisponibilidad: EstadoDisponibilidadEnum.DISPONIBLE,
        fechaActualizacion: new Date('2026-04-08T15:30:00.000Z'),
      } as any)
      .mockResolvedValueOnce({
        productoId: 1,
        estadoProducto: EstadoProductoEnum.ACTIVO,
        esPublicable: false,
        estadoDisponibilidad: EstadoDisponibilidadEnum.NO_PUBLICADO,
        fechaActualizacion: new Date('2026-04-08T16:00:00.000Z'),
      } as any);

    productoRepository.save.mockResolvedValue({
      productoId: 1,
      estadoProducto: EstadoProductoEnum.ACTIVO,
      esPublicable: false,
      estadoDisponibilidad: EstadoDisponibilidadEnum.NO_PUBLICADO,
      fechaActualizacion: new Date('2026-04-08T16:00:00.000Z'),
    } as any);

    const result = await service.despublicar(1, {});

    expect(publicacionRulesValidator.ensureCanUnpublish).toHaveBeenCalledTimes(1);
    expect(productoRepository.save).toHaveBeenCalledWith(
      expect.objectContaining({
        esPublicable: false,
        estadoDisponibilidad: EstadoDisponibilidadEnum.NO_PUBLICADO,
      }),
    );
    expect(result.estaPublicadoPublicamente).toBe(false);
  });

  it('debe actualizar disponibilidad visible de un producto publicado', async () => {
    productoRepository.findById
      .mockResolvedValueOnce({
        productoId: 1,
        estadoProducto: EstadoProductoEnum.ACTIVO,
        esPublicable: true,
        estadoDisponibilidad: EstadoDisponibilidadEnum.DISPONIBLE,
        fechaActualizacion: new Date('2026-04-08T15:30:00.000Z'),
      } as any)
      .mockResolvedValueOnce({
        productoId: 1,
        estadoProducto: EstadoProductoEnum.ACTIVO,
        esPublicable: true,
        estadoDisponibilidad: EstadoDisponibilidadEnum.AGOTADO,
        fechaActualizacion: new Date('2026-04-08T16:00:00.000Z'),
      } as any);

    productoRepository.save.mockResolvedValue({
      productoId: 1,
      estadoProducto: EstadoProductoEnum.ACTIVO,
      esPublicable: true,
      estadoDisponibilidad: EstadoDisponibilidadEnum.AGOTADO,
      fechaActualizacion: new Date('2026-04-08T16:00:00.000Z'),
    } as any);

    const result = await service.actualizarDisponibilidad(1, {
      estadoDisponibilidad: EstadoDisponibilidadEnum.AGOTADO,
    });

    expect(publicacionRulesValidator.ensureCanUpdateDisponibilidad).toHaveBeenCalledTimes(1);
    expect(result.estadoDisponibilidad).toBe(EstadoDisponibilidadEnum.AGOTADO);
    expect(result.estaPublicadoPublicamente).toBe(true);
  });

  it('debe lanzar error si el producto no existe', async () => {
    productoRepository.findById.mockResolvedValue(null);

    await expect(service.consultarEstado(999)).rejects.toBeInstanceOf(
      ResourceNotFoundException,
    );
  });
});
