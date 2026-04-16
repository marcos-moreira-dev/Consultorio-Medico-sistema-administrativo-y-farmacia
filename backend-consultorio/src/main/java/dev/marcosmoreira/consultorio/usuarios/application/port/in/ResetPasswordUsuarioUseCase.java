package dev.marcosmoreira.consultorio.usuarios.application.port.in;

import dev.marcosmoreira.consultorio.usuarios.domain.model.Usuario;

/**
 * Caso de uso para resetear la contraseña de un usuario.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public interface ResetPasswordUsuarioUseCase {

    /**
     * Resetea la contraseña del usuario indicado.
     *
     * @param usuarioId identificador del usuario
     * @param nuevoPassword nueva contraseña en texto plano
     * @return usuario actualizado
     */
    Usuario resetPassword(Long usuarioId, String nuevoPassword);
}
