import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';

import { MediaRecursoEntity } from '../entities/media-recurso.entity';

/**
 * Repositorio de infraestructura para recursos de media.
 */
@Injectable()
export class MediaRecursoRepository {
  /**
   * Crea el repositorio wrapper.
   *
   * @param ormRepository Repositorio ORM de TypeORM.
   */
  constructor(
    @InjectRepository(MediaRecursoEntity)
    private readonly ormRepository: Repository<MediaRecursoEntity>,
  ) {}

  /**
   * Crea y persiste un nuevo recurso de media.
   *
   * @param payload Datos del recurso.
   * @returns Entidad persistida.
   */
  async createAndSave(payload: Partial<MediaRecursoEntity>): Promise<MediaRecursoEntity> {
    const entity = this.ormRepository.create(payload);

    return this.ormRepository.save(entity);
  }

  /**
   * Persiste un recurso de media existente.
   *
   * @param entity Entidad a guardar.
   * @returns Entidad persistida.
   */
  async save(entity: MediaRecursoEntity): Promise<MediaRecursoEntity> {
    return this.ormRepository.save(entity);
  }

  /**
   * Busca un recurso de media por identificador.
   *
   * @param mediaRecursoId Identificador principal.
   * @returns Recurso encontrado o null.
   */
  async findById(mediaRecursoId: number): Promise<MediaRecursoEntity | null> {
    return this.ormRepository.findOne({
      where: {
        mediaRecursoId,
      },
      relations: {
        producto: true,
      },
    });
  }

  /**
   * Busca la imagen asociada actualmente a un producto.
   *
   * @param productoId Identificador del producto.
   * @returns Recurso asociado o null.
   */
  async findByProductoId(productoId: number): Promise<MediaRecursoEntity | null> {
    return this.ormRepository.findOne({
      where: {
        productoId,
      },
      relations: {
        producto: true,
      },
      order: {
        mediaRecursoId: 'DESC',
      },
    });
  }
}
