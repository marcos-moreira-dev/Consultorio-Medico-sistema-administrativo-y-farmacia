package dev.marcosmoreira.consultorio.pacientes.domain.exception;

import dev.marcosmoreira.consultorio.shared.error.DuplicateResourceException;

/**
 * Excepción usada cuando se detecta una posible duplicidad relevante
 * al registrar o actualizar un paciente.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class PacienteDuplicadoException extends DuplicateResourceException {

    /**
     * Construye la excepción con un mensaje descriptivo.
     *
     * @param message detalle del conflicto de duplicidad
     */
    public PacienteDuplicadoException(String message) {
        super(message);
    }

    /**
     * Construye la excepción con mensaje y causa raíz.
     *
     * @param message detalle del conflicto
     * @param cause causa original
     */
    public PacienteDuplicadoException(String message, Throwable cause) {
        super(message, cause);
    }
}
