import { Pipe, PipeTransform } from '@angular/core';

import { ImagePathUtil } from '../../core/utils/image-path.util';

@Pipe({
  name: 'safeImage',
  standalone: true,
})
export class SafeImagePipe implements PipeTransform {
  transform(value: string | null | undefined): string {
    return ImagePathUtil.resolvePublicImage(value);
  }
}
