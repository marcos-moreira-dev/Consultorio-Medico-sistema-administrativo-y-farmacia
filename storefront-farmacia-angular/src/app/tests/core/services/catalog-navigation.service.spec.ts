import { TestBed } from '@angular/core/testing';
import { DefaultUrlSerializer, NavigationEnd, Router } from '@angular/router';
import { Subject } from 'rxjs';

import { CatalogNavigationService } from '../../../core/services/catalog-navigation.service';

describe('CatalogNavigationService', () => {
  let service: CatalogNavigationService;
  let events$: Subject<NavigationEnd>;
  let currentUrl = '/';

  beforeEach(() => {
    events$ = new Subject<NavigationEnd>();
    currentUrl = '/';

    const serializer = new DefaultUrlSerializer();
    const routerStub = {
      events: events$.asObservable(),
      parseUrl: (url: string) => serializer.parse(url),
      get url() {
        return currentUrl;
      },
    } as unknown as Router;

    if (typeof sessionStorage !== 'undefined') {
      sessionStorage.clear();
    }

    TestBed.configureTestingModule({
      providers: [
        CatalogNavigationService,
        {
          provide: Router,
          useValue: routerStub,
        },
      ],
    });

    service = TestBed.inject(CatalogNavigationService);
  });

  it('debe recordar la última URL del catálogo con query params', () => {
    currentUrl = '/catalogo?q=ibuprofeno&categoriaId=2&page=3';
    events$.next(new NavigationEnd(1, currentUrl, currentUrl));

    const link = service.getLastCatalogLink();

    expect(service.getLastCatalogUrl()).toBe(currentUrl);
    expect(link.routerLink).toEqual(['/catalogo']);
    expect(link.queryParams?.['q']).toBe('ibuprofeno');
    expect(link.queryParams?.['categoriaId']).toBe('2');
    expect(link.queryParams?.['page']).toBe('3');
  });

  it('debe ignorar rutas que no pertenecen al catálogo', () => {
    currentUrl = '/catalogo?sortBy=nombreProducto';
    events$.next(new NavigationEnd(1, currentUrl, currentUrl));

    currentUrl = '/producto/12';
    events$.next(new NavigationEnd(2, currentUrl, currentUrl));

    expect(service.getLastCatalogUrl()).toBe('/catalogo?sortBy=nombreProducto');
  });
});
