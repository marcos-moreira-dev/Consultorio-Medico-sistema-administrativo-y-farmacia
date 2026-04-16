import { PageMetaDto } from '../../core/http/dto/common/page-meta.dto';

export interface PagedResultModel<TItem> {
  items: TItem[];
  meta: PageMetaDto;
  correlationId?: string;
  timestamp?: string;
}
