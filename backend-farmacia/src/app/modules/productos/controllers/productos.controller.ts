import { Body, Controller, Get, Param, Patch, Post, Query } from '@nestjs/common';
import {
  ApiBearerAuth,
  ApiOkResponse,
  ApiOperation,
  ApiTags,
} from '@nestjs/swagger';

import { PageResponseDto } from '../../../common/dto/page-response.dto';
import { RolAdminEnum } from '../../../common/enums/rol-admin.enum';
import { ParseIntSafePipe } from '../../../common/pipes/parse-int-safe.pipe';
import { Roles } from '../../../security/decorators/roles.decorator';
import { ActualizarProductoRequestDto } from '../dto/actualizar-producto.request.dto';
import { CambiarEstadoProductoRequestDto } from '../dto/cambiar-estado-producto.request.dto';
import { CrearProductoRequestDto } from '../dto/crear-producto.request.dto';
import { ListarProductosQueryDto } from '../dto/listar-productos.query.dto';
import { ProductoResponseDto } from '../dto/producto.response.dto';
import { ProductoResumenResponseDto } from '../dto/producto-resumen.response.dto';
import { ActualizarProductoUseCase } from '../use-cases/actualizar-producto.use-case';
import { CambiarEstadoProductoUseCase } from '../use-cases/cambiar-estado-producto.use-case';
import { CrearProductoUseCase } from '../use-cases/crear-producto.use-case';
import { ListarProductosUseCase } from '../use-cases/listar-productos.use-case';
import { ObtenerProductoUseCase } from '../use-cases/obtener-producto.use-case';

/**
 * Controlador HTTP de la superficie administrativa de productos.
 *
 * <p>Este controlador gestiona el CRUD interno de productos del catálogo de
 * farmacia. Todos los endpoints requieren rol {@code ADMIN_FARMACIA} y token
 * JWT, por lo que nunca deben exponerse al storefront público.</p>
 *
 * <p>Semántica importante:</p>
 * <ul>
 *   <li>{@code POST /admin/productos} crea un producto en estado interno
 *   ({@code ACTIVO}, {@code esPublicable=false}, {@code NO_PUBLICADO}).
 *   El producto NO es visible públicamente hasta que se publique explícitamente
 *   a través del módulo de disponibilidad-publicación.</li>
 *   <li>{@code PATCH /admin/productos/:id} solo modifica datos base (nombre,
 *   presentación, descripción, precio). No altera publicación ni disponibilidad.</li>
 *   <li>{@code PATCH /admin/productos/:id/estado} cambia entre ACTIVO/INACTIVO.
 *   Si se inactiva, se fuerza {@code esPublicable=false} y
 *   {@code estadoDisponibilidad=NO_PUBLICADO} para garantizar coherencia.</li>
 * </ul>
 *
 * <p>Esta separación entre datos base, estado lógico y publicación es intencional:
 * evita que un cambio accidental de nombre publique un producto no listo, o que
 * un producto inactivo siga apareciendo en el storefront.</p>
 *
 * @see DisponibilidadPublicacionService para publicación y disponibilidad
 * @see ProductoPublicacionUtil para las reglas de visibilidad pública
 */
@ApiTags('Productos')
@ApiBearerAuth()
@Roles(RolAdminEnum.ADMIN_FARMACIA)
@Controller('admin/productos')
export class ProductosController {
  /**
   * Crea el controlador de productos.
   *
   * @param crearProductoUseCase Caso de uso para crear productos.
   * @param listarProductosUseCase Caso de uso para listar productos.
   * @param obtenerProductoUseCase Caso de uso para obtener un producto.
   * @param actualizarProductoUseCase Caso de uso para actualizar productos.
   * @param cambiarEstadoProductoUseCase Caso de uso para cambiar el estado lógico.
   */
  constructor(
    private readonly crearProductoUseCase: CrearProductoUseCase,
    private readonly listarProductosUseCase: ListarProductosUseCase,
    private readonly obtenerProductoUseCase: ObtenerProductoUseCase,
    private readonly actualizarProductoUseCase: ActualizarProductoUseCase,
    private readonly cambiarEstadoProductoUseCase: CambiarEstadoProductoUseCase,
  ) {}

