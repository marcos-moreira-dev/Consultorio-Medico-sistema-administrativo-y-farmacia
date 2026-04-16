package dev.marcosmoreira.consultorio.profesionales.domain.enums;

/**
 * Catálogo de estados lógicos del profesional del consultorio.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public enum EstadoProfesional {

    /**
     * Profesional habilitado para operar y aparecer en flujos activos.
     */
    ACTIVO,

    /**
     * Profesional inhabilitado lógicamente.
     */
    INACTIVO;

    /**
     * Indica si el estado actual representa operatividad.
     *
     * @return {@code true} si es ACTIVO; {@code false} en otro caso
     */
    public boolean isOperativo() {
        return this == ACTIVO;
    }
}
