package dev.marcosmoreira.desktopconsultorio.http.dto.dashboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DashboardResumenDto {

    @JsonProperty("totalPacientes")
    private Long totalPacientes;

    @JsonProperty("citasHoy")
    private Long citasHoy;

    @JsonProperty("citasSemana")
    private Long citasSemana;

    @JsonProperty("atencionesMes")
    private Long atencionesMes;

    @JsonProperty("cobrosMes")
    private Long cobrosMes;

    @JsonProperty("totalCobradoMes")
    private BigDecimal totalCobradoMes;

    @JsonProperty("cobrosPendientes")
    private Long cobrosPendientes;

    @JsonProperty("profesionalesActivos")
    private Long profesionalesActivos;

    @JsonProperty("generadoEn")
    private String generadoEn;

    public Long getTotalPacientes() { return totalPacientes; }
    public void setTotalPacientes(Long totalPacientes) { this.totalPacientes = totalPacientes; }
    public Long getCitasHoy() { return citasHoy; }
    public void setCitasHoy(Long citasHoy) { this.citasHoy = citasHoy; }
    public Long getCitasSemana() { return citasSemana; }
    public void setCitasSemana(Long citasSemana) { this.citasSemana = citasSemana; }
    public Long getAtencionesMes() { return atencionesMes; }
    public void setAtencionesMes(Long atencionesMes) { this.atencionesMes = atencionesMes; }
    public Long getCobrosMes() { return cobrosMes; }
    public void setCobrosMes(Long cobrosMes) { this.cobrosMes = cobrosMes; }
    public BigDecimal getTotalCobradoMes() { return totalCobradoMes; }
    public void setTotalCobradoMes(BigDecimal totalCobradoMes) { this.totalCobradoMes = totalCobradoMes; }
    public Long getCobrosPendientes() { return cobrosPendientes; }
    public void setCobrosPendientes(Long cobrosPendientes) { this.cobrosPendientes = cobrosPendientes; }
    public Long getProfesionalesActivos() { return profesionalesActivos; }
    public void setProfesionalesActivos(Long profesionalesActivos) { this.profesionalesActivos = profesionalesActivos; }
    public String getGeneradoEn() { return generadoEn; }
    public void setGeneradoEn(String generadoEn) { this.generadoEn = generadoEn; }
}
