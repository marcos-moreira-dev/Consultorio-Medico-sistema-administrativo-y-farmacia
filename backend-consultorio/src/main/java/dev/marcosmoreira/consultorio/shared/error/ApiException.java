package dev.marcosmoreira.consultorio.shared.error;

import org.springframework.http.HttpStatus;

/**
 * Excepción base de la API para errores de negocio o aplicación que deben
 * convertirse de forma explícita a respuesta HTTP controlada.
 *
 * <p>Esta clase evita depender únicamente de {@link RuntimeException} sin contexto.
 * Cada subclase puede especificar un código de error y un estado HTTP apropiado.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public abstract class ApiException extends RuntimeException {

    private final ApiErrorCode errorCode;
    private final HttpStatus httpStatus;

    /**
     * Construye una excepción de API con mensaje, código y estado HTTP.
     *
     * @param message mensaje descriptivo del error
     * @param errorCode código de error estable
     * @param httpStatus estado HTTP asociado
     */
    protected ApiException(String message, ApiErrorCode errorCode, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    /**
     * Construye una excepción de API con mensaje, causa, código y estado HTTP.
     *
     * @param message mensaje descriptivo del error
     * @param cause causa original
     * @param errorCode código de error estable
     * @param httpStatus estado HTTP asociado
     */
    protected ApiException(
            String message,
            Throwable cause,
            ApiErrorCode errorCode,
            HttpStatus httpStatus
    ) {
        super(message, cause);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public ApiErrorCode getErrorCode() {
        return errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
