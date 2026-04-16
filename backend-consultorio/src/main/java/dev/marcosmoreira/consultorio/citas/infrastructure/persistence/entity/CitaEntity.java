package dev.marcosmoreira.consultorio.citas.infrastructure.persistence.entity;

import dev.marcosmoreira.consultorio.citas.domain.enums.EstadoCita;
import dev.marcosmoreira.consultorio.shared.persistence.AuditableEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad JPA del módulo de citas.
 *
 * <p>Se alinea con la tabla {@code cita} de la base de datos V2 del proyecto.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Entity
@Table(schema = "consultorio", name = "cita")
public class CitaEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cita_id")
    private Long citaId;

    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @Column(name = "profesional_id", nullable = false)
    private Long profesionalId;

    @Column(name = "fecha_hora_inicio", nullable = false)
    private LocalDateTime fechaHoraInicio;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_cita", nullable = false, length = 20)
    private EstadoCita estadoCita;

    @Column(name = "motivo_breve", length = 200)
    private String motivoBreve;

    @Column(name = "observacion_operativa", length = 300)
    private String observacionOperativa;

    /**
     * Constructor vacío requerido por JPA.
     */
    public CitaEntity() {
    }

    /**
     * Construye una entidad completa de cita.
     *
     * @param citaId identificador de la cita
     * @param pacienteId identificador del paciente
     * @param profesionalId identificador del profesional
     * @param fechaHoraInicio fecha y hora de inicio
     * @param estadoCita estado lógico
     * @param motivoBreve motivo breve
     * @param observacionOperativa observación operativa
     */
    public CitaEntity(
            Long citaId,
            Long pacienteId,
            Long profesionalId,
            LocalDateTime fechaHoraInicio,
            EstadoCita estadoCita,
            String motivoBreve,
            String observacionOperativa
    ) {
        this.citaId = citaId;
        this.pacienteId = pacienteId;
        this.profesionalId = profesionalId;
        this.fechaHoraInicio = fechaHoraInicio;
        this.estadoCita = estadoCita;
        this.motivoBreve = normalizeNullableText(motivoBreve);
        this.observacionOperativa = normalizeNullableText(observacionOperativa);
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
