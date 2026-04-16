import { Directive, ElementRef, HostListener, Input } from '@angular/core';

import { ImagePathUtil } from '../../core/utils/image-path.util';

@Directive({
  selector: 'img[appImageFallback]',
  standalone: true,
})
export class ImageFallbackDirective {
  @Input() fallbackSrc = ImagePathUtil.getProductPlaceholder();

  constructor(private readonly elementRef: ElementRef<HTMLImageElement>) {}

  @HostListener('error')
  onError(): void {
    const nativeElement = this.elementRef.nativeElement;

    if (nativeElement.src !== this.fallbackSrc) {
      nativeElement.src = this.fallbackSrc;
    }
  }
}
