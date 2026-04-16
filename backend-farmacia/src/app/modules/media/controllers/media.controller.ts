import {
  Body,
  Controller,
  Param,
  ParseFilePipeBuilder,
  Patch,
  Post,
  UploadedFile,
  UseInterceptors,
} from '@nestjs/common';
import {
  ApiBearerAuth,
  ApiBody,
  ApiConsumes,
  ApiOkResponse,
  ApiOperation,
  ApiTags,
} from '@nestjs/swagger';
import { FileInterceptor } from '@nestjs/platform-express';

import { RolAdminEnum } from '../../../common/enums/rol-admin.enum';
import { UploadedFileType } from '../../../common/types/uploaded-file.type';
import { ParseIntSafePipe } from '../../../common/pipes/parse-int-safe.pipe';
import { Roles } from '../../../security/decorators/roles.decorator';
import { AsociarImagenProductoRequestDto } from '../dto/asociar-imagen-producto.request.dto';
import { MediaResponseDto } from '../dto/media.response.dto';
import { ReemplazarImagenProductoRequestDto } from '../dto/reemplazar-imagen-producto.request.dto';
import { AsociarImagenProductoUseCase } from '../use-cases/asociar-imagen-producto.use-case';
import { ReemplazarImagenProductoUseCase } from '../use-cases/reemplazar-imagen-producto.use-case';
import { SubirImagenProductoUseCase } from '../use-cases/subir-imagen-producto.use-case';

/**
 * Controlador HTTP de la superficie administrativa de media para farmacia.
 *
 * <p>Este controlador gestiona el ciclo de vida de imágenes de producto mediante
 * un flujo intencionalmente dividido en dos pasos:</p>
 *
 * <ol>
 *   <li><strong>Upload</strong>: se sube el archivo físico y se registra como
 *   recurso de media, pero <em>sin asociarlo</em> a ningún producto todavía.
 *   Esto permite validar el archivo (MIME type, tamaño, virus scan futuro)
 *   antes de vincularlo al catálogo público.</li>
 *   <li><strong>Associate / Reemplazar</strong>: se asocia un recurso ya
 *   validado a un producto específico. La separación evita que un archivo
 *   corrupto o malicioso quede directamente visible en el storefront.</li>
 * </ol>
 *
 * <p>Todos los endpoints requieren rol {@code ADMIN_FARMACIA} y token JWT
 * válido. Los archivos se almacenan en filesystem local bajo {@code STORAGE_ROOT},
 * organizados por año/mes para facilitar la gestión y limpieza posterior.</p>
 *
 * <p>Nota operativa: los archivos huérfanos (subidos pero nunca asociados, o
 * desasociados tras un reemplazo) no se eliminan físicamente en esta versión.
 * Un proceso de limpieza futura podría escanear {@code productoId IS NULL} y
 * purgar los recursos sin uso después de un período de gracia.</p>
 *
 * @see MediaService para la lógica de almacenamiento y asociación
 * @see MediaFileValidator para las reglas de validación de archivos
 */
@ApiTags('Media')
@ApiBearerAuth()
@Roles(RolAdminEnum.ADMIN_FARMACIA)
@Controller('admin/media')
export class MediaController {
  /**
   * Crea el controlador del módulo de media.
   *
   * @param subirImagenProductoUseCase Caso de uso para subir imágenes.
   * @param asociarImagenProductoUseCase Caso de uso para asociar imágenes a producto.
   * @param reemplazarImagenProductoUseCase Caso de uso para reemplazar la imagen actual de producto.
   */
  constructor(
    private readonly subirImagenProductoUseCase: SubirImagenProductoUseCase,
    private readonly asociarImagenProductoUseCase: AsociarImagenProductoUseCase,
    private readonly reemplazarImagenProductoUseCase: ReemplazarImagenProductoUseCase,
  ) {}

  /**
   * Sube un archivo de imagen para producto y lo registra como recurso de media.
   *
   * Este endpoint solo guarda el recurso. La asociación al producto se realiza
   * con un endpoint separado para mantener explícito el flujo.
   *
   * @param file Archivo multipart recibido.
   * @returns Recurso de media persistido.
   */
  @Post('productos/imagen/upload')
  @UseInterceptors(FileInterceptor('file'))
  @ApiConsumes('multipart/form-data')
  @ApiOperation({
    summary: 'Subir imagen de producto',
    description:
      'Sube una imagen y la registra como recurso de media, sin asociarla todavía a un producto.',
  })
  @ApiBody({
    schema: {
      type: 'object',
      required: ['file'],
      properties: {
        file: {
          type: 'string',
          format: 'binary',
        },
      },
    },
  })
  @ApiOkResponse({
    description: 'Imagen subida correctamente.',
    type: MediaResponseDto,
  })
  async subirImagenProducto(
    @UploadedFile(
      new ParseFilePipeBuilder()
        .addFileTypeValidator({
          fileType: /(jpg|jpeg|png|webp)$/i,
        })
        .build({
          fileIsRequired: true,
        }),
    )
    file: UploadedFileType,
  ) {
    const data = await this.subirImagenProductoUseCase.execute(file);

    return {
      message: 'Imagen subida correctamente.',
      data,
    };
  }

  /**
   * Asocia un recurso de imagen ya subido a un producto que todavía no tiene imagen.
   *
   * @param productoId Identificador del producto.
   * @param request DTO con el recurso de media a asociar.
   * @returns Recurso de media asociado al producto.
   */
  @Patch('productos/:productoId/imagen/asociar')
  @ApiOperation({
    summary: 'Asociar imagen a producto',
    description:
      'Asocia una imagen previamente subida a un producto que todavía no tiene imagen asociada.',
  })
  @ApiOkResponse({
    description: 'Imagen asociada correctamente.',
    type: MediaResponseDto,
  })
  async asociarImagenProducto(
    @Param(
      'productoId',
      new ParseIntSafePipe({
        fieldName: 'productoId',
        min: 1,
      }),
    )
    productoId: number,
    @Body() request: AsociarImagenProductoRequestDto,
  ) {
    const data = await this.asociarImagenProductoUseCase.execute(productoId, request);

    return {
      message: 'Imagen asociada correctamente.',
      data,
    };
  }

  /**
   * Reemplaza la imagen actualmente asociada a un producto.
   *
   * El recurso anterior se desasocia, pero no se elimina físicamente todavía.
   *
   * @param productoId Identificador del producto.
   * @param request DTO con el nuevo recurso de media.
   * @returns Recurso de media asociado tras el reemplazo.
   */
  @Patch('productos/:productoId/imagen/reemplazar')
  @ApiOperation({
    summary: 'Reemplazar imagen de producto',
    description:
      'Reemplaza la imagen actual del producto por otra previamente subida.',
  })
  @ApiOkResponse({
    description: 'Imagen reemplazada correctamente.',
    type: MediaResponseDto,
  })
  async reemplazarImagenProducto(
    @Param(
      'productoId',
      new ParseIntSafePipe({
        fieldName: 'productoId',
        min: 1,
      }),
    )
    productoId: number,
    @Body() request: ReemplazarImagenProductoRequestDto,
  ) {
    const data = await this.reemplazarImagenProductoUseCase.execute(productoId, request);

    return {
      message: 'Imagen reemplazada correctamente.',
      data,
    };
  }
}
