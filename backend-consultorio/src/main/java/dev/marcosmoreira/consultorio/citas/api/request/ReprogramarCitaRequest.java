package dev.marcosmoreira.consultorio.citas.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * DTO de entrada para reprogramar una cita.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class ReprogramarCitaRequest {

    @NotNull(message = "La nueva fecha y hora de inicio es obligatoria.")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime nuevaFechaHoraInicio;

    @Size(max = 300, message = "La observación operativa no puede exceder los 300 caracteres.")
    private String observacionOperativa;

    /**
     * Constructor vacío requerido por serialización.
     */
    public ReprogramarCitaRequest() {
    }

    /**
     * Construye el request completo.
     *
     * @param nuevaFechaHoraInicio nueva fecha/hora de inicio
     * @param observacionOperativa observación operativa asociada
     */
    public ReprogramarCitaRequest(
            LocalDateTime nuevaFechaHoraInicio,
            String observacionOperativa
    ) {
        this.nuevaFechaHoraInicio = nuevaFechaHoraInicio;
        this.observacionOperativa = normalizeNullableText(observacionOperativa);
    }

    public LocalDateTime getNuevaFechaHoraInicio() {
        return nuevaFechaHoraInicio;
    }

    public void setNuevaFechaHoraInicio(LocalDateTime nuevaFechaHoraInicio) {
        this.nuevaFechaHoraInicio = nuevaFechaHoraInicio;
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
