import { Injectable, Signal, computed, signal } from '@angular/core';

import { CatalogItemModel } from '../../catalogo/models/catalog-item.model';
import { ProductoDetalleModel } from '../models/producto-detalle.model';
import { ProductoDetalleState } from './producto-detalle.state';

const INITIAL_STATE: ProductoDetalleState = {
  producto: undefined,
  relacionados: [],
  loading: true,
  errorMessage: undefined,
};

@Injectable({
  providedIn: 'root',
})
export class ProductoDetalleStore {
  private readonly state = signal<ProductoDetalleState>(INITIAL_STATE);

  readonly producto: Signal<ProductoDetalleModel | undefined> = computed(() => this.state().producto);
  readonly relacionados: Signal<CatalogItemModel[]> = computed(() => this.state().relacionados);
  readonly loading: Signal<boolean> = computed(() => this.state().loading);
  readonly errorMessage: Signal<string> = computed(() => this.state().errorMessage ?? '');

  startLoading(): void {
    this.patchState({
      producto: undefined,
      relacionados: [],
      loading: true,
      errorMessage: undefined,
    });
  }

  setProducto(producto: ProductoDetalleModel): void {
    this.patchState({
      producto,
      loading: false,
      errorMessage: undefined,
    });
  }

  setRelacionados(relacionados: CatalogItemModel[]): void {
    this.patchState({
      relacionados,
    });
  }

  setError(errorMessage: string): void {
    this.patchState({
      producto: undefined,
      relacionados: [],
      loading: false,
      errorMessage,
    });
  }

  reset(): void {
    this.state.set(INITIAL_STATE);
  }

  private patchState(patch: Partial<ProductoDetalleState>): void {
    this.state.update((current) => ({
      ...current,
      ...patch,
    }));
  }
}
