import { Body, Controller, Get, Param, Patch } from '@nestjs/common';
import {
  ApiBearerAuth,
  ApiOkResponse,
  ApiOperation,
  ApiTags,
} from '@nestjs/swagger';

import { RolAdminEnum } from '../../../common/enums/rol-admin.enum';
import { ParseIntSafePipe } from '../../../common/pipes/parse-int-safe.pipe';
import { Roles } from '../../../security/decorators/roles.decorator';
import { ActualizarDisponibilidadRequestDto } from '../dto/actualizar-disponibilidad.request.dto';
import { DespublicarProductoRequestDto } from '../dto/despublicar-producto.request.dto';
import { EstadoPublicacionResponseDto } from '../dto/estado-publicacion.response.dto';
import { PublicarProductoRequestDto } from '../dto/publicar-producto.request.dto';
import { ActualizarDisponibilidadUseCase } from '../use-cases/actualizar-disponibilidad.use-case';
import { ConsultarEstadoPublicacionUseCase } from '../use-cases/consultar-estado-publicacion.use-case';
import { DespublicarProductoUseCase } from '../use-cases/despublicar-producto.use-case';
import { PublicarProductoUseCase } from '../use-cases/publicar-producto.use-case';

/**
 * Controlador HTTP del módulo de disponibilidad y publicación.
 */
@ApiTags('Disponibilidad y Publicación')
@ApiBearerAuth()
@Roles(RolAdminEnum.ADMIN_FARMACIA)
@Controller('admin/productos')
export class DisponibilidadPublicacionController {
  /**
   * Crea el controlador del módulo.
   *
   * @param consultarEstadoPublicacionUseCase Caso de uso para consultar estado.
   * @param publicarProductoUseCase Caso de uso para publicar producto.
   * @param despublicarProductoUseCase Caso de uso para despublicar producto.
   * @param actualizarDisponibilidadUseCase Caso de uso para actualizar disponibilidad.
   */
  constructor(
    private readonly consultarEstadoPublicacionUseCase: ConsultarEstadoPublicacionUseCase,
    private readonly publicarProductoUseCase: PublicarProductoUseCase,
    private readonly despublicarProductoUseCase: DespublicarProductoUseCase,
    private readonly actualizarDisponibilidadUseCase: ActualizarDisponibilidadUseCase,
  ) {}

  /**
   * Consulta el estado actual de publicación de un producto.
   *
   * @param productoId Identificador del producto.
   * @returns Estado actual de publicación y disponibilidad.
   */
  @Get(':productoId/publicacion')
  @ApiOperation({
    summary: 'Consultar estado de publicación',
    description:
      'Devuelve el estado actual de publicación y disponibilidad de un producto administrativo.',
  })
  @ApiOkResponse({
    description: 'Estado de publicación consultado correctamente.',
    type: EstadoPublicacionResponseDto,
  })
  async consultarEstado(
    @Param(
      'productoId',
      new ParseIntSafePipe({
        fieldName: 'productoId',
        min: 1,
      }),
    )
    productoId: number,
  ) {
    const data = await this.consultarEstadoPublicacionUseCase.execute(productoId);

    return {
      data,
    };
  }

  /**
   * Publica un producto en la superficie pública.
   *
   * Regla:
   * - el producto debe estar ACTIVO;
   * - se activa `esPublicable`;
   * - el estado de disponibilidad deja de ser `NO_PUBLICADO`.
   *
   * @param productoId Identificador del producto.
   * @param request DTO con el estado inicial de disponibilidad pública.
   * @returns Estado resultante de publicación.
   */
  @Patch(':productoId/publicacion/publicar')
  @ApiOperation({
    summary: 'Publicar producto',
    description:
      'Publica un producto y le asigna un estado inicial de disponibilidad visible.',
  })
  @ApiOkResponse({
    description: 'Producto publicado correctamente.',
    type: EstadoPublicacionResponseDto,
  })
  async publicar(
    @Param(
      'productoId',
      new ParseIntSafePipe({
        fieldName: 'productoId',
        min: 1,
      }),
    )
    productoId: number,
    @Body() request: PublicarProductoRequestDto,
  ) {
    const data = await this.publicarProductoUseCase.execute(productoId, request);

    return {
      message: 'Producto publicado correctamente.',
      data,
    };
  }

  /**
   * Despublica un producto de la superficie pública.
   *
   * Regla:
   * - fuerza `esPublicable = false`;
   * - fuerza `estadoDisponibilidad = NO_PUBLICADO`.
   *
   * @param productoId Identificador del producto.
   * @param request DTO opcional con motivo operativo.
   * @returns Estado resultante de publicación.
   */
  @Patch(':productoId/publicacion/despublicar')
  @ApiOperation({
    summary: 'Despublicar producto',
    description: 'Retira un producto de la superficie pública.',
  })
  @ApiOkResponse({
    description: 'Producto despublicado correctamente.',
    type: EstadoPublicacionResponseDto,
  })
  async despublicar(
    @Param(
      'productoId',
      new ParseIntSafePipe({
        fieldName: 'productoId',
        min: 1,
      }),
    )
    productoId: number,
    @Body() request: DespublicarProductoRequestDto,
  ) {
    const data = await this.despublicarProductoUseCase.execute(productoId, request);

    return {
      message: 'Producto despublicado correctamente.',
      data,
    };
  }

  /**
   * Actualiza la disponibilidad operativa de un producto publicado.
   *
   * Regla:
   * - solo aplica a productos ACTIVO y publicables;
   * - solo acepta DISPONIBLE o AGOTADO;
   * - NO_PUBLICADO queda reservado para despublicación.
   *
   * @param productoId Identificador del producto.
   * @param request DTO con el nuevo estado de disponibilidad.
   * @returns Estado resultante de publicación.
   */
  @Patch(':productoId/disponibilidad')
  @ApiOperation({
    summary: 'Actualizar disponibilidad',
    description: 'Actualiza la disponibilidad operativa de un producto publicado.',
  })
  @ApiOkResponse({
    description: 'Disponibilidad actualizada correctamente.',
    type: EstadoPublicacionResponseDto,
  })
  async actualizarDisponibilidad(
    @Param(
      'productoId',
      new ParseIntSafePipe({
        fieldName: 'productoId',
        min: 1,
      }),
    )
    productoId: number,
    @Body() request: ActualizarDisponibilidadRequestDto,
  ) {
    const data = await this.actualizarDisponibilidadUseCase.execute(productoId, request);

    return {
      message: 'Disponibilidad actualizada correctamente.',
      data,
    };
  }
}
