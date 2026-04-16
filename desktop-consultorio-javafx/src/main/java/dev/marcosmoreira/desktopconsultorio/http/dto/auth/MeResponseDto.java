package dev.marcosmoreira.desktopconsultorio.http.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Resumen del usuario autenticado actual (endpoint /auth/me).
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MeResponseDto {

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
