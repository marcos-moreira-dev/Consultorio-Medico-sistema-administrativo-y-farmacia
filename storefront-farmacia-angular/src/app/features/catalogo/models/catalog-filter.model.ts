export interface CatalogFilterModel {
  page: number;
  limit: number;
  q?: string;
  categoriaId?: number;
  sortBy?: 'nombreProducto' | 'precioVisible' | 'fechaActualizacion';
  sortDirection?: 'ASC' | 'DESC';
}
