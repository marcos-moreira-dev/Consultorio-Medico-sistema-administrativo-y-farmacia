package dev.marcosmoreira.desktopconsultorio.shared.error;

/**
 * Excepción de aplicación.
 * TODO document {@code SessionExpiredException}.
 */
public class SessionExpiredException extends RuntimeException {

    public SessionExpiredException(String message) {
        super(message);
    }

    public SessionExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}