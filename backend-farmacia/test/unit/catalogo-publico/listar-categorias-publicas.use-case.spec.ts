import { ListarCategoriasPublicasUseCase } from '../../../src/app/modules/catalogo-publico/use-cases/listar-categorias-publicas.use-case';
import { CatalogoPublicoService } from '../../../src/app/modules/catalogo-publico/services/catalogo-publico.service';

describe('ListarCategoriasPublicasUseCase', () => {
  let useCase: ListarCategoriasPublicasUseCase;
  let catalogoPublicoService: jest.Mocked<CatalogoPublicoService>;

  beforeEach(() => {
    catalogoPublicoService = {
      listarCategoriasPublicas: jest.fn(),
    } as unknown as jest.Mocked<CatalogoPublicoService>;

    useCase = new ListarCategoriasPublicasUseCase(catalogoPublicoService);
  });

  it('debe delegar el listado de categorías públicas al servicio', async () => {
    const expectedResponse = [
      {
        categoriaId: 1,
        nombreCategoria: 'Analgésicos',
        cantidadProductosVisibles: 12,
      },
      {
        categoriaId: 2,
        nombreCategoria: 'Antigripales',
        cantidadProductosVisibles: 5,
      },
    ];

    catalogoPublicoService.listarCategoriasPublicas.mockResolvedValue(expectedResponse);

    const result = await useCase.execute();

    expect(catalogoPublicoService.listarCategoriasPublicas).toHaveBeenCalledTimes(1);
    expect(result).toEqual(expectedResponse);
  });
});
