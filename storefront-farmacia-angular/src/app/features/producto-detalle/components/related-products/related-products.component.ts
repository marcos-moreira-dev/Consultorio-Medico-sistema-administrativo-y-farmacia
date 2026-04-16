import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

import { ProductCardComponent } from '../../../../shared/ui/cards/product-card/product-card.component';
import { CatalogItemModel } from '../../../catalogo/models/catalog-item.model';

@Component({
  selector: 'app-related-products',
  standalone: true,
  imports: [CommonModule, ProductCardComponent],
  templateUrl: './related-products.component.html',
  styleUrls: ['./related-products.component.css'],
})
export class RelatedProductsComponent {
  @Input() items: CatalogItemModel[] = [];

  protected trackByProductId(_index: number, item: CatalogItemModel): number {
    return item.productoId;
  }
}
