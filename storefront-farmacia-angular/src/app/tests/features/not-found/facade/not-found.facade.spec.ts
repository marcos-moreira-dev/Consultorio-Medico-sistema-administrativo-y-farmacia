import { NotFoundFacade } from '../../../../features/not-found/facade/not-found.facade';

describe('NotFoundFacade', () => {
  it('debe exponer acciones públicas coherentes', () => {
    const facade = new NotFoundFacade();

    expect(facade.getActions()).toEqual([
      {
        label: 'Ir al inicio',
        routerLink: '/',
      },
      {
        label: 'Ver catálogo',
        routerLink: '/catalogo',
      },
    ]);
  });
});
