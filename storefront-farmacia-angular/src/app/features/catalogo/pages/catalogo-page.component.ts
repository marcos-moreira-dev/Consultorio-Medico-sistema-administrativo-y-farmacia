import { CommonModule } from '@angular/common';
import { Component, DestroyRef, computed, inject } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { ActivatedRoute, Router } from '@angular/router';

import { AppError } from '../../../core/errors/app-error';
import { PageTitleService } from '../../../core/services/page-title.service';
import { RouteAnalyticsService } from '../../../core/services/route-analytics.service';
import { SeoMetaService } from '../../../core/services/seo-meta.service';
import { QueryParamsUtil } from '../../../core/utils/query-params.util';
import { BreadcrumbItemModel } from '../../../shared/models/breadcrumb-item.model';
import { CategoriaPublicaModel } from '../../../shared/models/categoria-publica.model';
import { CatalogEmptyStateComponent } from '../../../shared/ui/empty-states/catalog-empty-state/catalog-empty-state.component';
import { BreadcrumbsComponent } from '../../../shared/ui/navigation/breadcrumbs/breadcrumbs.component';
import { PaginationComponent } from '../../../shared/ui/navigation/pagination/pagination.component';
import { SectionHeaderComponent } from '../../../shared/ui/section-header/section-header.component';
import { CatalogToolbarComponent } from '../components/catalog-toolbar/catalog-toolbar.component';
import { ProductGridComponent } from '../components/product-grid/product-grid.component';
import { CatalogoFacade } from '../facade/catalogo.facade';
import { CatalogFilterModel } from '../models/catalog-filter.model';
import { CatalogPageModel } from '../models/catalog-page.model';
import { CatalogoStore } from '../state/catalogo.store';

interface ActiveFilterChip {
  key: 'q' | 'categoriaId' | 'sort' | 'limit';
  label: string;
}

@Component({
  selector: 'app-catalogo-page',
  standalone: true,
  imports: [
    CommonModule,
    BreadcrumbsComponent,
    SectionHeaderComponent,
    CatalogToolbarComponent,
    ProductGridComponent,
    CatalogEmptyStateComponent,
    PaginationComponent,
  ],
  templateUrl: './catalogo-page.component.html',
  styleUrls: ['./catalogo-page.component.css'],
})
export class CatalogoPageComponent {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly catalogoFacade = inject(CatalogoFacade);
  private readonly catalogoStore = inject(CatalogoStore);
  private readonly destroyRef = inject(DestroyRef);
  private readonly pageTitleService = inject(PageTitleService);
  private readonly seoMetaService = inject(SeoMetaService);
  private readonly routeAnalyticsService = inject(RouteAnalyticsService);

  protected readonly defaultFilters: CatalogFilterModel = {
    page: 1,
    limit: 12,
  };

  protected readonly breadcrumbs: BreadcrumbItemModel[] = [
    {
      label: 'Inicio',
      routerLink: ['/'],
    },
    {
      label: 'Catálogo',
      current: true,
    },
  ];

  protected readonly hasActiveFilters = computed(() => this.activeFilterSummary().length > 0);

  protected get filters(): CatalogFilterModel {
    return this.catalogoStore.filtros();
  }

  protected get categories(): CategoriaPublicaModel[] {
    return this.catalogoStore.categorias();
  }

  protected get pageData(): CatalogPageModel | undefined {
    return this.catalogoStore.page();
  }

  protected get loading(): boolean {
    return this.catalogoStore.loading();
  }

  protected get errorMessage(): string {
    return this.catalogoStore.errorMessage();
  }

  constructor() {
    this.pageTitleService.setTitle('Catálogo');
    this.seoMetaService.updateDescription(
      'Filtra productos visibles por categoría, búsqueda y orden dentro del catálogo público de farmacia.',
    );
    this.routeAnalyticsService.trackView('catalogo_page_viewed');

    this.route.queryParams
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((queryParams) => {
        const normalizedQuery = QueryParamsUtil.normalizeCatalogQuery(queryParams);
        this.catalogoStore.setFiltros({
          ...this.defaultFilters,
          ...normalizedQuery,
        });
        this.loadCatalog();
      });

    this.loadCategories();
  }

