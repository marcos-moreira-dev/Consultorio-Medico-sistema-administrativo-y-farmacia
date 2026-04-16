import { SortDirectionEnum } from '../../../src/app/common/enums/sort-direction.enum';
import { BusinessRuleException } from '../../../src/app/common/exceptions/business-rule.exception';
import { ResourceNotFoundException } from '../../../src/app/common/exceptions/resource-not-found.exception';
import { CatalogoPublicoService } from '../../../src/app/modules/catalogo-publico/services/catalogo-publico.service';

describe('CatalogoPublicoService', () => {
  let service: CatalogoPublicoService;
  let productoOrmRepository: {
    createQueryBuilder: jest.Mock;
    count: jest.Mock;
  };
  let mediaOrmRepository: {
    find: jest.Mock;
  };
  let categoriaOrmRepository: {
    find: jest.Mock;
    findOne: jest.Mock;
  };

  beforeEach(() => {
    productoOrmRepository = {
      createQueryBuilder: jest.fn(),
      count: jest.fn(),
    };

    mediaOrmRepository = {
      find: jest.fn(),
    };

    categoriaOrmRepository = {
      find: jest.fn(),
      findOne: jest.fn(),
    };

    service = new CatalogoPublicoService(
      productoOrmRepository as any,
      categoriaOrmRepository as any,
      mediaOrmRepository as any,
    );
  });

  it('debe rechazar búsqueda con menos de 2 caracteres', async () => {
    await expect(
      service.buscar({
        q: 'a',
        page: 1,
        limit: 10,
      }),
    ).rejects.toBeInstanceOf(BusinessRuleException);
  });

  it('debe rechazar obtener detalle público si el producto no existe o no es visible', async () => {
    const qb = {
      leftJoinAndSelect: jest.fn().mockReturnThis(),
      where: jest.fn().mockReturnThis(),
      andWhere: jest.fn().mockReturnThis(),
      getOne: jest.fn().mockResolvedValue(null),
    };

    productoOrmRepository.createQueryBuilder.mockReturnValue(qb);

    await expect(
      service.obtenerProductoPublico(999),
    ).rejects.toBeInstanceOf(ResourceNotFoundException);
  });

  it('debe listar catálogo público con imagen principal cuando exista', async () => {
    const qb = {
      leftJoinAndSelect: jest.fn().mockReturnThis(),
      where: jest.fn().mockReturnThis(),
      andWhere: jest.fn().mockReturnThis(),
      orderBy: jest.fn().mockReturnThis(),
      skip: jest.fn().mockReturnThis(),
      take: jest.fn().mockReturnThis(),
      getManyAndCount: jest.fn().mockResolvedValue([
        [
          {
            productoId: 1,
            categoriaId: 1,
            nombreProducto: 'Paracetamol',
            presentacion: 'Caja x 20 tabletas',
            descripcionBreve: 'Analgésico',
            precioVisible: 3.5,
            estadoDisponibilidad: 'DISPONIBLE',
            fechaActualizacion: new Date('2026-04-08T15:30:00.000Z'),
            categoria: {
              nombreCategoria: 'Analgésicos',
            },
          },
        ],
        1,
      ]),
    };

    productoOrmRepository.createQueryBuilder.mockReturnValue(qb);
    mediaOrmRepository.find.mockResolvedValue([
      {
        mediaRecursoId: 10,
        productoId: 1,
        urlPublica: 'http://localhost:3001/media/productos/imagenes/2026/04/uuid-demo.png',
      },
    ]);

    const result = await service.listar({
      page: 1,
      limit: 10,
      sortBy: 'nombreProducto',
      sortDirection: SortDirectionEnum.ASC,
    });

    expect(productoOrmRepository.createQueryBuilder).toHaveBeenCalledWith('producto');
    expect(mediaOrmRepository.find).toHaveBeenCalledTimes(1);
    expect(result.ok).toBe(true);
    expect(result.data).toHaveLength(1);
    expect(result.data[0].nombreProducto).toBe('Paracetamol');
    expect(result.data[0].imagenUrl).toBe(
      'http://localhost:3001/media/productos/imagenes/2026/04/uuid-demo.png',
    );
    expect(result.meta.totalItems).toBe(1);
  });

  it('debe listar categorías públicas visibles', async () => {
    categoriaOrmRepository.find.mockResolvedValue([
      { categoriaId: 1, nombreCategoria: 'Analgésicos' },
    ]);
    productoOrmRepository.count.mockResolvedValueOnce(12);

    const result = await service.listarCategoriasPublicas();

    expect(categoriaOrmRepository.find).toHaveBeenCalledWith({
      order: { nombreCategoria: 'ASC' },
    });
    expect(result).toEqual([
      {
        categoriaId: 1,
        nombreCategoria: 'Analgésicos',
        cantidadProductosVisibles: 12,
      },
    ]);
  });
});
