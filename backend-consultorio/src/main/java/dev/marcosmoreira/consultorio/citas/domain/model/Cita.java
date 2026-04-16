package dev.marcosmoreira.consultorio.citas.domain.model;

import dev.marcosmoreira.consultorio.citas.domain.enums.EstadoCita;
import java.time.LocalDateTime;

/**
 * Modelo de dominio que representa una cita dentro de la agenda del consultorio.
 *
 * <p>Una cita es una reserva de tiempo para un profesional con un paciente
 * determinado. La regla de negocio más importante es la prevención de
 * solapamientos: un mismo profesional no puede tener dos citas a la misma
 * hora (RN-020), validada mediante constraint único en base de datos y
 * verificada por {@code CitaApplicationService} antes de crear.</p>
 *
 * <p>Las citas pueden existir sin atención asociada: una cita cancelada
 * o programada no genera atención. La atención se crea independientemente
 * cuando el profesional registra la consulta (RN-030: puede existir atención
 * sin cita previa para pacientes que llegan sin agendar).</p>
 *
 * <p>El método {@link #isProgramada()} encapsula la verificación de estado,
 * siguiendo el principio de decirle-a-objetos en vez de preguntar por campos.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 * @see dev.marcosmoreira.consultorio.citas.application.service.CitaApplicationService
 *      para las reglas de creación, cancelación y reprogramación
 */
public class Cita {

    private Long citaId;
    private Long pacienteId;
    private Long profesionalId;
    private LocalDateTime fechaHoraInicio;
    private EstadoCita estadoCita;
    private String motivoBreve;
    private String observacionOperativa;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    /**
     * Constructor vacío.
     */
    public Cita() {
    }

    /**
     * Construye una cita completa.
     *
     * @param citaId identificador de la cita
     * @param pacienteId identificador del paciente
     * @param profesionalId identificador del profesional
     * @param fechaHoraInicio fecha y hora de inicio
     * @param estadoCita estado lógico
     * @param motivoBreve motivo breve
     * @param observacionOperativa observación operativa
     * @param fechaCreacion fecha de creación
     * @param fechaActualizacion fecha de última actualización
     */
    public Cita(
            Long citaId,
            Long pacienteId,
            Long profesionalId,
            LocalDateTime fechaHoraInicio,
            EstadoCita estadoCita,
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
        this.motivoBreve = normalizeNullableText(motivoBreve);
        this.observacionOperativa = normalizeNullableText(observacionOperativa);
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
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

    public EstadoCita getEstadoCita() {
        return estadoCita;
    }

    public void setEstadoCita(EstadoCita estadoCita) {
        this.estadoCita = estadoCita;
    }

    public String getMotivoBreve() {
        return motivoBreve;
    }

    public void setMotivoBreve(String motivoBreve) {
        this.motivoBreve = normalizeNullableText(motivoBreve);
    }

    public String getObservacionOperativa() {
        return observacionOperativa;
    }

    public void setObservacionOperativa(String observacionOperativa) {
        this.observacionOperativa = normalizeNullableText(observacionOperativa);
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

    /**
     * Indica si la cita se encuentra en estado PROGRAMADA.
     *
     * @return {@code true} si está programada; {@code false} en otro caso
     */
    public boolean isProgramada() {
        return estadoCita != null && estadoCita.isProgramable();
    }

    /**
     * Normaliza un texto opcional.
     *
     * @param value texto a normalizar
     * @return texto con trim aplicado o {@code null} si queda vacío
     */
    private String normalizeNullableText(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }
}
