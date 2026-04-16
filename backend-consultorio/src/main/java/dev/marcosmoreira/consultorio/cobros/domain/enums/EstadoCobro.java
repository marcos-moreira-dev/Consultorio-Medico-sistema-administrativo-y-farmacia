package dev.marcosmoreira.consultorio.cobros.domain.enums;

/**
 * Catálogo de estados lógicos de un cobro.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public enum EstadoCobro {

    /**
     * Cobro ya pagado.
     */
    PAGADO,

    /**
     * Cobro registrado pero aún pendiente de pago.
     */
    PENDIENTE;

    /**
     * Indica si el cobro ya está completamente pagado.
     *
     * @return {@code true} si el estado es PAGADO; {@code false} en otro caso
     */
    public boolean isPagado() {
        return this == PAGADO;
    }
}
