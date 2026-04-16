import { Injectable } from '@nestjs/common';

import { PageResponseDto } from '../../../common/dto/page-response.dto';
import { BuscarCatalogoQueryDto } from '../dto/buscar-catalogo.query.dto';
import { CatalogoItemResponseDto } from '../dto/catalogo-item.response.dto';
import { CatalogoPublicoService } from '../services/catalogo-publico.service';

/**
 * Caso de uso para listar el catálogo público.
 */
@Injectable()
export class ListarCatalogoPublicoUseCase {
  /**
   * Crea el caso de uso.
   *
   * @param catalogoPublicoService Servicio del catálogo público.
   */
  constructor(private readonly catalogoPublicoService: CatalogoPublicoService) {}

  /**
   * Ejecuta el listado público del catálogo.
   *
   * @param query DTO de filtros y paginación.
   * @returns Respuesta paginada del catálogo público.
   */
  async execute(
    query: BuscarCatalogoQueryDto,
  ): Promise<PageResponseDto<CatalogoItemResponseDto>> {
    return this.catalogoPublicoService.listar(query);
  }
}
