import { ProductoResponseDto } from '../dto/producto.response.dto';
import { ProductoResumenResponseDto } from '../dto/producto-resumen.response.dto';
import { ProductoEntity } from '../entities/producto.entity';

/**
 * Mapper del módulo de productos.
 */
export class ProductoMapper {
  private constructor() {}

  /**
   * Convierte una entidad de producto a DTO detallado.
   *
   * @param entity Entidad de producto.
   * @returns DTO detallado serializable.
   */
  static toResponseDto(entity: ProductoEntity): ProductoResponseDto {
    return {
      productoId: entity.productoId,
      categoriaId: entity.categoriaId,
      nombreCategoria: entity.categoria?.nombreCategoria,
      nombreProducto: entity.nombreProducto,
      presentacion: entity.presentacion,
      descripcionBreve: entity.descripcionBreve ?? null,
      precioVisible: Number(entity.precioVisible),
      estadoProducto: entity.estadoProducto,
      esPublicable: entity.esPublicable,
      estadoDisponibilidad: entity.estadoDisponibilidad,
      fechaCreacion: entity.fechaCreacion.toISOString(),
      fechaActualizacion: entity.fechaActualizacion.toISOString(),
    };
  }

  /**
   * Convierte una entidad de producto a DTO resumen.
   *
   * @param entity Entidad de producto.
   * @returns DTO resumen serializable.
   */
  static toResumenResponseDto(entity: ProductoEntity): ProductoResumenResponseDto {
    return {
      productoId: entity.productoId,
      categoriaId: entity.categoriaId,
      nombreCategoria: entity.categoria?.nombreCategoria,
      nombreProducto: entity.nombreProducto,
      presentacion: entity.presentacion,
      precioVisible: Number(entity.precioVisible),
      estadoProducto: entity.estadoProducto,
      esPublicable: entity.esPublicable,
      estadoDisponibilidad: entity.estadoDisponibilidad,
      fechaActualizacion: entity.fechaActualizacion.toISOString(),
    };
  }
}
