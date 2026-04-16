import { CatalogoStore } from '../../../../features/catalogo/state/catalogo.store';

describe('CatalogoStore', () => {
  let store: CatalogoStore;

  beforeEach(() => {
    store = new CatalogoStore();
  });

  it('debe iniciar con filtros base y loading activo', () => {
    expect(store.filtros()).toEqual({ page: 1, limit: 12 });
    expect(store.loading()).toBeTrue();
    expect(store.errorMessage()).toBe('');
  });

  it('debe guardar categorías y resultado paginado', () => {
    store.setCategorias([
      {
        categoriaId: 1,
        nombreCategoria: 'Analgésicos',
        cantidadProductosVisibles: 3,
      },
    ]);

    store.setPage({
      items: [],
      meta: {
        page: 1,
        limit: 12,
        totalItems: 0,
        totalPages: 0,
        hasNextPage: false,
        hasPreviousPage: false,
      },
    });

    expect(store.categorias().length).toBe(1);
    expect(store.page()?.meta.page).toBe(1);
    expect(store.loading()).toBeFalse();
  });

  it('debe dejar error legible cuando falla la carga', () => {
    store.setError('Fallo de integración');

    expect(store.errorMessage()).toBe('Fallo de integración');
    expect(store.page()).toBeUndefined();
    expect(store.loading()).toBeFalse();
  });
});
