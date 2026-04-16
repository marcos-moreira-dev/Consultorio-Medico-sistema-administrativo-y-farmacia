package dev.marcosmoreira.desktopconsultorio.http.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Respuesta exitosa de login con token JWT y datos del usuario.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponseDto {

    @JsonProperty("accessToken")
    private String accessToken;

    @JsonProperty("tokenType")
    private String tokenType;

    @JsonProperty("expiresInSeconds")
    private Long expiresInSeconds;

    @JsonProperty("usuario")
    private UserInfoDto usuario;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserInfoDto {
        @JsonProperty("usuarioId")
        private Long usuarioId;

        @JsonProperty("username")
        private String username;

        @JsonProperty("nombreCompleto")
        private String nombreCompleto;

        @JsonProperty("rolCodigo")
        private String rolCodigo;

        @JsonProperty("rolNombre")
        private String rolNombre;

        @JsonProperty("activo")
        private Boolean activo;

        public Long getUsuarioId() { return usuarioId; }
        public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getNombreCompleto() { return nombreCompleto; }
        public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
        public String getRolCodigo() { return rolCodigo; }
        public void setRolCodigo(String rolCodigo) { this.rolCodigo = rolCodigo; }
        public String getRolNombre() { return rolNombre; }
        public void setRolNombre(String rolNombre) { this.rolNombre = rolNombre; }
        public Boolean getActivo() { return activo; }
        public void setActivo(Boolean activo) { this.activo = activo; }
    }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }
    public Long getExpiresInSeconds() { return expiresInSeconds; }
    public void setExpiresInSeconds(Long expiresInSeconds) { this.expiresInSeconds = expiresInSeconds; }
    public UserInfoDto getUsuario() { return usuario; }
    public void setUsuario(UserInfoDto usuario) { this.usuario = usuario; }
}
