import { HttpErrorResponse } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { firstValueFrom, of, throwError } from 'rxjs';

import { CatalogoApiService } from '../../../core/services/catalogo-api.service';
import { CatalogoHttpAdapter } from '../../../core/http/adapters/catalogo-http.adapter';
import { ProductoHttpAdapter } from '../../../core/http/adapters/producto-http.adapter';
import { CatalogoApiClient } from '../../../core/http/clients/catalogo-api.client';
import { RemoteApiError } from '../../../core/errors/remote-api-error';

describe('CatalogoApiService', () => {
  let service: CatalogoApiService;
  let client: jasmine.SpyObj<CatalogoApiClient>;

  beforeEach(() => {
    client = jasmine.createSpyObj<CatalogoApiClient>('CatalogoApiClient', [
      'listarCategorias',
      'listarCatalogo',
      'buscarCatalogo',
      'obtenerProducto',
    ]);

    TestBed.configureTestingModule({
      providers: [
        CatalogoApiService,
        CatalogoHttpAdapter,
        ProductoHttpAdapter,
        {
          provide: CatalogoApiClient,
          useValue: client,
        },
      ],
    });

    service = TestBed.inject(CatalogoApiService);
  });

  it('debe mapear categorías públicas desde el cliente HTTP', async () => {
    client.listarCategorias.and.returnValue(
      of({
        ok: true,
        data: [
          {
            categoriaId: 10,
            nombreCategoria: 'Vitaminas',
            cantidadProductosVisibles: 4,
          },
        ],
      }),
    );

    const categorias = await firstValueFrom(service.listarCategorias());

    expect(categorias).toEqual([
      {
        categoriaId: 10,
        nombreCategoria: 'Vitaminas',
        cantidadProductosVisibles: 4,
      },
    ]);
  });

  it('debe delegar en listarCatalogo cuando la búsqueda no alcanza longitud mínima', async () => {
    client.listarCatalogo.and.returnValue(
      of({
        ok: true,
        data: [],
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

    await firstValueFrom(
      service.buscarCatalogo({
        q: ' a ',
        page: 1,
        limit: 12,
      }),
    );

    expect(client.listarCatalogo).toHaveBeenCalledWith({
      q: undefined,
      page: 1,
      limit: 12,
    });
    expect(client.buscarCatalogo).not.toHaveBeenCalled();
  });

  it('debe pedir relacionados filtrando el producto actual y respetando el límite solicitado', async () => {
    client.listarCatalogo.and.returnValue(
      of({
        ok: true,
        data: [
          {
            productoId: 100,
            categoriaId: 7,
            nombreCategoria: 'Analgésicos',
            nombreProducto: 'Producto actual',
            presentacion: 'Caja',
            precioVisible: 3.5,
            estadoDisponibilidad: 'DISPONIBLE',
            disponible: true,
          },
          {
            productoId: 101,
            categoriaId: 7,
            nombreCategoria: 'Analgésicos',
            nombreProducto: 'Ibuprofeno',
            presentacion: 'Tabletas',
            precioVisible: 4.1,
            estadoDisponibilidad: 'DISPONIBLE',
            disponible: true,
            imagenUrl: 'media/ibuprofeno.png',
          },
          {
            productoId: 102,
            categoriaId: 7,
            nombreCategoria: 'Analgésicos',
            nombreProducto: 'Paracetamol',
            presentacion: 'Jarabe',
            precioVisible: 5.2,
            estadoDisponibilidad: 'DISPONIBLE',
            disponible: true,
          },
          {
            productoId: 103,
            categoriaId: 7,
            nombreCategoria: 'Analgésicos',
            nombreProducto: 'Diclofenaco',
            presentacion: 'Ampollas',
            precioVisible: 8.9,
            estadoDisponibilidad: 'DISPONIBLE',
            disponible: true,
          },
        ],
        meta: {
          page: 1,
          limit: 4,
          totalItems: 4,
          totalPages: 1,
          hasNextPage: false,
          hasPreviousPage: false,
        },
      }),
    );

    const relacionados = await firstValueFrom(service.listarRelacionados(7, 100, 2));

    expect(client.listarCatalogo).toHaveBeenCalledWith({
      categoriaId: 7,
      limit: 4,
      page: 1,
      sortBy: 'nombreProducto',
      sortDirection: 'ASC',
    });
    expect(relacionados.length).toBe(2);
    expect(relacionados.every((item) => item.productoId !== 100)).toBeTrue();
    expect(relacionados[0].imagenSrc).toContain('/media/ibuprofeno.png');
  });

  it('debe mapear fallos HTTP a RemoteApiError legible', async () => {
    client.obtenerProducto.and.returnValue(
      throwError(
        () =>
          new HttpErrorResponse({
            status: 503,
            error: {
              correlationId: 'corr-503',
              error: {
                code: 'CATALOGO_DOWN',
                message: 'Catálogo no disponible',
              },
            },
          }),
      ),
    );

    await expectAsync(firstValueFrom(service.obtenerProducto(55))).toBeRejectedWith(
      jasmine.objectContaining<RemoteApiError>({
        name: 'RemoteApiError',
        message: 'Catálogo no disponible',
        code: 'CATALOGO_DOWN',
        correlationId: 'corr-503',
      }),
    );
  });
});
