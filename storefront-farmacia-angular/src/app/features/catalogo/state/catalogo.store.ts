import { Injectable, Signal, computed, signal } from '@angular/core';

import { CategoriaPublicaModel } from '../../../shared/models/categoria-publica.model';
import { CatalogFilterModel } from '../models/catalog-filter.model';
import { CatalogPageModel } from '../models/catalog-page.model';
import { CatalogoState } from './catalogo.state';

/**
 * Filtros por defecto del catálogo: página 1, 12 productos por vista.
 *
 * Este valor de 12 se eligió como equilibrio entre densidad de información
 * y rendimiento en móviles, evitando scroll excesivo en pantallas pequeñas.
 */
const DEFAULT_FILTERS: CatalogFilterModel = {
  page: 1,
  limit: 12,
};

const INITIAL_STATE: CatalogoState = {
  filtros: DEFAULT_FILTERS,
  categorias: [],
  loading: true,
};

/**
 * Store reactivo del catálogo público usando Signals de Angular 20.
 *
 * <p>Esta store concentra todo el estado de la página de catálogo:
 * filtros activos, categorías disponibles, página cargada, estado de carga
 * y mensajes de error. Al usar {@code signal} + {@code computed}, los
 * componentes se actualizan automáticamente cuando el estado cambia sin
 * necesidad de ChangeDetectionStrategy.OnPush manual.</p>
 *
 * <p>El patrón de actualización es inmutable: {@code patchState} crea un
 * nuevo objeto de estado en cada operación, evitando mutaciones accidentales
 * que podrían romper la reactividad de Angular Signals.</p>
 *
 * <p>Los filtros se persisten en la URL mediante query params, por lo que
 * esta store solo mantiene el estado durante la vida del componente.
 * Si el usuario navega fuera del catálogo y regresa, los filtros se
 * reconstruyen desde la URL.</p>
 */
@Injectable({
  providedIn: 'root',
})
export class CatalogoStore {
  private readonly state = signal<CatalogoState>(INITIAL_STATE);

  readonly filtros: Signal<CatalogFilterModel> = computed(() => this.state().filtros);
  readonly categorias: Signal<CategoriaPublicaModel[]> = computed(() => this.state().categorias);
  readonly page: Signal<CatalogPageModel | undefined> = computed(() => this.state().page);
  readonly loading: Signal<boolean> = computed(() => this.state().loading);
  readonly errorMessage: Signal<string> = computed(() => this.state().errorMessage ?? '');

  setFiltros(filtros: CatalogFilterModel): void {
    this.patchState({
      filtros,
    });
  }

  setCategorias(categorias: CategoriaPublicaModel[]): void {
    this.patchState({
      categorias,
    });
  }

  startLoading(): void {
    this.patchState({
      loading: true,
      errorMessage: undefined,
    });
  }

  setPage(page: CatalogPageModel): void {
    this.patchState({
      page,
      loading: false,
      errorMessage: undefined,
    });
  }

  setError(errorMessage: string): void {
    this.patchState({
      page: undefined,
      loading: false,
      errorMessage,
    });
  }

  reset(): void {
    this.state.set(INITIAL_STATE);
  }

  private patchState(patch: Partial<CatalogoState>): void {
    this.state.update((current) => ({
      ...current,
      ...patch,
    }));
  }
}
