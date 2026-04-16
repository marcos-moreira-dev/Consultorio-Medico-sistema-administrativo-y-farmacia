import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { apiConfig } from '../../../core/config/api.config';
import { CatalogoApiService } from '../../../core/services/catalogo-api.service';
import { CatalogItemModel } from '../../catalogo/models/catalog-item.model';
import { ProductoDetalleModel } from '../models/producto-detalle.model';

@Injectable({
  providedIn: 'root',
})
export class ProductoDetalleFacade {
  private readonly catalogoApiService = inject(CatalogoApiService);

  cargarProducto(productoId: number): Observable<ProductoDetalleModel> {
    return this.catalogoApiService.obtenerProducto(productoId);
  }

  cargarRelacionados(
    categoriaId: number,
    productoIdActual: number,
  ): Observable<CatalogItemModel[]> {
    return this.catalogoApiService.listarRelacionados(
      categoriaId,
      productoIdActual,
      apiConfig.relatedProductsLimit,
    );
  }
}
