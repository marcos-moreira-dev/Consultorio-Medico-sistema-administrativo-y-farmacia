package dev.marcosmoreira.consultorio.usuarios.domain.exception;

import dev.marcosmoreira.consultorio.shared.error.DuplicateResourceException;

/**
 * Excepción de dominio/aplicación usada cuando se intenta registrar un usuario
 * cuyo username ya existe en el sistema.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class UsuarioDuplicadoException extends DuplicateResourceException {

    /**
     * Construye la excepción con un mensaje descriptivo.
     *
     * @param message detalle del conflicto de duplicidad
     */
    public UsuarioDuplicadoException(String message) {
        super(message);
    }

    /**
     * Construye la excepción con mensaje y causa raíz.
     *
     * @param message detalle del conflicto
     * @param cause causa original
     */
    public UsuarioDuplicadoException(String message, Throwable cause) {
        super(message, cause);
    }
}
