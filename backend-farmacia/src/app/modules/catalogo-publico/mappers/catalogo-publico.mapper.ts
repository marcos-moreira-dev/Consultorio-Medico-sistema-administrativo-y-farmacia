import { EstadoDisponibilidadEnum } from '../../../common/enums/estado-disponibilidad.enum';
import { CatalogoItemResponseDto } from '../dto/catalogo-item.response.dto';
import { ProductoPublicoDetalleResponseDto } from '../dto/producto-publico-detalle.response.dto';
import { ProductoEntity } from '../../productos/entities/producto.entity';

/**
 * Mapper del módulo de catálogo público.
 */
export class CatalogoPublicoMapper {
  private constructor() {}

  /**
   * Convierte una entidad pública visible a DTO resumen para listados de catálogo.
   *
   * @param entity Producto visible públicamente.
   * @param imagenUrl URL pública de la imagen principal, si existe.
   * @returns DTO resumen serializable.
   */
  static toCatalogoItemResponseDto(
    entity: ProductoEntity,
    imagenUrl: string | null,
  ): CatalogoItemResponseDto {
    return {
      productoId: entity.productoId,
      categoriaId: entity.categoriaId,
      nombreCategoria: entity.categoria?.nombreCategoria,
      nombreProducto: entity.nombreProducto,
      presentacion: entity.presentacion,
      descripcionBreve: entity.descripcionBreve ?? null,
      precioVisible: Number(entity.precioVisible),
      estadoDisponibilidad: entity.estadoDisponibilidad,
      disponible: entity.estadoDisponibilidad === EstadoDisponibilidadEnum.DISPONIBLE,
      imagenUrl,
    };
  }

  /**
   * Convierte una entidad pública visible a DTO detallado.
   *
   * @param entity Producto visible públicamente.
   * @param imagenUrl URL pública de la imagen principal, si existe.
   * @returns DTO detallado serializable.
   */
  static toProductoPublicoDetalleResponseDto(
    entity: ProductoEntity,
    imagenUrl: string | null,
  ): ProductoPublicoDetalleResponseDto {
    return {
      productoId: entity.productoId,
      categoriaId: entity.categoriaId,
      nombreCategoria: entity.categoria?.nombreCategoria,
      nombreProducto: entity.nombreProducto,
      presentacion: entity.presentacion,
      descripcionBreve: entity.descripcionBreve ?? null,
      precioVisible: Number(entity.precioVisible),
      estadoDisponibilidad: entity.estadoDisponibilidad,
      disponible: entity.estadoDisponibilidad === EstadoDisponibilidadEnum.DISPONIBLE,
      imagenUrl,
      fechaActualizacion: entity.fechaActualizacion.toISOString(),
    };
  }
}
