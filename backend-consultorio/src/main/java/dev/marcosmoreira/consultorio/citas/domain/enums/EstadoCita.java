package dev.marcosmoreira.consultorio.citas.domain.enums;

/**
 * Catálogo de estados lógicos de una cita.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public enum EstadoCita {

    /**
     * Cita registrada y pendiente de atención.
     */
    PROGRAMADA,

    /**
     * Cita ya atendida.
     */
    ATENDIDA,

    /**
     * Cita cancelada antes de su atención.
     */
    CANCELADA;

    /**
     * Indica si la cita aún puede operarse como parte de la agenda programada.
     *
     * @return {@code true} si está programada; {@code false} en otro caso
     */
    public boolean isProgramable() {
        return this == PROGRAMADA;
    }
}
