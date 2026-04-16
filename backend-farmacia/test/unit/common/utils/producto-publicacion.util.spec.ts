import { EstadoDisponibilidadEnum } from '../../../../src/app/common/enums/estado-disponibilidad.enum';
import { EstadoProductoEnum } from '../../../../src/app/common/enums/estado-producto.enum';
import { ProductoPublicacionUtil } from '../../../../src/app/common/utils/producto-publicacion.util';

describe('ProductoPublicacionUtil', () => {
  it('debe normalizar estados de producto válidos', () => {
    expect(
      ProductoPublicacionUtil.normalizeEstadoProducto('activo'),
    ).toBe(EstadoProductoEnum.ACTIVO);

    expect(
      ProductoPublicacionUtil.normalizeEstadoProducto(' INACTIVO '),
    ).toBe(EstadoProductoEnum.INACTIVO);
  });

  it('debe devolver null cuando el estado de producto es inválido', () => {
    expect(
      ProductoPublicacionUtil.normalizeEstadoProducto('DESCONOCIDO'),
    ).toBeNull();
  });

  it('debe reconocer estados de disponibilidad visibles públicamente', () => {
    expect(
      ProductoPublicacionUtil.isVisiblePublicDisponibilidad(
        EstadoDisponibilidadEnum.DISPONIBLE,
      ),
    ).toBe(true);

    expect(
      ProductoPublicacionUtil.isVisiblePublicDisponibilidad(
        EstadoDisponibilidadEnum.AGOTADO,
      ),
    ).toBe(true);

    expect(
      ProductoPublicacionUtil.isVisiblePublicDisponibilidad(
        EstadoDisponibilidadEnum.NO_PUBLICADO,
      ),
    ).toBe(false);
  });

  it('debe determinar correctamente si un producto es visible públicamente', () => {
    expect(
      ProductoPublicacionUtil.isPublicVisible(
        EstadoProductoEnum.ACTIVO,
        true,
        EstadoDisponibilidadEnum.DISPONIBLE,
      ),
    ).toBe(true);

    expect(
      ProductoPublicacionUtil.isPublicVisible(
        EstadoProductoEnum.ACTIVO,
        true,
        EstadoDisponibilidadEnum.AGOTADO,
      ),
    ).toBe(true);

    expect(
      ProductoPublicacionUtil.isPublicVisible(
        EstadoProductoEnum.ACTIVO,
        false,
        EstadoDisponibilidadEnum.DISPONIBLE,
      ),
    ).toBe(false);

    expect(
      ProductoPublicacionUtil.isPublicVisible(
        EstadoProductoEnum.INACTIVO,
        true,
        EstadoDisponibilidadEnum.DISPONIBLE,
      ),
    ).toBe(false);

    expect(
      ProductoPublicacionUtil.isPublicVisible(
        EstadoProductoEnum.ACTIVO,
        true,
        EstadoDisponibilidadEnum.NO_PUBLICADO,
      ),
    ).toBe(false);
  });

  it('debe devolver el estado interno correcto para despublicar', () => {
    expect(ProductoPublicacionUtil.getUnpublishedState()).toEqual({
      esPublicable: false,
      estadoDisponibilidad: EstadoDisponibilidadEnum.NO_PUBLICADO,
    });
  });
});
