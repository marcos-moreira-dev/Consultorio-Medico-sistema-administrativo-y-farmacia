import { Injectable, inject, signal } from '@angular/core';
import { NavigationEnd, Params, Router } from '@angular/router';
import { filter, startWith } from 'rxjs/operators';

const LAST_CATALOG_URL_STORAGE_KEY = 'laf:lastCatalogUrl';

export interface CatalogNavigationLink {
  routerLink: readonly string[];
  queryParams?: Params;
}

@Injectable({
  providedIn: 'root',
})
export class CatalogNavigationService {
  private readonly router = inject(Router);
  private readonly lastCatalogUrlSignal = signal('/catalogo');

  constructor() {
    this.restorePersistedCatalogUrl();

    this.router.events
      .pipe(
        filter((event): event is NavigationEnd => event instanceof NavigationEnd),
        startWith(null),
      )
      .subscribe(() => {
        this.captureCurrentCatalogUrl();
      });
  }

  getLastCatalogUrl(): string {
    return this.lastCatalogUrlSignal();
  }

  getLastCatalogLink(): CatalogNavigationLink {
    const tree = this.router.parseUrl(this.lastCatalogUrlSignal());
    const queryParams = Object.keys(tree.queryParams).length > 0 ? tree.queryParams : undefined;

    return {
      routerLink: ['/catalogo'],
      queryParams,
    };
  }

  private captureCurrentCatalogUrl(): void {
    const currentUrl = this.router.url;

    if (!this.isCatalogUrl(currentUrl)) {
      return;
    }

    this.lastCatalogUrlSignal.set(currentUrl);
    this.persistCatalogUrl(currentUrl);
  }

  private restorePersistedCatalogUrl(): void {
    const persistedUrl = this.readPersistedCatalogUrl();

    if (!persistedUrl || !this.isCatalogUrl(persistedUrl)) {
      return;
    }

    this.lastCatalogUrlSignal.set(persistedUrl);
  }

  private readPersistedCatalogUrl(): string | null {
    if (typeof sessionStorage === 'undefined') {
      return null;
    }

    return sessionStorage.getItem(LAST_CATALOG_URL_STORAGE_KEY);
  }

  private persistCatalogUrl(url: string): void {
    if (typeof sessionStorage === 'undefined') {
      return;
    }

    sessionStorage.setItem(LAST_CATALOG_URL_STORAGE_KEY, url);
  }

  private isCatalogUrl(url: string): boolean {
    const tree = this.router.parseUrl(url);
    const primarySegments = tree.root.children['primary']?.segments.map((segment) => segment.path) ?? [];

    return primarySegments[0] === 'catalogo';
  }
}
