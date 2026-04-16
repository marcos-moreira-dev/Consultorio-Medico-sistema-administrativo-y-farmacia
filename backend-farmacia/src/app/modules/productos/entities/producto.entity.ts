import {
  Column,
  CreateDateColumn,
  Entity,
  Index,
  JoinColumn,
  ManyToOne,
  PrimaryGeneratedColumn,
  UpdateDateColumn,
  ValueTransformer,
} from 'typeorm';

import { CategoriaEntity } from '../../categorias/entities/categoria.entity';

/**
 * Transformer sencillo para columnas numéricas que deben exponerse como number.
 */
const DecimalToNumberTransformer: ValueTransformer = {
  to: (value?: number | null) => value,
  from: (value?: string | number | null) =>
    value === null || value === undefined ? null : Number(value),
};

/**
 * Entidad de persistencia de producto.
 *
 * Esta entidad refleja la tabla `producto` de la base de datos de farmacia.
 */
@Entity({ name: 'producto' })
@Index('ux_producto_categoria_nombre_presentacion', ['categoriaId', 'nombreProducto', 'presentacion'], {
  unique: true,
})
@Index('ix_producto_categoria_id', ['categoriaId'])
@Index('ix_producto_estado_producto', ['estadoProducto'])
@Index('ix_producto_estado_disponibilidad', ['estadoDisponibilidad'])
export class ProductoEntity {
  /**
   * Identificador principal del producto.
   */
  @PrimaryGeneratedColumn({
    name: 'producto_id',
    type: 'integer',
  })
  productoId!: number;

  /**
   * Identificador de la categoría asociada.
   */
  @Column({
    name: 'categoria_id',
    type: 'integer',
  })
  categoriaId!: number;

  /**
   * Nombre visible del producto.
   */
  @Column({
    name: 'nombre_producto',
    type: 'varchar',
    length: 150,
  })
  nombreProducto!: string;

  /**
   * Presentación comercial del producto.
   */
  @Column({
    name: 'presentacion',
    type: 'varchar',
    length: 100,
  })
  presentacion!: string;

  /**
   * Descripción breve opcional.
   */
  @Column({
    name: 'descripcion_breve',
    type: 'varchar',
    length: 300,
    nullable: true,
  })
  descripcionBreve?: string | null;

  /**
   * Precio visible al público.
   */
  @Column({
    name: 'precio_visible',
    type: 'numeric',
    precision: 10,
    scale: 2,
    transformer: DecimalToNumberTransformer,
  })
  precioVisible!: number;

  /**
   * Estado lógico del producto.
   */
  @Column({
    name: 'estado_producto',
    type: 'varchar',
    length: 20,
    default: 'ACTIVO',
  })
  estadoProducto!: string;

  /**
   * Indica si el producto es publicable o está publicado.
   */
  @Column({
    name: 'es_publicable',
    type: 'boolean',
    default: false,
  })
  esPublicable!: boolean;

  /**
   * Estado de disponibilidad operativa del producto.
   */
  @Column({
    name: 'estado_disponibilidad',
    type: 'varchar',
    length: 20,
    default: 'NO_PUBLICADO',
  })
  estadoDisponibilidad!: string;

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
   * Relación con la categoría asociada.
   */
  @ManyToOne(() => CategoriaEntity, {
    nullable: false,
    eager: false,
  })
  @JoinColumn({ name: 'categoria_id', referencedColumnName: 'categoriaId' })
  categoria?: CategoriaEntity;
}
