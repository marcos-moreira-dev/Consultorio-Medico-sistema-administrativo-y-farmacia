import { BusinessRuleException } from '../../../src/app/common/exceptions/business-rule.exception';
import { DuplicateResourceException } from '../../../src/app/common/exceptions/duplicate-resource.exception';
import { CategoriaRulesValidator } from '../../../src/app/modules/categorias/validation/categoria-rules.validator';
import { CategoriaRepository } from '../../../src/app/modules/categorias/repositories/categoria.repository';

describe('CategoriaRulesValidator', () => {
  let validator: CategoriaRulesValidator;
  let categoriaRepository: jest.Mocked<CategoriaRepository>;

  beforeEach(() => {
    categoriaRepository = {
      findByNormalizedNombre: jest.fn(),
    } as unknown as jest.Mocked<CategoriaRepository>;

    validator = new CategoriaRulesValidator(categoriaRepository);
  });

  it('debe permitir crear una categoría cuando no existe duplicado', async () => {
    categoriaRepository.findByNormalizedNombre.mockResolvedValue(null);

    await expect(
      validator.ensureCanCreate('Analgésicos'),
    ).resolves.toBeUndefined();

    expect(categoriaRepository.findByNormalizedNombre).toHaveBeenCalledWith('Analgésicos');
  });

  it('debe rechazar crear una categoría duplicada', async () => {
    categoriaRepository.findByNormalizedNombre.mockResolvedValue({
      categoriaId: 1,
    } as any);

    await expect(
      validator.ensureCanCreate('Analgésicos'),
    ).rejects.toBeInstanceOf(DuplicateResourceException);
  });

  it('debe rechazar actualización sin campos', async () => {
    await expect(
      validator.ensureCanUpdate(1, {}, {
        categoriaId: 1,
        nombreCategoria: 'Analgésicos',
        descripcionBreve: 'Texto',
      } as any),
    ).rejects.toBeInstanceOf(BusinessRuleException);
  });

  it('debe rechazar actualización cuando el nuevo nombre ya pertenece a otra categoría', async () => {
    categoriaRepository.findByNormalizedNombre.mockResolvedValue({
      categoriaId: 2,
    } as any);

    await expect(
      validator.ensureCanUpdate(
        1,
        { nombreCategoria: 'Antibióticos' },
        {
          categoriaId: 1,
          nombreCategoria: 'Analgésicos',
          descripcionBreve: 'Texto',
        } as any,
      ),
    ).rejects.toBeInstanceOf(DuplicateResourceException);
  });

  it('debe rechazar actualización sin cambios efectivos', async () => {
    categoriaRepository.findByNormalizedNombre.mockResolvedValue(null);

    await expect(
      validator.ensureCanUpdate(
        1,
        {
          nombreCategoria: 'Analgésicos',
          descripcionBreve: 'Texto',
        },
        {
          categoriaId: 1,
          nombreCategoria: 'Analgésicos',
          descripcionBreve: 'Texto',
        } as any,
      ),
    ).rejects.toBeInstanceOf(BusinessRuleException);
  });
});
