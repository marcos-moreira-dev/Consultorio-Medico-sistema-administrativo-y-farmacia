package dev.marcosmoreira.consultorio.shared.error;

import org.springframework.http.HttpStatus;

/**
 * Excepción usada cuando no se encuentra un recurso requerido para completar
 * la operación solicitada.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class ResourceNotFoundException extends ApiException {

    /**
     * Construye la excepción con un mensaje descriptivo.
     *
     * @param message detalle del recurso no encontrado
     */
    public ResourceNotFoundException(String message) {
        super(message, ApiErrorCode.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    /**
     * Construye la excepción con mensaje y causa raíz.
     *
     * @param message detalle del recurso no encontrado
     * @param cause causa original
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause, ApiErrorCode.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
