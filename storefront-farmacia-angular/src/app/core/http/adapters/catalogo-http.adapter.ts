import { Injectable } from '@angular/core';

import { CatalogItemModel } from '../../../features/catalogo/models/catalog-item.model';
import { CatalogPageModel } from '../../../features/catalogo/models/catalog-page.model';
import { CategoriaPublicaModel } from '../../../shared/models/categoria-publica.model';
import { ImagePathUtil } from '../../utils/image-path.util';
import { CategoriaPublicaDto } from '../dto/catalogo/categoria-publica.dto';
import { CatalogoItemDto } from '../dto/catalogo/catalogo-item.dto';
import { PageResponseDto } from '../dto/common/page-response.dto';

/**
 * Adaptador HTTP que transforma DTOs del backend en modelos de UI del storefront.
 *
 * <p>Este adapter cumple el patrón Adapter del lado frontend: los DTOs reflejan
 * exactamente la forma en que el backend serializa las respuestas (incluyendo
 * campos como {@code imagenUrl} que pueden ser {@code null}), mientras que los
 * modelos de UI contienen datos ya procesados como {@code imagenSrc} con
 * placeholders resueltos por {@link ImagePathUtil}.</p>
 *
 * <p>La separación DTO → Model es intencional: si el backend cambia la forma
 * de sus respuestas (por ejemplo, renombra {@code imagenUrl} a {@code imageUrl}),
 * solo este adapter necesita actualizarse, sin tocar los componentes de UI.</p>
 */
@Injectable({
  providedIn: 'root',
})
export class CatalogoHttpAdapter {
  toCategoriaModel(dto: CategoriaPublicaDto): CategoriaPublicaModel {
    return {
      categoriaId: dto.categoriaId,
      nombreCategoria: dto.nombreCategoria,
      cantidadProductosVisibles: dto.cantidadProductosVisibles,
    };
  }

  toCatalogItemModel(dto: CatalogoItemDto): CatalogItemModel {
    return {
      ...dto,
      imagenSrc: ImagePathUtil.resolvePublicImage(dto.imagenUrl, dto.nombreProducto, dto.nombreCategoria),
    };
  }

  toCatalogPageModel(dto: PageResponseDto<CatalogoItemDto>): CatalogPageModel {
    return {
      items: dto.data.map((item) => this.toCatalogItemModel(item)),
      meta: dto.meta,
      correlationId: dto.correlationId,
      timestamp: dto.timestamp,
    };
  }
}
