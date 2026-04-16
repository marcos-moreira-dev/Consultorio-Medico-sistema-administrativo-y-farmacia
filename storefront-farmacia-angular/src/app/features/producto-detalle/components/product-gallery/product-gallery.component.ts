import { Component, Input } from '@angular/core';

import { ImageFallbackDirective } from '../../../../shared/directives/image-fallback.directive';

@Component({
  selector: 'app-product-gallery',
  standalone: true,
  imports: [ImageFallbackDirective],
  templateUrl: './product-gallery.component.html',
  styleUrls: ['./product-gallery.component.css'],
})
export class ProductGalleryComponent {
  @Input({ required: true }) imageSrc = '';
  @Input({ required: true }) alt = '';
}
