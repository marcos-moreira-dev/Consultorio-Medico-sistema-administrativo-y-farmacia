import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { CategoriaPublicaModel } from '../../../shared/models/categoria-publica.model';
import { CatalogoApiService } from '../../../core/services/catalogo-api.service';
import { CatalogFilterModel } from '../models/catalog-filter.model';
import { CatalogItemModel } from '../models/catalog-item.model';
import { CatalogPageModel } from '../models/catalog-page.model';

@Injectable({
  providedIn: 'root',
})
export class CatalogoFacade {
  private readonly catalogoApiService = inject(CatalogoApiService);

  listarCategorias(): Observable<CategoriaPublicaModel[]> {
    return this.catalogoApiService.listarCategorias();
  }

  cargarCatalogo(filters: CatalogFilterModel): Observable<CatalogPageModel> {
    return this.catalogoApiService.buscarCatalogo(filters);
  }

  cargarRelacionados(
    categoriaId: number,
    productoIdActual: number,
    limit = 4,
  ): Observable<CatalogItemModel[]> {
    return this.catalogoApiService.listarRelacionados(categoriaId, productoIdActual, limit);
  }
}
