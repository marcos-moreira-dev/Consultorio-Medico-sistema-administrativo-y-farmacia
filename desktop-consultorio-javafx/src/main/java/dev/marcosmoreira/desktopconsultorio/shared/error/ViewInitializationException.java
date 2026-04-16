package dev.marcosmoreira.desktopconsultorio.shared.error;

/**
 * Excepción de aplicación.
 * TODO document {@code ViewInitializationException}.
 */
public class ViewInitializationException extends RuntimeException {

    public ViewInitializationException(String message) {
        super(message);
    }

    public ViewInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}