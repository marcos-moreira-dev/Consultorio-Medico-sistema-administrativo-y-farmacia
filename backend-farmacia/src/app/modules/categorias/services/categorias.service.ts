import { Injectable } from '@nestjs/common';

import { PageResponseDto } from '../../../common/dto/page-response.dto';
import { SortDirectionEnum } from '../../../common/enums/sort-direction.enum';
import { ResourceNotFoundException } from '../../../common/exceptions/resource-not-found.exception';
import { PaginationUtil } from '../../../common/utils/pagination.util';
import { TextNormalizationUtil } from '../../../common/utils/text-normalization.util';
import { ActualizarCategoriaRequestDto } from '../dto/actualizar-categoria.request.dto';
import { CategoriaResponseDto } from '../dto/categoria.response.dto';
import { CrearCategoriaRequestDto } from '../dto/crear-categoria.request.dto';
import { ListarCategoriasQueryDto } from '../dto/listar-categorias.query.dto';
import { CategoriaEntity } from '../entities/categoria.entity';
import { CategoriaRepository } from '../repositories/categoria.repository';
import { CategoriaRulesValidator } from '../validation/categoria-rules.validator';

/**
 * Servicio de aplicación del módulo de categorías.
 */
@Injectable()
export class CategoriasService {
  /**
   * Crea el servicio de categorías.
   *
   * @param categoriaRepository Repositorio de categorías.
   * @param categoriaRulesValidator Validador de reglas del módulo.
   */
  constructor(
    private readonly categoriaRepository: CategoriaRepository,
    private readonly categoriaRulesValidator: CategoriaRulesValidator,
  ) {}

  /**
   * Crea una nueva categoría.
   *
   * @param request DTO de creación.
   * @returns DTO de salida de la categoría creada.
   */
  async crear(request: CrearCategoriaRequestDto): Promise<CategoriaResponseDto> {
    const nombreCategoria = TextNormalizationUtil.normalizeRequired(request.nombreCategoria);
    const descripcionBreve = TextNormalizationUtil.normalizeOptional(request.descripcionBreve);

    await this.categoriaRulesValidator.ensureCanCreate(nombreCategoria);

    const categoria = await this.categoriaRepository.createAndSave({
      nombreCategoria,
      descripcionBreve,
    });

    return this.toResponseDto(categoria);
  }

  /**
   * Lista categorías con paginación y búsqueda.
   *
   * @param query DTO de query del listado.
   * @returns Respuesta paginada de categorías.
   */
  async listar(
    query: ListarCategoriasQueryDto,
  ): Promise<PageResponseDto<CategoriaResponseDto>> {
    const page = PaginationUtil.normalizePage(query.page);
    const limit = PaginationUtil.normalizeLimit(query.limit);
    const q = TextNormalizationUtil.normalizeOptional(query.q);
    const sortBy = query.sortBy;
    const sortDirection = PaginationUtil.normalizeSortDirection(
      query.sortDirection ?? SortDirectionEnum.ASC,
    );

    const { items, total } = await this.categoriaRepository.findPaginated({
      skip: PaginationUtil.getSkip(page, limit),
      take: limit,
      q: q ?? undefined,
      sortBy,
      sortDirection,
    });

    return {
      ok: true,
      data: items.map((item) => this.toResponseDto(item)),
      meta: PaginationUtil.buildPageMeta({
        page,
        limit,
        totalItems: total,
        sortBy,
        sortDirection,
        query: q ?? undefined,
      }),
      timestamp: new Date().toISOString(),
    };
  }

  /**
   * Obtiene una categoría por id.
   *
   * @param categoriaId Identificador principal.
   * @returns DTO de salida de la categoría encontrada.
   */
  async obtenerPorId(categoriaId: number): Promise<CategoriaResponseDto> {
    const categoria = await this.categoriaRepository.findById(categoriaId);

    if (!categoria) {
      throw new ResourceNotFoundException('No se encontró la categoría solicitada.');
    }

    return this.toResponseDto(categoria);
  }

  /**
   * Actualiza una categoría existente.
   *
   * @param categoriaId Identificador principal.
   * @param request DTO parcial de actualización.
   * @returns DTO de salida de la categoría actualizada.
   */
  async actualizar(
    categoriaId: number,
    request: ActualizarCategoriaRequestDto,
  ): Promise<CategoriaResponseDto> {
    const categoria = await this.categoriaRepository.findById(categoriaId);

    if (!categoria) {
      throw new ResourceNotFoundException('No se encontró la categoría solicitada.');
    }

    const normalizedRequest = this.normalizeUpdateRequest(request);

    await this.categoriaRulesValidator.ensureCanUpdate(
      categoriaId,
      normalizedRequest,
      categoria,
    );

    if (normalizedRequest.nombreCategoria !== undefined) {
      categoria.nombreCategoria = normalizedRequest.nombreCategoria;
    }

    if (normalizedRequest.descripcionBreve !== undefined) {
      categoria.descripcionBreve = normalizedRequest.descripcionBreve;
    }

    const updated = await this.categoriaRepository.save(categoria);

    return this.toResponseDto(updated);
  }

  /**
   * Convierte una entidad de persistencia a DTO de salida.
   *
   * @param entity Entidad de categoría.
   * @returns DTO serializable de categoría.
   */
  private toResponseDto(entity: CategoriaEntity): CategoriaResponseDto {
    return {
      categoriaId: entity.categoriaId,
      nombreCategoria: entity.nombreCategoria,
      descripcionBreve: entity.descripcionBreve ?? null,
      fechaCreacion: entity.fechaCreacion.toISOString(),
      fechaActualizacion: entity.fechaActualizacion.toISOString(),
    };
  }

  /**
   * Normaliza el DTO parcial de actualización.
   *
   * @param request DTO original de entrada.
   * @returns DTO equivalente con textos ya normalizados.
   */
  private normalizeUpdateRequest(
    request: ActualizarCategoriaRequestDto,
  ): ActualizarCategoriaRequestDto {
    return {
      nombreCategoria:
        request.nombreCategoria !== undefined
          ? TextNormalizationUtil.normalizeRequired(request.nombreCategoria)
          : undefined,
      descripcionBreve:
        request.descripcionBreve !== undefined
          ? TextNormalizationUtil.normalizeOptional(request.descripcionBreve ?? undefined) ?? null
          : undefined,
    };
  }
}
