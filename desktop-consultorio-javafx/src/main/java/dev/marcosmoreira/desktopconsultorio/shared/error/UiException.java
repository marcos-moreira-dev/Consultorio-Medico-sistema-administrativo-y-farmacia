package dev.marcosmoreira.desktopconsultorio.shared.error;

/**
 * Excepción de aplicación.
 * TODO document {@code UiException}.
 */
public class UiException extends RuntimeException {

    public UiException(String message) {
        super(message);
    }

    public UiException(String message, Throwable cause) {
        super(message, cause);
    }
}