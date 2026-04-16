import { Injectable } from '@nestjs/common';

import { ActualizarProductoRequestDto } from '../dto/actualizar-producto.request.dto';
import { ProductoResponseDto } from '../dto/producto.response.dto';
import { ProductosService } from '../services/productos.service';

/**
 * Caso de uso para actualizar productos.
 */
@Injectable()
export class ActualizarProductoUseCase {
  /**
   * Crea el caso de uso.
   *
   * @param productosService Servicio de productos.
   */
  constructor(private readonly productosService: ProductosService) {}

  /**
   * Ejecuta la actualización de un producto.
   *
   * @param productoId Identificador principal.
   * @param request DTO parcial de actualización.
   * @returns Producto actualizado.
   */
  async execute(
    productoId: number,
    request: ActualizarProductoRequestDto,
  ): Promise<ProductoResponseDto> {
    return this.productosService.actualizar(productoId, request);
  }
}
