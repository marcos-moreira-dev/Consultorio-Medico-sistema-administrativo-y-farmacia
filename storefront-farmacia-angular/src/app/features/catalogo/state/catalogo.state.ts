import { CategoriaPublicaModel } from '../../../shared/models/categoria-publica.model';
import { CatalogFilterModel } from '../models/catalog-filter.model';
import { CatalogPageModel } from '../models/catalog-page.model';

export interface CatalogoState {
  filtros: CatalogFilterModel;
  categorias: CategoriaPublicaModel[];
  page?: CatalogPageModel;
  loading: boolean;
  errorMessage?: string;
}
