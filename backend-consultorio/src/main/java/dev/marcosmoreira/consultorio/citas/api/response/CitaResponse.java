package dev.marcosmoreira.consultorio.citas.api.response;

import dev.marcosmoreira.consultorio.citas.domain.model.Cita;
import java.time.LocalDateTime;

/**
 * DTO de salida con el detalle completo de una cita.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class CitaResponse {

    private Long citaId;
    private Long pacienteId;
    private Long profesionalId;
    private LocalDateTime fechaHoraInicio;
    private String estadoCita;
    private String motivoBreve;
    private String observacionOperativa;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    /**
     * Constructor vacío requerido por serialización.
     */
    public CitaResponse() {
    }

    /**
     * Construye la respuesta completa.
     *
     * @param citaId identificador de la cita
     * @param pacienteId identificador del paciente
     * @param profesionalId identificador del profesional
     * @param fechaHoraInicio fecha y hora de inicio
     * @param estadoCita estado lógico de la cita
     * @param motivoBreve motivo breve
     * @param observacionOperativa observación operativa
     * @param fechaCreacion fecha de creación
     * @param fechaActualizacion fecha de última actualización
     */
    public CitaResponse(
            Long citaId,
            Long pacienteId,
            Long profesionalId,
            LocalDateTime fechaHoraInicio,
            String estadoCita,
            String motivoBreve,
            String observacionOperativa,
            LocalDateTime fechaCreacion,
            LocalDateTime fechaActualizacion
    ) {
        this.citaId = citaId;
        this.pacienteId = pacienteId;
        this.profesionalId = profesionalId;
        this.fechaHoraInicio = fechaHoraInicio;
        this.estadoCita = estadoCita;
        this.motivoBreve = motivoBreve;
        this.observacionOperativa = observacionOperativa;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    /**
     * Construye la respuesta a partir del dominio.
     *
     * @param cita cita del dominio
     * @return DTO listo para serializar
     */
    public static CitaResponse fromDomain(Cita cita) {
        if (cita == null) {
            throw new IllegalArgumentException("La cita no puede ser nula.");
        }

        return new CitaResponse(
                cita.getCitaId(),
                cita.getPacienteId(),
                cita.getProfesionalId(),
                cita.getFechaHoraInicio(),
                cita.getEstadoCita() == null ? null : cita.getEstadoCita().name(),
                cita.getMotivoBreve(),
                cita.getObservacionOperativa(),
                cita.getFechaCreacion(),
                cita.getFechaActualizacion()
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

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
