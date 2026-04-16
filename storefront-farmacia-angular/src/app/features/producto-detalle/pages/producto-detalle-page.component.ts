import { CommonModule } from '@angular/common';
import { Component, DestroyRef, inject } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { ActivatedRoute, RouterLink } from '@angular/router';

import { CatalogNavigationService } from '../../../core/services/catalog-navigation.service';
import { AppError } from '../../../core/errors/app-error';
import { PageTitleService } from '../../../core/services/page-title.service';
import { RouteAnalyticsService } from '../../../core/services/route-analytics.service';
import { SeoMetaService } from '../../../core/services/seo-meta.service';
import { BreadcrumbItemModel } from '../../../shared/models/breadcrumb-item.model';
import { CatalogEmptyStateComponent } from '../../../shared/ui/empty-states/catalog-empty-state/catalog-empty-state.component';
import { ProductSkeletonComponent } from '../../../shared/ui/loaders/product-skeleton/product-skeleton.component';
import { BreadcrumbsComponent } from '../../../shared/ui/navigation/breadcrumbs/breadcrumbs.component';
import { SectionHeaderComponent } from '../../../shared/ui/section-header/section-header.component';
import { ProductGridComponent } from '../../catalogo/components/product-grid/product-grid.component';
import { CatalogItemModel } from '../../catalogo/models/catalog-item.model';
import { ProductGalleryComponent } from '../components/product-gallery/product-gallery.component';
import { ProductInfoComponent } from '../components/product-info/product-info.component';
import { RelatedProductsComponent } from '../components/related-products/related-products.component';
import { ProductoDetalleFacade } from '../facade/producto-detalle.facade';
import { ProductoDetalleModel } from '../models/producto-detalle.model';
import { ProductoDetalleStore } from '../state/producto-detalle.store';

@Component({
  selector: 'app-producto-detalle-page',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    CatalogEmptyStateComponent,
    ProductSkeletonComponent,
    ProductGridComponent,
    BreadcrumbsComponent,
    SectionHeaderComponent,
    ProductGalleryComponent,
    ProductInfoComponent,
    RelatedProductsComponent,
  ],
  templateUrl: './producto-detalle-page.component.html',
  styleUrls: ['./producto-detalle-page.component.css'],
})
export class ProductoDetallePageComponent {
  private readonly route = inject(ActivatedRoute);
  private readonly facade = inject(ProductoDetalleFacade);
  private readonly store = inject(ProductoDetalleStore);
  private readonly destroyRef = inject(DestroyRef);
  private readonly pageTitleService = inject(PageTitleService);
  private readonly seoMetaService = inject(SeoMetaService);
  private readonly routeAnalyticsService = inject(RouteAnalyticsService);
  private readonly catalogNavigationService = inject(CatalogNavigationService);

  protected currentProductId?: number;
  protected relatedProductsLoading = false;

  protected get catalogLink() {
    return this.catalogNavigationService.getLastCatalogLink();
  }

  protected get breadcrumbs(): BreadcrumbItemModel[] {
    const items: BreadcrumbItemModel[] = [
      {
        label: 'Inicio',
        routerLink: ['/'],
      },
      {
        label: 'Catálogo',
        routerLink: this.catalogLink.routerLink,
        queryParams: this.catalogLink.queryParams,
      },
    ];

    if (this.product?.nombreCategoria) {
      items.push({
        label: this.product.nombreCategoria,
        routerLink: ['/catalogo'],
        queryParams: {
          categoriaId: this.product.categoriaId,
        },
      });
    }

    items.push({
      label: this.product?.nombreProducto || 'Detalle del producto',
      current: true,
    });

    return items;
  }

  protected get product(): ProductoDetalleModel | undefined {
    return this.store.producto();
  }

  protected get relatedProducts(): CatalogItemModel[] {
    return this.store.relacionados();
  }

  protected get loading(): boolean {
    return this.store.loading();
  }

  protected get errorMessage(): string {
    return this.store.errorMessage();
  }

  constructor() {
    this.pageTitleService.setTitle('Detalle de producto');
    this.seoMetaService.updateDescription(
      'Consulta la ficha pública del producto y explora opciones relacionadas dentro del catálogo visible.',
    );
    this.routeAnalyticsService.trackView('producto_detalle_page_viewed');

    this.route.paramMap.pipe(takeUntilDestroyed(this.destroyRef)).subscribe((paramMap) => {
      const productoId = Number(paramMap.get('productoId'));

      if (!Number.isInteger(productoId) || productoId < 1) {
        this.currentProductId = undefined;
        this.store.setError('El identificador del producto no es válido.');
        return;
      }

      this.currentProductId = productoId;
      this.loadProduct(productoId);
    });
  }

  protected retryLoad(): void {
    if (!this.currentProductId) {
      return;
    }

    this.loadProduct(this.currentProductId);
  }

  private loadProduct(productoId: number): void {
    this.store.startLoading();
    this.relatedProductsLoading = false;

    this.facade
      .cargarProducto(productoId)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (product) => {
          this.store.setProducto(product);
          this.pageTitleService.setTitle(product.nombreProducto);
          this.seoMetaService.updateDescription(
            `${product.nombreProducto}. ${product.presentacion}. Estado público: ${
              product.disponible ? 'disponible' : 'no disponible'
            }.`,
          );
          this.loadRelatedProducts(product);
        },
        error: (error: AppError) => {
          this.store.setError(error.message);
        },
      });
  }

  private loadRelatedProducts(product: ProductoDetalleModel): void {
    this.relatedProductsLoading = true;

    this.facade
      .cargarRelacionados(product.categoriaId, product.productoId)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (items) => {
          this.store.setRelacionados(items);
          this.relatedProductsLoading = false;
        },
        error: () => {
          this.store.setRelacionados([]);
          this.relatedProductsLoading = false;
        },
      });
  }
}
