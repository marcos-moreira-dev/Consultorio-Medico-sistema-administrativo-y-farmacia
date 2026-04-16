package dev.marcosmoreira.consultorio.profesionales.api.request;

import dev.marcosmoreira.consultorio.profesionales.domain.enums.EstadoProfesional;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de entrada para cambiar el estado lógico de un profesional.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class CambiarEstadoProfesionalRequest {

    @NotNull(message = "El nuevo estado es obligatorio.")
    private EstadoProfesional nuevoEstado;

    /**
     * Constructor vacío requerido por serialización.
     */
    public CambiarEstadoProfesionalRequest() {
    }

    /**
     * Construye el request completo.
     *
     * @param nuevoEstado nuevo estado del profesional
     */
    public CambiarEstadoProfesionalRequest(EstadoProfesional nuevoEstado) {
        this.nuevoEstado = nuevoEstado;
    }

    public EstadoProfesional getNuevoEstado() {
        return nuevoEstado;
    }

    public void setNuevoEstado(EstadoProfesional nuevoEstado) {
        this.nuevoEstado = nuevoEstado;
    }
}
