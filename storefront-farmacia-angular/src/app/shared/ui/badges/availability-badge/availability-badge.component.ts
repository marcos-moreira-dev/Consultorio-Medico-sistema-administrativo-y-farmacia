import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-availability-badge',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './availability-badge.component.html',
  styleUrls: ['./availability-badge.component.css'],
})
export class AvailabilityBadgeComponent {
  @Input() estado: string = 'DISPONIBLE';
  @Input() disponible = true;

  get label(): string {
    if (this.estado === 'AGOTADO') {
      return 'Agotado';
    }

    if (!this.disponible || this.estado === 'NO_DISPONIBLE') {
      return 'No disponible';
    }

    return 'Disponible';
  }

  get toneClass(): string {
    if (this.estado === 'AGOTADO') {
      return 'availability-badge--warning';
    }

    if (!this.disponible || this.estado === 'NO_DISPONIBLE') {
      return 'availability-badge--muted';
    }

    return 'availability-badge--success';
  }
}