  /**
   * Crea un nuevo producto administrativo.
   *
   * @param request Datos de creación del producto.
   * @returns Producto creado.
   */
  @Post()
  @ApiOperation({
    summary: 'Crear producto',
    description: 'Crea un nuevo producto administrativo de farmacia.',
  })
  @ApiOkResponse({
    description: 'Producto creado correctamente.',
    type: ProductoResponseDto,
  })
  async crear(@Body() request: CrearProductoRequestDto) {
    const data = await this.crearProductoUseCase.execute(request);

    return {
      message: 'Producto creado correctamente.',
      data,
    };
  }

  /**
   * Lista productos administrativos con paginación y filtros.
   *
   * @param query DTO de filtros y paginación.
   * @returns Respuesta paginada de productos.
   */
  @Get()
  @ApiOperation({
    summary: 'Listar productos',
    description: 'Devuelve una colección paginada de productos administrativos.',
  })
  @ApiOkResponse({
    description: 'Listado paginado de productos.',
    type: PageResponseDto,
  })
  async listar(@Query() query: ListarProductosQueryDto) {
    return this.listarProductosUseCase.execute(query);
  }

  /**
   * Obtiene un producto por identificador.
   *
   * @param productoId Identificador del producto.
   * @returns Producto encontrado.
   */
  @Get(':productoId')
  @ApiOperation({
    summary: 'Obtener producto por id',
    description: 'Recupera un producto administrativo por su identificador.',
  })
  @ApiOkResponse({
    description: 'Producto encontrado.',
    type: ProductoResponseDto,
  })
  async obtener(
    @Param(
      'productoId',
      new ParseIntSafePipe({
        fieldName: 'productoId',
        min: 1,
      }),
    )
    productoId: number,
  ) {
    const data = await this.obtenerProductoUseCase.execute(productoId);

    return {
      data,
    };
  }

  /**
   * Actualiza datos base de un producto existente.
   *
   * Este endpoint no cambia la publicación pública ni la disponibilidad
   * operativa. Esa semántica queda reservada para módulos más específicos.
   *
   * @param productoId Identificador del producto.
   * @param request DTO parcial de actualización.
   * @returns Producto actualizado.
   */
  @Patch(':productoId')
  @ApiOperation({
    summary: 'Actualizar producto',
    description: 'Actualiza los datos base de un producto administrativo existente.',
  })
  @ApiOkResponse({
    description: 'Producto actualizado correctamente.',
    type: ProductoResponseDto,
  })
  async actualizar(
    @Param(
      'productoId',
      new ParseIntSafePipe({
        fieldName: 'productoId',
        min: 1,
      }),
    )
    productoId: number,
    @Body() request: ActualizarProductoRequestDto,
  ) {
    const data = await this.actualizarProductoUseCase.execute(productoId, request);

    return {
      message: 'Producto actualizado correctamente.',
      data,
    };
  }

  /**
   * Cambia el estado lógico del producto.
   *
   * Cuando un producto pasa a INACTIVO se fuerza un estado interno coherente:
   * - es_publicable = false
   * - estado_disponibilidad = NO_PUBLICADO
   *
   * @param productoId Identificador del producto.
   * @param request DTO con el nuevo estado lógico.
   * @returns Producto actualizado tras el cambio de estado.
   */
  @Patch(':productoId/estado')
  @ApiOperation({
    summary: 'Cambiar estado lógico del producto',
    description: 'Cambia el estado lógico del producto entre ACTIVO e INACTIVO.',
  })
  @ApiOkResponse({
    description: 'Estado del producto actualizado correctamente.',
    type: ProductoResponseDto,
  })
  async cambiarEstado(
    @Param(
      'productoId',
      new ParseIntSafePipe({
        fieldName: 'productoId',
        min: 1,
      }),
    )
    productoId: number,
    @Body() request: CambiarEstadoProductoRequestDto,
  ) {
    const data = await this.cambiarEstadoProductoUseCase.execute(productoId, request);

    return {
      message: 'Estado del producto actualizado correctamente.',
      data,
    };
  }
}
