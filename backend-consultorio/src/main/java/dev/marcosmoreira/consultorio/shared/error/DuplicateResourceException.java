package dev.marcosmoreira.consultorio.shared.error;

import org.springframework.http.HttpStatus;

/**
 * Excepción usada cuando se intenta crear o registrar un recurso que ya existe
 * y cuya unicidad debe preservarse.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class DuplicateResourceException extends ApiException {

    /**
     * Construye la excepción con un mensaje descriptivo.
     *
     * @param message detalle del conflicto de duplicidad
     */
    public DuplicateResourceException(String message) {
        super(message, ApiErrorCode.DUPLICATE_RESOURCE, HttpStatus.CONFLICT);
    }

    /**
     * Construye la excepción con mensaje y causa raíz.
     *
     * @param message detalle del conflicto de duplicidad
     * @param cause causa original
     */
    public DuplicateResourceException(String message, Throwable cause) {
        super(message, cause, ApiErrorCode.DUPLICATE_RESOURCE, HttpStatus.CONFLICT);
    }
}
