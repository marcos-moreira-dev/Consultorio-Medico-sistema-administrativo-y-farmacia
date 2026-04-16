import { Injectable } from '@nestjs/common';

import { ActualizarCategoriaRequestDto } from '../dto/actualizar-categoria.request.dto';
import { CategoriaResponseDto } from '../dto/categoria.response.dto';
import { CategoriasService } from '../services/categorias.service';

/**
 * Caso de uso para actualizar categorías.
 */
@Injectable()
export class ActualizarCategoriaUseCase {
  /**
   * Crea el caso de uso.
   *
   * @param categoriasService Servicio de categorías.
   */
  constructor(private readonly categoriasService: CategoriasService) {}

  /**
   * Ejecuta la actualización de una categoría.
   *
   * @param categoriaId Identificador principal.
   * @param request DTO parcial de actualización.
   * @returns Categoría actualizada.
   */
  async execute(
    categoriaId: number,
    request: ActualizarCategoriaRequestDto,
  ): Promise<CategoriaResponseDto> {
    return this.categoriasService.actualizar(categoriaId, request);
  }
}
