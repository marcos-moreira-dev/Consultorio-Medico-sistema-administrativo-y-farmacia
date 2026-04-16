package dev.marcosmoreira.consultorio.usuarios.application.port.in;

import dev.marcosmoreira.consultorio.usuarios.domain.enums.EstadoUsuario;
import dev.marcosmoreira.consultorio.usuarios.domain.model.Usuario;

/**
 * Caso de uso para cambiar el estado lógico de un usuario.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public interface CambiarEstadoUsuarioUseCase {

    /**
     * Cambia el estado lógico del usuario indicado.
     *
     * @param usuarioId identificador del usuario
     * @param nuevoEstado nuevo estado a asignar
     * @return usuario actualizado
     */
    Usuario cambiarEstado(Long usuarioId, EstadoUsuario nuevoEstado);
}
