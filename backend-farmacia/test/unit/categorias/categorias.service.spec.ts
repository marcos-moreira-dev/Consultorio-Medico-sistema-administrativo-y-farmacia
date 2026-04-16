import { SortDirectionEnum } from '../../../src/app/common/enums/sort-direction.enum';
import { ResourceNotFoundException } from '../../../src/app/common/exceptions/resource-not-found.exception';
import { CategoriasService } from '../../../src/app/modules/categorias/services/categorias.service';
import { CategoriaRepository } from '../../../src/app/modules/categorias/repositories/categoria.repository';
import { CategoriaRulesValidator } from '../../../src/app/modules/categorias/validation/categoria-rules.validator';

describe('CategoriasService', () => {
  let service: CategoriasService;
  let categoriaRepository: jest.Mocked<CategoriaRepository>;
  let categoriaRulesValidator: jest.Mocked<CategoriaRulesValidator>;

  beforeEach(() => {
    categoriaRepository = {
      createAndSave: jest.fn(),
      save: jest.fn(),
      findById: jest.fn(),
      findPaginated: jest.fn(),
    } as unknown as jest.Mocked<CategoriaRepository>;

    categoriaRulesValidator = {
      ensureCanCreate: jest.fn(),
      ensureCanUpdate: jest.fn(),
    } as unknown as jest.Mocked<CategoriaRulesValidator>;

    service = new CategoriasService(categoriaRepository, categoriaRulesValidator);
  });

  it('debe crear una categoría normalizando campos de entrada', async () => {
    categoriaRepository.createAndSave.mockResolvedValue({
      categoriaId: 1,
      nombreCategoria: 'Analgésicos',
      descripcionBreve: 'Medicamentos para dolor y fiebre.',
      fechaCreacion: new Date('2026-04-08T15:30:00.000Z'),
      fechaActualizacion: new Date('2026-04-08T15:30:00.000Z'),
    } as any);

    const result = await service.crear({
      nombreCategoria: '  Analgésicos   ',
      descripcionBreve: '  Medicamentos   para dolor y fiebre. ',
    });

    expect(categoriaRulesValidator.ensureCanCreate).toHaveBeenCalledWith('Analgésicos');
    expect(categoriaRepository.createAndSave).toHaveBeenCalledWith({
      nombreCategoria: 'Analgésicos',
      descripcionBreve: 'Medicamentos para dolor y fiebre.',
    });
    expect(result.categoriaId).toBe(1);
    expect(result.nombreCategoria).toBe('Analgésicos');
  });

  it('debe lanzar error si la categoría no existe al obtener por id', async () => {
    categoriaRepository.findById.mockResolvedValue(null);

    await expect(service.obtenerPorId(999)).rejects.toBeInstanceOf(
      ResourceNotFoundException,
    );
  });

  it('debe listar categorías paginadas', async () => {
    categoriaRepository.findPaginated.mockResolvedValue({
      items: [
        {
          categoriaId: 1,
          nombreCategoria: 'Analgésicos',
          descripcionBreve: 'Medicamentos para dolor.',
          fechaCreacion: new Date('2026-04-08T15:30:00.000Z'),
          fechaActualizacion: new Date('2026-04-08T15:30:00.000Z'),
        },
      ],
      total: 1,
    });

    const result = await service.listar({
      page: 1,
      limit: 10,
      q: 'analg',
      sortBy: 'nombreCategoria',
      sortDirection: SortDirectionEnum.ASC,
    });

    expect(categoriaRepository.findPaginated).toHaveBeenCalledTimes(1);
    expect(result.ok).toBe(true);
    expect(result.data).toHaveLength(1);
    expect(result.meta.totalItems).toBe(1);
    expect(result.data[0].nombreCategoria).toBe('Analgésicos');
  });

  it('debe actualizar una categoría existente', async () => {
    categoriaRepository.findById.mockResolvedValue({
      categoriaId: 1,
      nombreCategoria: 'Analgésicos',
      descripcionBreve: 'Texto viejo',
      fechaCreacion: new Date('2026-04-08T15:30:00.000Z'),
      fechaActualizacion: new Date('2026-04-08T15:30:00.000Z'),
    } as any);

    categoriaRepository.save.mockResolvedValue({
      categoriaId: 1,
      nombreCategoria: 'Analgésicos y antifebriles',
      descripcionBreve: 'Texto nuevo',
      fechaCreacion: new Date('2026-04-08T15:30:00.000Z'),
      fechaActualizacion: new Date('2026-04-08T16:00:00.000Z'),
    } as any);

    const result = await service.actualizar(1, {
      nombreCategoria: '  Analgésicos y antifebriles ',
      descripcionBreve: ' Texto nuevo ',
    });

    expect(categoriaRulesValidator.ensureCanUpdate).toHaveBeenCalledTimes(1);
    expect(categoriaRepository.save).toHaveBeenCalledTimes(1);
    expect(result.nombreCategoria).toBe('Analgésicos y antifebriles');
    expect(result.descripcionBreve).toBe('Texto nuevo');
  });
});
