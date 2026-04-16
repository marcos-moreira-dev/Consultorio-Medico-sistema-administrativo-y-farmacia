import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';

import { UsuarioAdminEntity } from '../entities/usuario-admin.entity';

/**
 * Repositorio de infraestructura para usuarios admin.
 */
@Injectable()
export class UsuarioAdminRepository {
  /**
   * Crea el repositorio wrapper.
   *
   * @param ormRepository Repositorio ORM de TypeORM.
   */
  constructor(
    @InjectRepository(UsuarioAdminEntity)
    private readonly ormRepository: Repository<UsuarioAdminEntity>,
  ) {}

  /**
   * Busca un usuario admin por username.
   *
   * @param username Username exacto a buscar.
   * @returns Usuario admin encontrado o null.
   */
  async findByUsername(username: string): Promise<UsuarioAdminEntity | null> {
    const normalizedUsername = username.trim();

    return this.ormRepository.findOne({
      where: {
        username: normalizedUsername,
      },
    });
  }

  /**
   * Busca un usuario admin por identificador.
   *
   * @param usuarioAdminId Identificador del admin.
   * @returns Usuario admin encontrado o null.
   */
  async findById(usuarioAdminId: number): Promise<UsuarioAdminEntity | null> {
    return this.ormRepository.findOne({
      where: {
        usuarioAdminId,
      },
    });
  }
}
