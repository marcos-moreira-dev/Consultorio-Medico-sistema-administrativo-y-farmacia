import { Injectable } from '@nestjs/common';

import { PageResponseDto } from '../../../common/dto/page-response.dto';
import { BuscarCatalogoQueryDto } from '../dto/buscar-catalogo.query.dto';
import { CatalogoItemResponseDto } from '../dto/catalogo-item.response.dto';
import { CatalogoPublicoService } from '../services/catalogo-publico.service';

/**
 * Caso de uso para buscar dentro del catálogo público.
 */
@Injectable()
export class BuscarCatalogoPublicoUseCase {
  /**
   * Crea el caso de uso.
   *
   * @param catalogoPublicoService Servicio del catálogo público.
   */
  constructor(private readonly catalogoPublicoService: CatalogoPublicoService) {}

  /**
   * Ejecuta la búsqueda en el catálogo público.
   *
   * @param query DTO de búsqueda.
   * @returns Respuesta paginada de resultados públicos.
   */
  async execute(
    query: BuscarCatalogoQueryDto,
  ): Promise<PageResponseDto<CatalogoItemResponseDto>> {
    return this.catalogoPublicoService.buscar(query);
  }
}
