import { ElementRef } from '@angular/core';

import { ImageFallbackDirective } from '../../../shared/directives/image-fallback.directive';

describe('ImageFallbackDirective', () => {
  it('debe reemplazar la imagen rota por el fallback', () => {
    const image = document.createElement('img');
    image.src = 'https://example.com/rota.png';

    const directive = new ImageFallbackDirective(new ElementRef(image));
    directive.fallbackSrc = 'data:image/svg+xml;base64,fallback';

    directive.onError();

    expect(image.src).toContain('fallback');
  });
});
