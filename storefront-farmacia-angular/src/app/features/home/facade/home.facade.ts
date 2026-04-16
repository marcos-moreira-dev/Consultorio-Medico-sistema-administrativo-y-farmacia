import { Injectable, inject } from '@angular/core';
import { forkJoin, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { apiConfig } from '../../../core/config/api.config';
import { CatalogoApiService } from '../../../core/services/catalogo-api.service';
import { HomeSectionModel } from '../models/home-section.model';

@Injectable({
  providedIn: 'root',
})
export class HomeFacade {
  private readonly catalogoApiService = inject(CatalogoApiService);

  cargarHome(): Observable<HomeSectionModel> {
    return forkJoin({
      categorias: this.catalogoApiService.listarCategorias(),
      page: this.catalogoApiService.listarCatalogo({
        page: 1,
        limit: apiConfig.featuredProductsLimit,
        sortBy: 'nombreProducto',
        sortDirection: 'ASC',
      }),
    }).pipe(
      map(({ categorias, page }) => ({
        categorias,
        productosDestacados: page.items,
      })),
    );
  }
}
