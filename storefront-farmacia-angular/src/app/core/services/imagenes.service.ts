import { Injectable } from '@angular/core';

import { ImagePathUtil } from '../utils/image-path.util';

@Injectable({
  providedIn: 'root',
})
export class ImagenesService {
  resolveProductImage(url?: string | null): string {
    return ImagePathUtil.resolvePublicImage(url);
  }

  getProductPlaceholder(): string {
    return ImagePathUtil.getProductPlaceholder();
  }
}
