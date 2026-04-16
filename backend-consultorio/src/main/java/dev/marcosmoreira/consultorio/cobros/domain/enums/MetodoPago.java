package dev.marcosmoreira.consultorio.cobros.domain.enums;

/**
 * Catálogo de métodos de pago permitidos por el sistema.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public enum MetodoPago {

    /**
     * Pago en efectivo.
     */
    EFECTIVO,

    /**
     * Pago por transferencia.
     */
    TRANSFERENCIA,

    /**
     * Pago con tarjeta.
     */
    TARJETA
}
