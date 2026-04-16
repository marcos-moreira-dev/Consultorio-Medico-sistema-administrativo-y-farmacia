import { apiConfig } from '../config/api.config';

export class ImagePathUtil {
  static getProductPlaceholder(productName = 'Producto demo', categoryName = 'Farmacia'): string {
    return (
      'data:image/svg+xml;utf8,' +
      encodeURIComponent(`
        <svg xmlns="http://www.w3.org/2000/svg" width="800" height="800" viewBox="0 0 800 800">
          <rect width="800" height="800" fill="#f3f8f6"/>
          <rect x="150" y="130" width="500" height="540" rx="20" fill="#ffffff" stroke="#d6e8df" stroke-width="8"/>
          <rect x="220" y="205" width="360" height="54" rx="10" fill="#dcefe6"/>
          <rect x="220" y="290" width="280" height="28" rx="8" fill="#edf5f1"/>
          <rect x="220" y="336" width="320" height="28" rx="8" fill="#edf5f1"/>
          <rect x="220" y="458" width="180" height="64" rx="12" fill="#1f8b68"/>
          <text x="400" y="595" text-anchor="middle" fill="#0f172a" font-family="Arial, sans-serif" font-size="34" font-weight="700">${productName}</text>
          <text x="400" y="638" text-anchor="middle" fill="#567268" font-family="Arial, sans-serif" font-size="24">${categoryName}</text>
        </svg>
      `)
    );
  }

  static resolvePublicImage(url?: string | null, productName?: string, categoryName?: string): string {
    if (!url) {
      return ImagePathUtil.getProductPlaceholder(productName, categoryName);
    }

    if (/^https?:\/\//i.test(url) || url.startsWith('data:')) {
      return url;
    }

    if (url.startsWith('/media')) {
      return url;
    }

    if (url.startsWith('media/')) {
      return `${apiConfig.mediaBasePath}/${url.replace(/^media\//, '')}`;
    }

    if (url.startsWith('/')) {
      return url;
    }

    return `${apiConfig.mediaBasePath}/${url}`;
  }
}
