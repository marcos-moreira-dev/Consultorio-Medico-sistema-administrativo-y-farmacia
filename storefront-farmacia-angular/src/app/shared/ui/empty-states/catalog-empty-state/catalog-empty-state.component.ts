import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-catalog-empty-state',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './catalog-empty-state.component.html',
  styleUrls: ['./catalog-empty-state.component.css'],
})
export class CatalogEmptyStateComponent {
  @Input() title = 'No encontramos productos con esos filtros';
  @Input() message =
    'Prueba con otra búsqueda, quita un filtro o vuelve al catálogo completo.';
}
