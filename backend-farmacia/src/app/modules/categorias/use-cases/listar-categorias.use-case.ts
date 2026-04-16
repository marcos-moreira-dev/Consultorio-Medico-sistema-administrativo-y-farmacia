import { Injectable } from '@nestjs/common';

import { PageResponseDto } from '../../../common/dto/page-response.dto';
import { CategoriaResponseDto } from '../dto/categoria.response.dto';
import { ListarCategoriasQueryDto } from '../dto/listar-categorias.query.dto';
import { CategoriasService } from '../services/categorias.service';

/**
 * Caso de uso para listar categorías.
 */
@Injectable()
export class ListarCategoriasUseCase {
  /**
   * Crea el caso de uso.
   *
   * @param categoriasService Servicio de categorías.
   */
  constructor(private readonly categoriasService: CategoriasService) {}

  /**
   * Ejecuta el listado paginado de categorías.
   *
   * @param query DTO de query del listado.
   * @returns Respuesta paginada de categorías.
   */
  async execute(
    query: ListarCategoriasQueryDto,
  ): Promise<PageResponseDto<CategoriaResponseDto>> {
    return this.categoriasService.listar(query);
  }
}
