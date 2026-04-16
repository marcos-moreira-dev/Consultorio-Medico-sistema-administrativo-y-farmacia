import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnChanges, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { CategoriaPublicaModel } from '../../../../shared/models/categoria-publica.model';
import { CatalogFilterModel } from '../../models/catalog-filter.model';

@Component({
  selector: 'app-catalog-toolbar',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './catalog-toolbar.component.html',
  styleUrls: ['./catalog-toolbar.component.css'],
})
export class CatalogToolbarComponent implements OnChanges {
  @Input() categories: CategoriaPublicaModel[] = [];
  @Input() query: CatalogFilterModel = {
    page: 1,
    limit: 12,
  };

  @Output() filtersChange = new EventEmitter<Partial<CatalogFilterModel>>();
  @Output() clearFilters = new EventEmitter<void>();

  protected readonly pageSizeOptions = [12, 24, 36];

  protected draft: CatalogFilterModel = {
    page: 1,
    limit: 12,
  };

  ngOnChanges(): void {
    this.draft = {
      ...this.query,
      limit: this.query.limit ?? 12,
    };
  }

  submit(): void {
    this.filtersChange.emit({
      q: this.draft.q?.trim() || undefined,
      categoriaId: this.draft.categoriaId || undefined,
      sortBy: this.draft.sortBy || undefined,
      sortDirection: this.draft.sortDirection || undefined,
      page: 1,
      limit: this.draft.limit || 12,
    });
  }

  onClear(): void {
    this.clearFilters.emit();
  }
}
