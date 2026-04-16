import { CatalogItemModel } from '../../features/catalogo/models/catalog-item.model';
import { CatalogPageModel } from '../../features/catalogo/models/catalog-page.model';
import { ProductoDetalleModel } from '../../features/producto-detalle/models/producto-detalle.model';
import { CategoriaPublicaModel } from '../../shared/models/categoria-publica.model';
import { ImagePathUtil } from '../utils/image-path.util';
import { CatalogoQueryDto } from '../http/dto/catalogo/catalogo-query.dto';

const DEMO_ITEMS: Omit<CatalogItemModel, 'imagenSrc'>[] = [
  { productoId: 1, categoriaId: 1, nombreCategoria: 'Analgésicos', nombreProducto: 'Paracetamol', presentacion: 'Caja 20 tabletas 500 mg', descripcionBreve: 'Analgésico y antipirético de uso común.', precioVisible: 3.5, estadoDisponibilidad: 'DISPONIBLE', disponible: true, imagenUrl: null },
  { productoId: 2, categoriaId: 1, nombreCategoria: 'Analgésicos', nombreProducto: 'Ibuprofeno', presentacion: 'Caja 10 cápsulas 400 mg', descripcionBreve: 'Antiinflamatorio y analgésico de uso frecuente.', precioVisible: 4.2, estadoDisponibilidad: 'DISPONIBLE', disponible: true, imagenUrl: null },
  { productoId: 3, categoriaId: 2, nombreCategoria: 'Vitaminas', nombreProducto: 'Vitamina C', presentacion: 'Frasco 100 tabletas', descripcionBreve: 'Suplemento vitamínico de consumo habitual.', precioVisible: 6.8, estadoDisponibilidad: 'DISPONIBLE', disponible: true, imagenUrl: null },
  { productoId: 4, categoriaId: 2, nombreCategoria: 'Vitaminas', nombreProducto: 'Complejo B', presentacion: 'Caja 30 tabletas', descripcionBreve: 'Suplemento vitamínico para apoyo general.', precioVisible: 7.9, estadoDisponibilidad: 'AGOTADO', disponible: false, imagenUrl: null },
  { productoId: 5, categoriaId: 3, nombreCategoria: 'Higiene personal', nombreProducto: 'Alcohol antiséptico', presentacion: 'Botella 250 ml', descripcionBreve: 'Producto de higiene y desinfección básica.', precioVisible: 2.75, estadoDisponibilidad: 'DISPONIBLE', disponible: true, imagenUrl: null },
  { productoId: 6, categoriaId: 4, nombreCategoria: 'Digestivos', nombreProducto: 'Antiácido masticable', presentacion: 'Caja 12 tabletas', descripcionBreve: 'Producto para malestares digestivos leves.', precioVisible: 5.1, estadoDisponibilidad: 'DISPONIBLE', disponible: true, imagenUrl: null },
  { productoId: 7, categoriaId: 5, nombreCategoria: 'Primeros auxilios', nombreProducto: 'Venda elástica', presentacion: 'Unidad mediana', descripcionBreve: 'Insumo básico de primeros auxilios.', precioVisible: 3.1, estadoDisponibilidad: 'DISPONIBLE', disponible: true, imagenUrl: null },
  { productoId: 8, categoriaId: 5, nombreCategoria: 'Primeros auxilios', nombreProducto: 'Gasas estériles', presentacion: 'Paquete x 20', descripcionBreve: 'Insumo de uso común para curación básica.', precioVisible: 2.4, estadoDisponibilidad: 'DISPONIBLE', disponible: true, imagenUrl: null },
];

const DEMO_TIMESTAMP = '2026-04-09T00:00:00.000Z';

export class DemoCatalogData {
  static categories(): CategoriaPublicaModel[] {
    const counts = new Map<number, { nombre: string; total: number }>();
    for (const item of DEMO_ITEMS) {
      const existing = counts.get(item.categoriaId) ?? { nombre: item.nombreCategoria ?? 'Categoría', total: 0 };
      existing.total += 1;
      counts.set(item.categoriaId, existing);
    }
    return [...counts.entries()].map(([categoriaId, value]) => ({ categoriaId, nombreCategoria: value.nombre, cantidadProductosVisibles: value.total }));
  }

  static catalogPage(query: CatalogoQueryDto): CatalogPageModel {
    let items = DEMO_ITEMS.map((item) => DemoCatalogData.toModel(item));
    const normalizedQ = query.q?.trim().toLowerCase();
    if (normalizedQ) {
      items = items.filter((item) => [item.nombreProducto, item.presentacion, item.descripcionBreve ?? '', item.nombreCategoria ?? ''].some((part) => part.toLowerCase().includes(normalizedQ)));
    }
    if (query.categoriaId) {
      items = items.filter((item) => item.categoriaId === query.categoriaId);
    }
    if (query.sortBy === 'precioVisible') {
      items = items.sort((a, b) => a.precioVisible - b.precioVisible);
    } else if (query.sortBy === 'fechaActualizacion') {
      items = items.sort((a, b) => a.productoId - b.productoId);
    } else {
      items = items.sort((a, b) => a.nombreProducto.localeCompare(b.nombreProducto));
    }
    if (query.sortDirection === 'DESC') {
      items = [...items].reverse();
    }
    const page = query.page ?? 1;
    const limit = query.limit ?? 12;
    const totalItems = items.length;
    const totalPages = Math.max(1, Math.ceil(totalItems / limit));
    const start = (page - 1) * limit;
    const pagedItems = items.slice(start, start + limit);
    return {
      items: pagedItems,
      meta: {
        page,
        limit,
        totalItems,
        totalPages,
        hasPreviousPage: page > 1,
        hasNextPage: page < totalPages,
        sortBy: query.sortBy,
        sortDirection: query.sortDirection,
        query: query.q,
      },
      timestamp: DEMO_TIMESTAMP,
    };
  }

  static productDetail(productoId: number): ProductoDetalleModel {
    const found = DEMO_ITEMS.find((item) => item.productoId === productoId) ?? DEMO_ITEMS[0];
    return { ...DemoCatalogData.toModel(found), fechaActualizacion: DEMO_TIMESTAMP };
  }

  private static toModel(item: Omit<CatalogItemModel, 'imagenSrc'>): CatalogItemModel {
    return {
      ...item,
      imagenSrc: ImagePathUtil.resolvePublicImage(item.imagenUrl, item.nombreProducto, item.nombreCategoria),
    };
  }
}
