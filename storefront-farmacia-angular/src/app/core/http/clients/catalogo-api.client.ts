import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { apiConfig } from '../../config/api.config';
import { CategoriaPublicaDto } from '../dto/catalogo/categoria-publica.dto';
import { CatalogoItemDto } from '../dto/catalogo/catalogo-item.dto';
import { CatalogoQueryDto } from '../dto/catalogo/catalogo-query.dto';
import { ApiResponseDto } from '../dto/common/api-response.dto';
import { PageResponseDto } from '../dto/common/page-response.dto';
import { ProductoPublicoDetalleDto } from '../dto/producto/producto-publico-detalle.dto';

@Injectable({
  providedIn: 'root',
})
export class CatalogoApiClient {
  private readonly http = inject(HttpClient);
  private readonly basePath = apiConfig.catalogoBasePath;

  listarCategorias(): Observable<ApiResponseDto<CategoriaPublicaDto[]>> {
    return this.http.get<ApiResponseDto<CategoriaPublicaDto[]>>(`${this.basePath}/categorias`);
  }

  listarCatalogo(query: CatalogoQueryDto): Observable<PageResponseDto<CatalogoItemDto>> {
    return this.http.get<PageResponseDto<CatalogoItemDto>>(this.basePath, {
      params: this.buildParams(query),
    });
  }

  buscarCatalogo(query: CatalogoQueryDto): Observable<PageResponseDto<CatalogoItemDto>> {
    return this.http.get<PageResponseDto<CatalogoItemDto>>(`${this.basePath}/buscar`, {
      params: this.buildParams(query),
    });
  }

  obtenerProducto(productoId: number): Observable<ApiResponseDto<ProductoPublicoDetalleDto>> {
    return this.http.get<ApiResponseDto<ProductoPublicoDetalleDto>>(
      `${this.basePath}/${productoId}`,
    );
  }

  private buildParams(query: CatalogoQueryDto): HttpParams {
    let params = new HttpParams();

    for (const [key, value] of Object.entries(query)) {
      if (value !== undefined && value !== null && value !== '') {
        params = params.set(key, String(value));
      }
    }

    return params;
  }
}
