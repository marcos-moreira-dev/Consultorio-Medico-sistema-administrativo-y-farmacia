import {
  Column,
  CreateDateColumn,
  Entity,
  Index,
  PrimaryGeneratedColumn,
  UpdateDateColumn,
} from 'typeorm';

/**
 * Entidad de persistencia de categoría.
 *
 * Esta entidad refleja la tabla `categoria` de la base de datos de farmacia.
 */
@Entity({ name: 'categoria' })
@Index('ux_categoria_nombre_categoria', ['nombreCategoria'], { unique: true })
export class CategoriaEntity {
  /**
   * Identificador principal de la categoría.
   */
  @PrimaryGeneratedColumn({
    name: 'categoria_id',
    type: 'integer',
  })
  categoriaId!: number;

  /**
   * Nombre visible de la categoría.
   */
  @Column({
    name: 'nombre_categoria',
    type: 'varchar',
    length: 100,
    unique: true,
  })
  nombreCategoria!: string;

  /**
   * Descripción breve opcional.
   */
  @Column({
    name: 'descripcion_breve',
    type: 'varchar',
    length: 200,
    nullable: true,
  })
  descripcionBreve?: string | null;

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
}