  protected applyFilters(partialFilters: Partial<CatalogFilterModel>): void {
    const nextFilters = {
      ...this.filters,
      ...partialFilters,
      page: partialFilters.page ?? 1,
    };

    void this.router.navigate([], {
      relativeTo: this.route,
      queryParams: QueryParamsUtil.toCatalogQueryParams(nextFilters),
    });
  }

  protected clearFilters(): void {
    void this.router.navigate([], {
      relativeTo: this.route,
      queryParams: {},
    });
  }

  protected clearFilterChip(chip: ActiveFilterChip): void {
    switch (chip.key) {
      case 'q':
        this.applyFilters({ q: undefined });
        break;
      case 'categoriaId':
        this.applyFilters({ categoriaId: undefined });
        break;
      case 'sort':
        this.applyFilters({ sortBy: undefined, sortDirection: undefined });
        break;
      case 'limit':
        this.applyFilters({ limit: this.defaultFilters.limit });
        break;
    }
  }

  protected goToPage(page: number): void {
    this.applyFilters({
      page,
    });
  }

  protected retryCatalog(): void {
    this.loadCatalog();
  }

  protected activeFilterSummary(): ActiveFilterChip[] {
    const items: ActiveFilterChip[] = [];

    if (this.filters.q) {
      items.push({
        key: 'q',
        label: `Búsqueda: “${this.filters.q}”`,
      });
    }

    const category = this.categories.find((item) => item.categoriaId === this.filters.categoriaId);

    if (category) {
      items.push({
        key: 'categoriaId',
        label: `Categoría: ${category.nombreCategoria}`,
      });
    }

    if (this.filters.sortBy) {
      items.push({
        key: 'sort',
        label: `Orden: ${this.readableSortLabel(this.filters.sortBy, this.filters.sortDirection)}`,
      });
    }

    if (this.filters.limit && this.filters.limit !== this.defaultFilters.limit) {
      items.push({
        key: 'limit',
        label: `Página: ${this.filters.limit} por vista`,
      });
    }

    return items;
  }

  protected resultRangeLabel(): string {
    if (!this.pageData || this.pageData.meta.totalItems === 0) {
      return 'No hay resultados visibles con los filtros actuales.';
    }

    const start = (this.pageData.meta.page - 1) * this.pageData.meta.limit + 1;
    const end = start + this.pageData.items.length - 1;

    return `Mostrando ${start}-${end} de ${this.pageData.meta.totalItems}.`;
  }

  protected trackByChip(_index: number, chip: ActiveFilterChip): string {
    return chip.key;
  }

  private loadCategories(): void {
    this.catalogoFacade
      .listarCategorias()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (categories) => {
          this.catalogoStore.setCategorias(categories);
        },
        error: () => {
          this.catalogoStore.setCategorias([]);
        },
      });
  }

  private loadCatalog(): void {
    this.catalogoStore.startLoading();

    this.catalogoFacade
      .cargarCatalogo(this.filters)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (page) => {
          this.catalogoStore.setPage(page);
        },
        error: (error: AppError) => {
          this.catalogoStore.setError(error.message);
        },
      });
  }

  private readableSortLabel(
    sortBy: CatalogFilterModel['sortBy'],
    sortDirection: CatalogFilterModel['sortDirection'],
  ): string {
    const field =
      sortBy === 'nombreProducto'
        ? 'nombre'
        : sortBy === 'precioVisible'
          ? 'precio'
          : sortBy === 'fechaActualizacion'
            ? 'actualización'
            : 'criterio';

    const direction = sortDirection === 'ASC' ? 'ascendente' : sortDirection === 'DESC' ? 'descendente' : 'automática';

    return `${field} · ${direction}`;
  }
}
