import {
  Column,
  CreateDateColumn,
  Entity,
  Index,
  PrimaryGeneratedColumn,
  UpdateDateColumn,
} from 'typeorm';

import { RolAdminEnum } from '../../../common/enums/rol-admin.enum';

/**
 * Entidad de persistencia del usuario administrativo de farmacia.
 *
 * Nota:
 * Esta entidad presupone una tabla `usuario_admin`. Si la base actual todavía
 * no la incluye, luego habrá que crear su migración correspondiente.
 */
@Entity({ name: 'usuario_admin' })
@Index('ux_usuario_admin_username', ['username'], { unique: true })
@Index('ix_usuario_admin_estado', ['estado'])
export class UsuarioAdminEntity {
  /**
   * Identificador principal del usuario admin.
   */
  @PrimaryGeneratedColumn({
    name: 'usuario_admin_id',
    type: 'integer',
  })
  usuarioAdminId!: number;

  /**
   * Username único del usuario admin.
   */
  @Column({
    name: 'username',
    type: 'varchar',
    length: 100,
    unique: true,
  })
  username!: string;

  /**
   * Hash BCrypt de la contraseña.
   */
  @Column({
    name: 'password_hash',
    type: 'varchar',
    length: 255,
  })
  passwordHash!: string;

  /**
   * Correo opcional del admin.
   */
  @Column({
    name: 'email',
    type: 'varchar',
    length: 150,
    nullable: true,
  })
  email?: string | null;

  /**
   * Estado lógico del usuario admin.
   */
  @Column({
    name: 'estado',
    type: 'varchar',
    length: 20,
    default: 'ACTIVO',
  })
  estado!: string;

  /**
   * Rol principal del usuario admin.
   */
  @Column({
    name: 'rol_codigo',
    type: 'varchar',
    length: 50,
    default: RolAdminEnum.ADMIN_FARMACIA,
  })
  rolCodigo!: RolAdminEnum;

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
   * Indica si el usuario admin está activo.
   *
   * @returns true cuando el estado lógico es ACTIVO.
   */
  isActivo(): boolean {
    return String(this.estado ?? '').trim().toUpperCase() === 'ACTIVO';
  }

  /**
   * Devuelve la lista de roles efectivos del usuario.
   *
   * @returns Arreglo de roles sin valores vacíos.
   */
  getRoles(): RolAdminEnum[] {
    const normalized = String(this.rolCodigo ?? '').trim();

    return normalized ? [normalized as RolAdminEnum] : [];
  }
}
