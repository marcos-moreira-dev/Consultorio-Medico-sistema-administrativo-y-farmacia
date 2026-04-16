import { CommonModule } from '@angular/common';
import { Component, DestroyRef, inject } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { RouterLink } from '@angular/router';

import { AppError } from '../../../core/errors/app-error';
import { PageTitleService } from '../../../core/services/page-title.service';
import { RouteAnalyticsService } from '../../../core/services/route-analytics.service';
import { SeoMetaService } from '../../../core/services/seo-meta.service';
import { BreadcrumbItemModel } from '../../../shared/models/breadcrumb-item.model';
import { CategoriaPublicaModel } from '../../../shared/models/categoria-publica.model';
import { CatalogEmptyStateComponent } from '../../../shared/ui/empty-states/catalog-empty-state/catalog-empty-state.component';
import { BreadcrumbsComponent } from '../../../shared/ui/navigation/breadcrumbs/breadcrumbs.component';
import { SectionHeaderComponent } from '../../../shared/ui/section-header/section-header.component';
import { ProductGridComponent } from '../../catalogo/components/product-grid/product-grid.component';
import { CatalogItemModel } from '../../catalogo/models/catalog-item.model';
import { FeaturedCategoriesComponent } from '../components/featured-categories/featured-categories.component';
import { HeroBannerComponent } from '../components/hero-banner/hero-banner.component';
import { HomeFacade } from '../facade/home.facade';

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    BreadcrumbsComponent,
    HeroBannerComponent,
    SectionHeaderComponent,
    FeaturedCategoriesComponent,
    ProductGridComponent,
    CatalogEmptyStateComponent,
  ],
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css'],
})
export class HomePageComponent {
  private readonly homeFacade = inject(HomeFacade);
  private readonly destroyRef = inject(DestroyRef);
  private readonly pageTitleService = inject(PageTitleService);
  private readonly seoMetaService = inject(SeoMetaService);
  private readonly routeAnalyticsService = inject(RouteAnalyticsService);

  protected readonly breadcrumbs: BreadcrumbItemModel[] = [
    {
      label: 'Inicio',
      current: true,
    },
  ];

  protected categories: CategoriaPublicaModel[] = [];
  protected featuredProducts: CatalogItemModel[] = [];
  protected loading = true;
  protected errorMessage = '';

  constructor() {
    this.pageTitleService.setTitle('Inicio');
    this.seoMetaService.updateDescription(
      'Explora categorías visibles, productos públicos y detalle rápido en La Alameda Farma.',
    );
    this.routeAnalyticsService.trackView('home_page_viewed');
    this.loadHome();
  }

  protected get categoryCount(): number {
    return this.categories.length;
  }

  protected get visibleProductCount(): number {
    return this.categories.reduce((total, item) => total + item.cantidadProductosVisibles, 0);
  }

  protected get topCategoryName(): string {
    if (this.categories.length === 0) {
      return 'Catálogo en preparación';
    }

    return this.categories.reduce((current, candidate) =>
      candidate.cantidadProductosVisibles > current.cantidadProductosVisibles ? candidate : current,
    ).nombreCategoria;
  }

  protected retryLoad(): void {
    this.loadHome();
  }

  private loadHome(): void {
    this.loading = true;
    this.errorMessage = '';

    this.homeFacade
      .cargarHome()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (data) => {
          this.categories = data.categorias.slice(0, 6);
          this.featuredProducts = data.productosDestacados;
          this.loading = false;
        },
        error: (error: AppError) => {
          this.categories = [];
          this.featuredProducts = [];
          this.errorMessage = error.message;
          this.loading = false;
        },
      });
  }
}
