import { Injectable, inject } from '@angular/core';
import { Meta } from '@angular/platform-browser';

@Injectable({
  providedIn: 'root',
})
export class SeoMetaService {
  private readonly meta = inject(Meta);

  updateDescription(description: string): void {
    this.meta.updateTag({
      name: 'description',
      content: description,
    });
  }
}
