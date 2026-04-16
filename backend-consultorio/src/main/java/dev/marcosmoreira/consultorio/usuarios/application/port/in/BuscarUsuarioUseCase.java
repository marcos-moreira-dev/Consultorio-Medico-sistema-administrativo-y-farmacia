package dev.marcosmoreira.consultorio.usuarios.application.port.in;

import dev.marcosmoreira.consultorio.usuarios.domain.model.Usuario;

/**
 * Caso de uso para consultar un usuario puntual por su identificador.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public interface BuscarUsuarioUseCase {

    /**
     * Busca un usuario por su identificador único.
     *
     * @param usuarioId identificador del usuario
     * @return usuario encontrado
     */
    Usuario buscarPorId(Long usuarioId);
}
