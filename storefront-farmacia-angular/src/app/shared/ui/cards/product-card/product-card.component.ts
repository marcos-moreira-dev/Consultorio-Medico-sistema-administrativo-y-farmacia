import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';

import { CatalogItemModel } from '../../../../features/catalogo/models/catalog-item.model';
import { ImageFallbackDirective } from '../../../directives/image-fallback.directive';
import { MoneyPipe } from '../../../pipes/money.pipe';
import { AvailabilityBadgeComponent } from '../../badges/availability-badge/availability-badge.component';

@Component({
  selector: 'app-product-card',
  standalone: true,
  imports: [CommonModule, RouterLink, MoneyPipe, ImageFallbackDirective, AvailabilityBadgeComponent],
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.css'],
})
export class ProductCardComponent {
  @Input({ required: true }) product!: CatalogItemModel;
}
