package dev.marcosmoreira.desktopconsultorio.http.dto.citas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/**
 * Item de agenda para el timeline del día en el módulo de citas.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgendaItemDto {

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

    @JsonProperty("fechaHoraInicio")
    private LocalDateTime fechaHoraInicio;

    @JsonProperty("estadoCita")
    private String estadoCita;

    @JsonProperty("motivoBreve")
    private String motivoBreve;

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
    public LocalDateTime getFechaHoraInicio() { return fechaHoraInicio; }
    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) { this.fechaHoraInicio = fechaHoraInicio; }
    public String getEstadoCita() { return estadoCita; }
    public void setEstadoCita(String estadoCita) { this.estadoCita = estadoCita; }
    public String getMotivoBreve() { return motivoBreve; }
    public void setMotivoBreve(String motivoBreve) { this.motivoBreve = motivoBreve; }
}
