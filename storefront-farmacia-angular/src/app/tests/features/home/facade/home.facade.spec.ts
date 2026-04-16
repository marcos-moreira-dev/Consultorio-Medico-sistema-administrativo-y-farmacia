import { TestBed } from '@angular/core/testing';
import { firstValueFrom, of } from 'rxjs';

import { apiConfig } from '../../../../core/config/api.config';
import { CatalogoApiService } from '../../../../core/services/catalogo-api.service';
import { HomeFacade } from '../../../../features/home/facade/home.facade';

describe('HomeFacade', () => {
  let facade: HomeFacade;
  let apiService: jasmine.SpyObj<CatalogoApiService>;

  beforeEach(() => {
    apiService = jasmine.createSpyObj<CatalogoApiService>('CatalogoApiService', [
      'listarCategorias',
      'listarCatalogo',
    ]);

    TestBed.configureTestingModule({
      providers: [
        HomeFacade,
        {
          provide: CatalogoApiService,
          useValue: apiService,
        },
      ],
    });

    facade = TestBed.inject(HomeFacade);
  });

  it('debe armar la home pública con categorías y productos destacados', async () => {
    apiService.listarCategorias.and.returnValue(
      of([
        {
          categoriaId: 9,
          nombreCategoria: 'Vitaminas',
          cantidadProductosVisibles: 5,
        },
      ]),
    );
    apiService.listarCatalogo.and.returnValue(
      of({
        items: [
          {
            productoId: 11,
            categoriaId: 9,
            nombreCategoria: 'Vitaminas',
            nombreProducto: 'Vitamina C',
            presentacion: 'Frasco',
            precioVisible: 12,
            estadoDisponibilidad: 'DISPONIBLE',
            disponible: true,
            imagenSrc: 'data:image/svg+xml;base64,test',
          },
        ],
        meta: {
          page: 1,
          limit: apiConfig.featuredProductsLimit,
          totalItems: 1,
          totalPages: 1,
          hasNextPage: false,
          hasPreviousPage: false,
        },
      }),
    );

    const home = await firstValueFrom(facade.cargarHome());

    expect(apiService.listarCatalogo).toHaveBeenCalledWith({
      page: 1,
      limit: apiConfig.featuredProductsLimit,
      sortBy: 'nombreProducto',
      sortDirection: 'ASC',
    });
    expect(home?.categorias.length).toBe(1);
    expect(home?.productosDestacados[0].nombreProducto).toBe('Vitamina C');
  });
});
