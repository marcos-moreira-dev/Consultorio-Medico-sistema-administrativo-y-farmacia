package dev.marcosmoreira.consultorio.citas.api.request;

import jakarta.validation.constraints.Size;

/**
 * DTO de entrada para cancelar una cita.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class CancelarCitaRequest {

    @Size(max = 300, message = "La observación operativa no puede exceder los 300 caracteres.")
    private String observacionOperativa;

    /**
     * Constructor vacío requerido por serialización.
     */
    public CancelarCitaRequest() {
    }

    /**
     * Construye el request completo.
     *
     * @param observacionOperativa observación operativa asociada a la cancelación
     */
    public CancelarCitaRequest(String observacionOperativa) {
        this.observacionOperativa = normalizeNullableText(observacionOperativa);
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
