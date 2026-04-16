import { CommonModule } from '@angular/common';
import { Component, DestroyRef, inject } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { FormsModule } from '@angular/forms';
import { NavigationEnd, Router, RouterLink, RouterLinkActive } from '@angular/router';
import { debounceTime, distinctUntilChanged, filter, startWith } from 'rxjs/operators';
import { Subject } from 'rxjs';

/**
 * Navbar de navegación pública con búsqueda debounced y panel colapsable.
 *
 * La búsqueda aplica un debounce de 400 ms para evitar disparos excesivos
 * al backend cuando el usuario escribe términos largos o corrige texto.
 * Esto reduce la carga innecesaria en el catálogo y mejora la percepción
 * de fluidez en la interfaz.
 */
@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, RouterLinkActive],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent {
  private readonly router = inject(Router);
  private readonly destroyRef = inject(DestroyRef);

  /**
   * Subject que emite cada vez que el usuario modifica el término de búsqueda.
   * Se debouncea antes de navegar para no saturar el catálogo con requests.
   */
  private readonly searchSubject = new Subject<string>();

  protected searchTerm = '';
  protected menuOpen = false;
  protected isSearching = false;

  constructor() {
    // Debounce de búsqueda: espera 400 ms sin cambios antes de navegar
    this.searchSubject
      .pipe(
        debounceTime(400),
        distinctUntilChanged(),
        takeUntilDestroyed(this.destroyRef),
      )
      .subscribe((q) => {
        this.isSearching = false;
        void this.router.navigate(['/catalogo'], {
          queryParams: q ? { q } : {},
        });
      });

    // Sincronizar término de búsqueda cuando cambia la ruta
    this.router.events
      .pipe(
        filter((event): event is NavigationEnd => event instanceof NavigationEnd),
        startWith(null),
        takeUntilDestroyed(this.destroyRef),
      )
      .subscribe(() => {
        this.syncSearchTermFromUrl();
        this.menuOpen = false;
      });
  }

  /**
   * Navega al catálogo con el término de búsqueda actual, aplicando debounce.
   */
  onSearchInput(): void {
    this.isSearching = true;
    this.searchSubject.next(this.searchTerm.trim());
  }

  /**
   * Búsqueda inmediata (cuando el usuario presiona Enter explícitamente).
   * Cancela el debounce y navega de inmediato.
   */
  onSubmit(): void {
    const q = this.searchTerm.trim();
    this.menuOpen = false;
    this.isSearching = false;

    void this.router.navigate(['/catalogo'], {
      queryParams: q ? { q } : {},
    });
  }

  toggleMenu(): void {
    this.menuOpen = !this.menuOpen;
  }

  closeMenu(): void {
    this.menuOpen = false;
  }

  private syncSearchTermFromUrl(): void {
    const urlTree = this.router.parseUrl(this.router.url);
    const primarySegments = urlTree.root.children['primary']?.segments.map((segment) => segment.path) ?? [];
    const isCatalogRoute = primarySegments[0] === 'catalogo';
    const q = isCatalogRoute && typeof urlTree.queryParams['q'] === 'string' ? urlTree.queryParams['q'] : '';

    this.searchTerm = q;
    this.isSearching = false;
  }
}
