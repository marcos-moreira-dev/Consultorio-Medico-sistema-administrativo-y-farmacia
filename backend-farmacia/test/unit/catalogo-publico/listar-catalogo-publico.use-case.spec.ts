import { SortDirectionEnum } from '../../../src/app/common/enums/sort-direction.enum';
import { ListarCatalogoPublicoUseCase } from '../../../src/app/modules/catalogo-publico/use-cases/listar-catalogo-publico.use-case';
import { CatalogoPublicoService } from '../../../src/app/modules/catalogo-publico/services/catalogo-publico.service';

describe('ListarCatalogoPublicoUseCase', () => {
  let useCase: ListarCatalogoPublicoUseCase;
  let catalogoPublicoService: jest.Mocked<CatalogoPublicoService>;

  beforeEach(() => {
    catalogoPublicoService = {
      listar: jest.fn(),
    } as unknown as jest.Mocked<CatalogoPublicoService>;

    useCase = new ListarCatalogoPublicoUseCase(catalogoPublicoService);
  });

  it('debe delegar el listado al servicio y devolver su resultado', async () => {
    const query = {
      page: 1,
      limit: 10,
      q: 'paracetamol',
      sortBy: 'nombreProducto' as const,
      sortDirection: SortDirectionEnum.ASC,
    };

    const expectedResponse = {
      ok: true,
      data: [
        {
          productoId: 1,
          categoriaId: 1,
          nombreCategoria: 'Analgésicos',
          nombreProducto: 'Paracetamol',
          presentacion: 'Caja x 20 tabletas',
          descripcionBreve: 'Analgésico y antipirético de uso común.',
          precioVisible: 3.5,
          estadoDisponibilidad: 'DISPONIBLE',
          disponible: true,
        },
      ],
      meta: {
        page: 1,
        limit: 10,
        totalItems: 1,
        totalPages: 1,
        hasPreviousPage: false,
        hasNextPage: false,
        sortBy: 'nombreProducto',
        sortDirection: SortDirectionEnum.ASC,
        query: 'paracetamol',
      },
      timestamp: '2026-04-08T15:30:00.000Z',
    };

    catalogoPublicoService.listar.mockResolvedValue(expectedResponse);

    const result = await useCase.execute(query);

    expect(catalogoPublicoService.listar).toHaveBeenCalledTimes(1);
    expect(catalogoPublicoService.listar).toHaveBeenCalledWith(query);
    expect(result).toEqual(expectedResponse);
  });
});
