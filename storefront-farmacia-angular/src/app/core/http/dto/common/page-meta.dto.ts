export interface PageMetaDto {
  page: number;
  limit: number;
  totalItems: number;
  totalPages: number;
  hasPreviousPage: boolean;
  hasNextPage: boolean;
  sortBy?: string;
  sortDirection?: string;
  query?: string;
}
