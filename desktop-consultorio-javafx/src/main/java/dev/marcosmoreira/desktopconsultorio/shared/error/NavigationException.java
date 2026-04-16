package dev.marcosmoreira.desktopconsultorio.shared.error;

/**
 * Excepción de aplicación.
 * TODO document {@code NavigationException}.
 */
public class NavigationException extends RuntimeException {

    public NavigationException(String message) {
        super(message);
    }

    public NavigationException(String message, Throwable cause) {
        super(message, cause);
    }
}