import { EstadoDisponibilidadEnum } from '../../../src/app/common/enums/estado-disponibilidad.enum';
import { EstadoProductoEnum } from '../../../src/app/common/enums/estado-producto.enum';
import { SortDirectionEnum } from '../../../src/app/common/enums/sort-direction.enum';
import { ResourceNotFoundException } from '../../../src/app/common/exceptions/resource-not-found.exception';
import { ProductosService } from '../../../src/app/modules/productos/services/productos.service';
import { ProductoRepository } from '../../../src/app/modules/productos/repositories/producto.repository';
import { ProductoRulesValidator } from '../../../src/app/modules/productos/validation/producto-rules.validator';

describe('ProductosService', () => {
  let service: ProductosService;
  let productoRepository: jest.Mocked<ProductoRepository>;
  let productoRulesValidator: jest.Mocked<ProductoRulesValidator>;
  let categoriaRepository: jest.Mocked<any>;

  beforeEach(() => {
    productoRepository = {
      createAndSave: jest.fn(),
      save: jest.fn(),
      findById: jest.fn(),
      findPaginated: jest.fn(),
    } as unknown as jest.Mocked<ProductoRepository>;

    productoRulesValidator = {
      ensureCanCreate: jest.fn(),
      ensureCanUpdate: jest.fn(),
      ensureCanChangeEstado: jest.fn(),
    } as unknown as jest.Mocked<ProductoRulesValidator>;

    categoriaRepository = {
      findByIds: jest.fn().mockResolvedValue([]),
    };

    service = new ProductosService(productoRepository, productoRulesValidator, categoriaRepository);
  });

  it('debe crear un producto con estado interno inicial coherente', async () => {
    productoRepository.createAndSave.mockResolvedValue({
      productoId: 1,
    } as any);

    productoRepository.findById.mockResolvedValue({
      productoId: 1,
      categoriaId: 1,
      nombreProducto: 'Paracetamol',
      presentacion: 'Caja x 20 tabletas',
      descripcionBreve: 'Analgésico',
      precioVisible: 3.5,
      estadoProducto: EstadoProductoEnum.ACTIVO,
      esPublicable: false,
      estadoDisponibilidad: EstadoDisponibilidadEnum.NO_PUBLICADO,
      fechaCreacion: new Date('2026-04-08T15:30:00.000Z'),
      fechaActualizacion: new Date('2026-04-08T15:30:00.000Z'),
      categoria: {
        categoriaId: 1,
        nombreCategoria: 'Analgésicos',
      },
    } as any);

    const result = await service.crear({
      categoriaId: 1,
      nombreProducto: '  Paracetamol ',
      presentacion: ' Caja x 20 tabletas ',
      descripcionBreve: ' Analgésico ',
      precioVisible: 3.5,
    });

    expect(productoRulesValidator.ensureCanCreate).toHaveBeenCalledTimes(1);
    expect(productoRepository.createAndSave).toHaveBeenCalledWith({
      categoriaId: 1,
      nombreProducto: 'Paracetamol',
      presentacion: 'Caja x 20 tabletas',
      descripcionBreve: 'Analgésico',
      precioVisible: 3.5,
      estadoProducto: EstadoProductoEnum.ACTIVO,
      esPublicable: false,
      estadoDisponibilidad: EstadoDisponibilidadEnum.NO_PUBLICADO,
    });
    expect(result.estadoProducto).toBe(EstadoProductoEnum.ACTIVO);
    expect(result.esPublicable).toBe(false);
    expect(result.estadoDisponibilidad).toBe(EstadoDisponibilidadEnum.NO_PUBLICADO);
  });

  it('debe lanzar error si el producto no existe al obtener por id', async () => {
    productoRepository.findById.mockResolvedValue(null);

    await expect(service.obtenerPorId(999)).rejects.toBeInstanceOf(
      ResourceNotFoundException,
    );
  });

  it('debe forzar despublicación al cambiar estado a INACTIVO', async () => {
    productoRepository.findById
      .mockResolvedValueOnce({
        productoId: 1,
        categoriaId: 1,
        nombreProducto: 'Paracetamol',
        presentacion: 'Caja x 20 tabletas',
        descripcionBreve: 'Analgésico',
        precioVisible: 3.5,
        estadoProducto: EstadoProductoEnum.ACTIVO,
        esPublicable: true,
        estadoDisponibilidad: EstadoDisponibilidadEnum.DISPONIBLE,
        fechaCreacion: new Date('2026-04-08T15:30:00.000Z'),
        fechaActualizacion: new Date('2026-04-08T15:30:00.000Z'),
      } as any)
      .mockResolvedValueOnce({
        productoId: 1,
        categoriaId: 1,
        nombreProducto: 'Paracetamol',
        presentacion: 'Caja x 20 tabletas',
        descripcionBreve: 'Analgésico',
        precioVisible: 3.5,
        estadoProducto: EstadoProductoEnum.INACTIVO,
        esPublicable: false,
        estadoDisponibilidad: EstadoDisponibilidadEnum.NO_PUBLICADO,
        fechaCreacion: new Date('2026-04-08T15:30:00.000Z'),
        fechaActualizacion: new Date('2026-04-08T16:00:00.000Z'),
      } as any);

    productoRepository.save.mockResolvedValue({
      productoId: 1,
      categoriaId: 1,
      nombreProducto: 'Paracetamol',
      presentacion: 'Caja x 20 tabletas',
      descripcionBreve: 'Analgésico',
      precioVisible: 3.5,
      estadoProducto: EstadoProductoEnum.INACTIVO,
      esPublicable: false,
      estadoDisponibilidad: EstadoDisponibilidadEnum.NO_PUBLICADO,
      fechaCreacion: new Date('2026-04-08T15:30:00.000Z'),
      fechaActualizacion: new Date('2026-04-08T16:00:00.000Z'),
    } as any);

    const result = await service.cambiarEstado(1, {
      estadoProducto: EstadoProductoEnum.INACTIVO,
    });

    expect(productoRulesValidator.ensureCanChangeEstado).toHaveBeenCalledTimes(1);
    expect(productoRepository.save).toHaveBeenCalledWith(
      expect.objectContaining({
        estadoProducto: EstadoProductoEnum.INACTIVO,
        esPublicable: false,
        estadoDisponibilidad: EstadoDisponibilidadEnum.NO_PUBLICADO,
      }),
    );
    expect(result.estadoProducto).toBe(EstadoProductoEnum.INACTIVO);
    expect(result.esPublicable).toBe(false);
    expect(result.estadoDisponibilidad).toBe(EstadoDisponibilidadEnum.NO_PUBLICADO);
  });

  it('debe listar productos paginados', async () => {
    productoRepository.findPaginated.mockResolvedValue({
      items: [
        {
          productoId: 1,
          categoriaId: 1,
          nombreProducto: 'Paracetamol',
          presentacion: 'Caja x 20 tabletas',
          descripcionBreve: 'Analgésico',
          precioVisible: 3.5,
          estadoProducto: EstadoProductoEnum.ACTIVO,
          esPublicable: false,
          estadoDisponibilidad: EstadoDisponibilidadEnum.NO_PUBLICADO,
          fechaCreacion: new Date('2026-04-08T15:30:00.000Z'),
          fechaActualizacion: new Date('2026-04-08T15:30:00.000Z'),
          categoria: {
            categoriaId: 1,
            nombreCategoria: 'Analgésicos',
            fechaCreacion: new Date('2026-04-08T15:30:00.000Z'),
            fechaActualizacion: new Date('2026-04-08T15:30:00.000Z'),
          },
        },
      ],
      total: 1,
    });

    const result = await service.listar({
      page: 1,
      limit: 10,
      q: 'para',
      sortBy: 'nombreProducto',
      sortDirection: SortDirectionEnum.ASC,
    });

    expect(result.ok).toBe(true);
    expect(result.data).toHaveLength(1);
    expect(result.meta.totalItems).toBe(1);
    expect(result.data[0].nombreProducto).toBe('Paracetamol');
  });
});
