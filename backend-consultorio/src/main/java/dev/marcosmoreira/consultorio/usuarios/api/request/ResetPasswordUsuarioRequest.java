package dev.marcosmoreira.consultorio.usuarios.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada para resetear la contraseña de un usuario.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class ResetPasswordUsuarioRequest {

    @NotBlank(message = "La nueva contraseña es obligatoria.")
    @Size(max = 255, message = "La nueva contraseña no puede exceder los 255 caracteres.")
    private String nuevoPassword;

    /**
     * Constructor vacío requerido por serialización.
     */
    public ResetPasswordUsuarioRequest() {
    }

    /**
     * Construye el request completo.
     *
     * @param nuevoPassword nueva contraseña en texto plano
     */
    public ResetPasswordUsuarioRequest(String nuevoPassword) {
        this.nuevoPassword = nuevoPassword;
    }

    public String getNuevoPassword() {
        return nuevoPassword;
    }

    public void setNuevoPassword(String nuevoPassword) {
        this.nuevoPassword = nuevoPassword;
    }
}
