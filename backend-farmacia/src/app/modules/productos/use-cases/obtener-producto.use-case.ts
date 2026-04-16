import { Injectable } from '@nestjs/common';

import { ProductoResponseDto } from '../dto/producto.response.dto';
import { ProductosService } from '../services/productos.service';

/**
 * Caso de uso para obtener un producto puntual.
 */
@Injectable()
export class ObtenerProductoUseCase {
  /**
   * Crea el caso de uso.
   *
   * @param productosService Servicio de productos.
   */
  constructor(private readonly productosService: ProductosService) {}

  /**
   * Ejecuta la obtención de un producto por id.
   *
   * @param productoId Identificador principal.
   * @returns Producto encontrado.
   */
  async execute(productoId: number): Promise<ProductoResponseDto> {
    return this.productosService.obtenerPorId(productoId);
  }
}
