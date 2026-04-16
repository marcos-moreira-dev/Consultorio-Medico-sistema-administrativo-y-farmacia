package dev.marcosmoreira.desktopconsultorio.http.dto.usuarios;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioResumenDto {

    @JsonProperty("usuarioId")
    private Long usuarioId;

    @JsonProperty("rolId")
    private Long rolId;

    @JsonProperty("rolCodigo")
    private String rolCodigo;

    @JsonProperty("rolNombre")
    private String rolNombre;

    @JsonProperty("username")
    private String username;

    @JsonProperty("nombreCompleto")
    private String nombreCompleto;

    @JsonProperty("estado")
    private String estado;

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public Long getRolId() { return rolId; }
    public void setRolId(Long rolId) { this.rolId = rolId; }
    public String getRolCodigo() { return rolCodigo; }
    public void setRolCodigo(String rolCodigo) { this.rolCodigo = rolCodigo; }
    public String getRolNombre() { return rolNombre; }
    public void setRolNombre(String rolNombre) { this.rolNombre = rolNombre; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
