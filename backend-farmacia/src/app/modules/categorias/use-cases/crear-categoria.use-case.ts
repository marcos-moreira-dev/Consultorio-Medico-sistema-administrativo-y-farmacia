import { Injectable } from '@nestjs/common';

import { CategoriaResponseDto } from '../dto/categoria.response.dto';
import { CrearCategoriaRequestDto } from '../dto/crear-categoria.request.dto';
import { CategoriasService } from '../services/categorias.service';

/**
 * Caso de uso para crear categorías.
 */
@Injectable()
export class CrearCategoriaUseCase {
  /**
   * Crea el caso de uso.
   *
   * @param categoriasService Servicio de categorías.
   */
  constructor(private readonly categoriasService: CategoriasService) {}

  /**
   * Ejecuta la creación de una categoría.
   *
   * @param request DTO de creación.
   * @returns Categoría creada.
   */
  async execute(request: CrearCategoriaRequestDto): Promise<CategoriaResponseDto> {
    return this.categoriasService.crear(request);
  }
}
