package dev.marcosmoreira.consultorio.dashboard.api.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DashboardResumenResponse {

    private Long totalPacientes;
    private Long citasHoy;
    private Long citasSemana;
    private Long atencionesMes;
    private Long cobrosMes;
    private BigDecimal totalCobradoMes;
    private Long cobrosPendientes;
    private Long profesionalesActivos;
    private LocalDateTime generadoEn;

    public DashboardResumenResponse() {}

    public DashboardResumenResponse(Long totalPacientes, Long citasHoy, Long citasSemana,
                                     Long atencionesMes, Long cobrosMes, BigDecimal totalCobradoMes,
                                     Long cobrosPendientes, Long profesionalesActivos) {
        this.totalPacientes = totalPacientes;
        this.citasHoy = citasHoy;
        this.citasSemana = citasSemana;
        this.atencionesMes = atencionesMes;
        this.cobrosMes = cobrosMes;
        this.totalCobradoMes = totalCobradoMes;
        this.cobrosPendientes = cobrosPendientes;
        this.profesionalesActivos = profesionalesActivos;
        this.generadoEn = LocalDateTime.now();
    }

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
    public LocalDateTime getGeneradoEn() { return generadoEn; }
    public void setGeneradoEn(LocalDateTime generadoEn) { this.generadoEn = generadoEn; }
}
