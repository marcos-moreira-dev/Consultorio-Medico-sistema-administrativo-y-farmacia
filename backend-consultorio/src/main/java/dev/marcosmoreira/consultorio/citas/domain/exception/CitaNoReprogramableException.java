package dev.marcosmoreira.consultorio.citas.domain.exception;

import dev.marcosmoreira.consultorio.shared.error.BusinessRuleException;

/**
 * Excepción usada cuando una cita no puede reprogramarse o manipularse
 * debido a su estado actual.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class CitaNoReprogramableException extends BusinessRuleException {

    /**
     * Construye la excepción con un mensaje descriptivo.
     *
     * @param message detalle de la regla incumplida
     */
    public CitaNoReprogramableException(String message) {
        super(message);
    }

    /**
     * Construye la excepción con mensaje y causa raíz.
     *
     * @param message detalle de la regla incumplida
     * @param cause causa original
     */
    public CitaNoReprogramableException(String message, Throwable cause) {
        super(message, cause);
    }
}
