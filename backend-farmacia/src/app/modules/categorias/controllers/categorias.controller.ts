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
import { ActualizarCategoriaRequestDto } from '../dto/actualizar-categoria.request.dto';
import { CategoriaResponseDto } from '../dto/categoria.response.dto';
import { CrearCategoriaRequestDto } from '../dto/crear-categoria.request.dto';
import { ListarCategoriasQueryDto } from '../dto/listar-categorias.query.dto';
import { ActualizarCategoriaUseCase } from '../use-cases/actualizar-categoria.use-case';
import { CrearCategoriaUseCase } from '../use-cases/crear-categoria.use-case';
import { ListarCategoriasUseCase } from '../use-cases/listar-categorias.use-case';
import { ObtenerCategoriaUseCase } from '../use-cases/obtener-categoria.use-case';

/**
 * Controlador HTTP del módulo de categorías.
 */
@ApiTags('Categorías')
@ApiBearerAuth()
@Roles(RolAdminEnum.ADMIN_FARMACIA)
@Controller('admin/categorias')
export class CategoriasController {
  /**
   * Crea el controlador de categorías.
   *
   * @param crearCategoriaUseCase Caso de uso para crear categorías.
   * @param listarCategoriasUseCase Caso de uso para listar categorías.
   * @param obtenerCategoriaUseCase Caso de uso para obtener una categoría.
   * @param actualizarCategoriaUseCase Caso de uso para actualizar categorías.
   */
  constructor(
    private readonly crearCategoriaUseCase: CrearCategoriaUseCase,
    private readonly listarCategoriasUseCase: ListarCategoriasUseCase,
    private readonly obtenerCategoriaUseCase: ObtenerCategoriaUseCase,
    private readonly actualizarCategoriaUseCase: ActualizarCategoriaUseCase,
  ) {}

  /**
   * Crea una categoría administrativa.
   *
   * @param request Datos de creación de la categoría.
   * @returns Categoría creada.
   */
  @Post()
  @ApiOperation({
    summary: 'Crear categoría',
    description: 'Crea una nueva categoría administrativa para el catálogo de farmacia.',
  })
  @ApiOkResponse({
    description: 'Categoría creada correctamente.',
    type: CategoriaResponseDto,
  })
  async crear(@Body() request: CrearCategoriaRequestDto) {
    const data = await this.crearCategoriaUseCase.execute(request);

    return {
      message: 'Categoría creada correctamente.',
      data,
    };
  }

  /**
   * Lista categorías con paginación, búsqueda y ordenamiento.
   *
   * @param query Filtros y parámetros de paginación.
   * @returns Respuesta paginada de categorías.
   */
  @Get()
  @ApiOperation({
    summary: 'Listar categorías',
    description: 'Devuelve una colección paginada de categorías administrativas.',
  })
  @ApiOkResponse({
    description: 'Listado paginado de categorías.',
    type: PageResponseDto,
  })
  async listar(@Query() query: ListarCategoriasQueryDto) {
    return this.listarCategoriasUseCase.execute(query);
  }

  /**
   * Obtiene una categoría por identificador.
   *
   * @param categoriaId Identificador de la categoría.
   * @returns Categoría encontrada.
   */
  @Get(':categoriaId')
  @ApiOperation({
    summary: 'Obtener categoría por id',
    description: 'Recupera una categoría administrativa por su identificador.',
  })
  @ApiOkResponse({
    description: 'Categoría encontrada.',
    type: CategoriaResponseDto,
  })
  async obtener(
    @Param(
      'categoriaId',
      new ParseIntSafePipe({
        fieldName: 'categoriaId',
        min: 1,
      }),
    )
    categoriaId: number,
  ) {
    const data = await this.obtenerCategoriaUseCase.execute(categoriaId);

    return {
      data,
    };
  }

  /**
   * Actualiza una categoría existente.
   *
   * @param categoriaId Identificador de la categoría.
   * @param request Datos parciales de actualización.
   * @returns Categoría actualizada.
   */
  @Patch(':categoriaId')
  @ApiOperation({
    summary: 'Actualizar categoría',
    description: 'Actualiza datos de una categoría administrativa existente.',
  })
  @ApiOkResponse({
    description: 'Categoría actualizada correctamente.',
    type: CategoriaResponseDto,
  })
  async actualizar(
    @Param(
      'categoriaId',
      new ParseIntSafePipe({
        fieldName: 'categoriaId',
        min: 1,
      }),
    )
    categoriaId: number,
    @Body() request: ActualizarCategoriaRequestDto,
  ) {
    const data = await this.actualizarCategoriaUseCase.execute(categoriaId, request);

    return {
      message: 'Categoría actualizada correctamente.',
      data,
    };
  }
}
