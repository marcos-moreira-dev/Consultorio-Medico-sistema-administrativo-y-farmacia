package dev.marcosmoreira.desktopconsultorio.http.dto.profesionales;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Resumen de profesional para listados y selección.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfesionalResumenDto {

    @JsonProperty("profesionalId")
    private Long profesionalId;

    @JsonProperty("nombres")
    private String nombres;

    @JsonProperty("apellidos")
    private String apellidos;

    @JsonProperty("especialidadBreve")
    private String especialidadBreve;

    @JsonProperty("estadoProfesional")
    private String estadoProfesional;

    @JsonProperty("totalCitasMes")
    private Integer totalCitasMes;

    public Long getProfesionalId() { return profesionalId; }
    public void setProfesionalId(Long profesionalId) { this.profesionalId = profesionalId; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getEspecialidadBreve() { return especialidadBreve; }
    public void setEspecialidadBreve(String especialidadBreve) { this.especialidadBreve = especialidadBreve; }
    public String getEstadoProfesional() { return estadoProfesional; }
    public void setEstadoProfesional(String estadoProfesional) { this.estadoProfesional = estadoProfesional; }
    public Integer getTotalCitasMes() { return totalCitasMes; }
    public void setTotalCitasMes(Integer totalCitasMes) { this.totalCitasMes = totalCitasMes; }

    public String getNombreCompleto() {
        return (nombres != null ? nombres : "") + " " + (apellidos != null ? apellidos : "");
    }
}
