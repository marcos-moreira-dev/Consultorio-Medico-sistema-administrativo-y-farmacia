import { ProductoDetalleStore } from '../../../../features/producto-detalle/state/producto-detalle.store';

describe('ProductoDetalleStore', () => {
  let store: ProductoDetalleStore;

  beforeEach(() => {
    store = new ProductoDetalleStore();
  });

  it('debe iniciar en carga y sin producto seleccionado', () => {
    expect(store.loading()).toBeTrue();
    expect(store.producto()).toBeUndefined();
    expect(store.relacionados()).toEqual([]);
  });

  it('debe almacenar producto y relacionados', () => {
    store.setProducto({
      productoId: 10,
      categoriaId: 3,
      nombreCategoria: 'Vitaminas',
      nombreProducto: 'Vitamina C',
      presentacion: 'Tabletas',
      descripcionBreve: 'Suplemento diario',
      precioVisible: 4.5,
      estadoDisponibilidad: 'DISPONIBLE',
      disponible: true,
      imagenUrl: null,
      imagenSrc: '/assets/product-placeholder.svg',
      fechaActualizacion: '2026-04-09T00:00:00.000Z',
    });

    store.setRelacionados([
      {
        productoId: 11,
        categoriaId: 3,
        nombreCategoria: 'Vitaminas',
        nombreProducto: 'Multivitamínico',
        presentacion: 'Cápsulas',
        descripcionBreve: 'Apoyo diario',
        precioVisible: 5.25,
        estadoDisponibilidad: 'DISPONIBLE',
        disponible: true,
        imagenUrl: null,
        imagenSrc: '/assets/product-placeholder.svg',
      },
    ]);

    expect(store.producto()?.productoId).toBe(10);
    expect(store.relacionados().length).toBe(1);
    expect(store.loading()).toBeFalse();
  });

  it('debe limpiar producto y relacionados al registrar error', () => {
    store.setError('No se pudo cargar el detalle');

    expect(store.errorMessage()).toBe('No se pudo cargar el detalle');
    expect(store.producto()).toBeUndefined();
    expect(store.relacionados()).toEqual([]);
  });
});
