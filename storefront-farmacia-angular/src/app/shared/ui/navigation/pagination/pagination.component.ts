import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

type PageToken = number | 'ellipsis-left' | 'ellipsis-right';

@Component({
  selector: 'app-pagination',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css'],
})
export class PaginationComponent {
  @Input() page = 1;
  @Input() totalPages = 1;
  @Input() hasPreviousPage = false;
  @Input() hasNextPage = false;

  @Output() pageChange = new EventEmitter<number>();

  protected tokens(): PageToken[] {
    if (this.totalPages <= 7) {
      return Array.from({ length: this.totalPages }, (_, index) => index + 1);
    }

    const pages = new Set<number>([1, this.totalPages, this.page - 1, this.page, this.page + 1]);
    const normalized = Array.from(pages)
      .filter((page) => page >= 1 && page <= this.totalPages)
      .sort((left, right) => left - right);

    const tokens: PageToken[] = [];

    normalized.forEach((current, index) => {
      const previous = normalized[index - 1];

      if (index > 0 && previous !== undefined && current - previous > 1) {
        tokens.push(previous === 1 ? 'ellipsis-left' : 'ellipsis-right');
      }

      tokens.push(current);
    });

    return tokens;
  }

  protected isCurrent(token: PageToken): boolean {
    return typeof token === 'number' && token === this.page;
  }

  protected isEllipsis(token: PageToken): boolean {
    return typeof token !== 'number';
  }

  protected emitToken(token: PageToken): void {
    if (typeof token !== 'number') {
      return;
    }

    this.emitPage(token);
  }

  protected emitPage(page: number): void {
    if (page < 1 || page > this.totalPages || page === this.page) {
      return;
    }

    this.pageChange.emit(page);
  }

  protected trackByToken(_index: number, token: PageToken): string {
    return String(token);
  }
}
