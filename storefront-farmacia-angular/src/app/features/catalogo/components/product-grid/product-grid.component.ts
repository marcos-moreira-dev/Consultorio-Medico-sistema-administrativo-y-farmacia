import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

import { ProductCardComponent } from '../../../../shared/ui/cards/product-card/product-card.component';
import { ProductSkeletonComponent } from '../../../../shared/ui/loaders/product-skeleton/product-skeleton.component';
import { CatalogItemModel } from '../../models/catalog-item.model';

@Component({
  selector: 'app-product-grid',
  standalone: true,
  imports: [CommonModule, ProductCardComponent, ProductSkeletonComponent],
  templateUrl: './product-grid.component.html',
  styleUrls: ['./product-grid.component.css'],
})
export class ProductGridComponent {
  @Input() items: CatalogItemModel[] = [];
  @Input() loading = false;
  @Input() skeletonCount = 6;

  protected skeletonItems(): number[] {
    return Array.from({ length: this.skeletonCount }, (_, index) => index);
  }

  protected trackByIndex(index: number): number {
    return index;
  }

  protected trackByProductId(_index: number, item: CatalogItemModel): number {
    return item.productoId;
  }
}
