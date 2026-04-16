package dev.marcosmoreira.consultorio.usuarios.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada para crear un usuario del sistema.
 *
 * <p>En V1 el request no recibe un nombre visible independiente porque la BD del
 * consultorio todavía no persiste ese dato en {@code usuario}. El nombre visible
 * se deriva luego desde {@code profesional} o, si no existe vínculo, desde el
 * propio {@code username}.</p>
 */
public class CrearUsuarioRequest {

    @NotNull(message = "El rol es obligatorio.")
    @Positive(message = "El rol debe ser mayor que cero.")
    private Long rolId;

    @NotBlank(message = "El username es obligatorio.")
    @Size(max = 100, message = "El username no puede exceder los 100 caracteres.")
    private String username;

    @NotBlank(message = "La contraseña temporal es obligatoria.")
    @Size(max = 255, message = "La contraseña temporal no puede exceder los 255 caracteres.")
    private String passwordTemporal;

    public CrearUsuarioRequest() {
    }

    public CrearUsuarioRequest(Long rolId, String username, String passwordTemporal) {
        this.rolId = rolId;
        this.username = normalizeNullableText(username);
        this.passwordTemporal = passwordTemporal;
    }

    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = normalizeNullableText(username);
    }

    public String getPasswordTemporal() {
        return passwordTemporal;
    }

    public void setPasswordTemporal(String passwordTemporal) {
        this.passwordTemporal = passwordTemporal;
    }

    private String normalizeNullableText(String value) {
        return value == null ? null : value.trim();
    }
}
