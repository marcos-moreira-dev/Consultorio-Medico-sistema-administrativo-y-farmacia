import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';

import { BreadcrumbItemModel } from '../../../models/breadcrumb-item.model';

@Component({
  selector: 'app-breadcrumbs',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './breadcrumbs.component.html',
  styleUrls: ['./breadcrumbs.component.css'],
})
export class BreadcrumbsComponent {
  @Input() items: BreadcrumbItemModel[] = [];

  protected trackByIndex(index: number): number {
    return index;
  }
}
