package dev.marcosmoreira.consultorio.usuarios.api.request;

import dev.marcosmoreira.consultorio.usuarios.domain.enums.EstadoUsuario;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de entrada para cambiar el estado lógico de un usuario.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class CambiarEstadoUsuarioRequest {

    @NotNull(message = "El nuevo estado es obligatorio.")
    private EstadoUsuario nuevoEstado;

    /**
     * Constructor vacío requerido por serialización.
     */
    public CambiarEstadoUsuarioRequest() {
    }

    /**
     * Construye el request completo.
     *
     * @param nuevoEstado nuevo estado lógico del usuario
     */
    public CambiarEstadoUsuarioRequest(EstadoUsuario nuevoEstado) {
        this.nuevoEstado = nuevoEstado;
    }

    public EstadoUsuario getNuevoEstado() {
        return nuevoEstado;
    }

    public void setNuevoEstado(EstadoUsuario nuevoEstado) {
        this.nuevoEstado = nuevoEstado;
    }
}
