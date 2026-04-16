import { Injectable } from '@angular/core';

import { ProductoDetalleModel } from '../../../features/producto-detalle/models/producto-detalle.model';
import { ImagePathUtil } from '../../utils/image-path.util';
import { ProductoPublicoDetalleDto } from '../dto/producto/producto-publico-detalle.dto';

@Injectable({
  providedIn: 'root',
})
export class ProductoHttpAdapter {
  toProductoDetalleModel(dto: ProductoPublicoDetalleDto): ProductoDetalleModel {
    return {
      ...dto,
      imagenSrc: ImagePathUtil.resolvePublicImage(dto.imagenUrl),
    };
  }
}
