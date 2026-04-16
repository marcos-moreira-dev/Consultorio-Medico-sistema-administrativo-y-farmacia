import { PageMetaDto } from '../../../core/http/dto/common/page-meta.dto';
import { CatalogItemModel } from './catalog-item.model';

export interface CatalogPageModel {
  items: CatalogItemModel[];
  meta: PageMetaDto;
  correlationId?: string;
  timestamp?: string;
}
