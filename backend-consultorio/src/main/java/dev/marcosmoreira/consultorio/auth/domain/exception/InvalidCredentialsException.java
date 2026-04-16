package dev.marcosmoreira.consultorio.auth.domain.exception;

/**
 * Excepción de dominio/aplicación usada cuando las credenciales no son válidas
 * o cuando no existe un contexto de autenticación válido para operar.
 *
 * <p>Aunque el nombre apunta a credenciales inválidas, en la versión 1.0 también
 * se reutiliza para escenarios cercanos de autenticación fallida, como ausencia
 * de usuario autenticado actual.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class InvalidCredentialsException extends RuntimeException {

    /**
     * Construye la excepción con un mensaje descriptivo.
     *
     * @param message detalle del error
     */
    public InvalidCredentialsException(String message) {
        super(message);
    }

    /**
     * Construye la excepción con mensaje y causa raíz.
     *
     * @param message detalle del error
     * @param cause excepción original
     */
    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}
