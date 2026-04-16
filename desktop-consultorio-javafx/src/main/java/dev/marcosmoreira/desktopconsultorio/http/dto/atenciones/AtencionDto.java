package dev.marcosmoreira.desktopconsultorio.http.dto.atenciones;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/**
 * Datos de una atención médica registrada.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AtencionDto {

    @JsonProperty("atencionId")
    private Long atencionId;

    @JsonProperty("pacienteId")
    private Long pacienteId;

    @JsonProperty("nombrePaciente")
    private String nombrePaciente;

    @JsonProperty("profesionalId")
    private Long profesionalId;

    @JsonProperty("nombreProfesional")
    private String nombreProfesional;

    @JsonProperty("citaId")
    private Long citaId;

    @JsonProperty("fechaHoraAtencion")
    private LocalDateTime fechaHoraAtencion;

    @JsonProperty("notaBreve")
    private String notaBreve;

    @JsonProperty("indicacionesBreves")
    private String indicacionesBreves;

    @JsonProperty("tieneCobro")
    private Boolean tieneCobro;

    public Long getAtencionId() { return atencionId; }
    public void setAtencionId(Long atencionId) { this.atencionId = atencionId; }
    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }
    public String getNombrePaciente() { return nombrePaciente; }
    public void setNombrePaciente(String nombrePaciente) { this.nombrePaciente = nombrePaciente; }
    public Long getProfesionalId() { return profesionalId; }
    public void setProfesionalId(Long profesionalId) { this.profesionalId = profesionalId; }
    public String getNombreProfesional() { return nombreProfesional; }
    public void setNombreProfesional(String nombreProfesional) { this.nombreProfesional = nombreProfesional; }
    public Long getCitaId() { return citaId; }
    public void setCitaId(Long citaId) { this.citaId = citaId; }
    public LocalDateTime getFechaHoraAtencion() { return fechaHoraAtencion; }
    public void setFechaHoraAtencion(LocalDateTime fechaHoraAtencion) { this.fechaHoraAtencion = fechaHoraAtencion; }
    public String getNotaBreve() { return notaBreve; }
    public void setNotaBreve(String notaBreve) { this.notaBreve = notaBreve; }
    public String getIndicacionesBreves() { return indicacionesBreves; }
    public void setIndicacionesBreves(String indicacionesBreves) { this.indicacionesBreves = indicacionesBreves; }
    public Boolean getTieneCobro() { return tieneCobro; }
    public void setTieneCobro(Boolean tieneCobro) { this.tieneCobro = tieneCobro; }
}
