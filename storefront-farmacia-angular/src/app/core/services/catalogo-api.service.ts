import { Injectable, inject } from '@angular/core';
import { catchError, map, Observable, of, throwError } from 'rxjs';

import { CatalogPageModel } from '../../features/catalogo/models/catalog-page.model';
import { ProductoDetalleModel } from '../../features/producto-detalle/models/producto-detalle.model';
import { CategoriaPublicaModel } from '../../shared/models/categoria-publica.model';
import { CatalogoHttpAdapter } from '../http/adapters/catalogo-http.adapter';
import { ProductoHttpAdapter } from '../http/adapters/producto-http.adapter';
import { CatalogoApiClient } from '../http/clients/catalogo-api.client';
import { CatalogoQueryDto } from '../http/dto/catalogo/catalogo-query.dto';
import { ErrorMapper } from '../errors/error-mapper';
import { CatalogItemModel } from '../../features/catalogo/models/catalog-item.model';
import { DemoCatalogData } from '../demo/demo-catalog.data';

/**
 * Servicio de alto nivel que orquesta el consumo de la API pública de farmacia.
 *
 * <p>Este servicio es la capa de abstracción principal entre la UI y el backend:
 * transforma DTOs HTTP en modelos de UI usando los adapters, y aplica fallback
 * a datos demo cuando el backend no está disponible. Esto permite que el
 * storefront funcione en modo "demo offline" para presentaciones donde el
 * backend no esté levantado.</p>
 *
 * <p>El fallback a {@link DemoCatalogData} es intencional: no se lanza un error
 * cuando el backend falla, sino que se devuelven datos demo para que la UI
 * no quede completamente vacía. Esto es útil para revisiones de diseño y UX
 * sin depender de la infraestructura completa.</p>
 *
 * <p>Para productos relacionados, este servicio reutiliza {@code listarCatalogo}
 * y filtra el producto actual en memoria, evitando crear un endpoint dedicado
 * en el backend solo para este caso de uso visual.</p>
 */
@Injectable({
  providedIn: 'root',
})
export class CatalogoApiService {
  private readonly client = inject(CatalogoApiClient);
  private readonly catalogoAdapter = inject(CatalogoHttpAdapter);
  private readonly productoAdapter = inject(ProductoHttpAdapter);

  listarCategorias(): Observable<CategoriaPublicaModel[]> {
    return this.client.listarCategorias().pipe(
      map((response) => response.data.map((item) => this.catalogoAdapter.toCategoriaModel(item))),
      catchError(() => of(DemoCatalogData.categories())),
    );
  }

  listarCatalogo(query: CatalogoQueryDto): Observable<CatalogPageModel> {
    return this.client.listarCatalogo(query).pipe(
      map((response) => this.catalogoAdapter.toCatalogPageModel(response)),
      catchError(() => of(DemoCatalogData.catalogPage(query))),
    );
  }

  buscarCatalogo(query: CatalogoQueryDto): Observable<CatalogPageModel> {
    const normalizedQuery = query.q?.trim();

    if (!normalizedQuery || normalizedQuery.length < 2) {
      return this.listarCatalogo({
        ...query,
        q: undefined,
      });
    }

    return this.client
      .buscarCatalogo({
        ...query,
        q: normalizedQuery,
      })
      .pipe(
        map((response) => this.catalogoAdapter.toCatalogPageModel(response)),
        catchError(() => of(DemoCatalogData.catalogPage({ ...query, q: normalizedQuery }))),
      );
  }

  obtenerProducto(productoId: number): Observable<ProductoDetalleModel> {
    return this.client.obtenerProducto(productoId).pipe(
      map((response) => this.productoAdapter.toProductoDetalleModel(response.data)),
      catchError(() => of(DemoCatalogData.productDetail(productoId))),
    );
  }

  listarRelacionados(
    categoriaId: number,
    productoIdActual: number,
    limit: number,
  ): Observable<CatalogItemModel[]> {
    return this.listarCatalogo({
      categoriaId,
      limit: Math.max(limit + 1, 4),
      page: 1,
      sortBy: 'nombreProducto',
      sortDirection: 'ASC',
    }).pipe(
      map((page) =>
        page.items
          .filter((item) => item.productoId !== productoIdActual)
          .slice(0, limit),
      ),
    );
  }
}
