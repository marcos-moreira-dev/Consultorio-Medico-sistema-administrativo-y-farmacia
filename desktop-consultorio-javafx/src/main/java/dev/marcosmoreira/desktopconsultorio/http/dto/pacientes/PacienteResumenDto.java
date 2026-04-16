package dev.marcosmoreira.desktopconsultorio.http.dto.pacientes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Resumen de paciente para listados y búsquedas rápidas.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PacienteResumenDto {

    @JsonProperty("pacienteId")
    private Long pacienteId;

    @JsonProperty("nombres")
    private String nombres;

    @JsonProperty("apellidos")
    private String apellidos;

    @JsonProperty("cedula")
    private String cedula;

    @JsonProperty("telefono")
    private String telefono;

    @JsonProperty("totalCitas")
    private Integer totalCitas;

    @JsonProperty("totalAtenciones")
    private Integer totalAtenciones;

    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public Integer getTotalCitas() { return totalCitas; }
    public void setTotalCitas(Integer totalCitas) { this.totalCitas = totalCitas; }
    public Integer getTotalAtenciones() { return totalAtenciones; }
    public void setTotalAtenciones(Integer totalAtenciones) { this.totalAtenciones = totalAtenciones; }

    public String getNombreCompleto() {
        return (nombres != null ? nombres : "") + " " + (apellidos != null ? apellidos : "");
    }
}
