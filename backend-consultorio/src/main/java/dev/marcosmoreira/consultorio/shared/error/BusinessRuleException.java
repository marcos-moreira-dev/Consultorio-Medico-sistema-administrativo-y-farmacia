package dev.marcosmoreira.consultorio.shared.error;

import org.springframework.http.HttpStatus;

/**
 * Excepción usada cuando una operación incumple una regla de negocio
 * válida dentro del dominio o la aplicación.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class BusinessRuleException extends ApiException {

    /**
     * Construye la excepción con un mensaje descriptivo.
     *
     * @param message detalle de la regla incumplida
     */
    public BusinessRuleException(String message) {
        super(message, ApiErrorCode.BUSINESS_RULE_VIOLATION, HttpStatus.BAD_REQUEST);
    }

    /**
     * Construye la excepción con mensaje y causa raíz.
     *
     * @param message detalle de la regla incumplida
     * @param cause causa original
     */
    public BusinessRuleException(String message, Throwable cause) {
        super(message, cause, ApiErrorCode.BUSINESS_RULE_VIOLATION, HttpStatus.BAD_REQUEST);
    }
}
