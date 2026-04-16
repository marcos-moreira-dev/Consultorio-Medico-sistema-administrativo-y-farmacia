import { TestBed } from '@angular/core/testing';
import { Meta } from '@angular/platform-browser';

import { SeoMetaService } from '../../../core/services/seo-meta.service';

describe('SeoMetaService', () => {
  let service: SeoMetaService;
  let meta: jasmine.SpyObj<Meta>;

  beforeEach(() => {
    meta = jasmine.createSpyObj<Meta>('Meta', ['updateTag']);

    TestBed.configureTestingModule({
      providers: [
        SeoMetaService,
        {
          provide: Meta,
          useValue: meta,
        },
      ],
    });

    service = TestBed.inject(SeoMetaService);
  });

  it('debe actualizar la meta description', () => {
    service.updateDescription('Descripción de prueba');

    expect(meta.updateTag).toHaveBeenCalledWith({
      name: 'description',
      content: 'Descripción de prueba',
    });
  });
});
