import { Injectable } from '@nestjs/common';

import { BusinessRuleException } from '../../../common/exceptions/business-rule.exception';
import { DuplicateResourceException } from '../../../common/exceptions/duplicate-resource.exception';
import { CategoriaEntity } from '../entities/categoria.entity';
import { CategoriaRepository } from '../repositories/categoria.repository';
import { ActualizarCategoriaRequestDto } from '../dto/actualizar-categoria.request.dto';

/**
 * Validador de reglas de negocio del módulo de categorías.
 *
 * <p>Concentra las reglas de integridad de categorías:</p>
 *
 * <ul>
 *   <li><strong>Nombre único</strong>: no se permiten dos categorías con el
 *   mismo nombre (case-insensitive tras normalización). La unicidad se valida
 *   aquí antes de llegar a la DB para dar un mensaje claro al usuario.</li>
 *   <li><strong>Actualización efectiva</strong>: si el update no cambia ningún
 *   campo respecto al estado actual, se rechaza para evitar writes y auditoría
 *   innecesarios.</li>
 * </ul>
 *
 * <p>La normalización de nombre (trim + uppercase) se aplica antes de la
 * comparación de duplicados para evitar falsos positivos como "Analgésicos"
 * vs "analgesicos ".</p>
 *
 * @see CategoriaEntity para el constraint {@code uq_categoria_nombre}
 */
@Injectable()
export class CategoriaRulesValidator {
  /**
   * Crea el validador de reglas.
   *
   * @param categoriaRepository Repositorio de categorías.
   */
  constructor(private readonly categoriaRepository: CategoriaRepository) {}

  /**
   * Valida que una categoría pueda ser creada.
   *
   * @param nombreCategoria Nombre ya normalizado de la nueva categoría.
   */
  async ensureCanCreate(nombreCategoria: string): Promise<void> {
    const existing = await this.categoriaRepository.findByNormalizedNombre(nombreCategoria);

    if (existing) {
      throw new DuplicateResourceException(
        'Ya existe una categoría con ese nombre.',
        'CATEGORIA_DUPLICADA',
      );
    }
  }

  /**
   * Valida que una categoría pueda ser actualizada.
   *
   * @param categoriaId Id de la categoría objetivo.
   * @param request DTO parcial ya normalizado.
   * @param currentEntity Entidad actual cargada desde persistencia.
   */
  async ensureCanUpdate(
    categoriaId: number,
    request: ActualizarCategoriaRequestDto,
    currentEntity: CategoriaEntity,
  ): Promise<void> {
    const hasAtLeastOneField =
      request.nombreCategoria !== undefined || request.descripcionBreve !== undefined;

    if (!hasAtLeastOneField) {
      throw new BusinessRuleException(
        'Debes enviar al menos un campo para actualizar la categoría.',
        'ACTUALIZACION_SIN_CAMBIOS',
      );
    }

    if (request.nombreCategoria !== undefined) {
      const existing = await this.categoriaRepository.findByNormalizedNombre(
        request.nombreCategoria,
        categoriaId,
      );

      if (existing) {
        throw new DuplicateResourceException(
          'Ya existe otra categoría con ese nombre.',
          'CATEGORIA_DUPLICADA',
        );
      }
    }

    const nextNombre =
      request.nombreCategoria !== undefined
        ? request.nombreCategoria
        : currentEntity.nombreCategoria;

    const nextDescripcion =
      request.descripcionBreve !== undefined
        ? request.descripcionBreve ?? null
        : currentEntity.descripcionBreve ?? null;

    const nothingChanged =
      nextNombre === currentEntity.nombreCategoria &&
      nextDescripcion === (currentEntity.descripcionBreve ?? null);

    if (nothingChanged) {
      throw new BusinessRuleException(
        'La actualización no produce cambios efectivos en la categoría.',
        'ACTUALIZACION_SIN_CAMBIOS_EFECTIVOS',
      );
    }
  }
}
