import { EstadoDisponibilidadModel } from '../../../shared/models/estado-disponibilidad.model';

export interface ProductoDetalleModel {
  productoId: number;
  categoriaId: number;
  nombreCategoria?: string;
  nombreProducto: string;
  presentacion: string;
  descripcionBreve?: string | null;
  precioVisible: number;
  estadoDisponibilidad: EstadoDisponibilidadModel;
  disponible: boolean;
  imagenUrl?: string | null;
  imagenSrc: string;
  fechaActualizacion: string;
}
