package dev.marcosmoreira.consultorio.atenciones.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * DTO de entrada para registrar una nueva atención.
 *
 * <p>Representa el contrato HTTP mínimo de creación del módulo de atenciones.
 * Su finalidad es transportar únicamente los datos que el cliente debe enviar,
 * manteniendo separado el contrato externo del modelo interno del dominio.</p>
 *
 * <p>Reglas mínimas reflejadas en este request:</p>
 *
 * <ul>
 *   <li>el paciente es obligatorio;</li>
 *   <li>el profesional es obligatorio;</li>
 *   <li>la fecha y hora de atención es obligatoria;</li>
 *   <li>la nota breve es obligatoria;</li>
 *   <li>la cita es opcional;</li>
 *   <li>las indicaciones breves son opcionales.</li>
 * </ul>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class CrearAtencionRequest {

    @NotNull(message = "El paciente es obligatorio.")
    @Positive(message = "El identificador del paciente debe ser mayor que cero.")
    private Long pacienteId;

    @NotNull(message = "El profesional es obligatorio.")
    @Positive(message = "El identificador del profesional debe ser mayor que cero.")
    private Long profesionalId;

    @Positive(message = "El identificador de la cita debe ser mayor que cero.")
    private Long citaId;

    @NotNull(message = "La fecha y hora de atención es obligatoria.")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaHoraAtencion;

    @NotBlank(message = "La nota breve es obligatoria.")
    @Size(max = 500, message = "La nota breve no puede exceder los 500 caracteres.")
    private String notaBreve;

    @Size(max = 500, message = "Las indicaciones breves no pueden exceder los 500 caracteres.")
    private String indicacionesBreves;

    /**
     * Constructor vacío requerido por frameworks de serialización y deserialización.
     */
    public CrearAtencionRequest() {
    }

    /**
     * Construye un request completo de creación de atención.
     *
     * @param pacienteId identificador del paciente atendido
     * @param profesionalId identificador del profesional que realiza la atención
     * @param citaId identificador de la cita asociada, si existe
     * @param fechaHoraAtencion fecha y hora efectiva de la atención
     * @param notaBreve nota clínica u operativa breve de la atención
     * @param indicacionesBreves indicaciones breves dadas al paciente, si aplican
     */
    public CrearAtencionRequest(
            Long pacienteId,
            Long profesionalId,
            Long citaId,
            LocalDateTime fechaHoraAtencion,
            String notaBreve,
            String indicacionesBreves
    ) {
        this.pacienteId = pacienteId;
        this.profesionalId = profesionalId;
        this.citaId = citaId;
        this.fechaHoraAtencion = fechaHoraAtencion;
        this.notaBreve = notaBreve == null ? null : notaBreve.trim();
        this.indicacionesBreves = normalizeNullableText(indicacionesBreves);
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

    public Long getCitaId() {
        return citaId;
    }

    public void setCitaId(Long citaId) {
        this.citaId = citaId;
    }

    public LocalDateTime getFechaHoraAtencion() {
        return fechaHoraAtencion;
    }

    public void setFechaHoraAtencion(LocalDateTime fechaHoraAtencion) {
        this.fechaHoraAtencion = fechaHoraAtencion;
    }

    public String getNotaBreve() {
        return notaBreve;
    }

    public void setNotaBreve(String notaBreve) {
        this.notaBreve = notaBreve == null ? null : notaBreve.trim();
    }

    public String getIndicacionesBreves() {
        return indicacionesBreves;
    }

    public void setIndicacionesBreves(String indicacionesBreves) {
        this.indicacionesBreves = normalizeNullableText(indicacionesBreves);
    }

    /**
     * Indica si el request incluye una cita asociada.
     *
     * @return {@code true} si existe un identificador de cita, {@code false} en caso contrario
     */
    public boolean hasCitaId() {
        return citaId != null;
    }

    /**
     * Indica si el request incluye indicaciones breves no vacías.
     *
     * @return {@code true} si existe texto útil en indicaciones, {@code false} en caso contrario
     */
    public boolean hasIndicacionesBreves() {
        return indicacionesBreves != null && !indicacionesBreves.isBlank();
    }

    /**
     * Normaliza un texto opcional.
     *
     * <p>Si el texto llega vacío o compuesto solo por espacios, se transforma en {@code null}.
     * Esto ayuda a evitar almacenar cadenas vacías cuando conceptualmente el dato es opcional.</p>
     *
     * @param value texto opcional a normalizar
     * @return texto normalizado o {@code null} si no contiene información útil
     */
    private String normalizeNullableText(String value) {
        if (value == null) {
            return null;
        }

        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }
}
