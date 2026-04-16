import {
  BadRequestException,
  Injectable,
  PipeTransform,
} from '@nestjs/common';

/**
 * Opciones de configuración del pipe.
 */
export interface ParseIntSafePipeOptions {
  /**
   * Nombre del campo o parámetro, usado en mensajes de error.
   */
  fieldName?: string;

  /**
   * Indica si el valor es opcional.
   */
  optional?: boolean;

  /**
   * Valor por defecto cuando no se recibe uno explícito.
   */
  defaultValue?: number;

  /**
   * Valor mínimo permitido.
   */
  min?: number;

  /**
   * Valor máximo permitido.
   */
  max?: number;
}

/**
 * Pipe para convertir enteros de forma segura y con mensajes de error claros.
 *
 * A diferencia de un parseo ingenuo, este pipe:
 * - valida que el valor exista cuando es obligatorio;
 * - rechaza enteros inválidos;
 * - permite declarar límites mínimos y máximos;
 * - soporta valores por defecto.
 */
@Injectable()
export class ParseIntSafePipe implements PipeTransform<unknown, number | undefined> {
  /**
   * Crea una instancia del pipe.
   *
   * @param options Opciones de configuración.
   */
  constructor(private readonly options: ParseIntSafePipeOptions = {}) {}

  /**
   * Transforma un valor de entrada en un entero seguro.
   *
   * @param value Valor recibido desde ruta, query o cuerpo.
   * @returns Entero validado o undefined cuando aplica.
   */
  transform(value: unknown): number | undefined {
    const fieldName = this.options.fieldName ?? 'valor';
    const isMissing = value === undefined || value === null || String(value).trim() === '';

    if (isMissing) {
      if (this.options.defaultValue !== undefined) {
        return this.options.defaultValue;
      }

      if (this.options.optional) {
        return undefined;
      }

      throw new BadRequestException(`${fieldName} es obligatorio y debe ser un entero válido.`);
    }

    const normalized = typeof value === 'number' ? String(value) : String(value).trim();

    if (!/^-?\d+$/.test(normalized)) {
      throw new BadRequestException(`${fieldName} debe ser un entero válido.`);
    }

    const parsed = Number(normalized);

    if (!Number.isSafeInteger(parsed)) {
      throw new BadRequestException(`${fieldName} debe ser un entero seguro.`);
    }

    if (this.options.min !== undefined && parsed < this.options.min) {
      throw new BadRequestException(
        `${fieldName} debe ser mayor o igual a ${this.options.min}.`,
      );
    }

    if (this.options.max !== undefined && parsed > this.options.max) {
      throw new BadRequestException(
        `${fieldName} debe ser menor o igual a ${this.options.max}.`,
      );
    }

    return parsed;
  }
}
