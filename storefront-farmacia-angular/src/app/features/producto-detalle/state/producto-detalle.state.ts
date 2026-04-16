import { CatalogItemModel } from '../../catalogo/models/catalog-item.model';
import { ProductoDetalleModel } from '../models/producto-detalle.model';

export interface ProductoDetalleState {
  producto?: ProductoDetalleModel;
  relacionados: CatalogItemModel[];
  loading: boolean;
  errorMessage?: string;
}
