import { MediaPathResolver } from '../../../src/app/modules/media/storage/media-path.resolver';

describe('MediaPathResolver', () => {
  let resolver: MediaPathResolver;

  beforeEach(() => {
    resolver = new MediaPathResolver();
  });

  it('debe construir la URL pública sin duplicar /media cuando la base ya lo contiene', () => {
    const result = resolver.buildPublicUrl(
      'http://localhost:3001/media',
      'productos/imagenes/2026/04/demo.png',
    );

    expect(result).toBe(
      'http://localhost:3001/media/productos/imagenes/2026/04/demo.png',
    );
  });

  it('debe añadir /media cuando la base corresponde al backend y no a media', () => {
    const result = resolver.buildPublicUrl(
      'http://localhost:3001',
      'productos/imagenes/2026/04/demo.png',
    );

    expect(result).toBe(
      'http://localhost:3001/media/productos/imagenes/2026/04/demo.png',
    );
  });
});
