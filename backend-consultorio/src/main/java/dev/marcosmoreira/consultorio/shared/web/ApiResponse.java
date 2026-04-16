package dev.marcosmoreira.consultorio.shared.web;

import dev.marcosmoreira.consultorio.shared.error.ApiErrorCode;
import java.time.LocalDateTime;

/**
 * Envoltorio estándar para respuestas HTTP del backend.
 *
 * <p>Su objetivo es ofrecer una estructura uniforme para respuestas exitosas
 * y respuestas de error, facilitando el consumo desde clientes como el desktop
 * y mejorando la coherencia general del contrato de la API.</p>
 *
 * @param <T> tipo de dato contenido en la respuesta
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class ApiResponse<T> {

    private boolean ok;
    private String message;
    private String errorCode;
    private String correlationId;
    private LocalDateTime timestamp;
    private T data;

    /**
     * Constructor vacío requerido por serialización.
     */
    public ApiResponse() {
    }

    /**
     * Construye una respuesta completa.
     *
     * @param ok indica si la operación fue exitosa
     * @param message mensaje legible
     * @param errorCode código de error, o {@code null} si no aplica
     * @param correlationId identificador de correlación de la request
     * @param timestamp timestamp de respuesta
     * @param data carga útil de la respuesta
     */
    public ApiResponse(
            boolean ok,
            String message,
            String errorCode,
            String correlationId,
            LocalDateTime timestamp,
            T data
    ) {
        this.ok = ok;
        this.message = message;
        this.errorCode = errorCode;
        this.correlationId = correlationId;
        this.timestamp = timestamp;
        this.data = data;
    }

    /**
     * Construye una respuesta exitosa con un mensaje por defecto.
     *
     * @param data carga útil de la respuesta
     * @param correlationId correlation id asociado a la request
     * @param <T> tipo del dato
     * @return respuesta exitosa
     */
    public static <T> ApiResponse<T> success(T data, String correlationId) {
        return new ApiResponse<>(
                true,
                "Operación exitosa.",
                null,
                correlationId,
                LocalDateTime.now(),
                data
        );
    }

    /**
     * Construye una respuesta exitosa con mensaje personalizado.
     *
     * @param message mensaje de éxito
     * @param data carga útil
     * @param correlationId correlation id asociado a la request
     * @param <T> tipo del dato
     * @return respuesta exitosa
     */
    public static <T> ApiResponse<T> success(String message, T data, String correlationId) {
        return new ApiResponse<>(
                true,
                message,
                null,
                correlationId,
                LocalDateTime.now(),
                data
        );
    }

    /**
     * Construye una respuesta de error.
     *
     * @param errorCode código de error transversal
     * @param message mensaje legible de error
     * @param correlationId correlation id asociado a la request
     * @param <T> tipo del dato, normalmente {@code Void}
     * @return respuesta de error
     */
    public static <T> ApiResponse<T> error(
            ApiErrorCode errorCode,
            String message,
            String correlationId
    ) {
        return new ApiResponse<>(
                false,
                message != null ? message : errorCode.getDefaultMessage(),
                errorCode.getCode(),
                correlationId,
                LocalDateTime.now(),
                null
        );
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
