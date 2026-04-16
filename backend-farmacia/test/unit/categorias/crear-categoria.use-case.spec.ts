import { CrearCategoriaUseCase } from '../../../src/app/modules/categorias/use-cases/crear-categoria.use-case';
import { CategoriasService } from '../../../src/app/modules/categorias/services/categorias.service';

describe('CrearCategoriaUseCase', () => {
  let useCase: CrearCategoriaUseCase;
  let categoriasService: jest.Mocked<CategoriasService>;

  beforeEach(() => {
    categoriasService = {
      crear: jest.fn(),
    } as unknown as jest.Mocked<CategoriasService>;

    useCase = new CrearCategoriaUseCase(categoriasService);
  });

  it('debe delegar la creación al servicio y devolver su resultado', async () => {
    const request = {
      nombreCategoria: 'Analgésicos',
      descripcionBreve: 'Medicamentos para dolor y fiebre.',
    };

    const expectedResponse = {
      categoriaId: 1,
      nombreCategoria: 'Analgésicos',
      descripcionBreve: 'Medicamentos para dolor y fiebre.',
      fechaCreacion: '2026-04-08T15:30:00.000Z',
      fechaActualizacion: '2026-04-08T15:30:00.000Z',
    };

    categoriasService.crear.mockResolvedValue(expectedResponse);

    const result = await useCase.execute(request);

    expect(categoriasService.crear).toHaveBeenCalledTimes(1);
    expect(categoriasService.crear).toHaveBeenCalledWith(request);
    expect(result).toEqual(expectedResponse);
  });
});
