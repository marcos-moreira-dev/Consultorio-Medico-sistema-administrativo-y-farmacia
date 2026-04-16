import { Controller, Get, Param, Query } from '@nestjs/common';
import {
  ApiOkResponse,
  ApiOperation,
  ApiTags,
} from '@nestjs/swagger';

import { PageResponseDto } from '../../../common/dto/page-response.dto';
import { ParseIntSafePipe } from '../../../common/pipes/parse-int-safe.pipe';
import { Public } from '../../../security/decorators/public.decorator';
import { BuscarCatalogoQueryDto } from '../dto/buscar-catalogo.query.dto';
import { CatalogoItemResponseDto } from '../dto/catalogo-item.response.dto';
import { CategoriaPublicaResponseDto } from '../dto/categoria-publica.response.dto';
import { ProductoPublicoDetalleResponseDto } from '../dto/producto-publico-detalle.response.dto';
import { BuscarCatalogoPublicoUseCase } from '../use-cases/buscar-catalogo-publico.use-case';
import { ListarCatalogoPublicoUseCase } from '../use-cases/listar-catalogo-publico.use-case';
import { ListarCategoriasPublicasUseCase } from '../use-cases/listar-categorias-publicas.use-case';
import { ObtenerProductoPublicoUseCase } from '../use-cases/obtener-producto-publico.use-case';

/**
 * Controlador HTTP de la superficie pública del catálogo de farmacia.
 *
 * <p>Este controlador expone exclusivamente información publicable de productos:
 * listado por categoría, búsqueda textual y detalle individual. Todos los
 * endpoints son públicos (decorador {@code @Public()}) y no requieren
 * autenticación, por lo que <strong>nunca deben devolver datos internos</strong>
 * como costo de adquisición, stock exacto, notas administrativas o referencias
 * de media no asociadas.</p>
 *
 * <p>Reglas de visibilidad aplicadas por los use cases subyacentes:</p>
 * <ul>
 *   <li>Solo productos con {@code estadoProducto = 'ACTIVO'};</li>
 *   <li>Solo productos con {@code esPublicable = true};</li>
 *   <li>Solo productos con {@code estadoDisponibilidad} en {DISPONIBLE, AGOTADO}.</li>
 * </ul>
 *
 * <p>La imagen del producto se resuelve como {@code imagenUrl} en una sola
 * consulta para evitar el problema N+1 que ocurriría si se hiciera un lookup
 * individual por cada producto del listado.</p>
 *
 * @see ProductoPublicacionUtil para la semántica de qué estados son visibles
 * @see CatalogoPublicoService para la lógica de consulta y enriquecimiento
 */
@ApiTags('Catálogo Público')
@Public()
@Controller('catalogo')
export class CatalogoPublicoController {
  /**
   * Crea el controlador del catálogo público.
   *
   * @param listarCatalogoPublicoUseCase Caso de uso para listar catálogo.
   * @param buscarCatalogoPublicoUseCase Caso de uso para buscar catálogo.
   * @param obtenerProductoPublicoUseCase Caso de uso para detalle público.
   * @param listarCategoriasPublicasUseCase Caso de uso para listar categorías públicas.
   */
  constructor(
    private readonly listarCatalogoPublicoUseCase: ListarCatalogoPublicoUseCase,
    private readonly buscarCatalogoPublicoUseCase: BuscarCatalogoPublicoUseCase,
    private readonly obtenerProductoPublicoUseCase: ObtenerProductoPublicoUseCase,
    private readonly listarCategoriasPublicasUseCase: ListarCategoriasPublicasUseCase,
  ) {}

  /**
   * Lista categorías visibles públicamente.
   *
   * @returns Colección de categorías públicas con cantidad de productos visibles.
   */
  @Get('categorias')
  @ApiOperation({
    summary: 'Listar categorías públicas',
    description:
      'Devuelve categorías que actualmente tienen productos visibles en el catálogo público.',
  })
  @ApiOkResponse({
    description: 'Listado de categorías públicas.',
    type: [CategoriaPublicaResponseDto],
  })
  async listarCategorias() {
    const data = await this.listarCategoriasPublicasUseCase.execute();

    return {
      data,
    };
  }

  /**
   * Lista el catálogo público visible.
   *
   * @param query DTO de filtros y paginación.
   * @returns Respuesta paginada del catálogo público.
   */
  @Get()
  @ApiOperation({
    summary: 'Listar catálogo público',
    description:
      'Lista productos visibles públicamente. Solo devuelve productos ACTIVO, publicables y con disponibilidad pública.',
  })
  @ApiOkResponse({
    description: 'Listado público del catálogo.',
    type: PageResponseDto,
  })
  async listar(@Query() query: BuscarCatalogoQueryDto) {
    return this.listarCatalogoPublicoUseCase.execute(query);
  }

  /**
   * Busca productos dentro del catálogo público.
   *
   * @param query DTO de búsqueda y paginación.
   * @returns Respuesta paginada de resultados públicos.
   */
  @Get('buscar')
  @ApiOperation({
    summary: 'Buscar en catálogo público',
    description:
      'Busca productos publicados por nombre, presentación, descripción o categoría.',
  })
  @ApiOkResponse({
    description: 'Resultados de búsqueda en catálogo público.',
    type: PageResponseDto,
  })
  async buscar(@Query() query: BuscarCatalogoQueryDto) {
    return this.buscarCatalogoPublicoUseCase.execute(query);
  }

  /**
   * Obtiene el detalle público de un producto visible en catálogo.
   *
   * @param productoId Identificador del producto.
   * @returns Detalle público del producto.
   */
  @Get(':productoId')
  @ApiOperation({
    summary: 'Obtener detalle público de producto',
    description:
      'Recupera el detalle público de un producto visible dentro del catálogo.',
  })
  @ApiOkResponse({
    description: 'Detalle público del producto.',
    type: ProductoPublicoDetalleResponseDto,
  })
  async obtenerProducto(
    @Param(
      'productoId',
      new ParseIntSafePipe({
        fieldName: 'productoId',
        min: 1,
      }),
    )
    productoId: number,
  ) {
    const data = await this.obtenerProductoPublicoUseCase.execute(productoId);

    return {
      data,
    };
  }
}
