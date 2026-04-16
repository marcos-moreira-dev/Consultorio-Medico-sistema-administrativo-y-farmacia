import {
  Column,
  CreateDateColumn,
  Entity,
  Index,
  JoinColumn,
  ManyToOne,
  PrimaryGeneratedColumn,
  UpdateDateColumn,
} from 'typeorm';

import { TipoRecursoMediaEnum } from '../../../common/enums/tipo-recurso-media.enum';
import { ProductoEntity } from '../../productos/entities/producto.entity';

/**
 * Entidad de persistencia para recursos de media.
 *
 * Nota:
 * Esta entidad presupone una tabla `media_recurso`. Si la base actual todavía no
 * la incluye, luego habrá que crear la migración correspondiente.
 */
@Entity({ name: 'media_recurso' })
@Index('ix_media_recurso_producto_id', ['productoId'])
@Index('ix_media_recurso_tipo_recurso', ['tipoRecurso'])
export class MediaRecursoEntity {
  /**
   * Identificador principal del recurso de media.
   */
  @PrimaryGeneratedColumn({
    name: 'media_recurso_id',
    type: 'integer',
  })
  mediaRecursoId!: number;

  /**
   * Identificador del producto asociado, si existe.
   */
  @Column({
    name: 'producto_id',
    type: 'integer',
    nullable: true,
  })
  productoId?: number | null;

  /**
   * Tipo funcional del recurso.
   */
  @Column({
    name: 'tipo_recurso',
    type: 'varchar',
    length: 50,
    default: TipoRecursoMediaEnum.IMAGEN_PRODUCTO,
  })
  tipoRecurso!: TipoRecursoMediaEnum;

  /**
   * Nombre original del archivo.
   */
  @Column({
    name: 'nombre_original',
    type: 'varchar',
    length: 255,
  })
  nombreOriginal!: string;

  /**
   * Nombre físico generado para almacenamiento.
   */
  @Column({
    name: 'nombre_archivo',
    type: 'varchar',
    length: 255,
  })
  nombreArchivo!: string;

  /**
   * MIME type detectado del archivo.
   */
  @Column({
    name: 'mime_type',
    type: 'varchar',
    length: 120,
  })
  mimeType!: string;

  /**
   * Extensión normalizada del archivo.
   */
  @Column({
    name: 'extension',
    type: 'varchar',
    length: 20,
  })
  extension!: string;

  /**
   * Tamaño del archivo en bytes.
   */
  @Column({
    name: 'tamano_bytes',
    type: 'integer',
  })
  tamanoBytes!: number;

  /**
   * Ruta relativa del archivo dentro del storage.
   */
  @Column({
    name: 'ruta_relativa',
    type: 'varchar',
    length: 500,
  })
  rutaRelativa!: string;

  /**
   * URL pública calculada del recurso.
   */
  @Column({
    name: 'url_publica',
    type: 'varchar',
    length: 500,
  })
  urlPublica!: string;

  /**
   * Marca de creación.
   */
  @CreateDateColumn({
    name: 'fecha_creacion',
    type: 'timestamp without time zone',
  })
  fechaCreacion!: Date;

  /**
   * Marca de última actualización.
   */
  @UpdateDateColumn({
    name: 'fecha_actualizacion',
    type: 'timestamp without time zone',
  })
  fechaActualizacion!: Date;

  /**
   * Relación con el producto asociado.
   */
  @ManyToOne(() => ProductoEntity, {
    nullable: true,
    eager: false,
  })
  @JoinColumn({ name: 'producto_id', referencedColumnName: 'productoId' })
  producto?: ProductoEntity | null;
}
