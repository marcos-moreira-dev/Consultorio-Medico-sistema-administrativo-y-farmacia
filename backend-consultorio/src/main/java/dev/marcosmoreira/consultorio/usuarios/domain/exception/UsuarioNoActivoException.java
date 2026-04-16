package dev.marcosmoreira.consultorio.usuarios.domain.exception;

import dev.marcosmoreira.consultorio.shared.error.BusinessRuleException;

/**
 * Excepción usada cuando una operación requiere que el usuario esté activo
 * y la regla no se cumple.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class UsuarioNoActivoException extends BusinessRuleException {

    /**
     * Construye la excepción con un mensaje descriptivo.
     *
     * @param message detalle de la regla incumplida
     */
    public UsuarioNoActivoException(String message) {
        super(message);
    }

    /**
     * Construye la excepción con mensaje y causa raíz.
     *
     * @param message detalle de la regla incumplida
     * @param cause causa original
     */
    public UsuarioNoActivoException(String message, Throwable cause) {
        super(message, cause);
    }
}
