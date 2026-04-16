import { Injectable } from '@nestjs/common';

import { CategoriaPublicaResponseDto } from '../dto/categoria-publica.response.dto';
import { CatalogoPublicoService } from '../services/catalogo-publico.service';

/**
 * Caso de uso para listar categorías visibles en el catálogo público.
 */
@Injectable()
export class ListarCategoriasPublicasUseCase {
  /**
   * Crea el caso de uso.
   *
   * @param catalogoPublicoService Servicio del catálogo público.
   */
  constructor(private readonly catalogoPublicoService: CatalogoPublicoService) {}

  /**
   * Ejecuta el listado de categorías públicas.
   *
   * @returns Colección de categorías con cantidad de productos visibles.
   */
  async execute(): Promise<CategoriaPublicaResponseDto[]> {
    return this.catalogoPublicoService.listarCategoriasPublicas();
  }
}
