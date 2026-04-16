package dev.marcosmoreira.consultorio.shared.error;

import org.springframework.http.HttpStatus;

/**
 * Excepción usada cuando el usuario autenticado existe, pero no tiene permiso
 * suficiente para ejecutar una operación concreta.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class UnauthorizedOperationException extends ApiException {

    /**
     * Construye la excepción con un mensaje descriptivo.
     *
     * @param message detalle de la operación no autorizada
     */
    public UnauthorizedOperationException(String message) {
        super(message, ApiErrorCode.UNAUTHORIZED_OPERATION, HttpStatus.FORBIDDEN);
    }

    /**
     * Construye la excepción con mensaje y causa raíz.
     *
     * @param message detalle de la operación no autorizada
     * @param cause causa original
     */
    public UnauthorizedOperationException(String message, Throwable cause) {
        super(message, cause, ApiErrorCode.UNAUTHORIZED_OPERATION, HttpStatus.FORBIDDEN);
    }
}
