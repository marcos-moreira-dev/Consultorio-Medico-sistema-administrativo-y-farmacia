package dev.marcosmoreira.consultorio.auth.api.response;

import dev.marcosmoreira.consultorio.auth.domain.model.AuthUser;

/**
 * DTO de salida para el inicio de sesión.
 *
 * <p>Contiene el token de acceso emitido y una vista resumida del usuario
 * autenticado. En la versión 1.0 se mantiene simple para facilitar la integración
 * inicial entre backend y cliente desktop.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class LoginResponse {

    private String accessToken;
    private String tokenType;
    private Long expiresInSeconds;
    private MeResponse usuario;

    /**
     * Constructor vacío requerido por serialización.
     */
    public LoginResponse() {
    }

    /**
     * Construye una respuesta completa de login.
     *
     * @param accessToken token de acceso emitido
     * @param tokenType tipo de token, por ejemplo {@code Bearer}
     * @param expiresInSeconds duración del token en segundos
     * @param usuario información básica del usuario autenticado
     */
    public LoginResponse(
            String accessToken,
            String tokenType,
            Long expiresInSeconds,
            MeResponse usuario
    ) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresInSeconds = expiresInSeconds;
        this.usuario = usuario;
    }

    /**
     * Construye la respuesta de login a partir del usuario autenticado
     * y del token emitido.
     *
     * @param authUser usuario autenticado
     * @param accessToken token de acceso emitido
     * @param expiresInSeconds duración del token en segundos
     * @return respuesta lista para devolverse por la API
     */
    public static LoginResponse of(
            AuthUser authUser,
            String accessToken,
            Long expiresInSeconds
    ) {
        return new LoginResponse(
                accessToken,
                "Bearer",
                expiresInSeconds,
                MeResponse.fromDomain(authUser)
        );
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getExpiresInSeconds() {
        return expiresInSeconds;
    }

    public void setExpiresInSeconds(Long expiresInSeconds) {
        this.expiresInSeconds = expiresInSeconds;
    }

    public MeResponse getUsuario() {
        return usuario;
    }

    public void setUsuario(MeResponse usuario) {
        this.usuario = usuario;
    }
}
