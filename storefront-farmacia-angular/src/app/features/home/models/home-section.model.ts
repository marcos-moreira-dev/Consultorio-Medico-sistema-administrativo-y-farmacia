import { CategoriaPublicaModel } from '../../../shared/models/categoria-publica.model';
import { CatalogItemModel } from '../../catalogo/models/catalog-item.model';

export interface HomeSectionModel {
  categorias: CategoriaPublicaModel[];
  productosDestacados: CatalogItemModel[];
}
