import { ApiResponseDto } from './api-response.dto';
import { PageMetaDto } from './page-meta.dto';

export interface PageResponseDto<TItem> extends ApiResponseDto<TItem[]> {
  meta: PageMetaDto;
}
