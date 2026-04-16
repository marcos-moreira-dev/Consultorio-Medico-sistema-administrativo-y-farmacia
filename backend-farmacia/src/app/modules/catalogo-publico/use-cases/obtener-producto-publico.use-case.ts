import { Injectable } from '@nestjs/common';

import { ProductoPublicoDetalleResponseDto } from '../dto/producto-publico-detalle.response.dto';
import { CatalogoPublicoService } from '../services/catalogo-publico.service';

/**
 * Caso de uso para obtener el detalle público de un producto.
 */
@Injectable()
export class ObtenerProductoPublicoUseCase {
  /**
   * Crea el caso de uso.
   *
   * @param catalogoPublicoService Servicio del catálogo público.
   */
  constructor(private readonly catalogoPublicoService: CatalogoPublicoService) {}

  /**
   * Ejecuta la obtención del detalle público de un producto.
   *
   * @param productoId Identificador principal del producto.
   * @returns DTO detallado visible públicamente.
   */
  async execute(productoId: number): Promise<ProductoPublicoDetalleResponseDto> {
    return this.catalogoPublicoService.obtenerProductoPublico(productoId);
  }
}
