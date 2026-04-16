package dev.marcosmoreira.consultorio.citas.domain.exception;

import dev.marcosmoreira.consultorio.shared.error.BusinessRuleException;

/**
 * Excepción usada cuando se intenta registrar o mover una cita
 * a un slot de agenda que ya está ocupado.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class AgendaOcupadaException extends BusinessRuleException {

    /**
     * Construye la excepción con un mensaje descriptivo.
     *
     * @param message detalle de la regla incumplida
     */
    public AgendaOcupadaException(String message) {
        super(message);
    }

    /**
     * Construye la excepción con mensaje y causa raíz.
     *
     * @param message detalle de la regla incumplida
     * @param cause causa original
     */
    public AgendaOcupadaException(String message, Throwable cause) {
        super(message, cause);
    }
}
