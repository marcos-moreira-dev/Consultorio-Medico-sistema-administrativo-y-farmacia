package dev.marcosmoreira.desktopconsultorio.http.dto.citas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/**
 * Resumen de cita para agenda y listados.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CitaDto {

    @JsonProperty("citaId")
    private Long citaId;

    @JsonProperty("pacienteId")
    private Long pacienteId;

    @JsonProperty("nombrePaciente")
    private String nombrePaciente;

    @JsonProperty("profesionalId")
    private Long profesionalId;

    @JsonProperty("nombreProfesional")
    private String nombreProfesional;

    @JsonProperty("especialidad")
    private String especialidad;

    @JsonProperty("fechaHoraInicio")
    private LocalDateTime fechaHoraInicio;

    @JsonProperty("estadoCita")
    private String estadoCita;

    @JsonProperty("motivoBreve")
    private String motivoBreve;

    @JsonProperty("observacionOperativa")
    private String observacionOperativa;

    @JsonProperty("tieneAtencion")
    private Boolean tieneAtencion;

    public Long getCitaId() { return citaId; }
    public void setCitaId(Long citaId) { this.citaId = citaId; }
    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }
    public String getNombrePaciente() { return nombrePaciente; }
    public void setNombrePaciente(String nombrePaciente) { this.nombrePaciente = nombrePaciente; }
    public Long getProfesionalId() { return profesionalId; }
    public void setProfesionalId(Long profesionalId) { this.profesionalId = profesionalId; }
    public String getNombreProfesional() { return nombreProfesional; }
    public void setNombreProfesional(String nombreProfesional) { this.nombreProfesional = nombreProfesional; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public LocalDateTime getFechaHoraInicio() { return fechaHoraInicio; }
    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) { this.fechaHoraInicio = fechaHoraInicio; }
    public String getEstadoCita() { return estadoCita; }
    public void setEstadoCita(String estadoCita) { this.estadoCita = estadoCita; }
    public String getMotivoBreve() { return motivoBreve; }
    public void setMotivoBreve(String motivoBreve) { this.motivoBreve = motivoBreve; }
    public String getObservacionOperativa() { return observacionOperativa; }
    public void setObservacionOperativa(String observacionOperativa) { this.observacionOperativa = observacionOperativa; }
    public Boolean getTieneAtencion() { return tieneAtencion; }
    public void setTieneAtencion(Boolean tieneAtencion) { this.tieneAtencion = tieneAtencion; }
}
