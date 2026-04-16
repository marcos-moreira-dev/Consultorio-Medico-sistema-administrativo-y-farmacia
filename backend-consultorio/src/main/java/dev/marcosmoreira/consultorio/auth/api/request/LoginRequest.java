package dev.marcosmoreira.consultorio.auth.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada para el inicio de sesión.
 *
 * <p>Representa el contrato HTTP mínimo para autenticar a un usuario
 * mediante credenciales básicas en la versión 1.0.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class LoginRequest {

    @NotBlank(message = "El username es obligatorio.")
    @Size(max = 100, message = "El username no puede exceder los 100 caracteres.")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria.")
    @Size(max = 255, message = "La contraseña no puede exceder los 255 caracteres.")
    private String password;

    /**
     * Constructor vacío requerido por serialización.
     */
    public LoginRequest() {
    }

    /**
     * Construye un request completo de login.
     *
     * @param username nombre de usuario
     * @param password contraseña en texto plano enviada por el cliente
     */
    public LoginRequest(String username, String password) {
        this.username = normalizeRequiredText(username);
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = normalizeRequiredText(username);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Normaliza un texto obligatorio aplicando trim.
     *
     * @param value texto a normalizar
     * @return texto normalizado
     */
    private String normalizeRequiredText(String value) {
        return value == null ? null : value.trim();
    }
}
