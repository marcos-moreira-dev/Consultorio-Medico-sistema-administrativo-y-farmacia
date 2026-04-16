import { Injectable } from '@nestjs/common';

import { CategoriaResponseDto } from '../dto/categoria.response.dto';
import { CategoriasService } from '../services/categorias.service';

/**
 * Caso de uso para obtener una categoría puntual.
 */
@Injectable()
export class ObtenerCategoriaUseCase {
  /**
   * Crea el caso de uso.
   *
   * @param categoriasService Servicio de categorías.
   */
  constructor(private readonly categoriasService: CategoriasService) {}

  /**
   * Ejecuta la obtención de una categoría por id.
   *
   * @param categoriaId Identificador principal.
   * @returns Categoría encontrada.
   */
  async execute(categoriaId: number): Promise<CategoriaResponseDto> {
    return this.categoriasService.obtenerPorId(categoriaId);
  }
}
