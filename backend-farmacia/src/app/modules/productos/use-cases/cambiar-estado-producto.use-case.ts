import { Injectable } from '@nestjs/common';

import { CambiarEstadoProductoRequestDto } from '../dto/cambiar-estado-producto.request.dto';
import { ProductoResponseDto } from '../dto/producto.response.dto';
import { ProductosService } from '../services/productos.service';

/**
 * Caso de uso para cambiar el estado lógico del producto.
 */
@Injectable()
export class CambiarEstadoProductoUseCase {
  /**
   * Crea el caso de uso.
   *
   * @param productosService Servicio de productos.
   */
  constructor(private readonly productosService: ProductosService) {}

  /**
   * Ejecuta el cambio de estado lógico del producto.
   *
   * @param productoId Identificador principal.
   * @param request DTO con el nuevo estado lógico.
   * @returns Producto actualizado.
   */
  async execute(
    productoId: number,
    request: CambiarEstadoProductoRequestDto,
  ): Promise<ProductoResponseDto> {
    return this.productosService.cambiarEstado(productoId, request);
  }
}
