package dev.marcosmoreira.consultorio.roles.application.port.in;

import dev.marcosmoreira.consultorio.roles.domain.model.Rol;

/**
 * Caso de uso para consultar un rol puntual por su identificador.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public interface BuscarRolUseCase {

    /**
     * Busca un rol por su identificador único.
     *
     * @param rolId identificador del rol
     * @return rol encontrado
     */
    Rol buscarPorId(Long rolId);
}
