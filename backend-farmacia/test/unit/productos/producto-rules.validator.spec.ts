import { DuplicateResourceException } from '../../../src/app/common/exceptions/duplicate-resource.exception';
import { ResourceNotFoundException } from '../../../src/app/common/exceptions/resource-not-found.exception';
import { BusinessRuleException } from '../../../src/app/common/exceptions/business-rule.exception';
import { ProductoRulesValidator } from '../../../src/app/modules/productos/validation/producto-rules.validator';
import { ProductoRepository } from '../../../src/app/modules/productos/repositories/producto.repository';

describe('ProductoRulesValidator', () => {
  let validator: ProductoRulesValidator;
  let productoRepository: jest.Mocked<ProductoRepository>;
  let categoriaOrmRepository: { findOne: jest.Mock };

  beforeEach(() => {
    productoRepository = {
      findByNormalizedIdentity: jest.fn(),
    } as unknown as jest.Mocked<ProductoRepository>;

    categoriaOrmRepository = {
      findOne: jest.fn(),
    };

    validator = new ProductoRulesValidator(
      productoRepository,
      categoriaOrmRepository as any,
    );
  });

  it('debe permitir crear un producto válido', async () => {
    categoriaOrmRepository.findOne.mockResolvedValue({ categoriaId: 1 });
    productoRepository.findByNormalizedIdentity.mockResolvedValue(null);

    await expect(
      validator.ensureCanCreate({
        categoriaId: 1,
        nombreProducto: 'Paracetamol',
        presentacion: 'Caja x 20 tabletas',
        precioVisible: 3.5,
      } as any),
    ).resolves.toBeUndefined();
  });

  it('debe rechazar creación si la categoría no existe', async () => {
    categoriaOrmRepository.findOne.mockResolvedValue(null);

    await expect(
      validator.ensureCanCreate({
        categoriaId: 999,
        nombreProducto: 'Paracetamol',
        presentacion: 'Caja x 20 tabletas',
        precioVisible: 3.5,
      } as any),
    ).rejects.toBeInstanceOf(ResourceNotFoundException);
  });

  it('debe rechazar creación si ya existe un producto con la misma identidad natural', async () => {
    categoriaOrmRepository.findOne.mockResolvedValue({ categoriaId: 1 });
    productoRepository.findByNormalizedIdentity.mockResolvedValue({
      productoId: 5,
    } as any);

    await expect(
      validator.ensureCanCreate({
        categoriaId: 1,
        nombreProducto: 'Paracetamol',
        presentacion: 'Caja x 20 tabletas',
        precioVisible: 3.5,
      } as any),
    ).rejects.toBeInstanceOf(DuplicateResourceException);
  });

  it('debe rechazar actualización sin cambios efectivos', async () => {
    categoriaOrmRepository.findOne.mockResolvedValue({ categoriaId: 1 });
    productoRepository.findByNormalizedIdentity.mockResolvedValue(null);

    await expect(
      validator.ensureCanUpdate(
        1,
        {
          categoriaId: 1,
          nombreProducto: 'Paracetamol',
          presentacion: 'Caja x 20 tabletas',
          descripcionBreve: 'Texto',
          precioVisible: 3.5,
        },
        {
          productoId: 1,
          categoriaId: 1,
          nombreProducto: 'Paracetamol',
          presentacion: 'Caja x 20 tabletas',
          descripcionBreve: 'Texto',
          precioVisible: 3.5,
        } as any,
      ),
    ).rejects.toBeInstanceOf(BusinessRuleException);
  });

  it('debe rechazar cambio de estado si ya está en ese estado', async () => {
    await expect(
      validator.ensureCanChangeEstado(
        {
          estadoProducto: 'ACTIVO',
        } as any,
        'ACTIVO',
      ),
    ).rejects.toBeInstanceOf(BusinessRuleException);
  });
});
