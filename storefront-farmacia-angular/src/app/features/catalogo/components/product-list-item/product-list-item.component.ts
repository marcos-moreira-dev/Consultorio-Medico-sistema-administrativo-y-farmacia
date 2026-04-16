import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';

import { MoneyPipe } from '../../../../shared/pipes/money.pipe';
import { SafeImagePipe } from '../../../../shared/pipes/safe-image.pipe';
import { AvailabilityBadgeComponent } from '../../../../shared/ui/badges/availability-badge/availability-badge.component';
import { CatalogItemModel } from '../../models/catalog-item.model';

/**
 * Variante compacta tipo fila para una futura vista de lista del catálogo.
 * No es la vista principal actual, pero queda lista para reuso sin permanecer como placeholder.
 */
@Component({
  selector: 'app-product-list-item',
  standalone: true,
  imports: [CommonModule, RouterLink, AvailabilityBadgeComponent, MoneyPipe, SafeImagePipe],
  templateUrl: './product-list-item.component.html',
  styleUrls: ['./product-list-item.component.css'],
})
export class ProductListItemComponent {
  @Input({ required: true }) product!: CatalogItemModel;
}
