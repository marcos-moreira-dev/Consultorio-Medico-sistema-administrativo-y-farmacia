export interface ProductoPublicoDetalleDto {
  productoId: number;
  categoriaId: number;
  nombreCategoria?: string;
  nombreProducto: string;
  presentacion: string;
  descripcionBreve?: string | null;
  precioVisible: number;
  estadoDisponibilidad: string;
  disponible: boolean;
  imagenUrl?: string | null;
  fechaActualizacion: string;
}
