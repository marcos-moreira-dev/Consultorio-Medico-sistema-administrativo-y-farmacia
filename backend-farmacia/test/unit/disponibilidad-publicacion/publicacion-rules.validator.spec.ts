import { BusinessRuleException } from '../../../src/app/common/exceptions/business-rule.exception';
import { PublicacionRulesValidator } from '../../../src/app/modules/disponibilidad-publicacion/validation/publicacion-rules.validator';
import { EstadoDisponibilidadEnum } from '../../../src/app/common/enums/estado-disponibilidad.enum';
import { EstadoProductoEnum } from '../../../src/app/common/enums/estado-producto.enum';

describe('PublicacionRulesValidator', () => {
  let validator: PublicacionRulesValidator;

  beforeEach(() => {
    validator = new PublicacionRulesValidator();
  });

  it('debe permitir publicar un producto ACTIVO', async () => {
    await expect(
      validator.ensureCanPublish(
        {
          estadoProducto: EstadoProductoEnum.ACTIVO,
          esPublicable: false,
          estadoDisponibilidad: EstadoDisponibilidadEnum.NO_PUBLICADO,
        } as any,
        EstadoDisponibilidadEnum.DISPONIBLE,
      ),
    ).resolves.toBeUndefined();
  });

  it('debe rechazar publicación de producto INACTIVO', async () => {
    await expect(
      validator.ensureCanPublish(
        {
          estadoProducto: EstadoProductoEnum.INACTIVO,
          esPublicable: false,
          estadoDisponibilidad: EstadoDisponibilidadEnum.NO_PUBLICADO,
        } as any,
        EstadoDisponibilidadEnum.DISPONIBLE,
      ),
    ).rejects.toBeInstanceOf(BusinessRuleException);
  });

  it('debe rechazar despublicación si el producto ya está despublicado', async () => {
    await expect(
      validator.ensureCanUnpublish(
        {
          esPublicable: false,
          estadoDisponibilidad: EstadoDisponibilidadEnum.NO_PUBLICADO,
        } as any,
        {},
      ),
    ).rejects.toBeInstanceOf(BusinessRuleException);
  });

  it('debe rechazar actualización de disponibilidad en producto no publicado', async () => {
    await expect(
      validator.ensureCanUpdateDisponibilidad(
        {
          estadoProducto: EstadoProductoEnum.ACTIVO,
          esPublicable: false,
          estadoDisponibilidad: EstadoDisponibilidadEnum.NO_PUBLICADO,
        } as any,
        EstadoDisponibilidadEnum.AGOTADO,
      ),
    ).rejects.toBeInstanceOf(BusinessRuleException);
  });

  it('debe rechazar actualización de disponibilidad sin cambio efectivo', async () => {
    await expect(
      validator.ensureCanUpdateDisponibilidad(
        {
          estadoProducto: EstadoProductoEnum.ACTIVO,
          esPublicable: true,
          estadoDisponibilidad: EstadoDisponibilidadEnum.DISPONIBLE,
        } as any,
        EstadoDisponibilidadEnum.DISPONIBLE,
      ),
    ).rejects.toBeInstanceOf(BusinessRuleException);
  });
});
