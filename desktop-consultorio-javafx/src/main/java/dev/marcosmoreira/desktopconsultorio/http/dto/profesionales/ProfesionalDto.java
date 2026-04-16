package dev.marcosmoreira.desktopconsultorio.http.dto.profesionales;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/**
 * Datos completos de un profesional.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfesionalDto {

    @JsonProperty("profesionalId")
    private Long profesionalId;

    @JsonProperty("usuarioId")
    private Long usuarioId;

    @JsonProperty("nombres")
    private String nombres;

    @JsonProperty("apellidos")
    private String apellidos;

    @JsonProperty("especialidadBreve")
    private String especialidadBreve;

    @JsonProperty("registroProfesional")
    private String registroProfesional;

    @JsonProperty("estadoProfesional")
    private String estadoProfesional;

    @JsonProperty("fechaCreacion")
    private LocalDateTime fechaCreacion;

    public Long getProfesionalId() { return profesionalId; }
    public void setProfesionalId(Long profesionalId) { this.profesionalId = profesionalId; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getEspecialidadBreve() { return especialidadBreve; }
    public void setEspecialidadBreve(String especialidadBreve) { this.especialidadBreve = especialidadBreve; }
    public String getRegistroProfesional() { return registroProfesional; }
    public void setRegistroProfesional(String registroProfesional) { this.registroProfesional = registroProfesional; }
    public String getEstadoProfesional() { return estadoProfesional; }
    public void setEstadoProfesional(String estadoProfesional) { this.estadoProfesional = estadoProfesional; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public String getNombreCompleto() {
        return (nombres != null ? nombres : "") + " " + (apellidos != null ? apellidos : "");
    }
}
