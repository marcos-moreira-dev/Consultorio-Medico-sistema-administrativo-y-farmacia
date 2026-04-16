import { TestBed } from '@angular/core/testing';
import { firstValueFrom, of } from 'rxjs';

import { CatalogoApiService } from '../../../../core/services/catalogo-api.service';
import { CatalogoFacade } from '../../../../features/catalogo/facade/catalogo.facade';

describe('CatalogoFacade', () => {
  let facade: CatalogoFacade;
  let apiService: jasmine.SpyObj<CatalogoApiService>;

  beforeEach(() => {
    apiService = jasmine.createSpyObj<CatalogoApiService>('CatalogoApiService', [
      'listarCategorias',
      'buscarCatalogo',
      'listarRelacionados',
    ]);

    TestBed.configureTestingModule({
      providers: [
        CatalogoFacade,
        {
          provide: CatalogoApiService,
          useValue: apiService,
        },
      ],
    });

    facade = TestBed.inject(CatalogoFacade);
  });

  it('debe delegar la carga del catálogo al servicio de API', async () => {
    apiService.buscarCatalogo.and.returnValue(
      of({
        items: [],
        meta: {
          page: 1,
          limit: 12,
          totalItems: 0,
          totalPages: 0,
          hasNextPage: false,
          hasPreviousPage: false,
        },
      }),
    );

    const page = await firstValueFrom(facade.cargarCatalogo({ page: 1, limit: 12 }));

    expect(apiService.buscarCatalogo).toHaveBeenCalledWith({ page: 1, limit: 12 });
    expect(page?.meta.page).toBe(1);
  });

  it('debe delegar categorías y relacionados sin alterar el contrato', async () => {
    apiService.listarCategorias.and.returnValue(
      of([
        {
          categoriaId: 1,
          nombreCategoria: 'Analgésicos',
          cantidadProductosVisibles: 3,
        },
      ]),
    );
    apiService.listarRelacionados.and.returnValue(of([]));

    const categorias = await firstValueFrom(facade.listarCategorias());
    await firstValueFrom(facade.cargarRelacionados(1, 99, 4));

    expect(categorias?.length).toBe(1);
    expect(apiService.listarRelacionados).toHaveBeenCalledWith(1, 99, 4);
  });
});
