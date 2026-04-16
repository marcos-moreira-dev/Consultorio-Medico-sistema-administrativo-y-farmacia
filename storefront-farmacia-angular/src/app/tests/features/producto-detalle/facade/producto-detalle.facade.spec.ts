import { TestBed } from '@angular/core/testing';
import { firstValueFrom, of } from 'rxjs';

import { apiConfig } from '../../../../core/config/api.config';
import { CatalogoApiService } from '../../../../core/services/catalogo-api.service';
import { ProductoDetalleFacade } from '../../../../features/producto-detalle/facade/producto-detalle.facade';

describe('ProductoDetalleFacade', () => {
  let facade: ProductoDetalleFacade;
  let apiService: jasmine.SpyObj<CatalogoApiService>;

  beforeEach(() => {
    apiService = jasmine.createSpyObj<CatalogoApiService>('CatalogoApiService', [
      'obtenerProducto',
      'listarRelacionados',
    ]);

    TestBed.configureTestingModule({
      providers: [
        ProductoDetalleFacade,
        {
          provide: CatalogoApiService,
          useValue: apiService,
        },
      ],
    });

    facade = TestBed.inject(ProductoDetalleFacade);
  });

  it('debe cargar un producto desde la API tipada', async () => {
    apiService.obtenerProducto.and.returnValue(
      of({
        productoId: 88,
        categoriaId: 3,
        nombreCategoria: 'Analgésicos',
        nombreProducto: 'Ketorolaco',
        presentacion: 'Caja',
        precioVisible: 7.25,
        estadoDisponibilidad: 'DISPONIBLE',
        disponible: true,
        imagenSrc: 'data:image/svg+xml;base64,test',
        fechaActualizacion: '2026-04-09T00:00:00Z',
      }),
    );

    const product = await firstValueFrom(facade.cargarProducto(88));

    expect(apiService.obtenerProducto).toHaveBeenCalledWith(88);
    expect(product?.nombreProducto).toBe('Ketorolaco');
  });

  it('debe pedir relacionados con el límite configurado del storefront', async () => {
    apiService.listarRelacionados.and.returnValue(of([]));

    await firstValueFrom(facade.cargarRelacionados(3, 88));

    expect(apiService.listarRelacionados).toHaveBeenCalledWith(3, 88, apiConfig.relatedProductsLimit);
  });
});
