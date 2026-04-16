package dev.marcosmoreira.consultorio.citas.api.response;

import dev.marcosmoreira.consultorio.citas.domain.model.Cita;
import java.time.LocalDateTime;

/**
 * DTO de salida resumido para la agenda de citas.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class AgendaItemResponse {

    private Long citaId;
    private Long pacienteId;
    private Long profesionalId;
    private LocalDateTime fechaHoraInicio;
    private String estadoCita;
    private String motivoBreve;
    private String observacionOperativa;

    /**
     * Constructor vacío requerido por serialización.
     */
    public AgendaItemResponse() {
    }

    /**
     * Construye la respuesta resumida de agenda.
     *
     * @param citaId identificador de la cita
     * @param pacienteId identificador del paciente
     * @param profesionalId identificador del profesional
     * @param fechaHoraInicio fecha y hora de inicio
     * @param estadoCita estado lógico de la cita
     * @param motivoBreve motivo breve
     * @param observacionOperativa observación operativa
     */
    public AgendaItemResponse(
            Long citaId,
            Long pacienteId,
            Long profesionalId,
            LocalDateTime fechaHoraInicio,
            String estadoCita,
            String motivoBreve,
            String observacionOperativa
    ) {
        this.citaId = citaId;
        this.pacienteId = pacienteId;
        this.profesionalId = profesionalId;
        this.fechaHoraInicio = fechaHoraInicio;
        this.estadoCita = estadoCita;
        this.motivoBreve = motivoBreve;
        this.observacionOperativa = observacionOperativa;
    }

    /**
     * Construye el item de agenda a partir del dominio.
     *
     * @param cita cita del dominio
     * @return DTO resumido
     */
    public static AgendaItemResponse fromDomain(Cita cita) {
        if (cita == null) {
            throw new IllegalArgumentException("La cita no puede ser nula.");
        }

        return new AgendaItemResponse(
                cita.getCitaId(),
                cita.getPacienteId(),
                cita.getProfesionalId(),
                cita.getFechaHoraInicio(),
                cita.getEstadoCita() == null ? null : cita.getEstadoCita().name(),
                cita.getMotivoBreve(),
                cita.getObservacionOperativa()
        );
    }

    public Long getCitaId() {
        return citaId;
    }

    public void setCitaId(Long citaId) {
        this.citaId = citaId;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getProfesionalId() {
        return profesionalId;
    }

    public void setProfesionalId(Long profesionalId) {
        this.profesionalId = profesionalId;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public String getEstadoCita() {
        return estadoCita;
    }

    public void setEstadoCita(String estadoCita) {
        this.estadoCita = estadoCita;
    }

    public String getMotivoBreve() {
        return motivoBreve;
    }

    public void setMotivoBreve(String motivoBreve) {
        this.motivoBreve = motivoBreve;
    }

    public String getObservacionOperativa() {
        return observacionOperativa;
    }

    public void setObservacionOperativa(String observacionOperativa) {
        this.observacionOperativa = observacionOperativa;
    }
}
