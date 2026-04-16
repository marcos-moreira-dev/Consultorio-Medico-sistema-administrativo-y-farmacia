import { Injectable } from '@nestjs/common';

import { EstadoDisponibilidadEnum } from '../../../common/enums/estado-disponibilidad.enum';
import { BusinessRuleException } from '../../../common/exceptions/business-rule.exception';
import { ProductoPublicacionUtil } from '../../../common/utils/producto-publicacion.util';
import { ProductoEntity } from '../../productos/entities/producto.entity';
import { DespublicarProductoRequestDto } from '../dto/despublicar-producto.request.dto';

/**
 * Validador de reglas de negocio del módulo de publicación.
 *
 * <p>Este validador concentra las reglas de transición entre estados de
 * publicación y disponibilidad de productos. Es el guardián semántico que
 * impide transiciones inválidas como:</p>
 *
 * <ul>
 *   <li>Publicar un producto INACTIVO (solo ACTIVO es publicable).</li>
 *   <li>Usar {@code NO_PUBLICADO} como disponibilidad operativa (este estado
 *   es exclusivo de despublicación, no de catálogo visible).</li>
 *   <li>Actualizar disponibilidad de un producto no publicado.</li>
 *   <li>Re-publicar un producto que ya está publicado con el mismo estado.</li>
 * </ul>
 *
 * <p>La separación entre {@code esPublicable} (booleano de exposición) y
 * {@code estadoDisponibilidad} (estado operativo) permite modelar escenarios
 * como: producto publicado pero agotado (visible pero no comprable), o producto
 * activo pero no publicado (existe internamente pero no aparece en el catálogo).</p>
 *
 * @see ProductoPublicacionUtil para las utilidades de cálculo de visibilidad
 */
@Injectable()
export class PublicacionRulesValidator {
  /**
   * Valida que un producto pueda publicarse.
   *
   * @param producto Producto actual.
   * @param nextEstadoDisponibilidad Estado de disponibilidad solicitado al publicar.
   */
  async ensureCanPublish(
    producto: ProductoEntity,
    nextEstadoDisponibilidad: string,
  ): Promise<void> {
    const estadoDisponibilidadActual = String(producto.estadoDisponibilidad)
      .trim()
      .toUpperCase();
    const estadoDisponibilidadSolicitado = String(nextEstadoDisponibilidad)
      .trim()
      .toUpperCase();

    if (!ProductoPublicacionUtil.canBePublished(producto.estadoProducto)) {
      throw new BusinessRuleException(
        'Solo se pueden publicar productos en estado ACTIVO.',
        'PRODUCTO_NO_PUBLICABLE_POR_ESTADO',
      );
    }

    if (
      producto.esPublicable === true &&
      estadoDisponibilidadActual === estadoDisponibilidadSolicitado &&
      ProductoPublicacionUtil.isVisiblePublicDisponibilidad(estadoDisponibilidadSolicitado)
    ) {
      throw new BusinessRuleException(
        'El producto ya se encuentra publicado con ese estado de disponibilidad.',
        'PUBLICACION_SIN_CAMBIO',
      );
    }
  }

  /**
   * Valida que un producto pueda despublicarse.
   *
   * @param producto Producto actual.
   * @param _request DTO opcional de despublicación.
   */
  async ensureCanUnpublish(
    producto: ProductoEntity,
    _request: DespublicarProductoRequestDto,
  ): Promise<void> {
    const estadoDisponibilidadActual = String(producto.estadoDisponibilidad)
      .trim()
      .toUpperCase();

    if (
      producto.esPublicable === false &&
      estadoDisponibilidadActual === EstadoDisponibilidadEnum.NO_PUBLICADO
    ) {
      throw new BusinessRuleException(
        'El producto ya se encuentra despublicado.',
        'DESPUBLICACION_SIN_CAMBIO',
      );
    }
  }

  /**
   * Valida que la disponibilidad operativa pueda actualizarse.
   *
   * @param producto Producto actual.
   * @param nextEstadoDisponibilidad Nuevo estado solicitado.
   */
  async ensureCanUpdateDisponibilidad(
    producto: ProductoEntity,
    nextEstadoDisponibilidad: string,
  ): Promise<void> {
    const estadoDisponibilidadActual = String(producto.estadoDisponibilidad)
      .trim()
      .toUpperCase();
    const estadoDisponibilidadSolicitado = String(nextEstadoDisponibilidad)
      .trim()
      .toUpperCase();

    if (!ProductoPublicacionUtil.canBePublished(producto.estadoProducto)) {
      throw new BusinessRuleException(
        'Solo se puede actualizar disponibilidad en productos ACTIVO.',
        'DISPONIBILIDAD_NO_EDITABLE_POR_ESTADO',
      );
    }

    if (producto.esPublicable !== true) {
      throw new BusinessRuleException(
        'Solo se puede actualizar disponibilidad de productos publicados.',
        'DISPONIBILIDAD_REQUIERE_PUBLICACION',
      );
    }

    if (estadoDisponibilidadActual === EstadoDisponibilidadEnum.NO_PUBLICADO) {
      throw new BusinessRuleException(
        'No se puede usar NO_PUBLICADO como disponibilidad operativa visible. Publica primero el producto.',
        'DISPONIBILIDAD_INVALIDA_PARA_PRODUCTO_NO_PUBLICADO',
      );
    }

    if (estadoDisponibilidadActual === estadoDisponibilidadSolicitado) {
      throw new BusinessRuleException(
        'El producto ya se encuentra en el estado de disponibilidad solicitado.',
        'DISPONIBILIDAD_SIN_CAMBIO',
      );
    }
  }
}
