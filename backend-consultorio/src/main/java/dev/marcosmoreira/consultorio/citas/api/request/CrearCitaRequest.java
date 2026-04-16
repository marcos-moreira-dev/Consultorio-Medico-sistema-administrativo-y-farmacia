package dev.marcosmoreira.consultorio.citas.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * DTO de entrada para crear una cita.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class CrearCitaRequest {

    @NotNull(message = "El paciente es obligatorio.")
    @Positive(message = "El paciente debe ser mayor que cero.")
    private Long pacienteId;

    @NotNull(message = "El profesional es obligatorio.")
    @Positive(message = "El profesional debe ser mayor que cero.")
    private Long profesionalId;

    @NotNull(message = "La fecha y hora de inicio es obligatoria.")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaHoraInicio;

    @Size(max = 200, message = "El motivo breve no puede exceder los 200 caracteres.")
    private String motivoBreve;

    @Size(max = 300, message = "La observación operativa no puede exceder los 300 caracteres.")
    private String observacionOperativa;

    /**
     * Constructor vacío requerido por serialización.
     */
    public CrearCitaRequest() {
    }

    /**
     * Construye el request completo de creación.
     *
     * @param pacienteId identificador del paciente
     * @param profesionalId identificador del profesional
     * @param fechaHoraInicio fecha y hora de inicio de la cita
     * @param motivoBreve motivo breve de la cita
     * @param observacionOperativa observación operativa
     */
    public CrearCitaRequest(
            Long pacienteId,
            Long profesionalId,
            LocalDateTime fechaHoraInicio,
            String motivoBreve,
            String observacionOperativa
    ) {
        this.pacienteId = pacienteId;
        this.profesionalId = profesionalId;
        this.fechaHoraInicio = fechaHoraInicio;
        this.motivoBreve = normalizeNullableText(motivoBreve);
        this.observacionOperativa = normalizeNullableText(observacionOperativa);
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
