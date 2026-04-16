import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';

import { MoneyPipe } from '../../../../shared/pipes/money.pipe';
import { AvailabilityBadgeComponent } from '../../../../shared/ui/badges/availability-badge/availability-badge.component';
import { ProductoDetalleModel } from '../../models/producto-detalle.model';

@Component({
  selector: 'app-product-info',
  standalone: true,
  imports: [CommonModule, RouterLink, MoneyPipe, AvailabilityBadgeComponent],
  templateUrl: './product-info.component.html',
  styleUrls: ['./product-info.component.css'],
})
export class ProductInfoComponent {
  @Input({ required: true }) product!: ProductoDetalleModel;
  @Input() catalogRouterLink: string | readonly unknown[] = ['/catalogo'];
  @Input() catalogQueryParams?: Record<string, string | number | boolean | undefined>;

  protected formatDate(value: string): string {
    return new Intl.DateTimeFormat('es-EC', {
      day: '2-digit',
      month: 'short',
      year: 'numeric',
    }).format(new Date(value));
  }

  protected disponibilidadLabel(): string {
    if (this.product.disponible) {
      return 'Disponible para consulta pública';
    }

    return 'Visible, pero actualmente no disponible';
  }
}
