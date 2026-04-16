import { Injectable } from '@nestjs/common';

import { PageResponseDto } from '../../../common/dto/page-response.dto';
import { ListarProductosQueryDto } from '../dto/listar-productos.query.dto';
import { ProductoResumenResponseDto } from '../dto/producto-resumen.response.dto';
import { ProductosService } from '../services/productos.service';

/**
 * Caso de uso para listar productos.
 */
@Injectable()
export class ListarProductosUseCase {
  /**
   * Crea el caso de uso.
   *
   * @param productosService Servicio de productos.
   */
  constructor(private readonly productosService: ProductosService) {}

  /**
   * Ejecuta el listado paginado de productos.
   *
   * @param query DTO de query del listado.
   * @returns Respuesta paginada de productos.
   */
  async execute(
    query: ListarProductosQueryDto,
  ): Promise<PageResponseDto<ProductoResumenResponseDto>> {
    return this.productosService.listar(query);
  }
}
