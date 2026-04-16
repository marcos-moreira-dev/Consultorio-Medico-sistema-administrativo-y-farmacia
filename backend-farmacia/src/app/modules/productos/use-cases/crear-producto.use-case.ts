import { Injectable } from '@nestjs/common';

import { CrearProductoRequestDto } from '../dto/crear-producto.request.dto';
import { ProductoResponseDto } from '../dto/producto.response.dto';
import { ProductosService } from '../services/productos.service';

/**
 * Caso de uso para crear productos.
 */
@Injectable()
export class CrearProductoUseCase {
  /**
   * Crea el caso de uso.
   *
   * @param productosService Servicio de productos.
   */
  constructor(private readonly productosService: ProductosService) {}

  /**
   * Ejecuta la creación de un producto.
   *
   * @param request DTO de creación.
   * @returns Producto creado.
   */
  async execute(request: CrearProductoRequestDto): Promise<ProductoResponseDto> {
    return this.productosService.crear(request);
  }
}
