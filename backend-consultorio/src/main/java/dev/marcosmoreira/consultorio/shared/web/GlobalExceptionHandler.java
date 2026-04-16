package dev.marcosmoreira.consultorio.shared.web;

import dev.marcosmoreira.consultorio.auth.domain.exception.InvalidCredentialsException;
import dev.marcosmoreira.consultorio.shared.error.ApiErrorCode;
import dev.marcosmoreira.consultorio.shared.error.ApiException;
import dev.marcosmoreira.consultorio.shared.util.CorrelationIdUtils;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de excepciones del backend.
 *
 * <p>Centraliza la traducción de errores Java a respuestas HTTP JSON consistentes.
 * Esto evita duplicar bloques try/catch en controladores y mejora la coherencia
 * del contrato de errores de la API.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de API controladas.
     *
     * @param ex excepción controlada del proyecto
     * @return respuesta HTTP consistente
     */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Void>> handleApiException(ApiException ex) {
        return buildErrorResponse(
                ex.getHttpStatus(),
                ex.getErrorCode(),
                ex.getMessage()
        );
    }

    /**
     * Maneja errores de autenticación lógicos del módulo auth.
     *
     * @param ex excepción de credenciales inválidas
     * @return respuesta HTTP 401
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidCredentials(InvalidCredentialsException ex) {
        return buildErrorResponse(
                HttpStatus.UNAUTHORIZED,
                ApiErrorCode.UNAUTHORIZED,
                ex.getMessage()
        );
    }

    /**
     * Maneja errores de validación de Bean Validation sobre request bodies.
     *
     * @param ex excepción de validación
     * @return respuesta HTTP 400 con mensaje consolidado
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::formatFieldError)
                .collect(Collectors.joining(" | "));

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                ApiErrorCode.VALIDATION_ERROR,
                message.isBlank() ? ApiErrorCode.VALIDATION_ERROR.getDefaultMessage() : message
        );
    }

    /**
     * Maneja errores de validación sobre parámetros sueltos y constraints.
     *
     * @param ex excepción de constraints
     * @return respuesta HTTP 400
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining(" | "));

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                ApiErrorCode.VALIDATION_ERROR,
                message.isBlank() ? ApiErrorCode.VALIDATION_ERROR.getDefaultMessage() : message
        );
    }

    /**
     * Maneja errores simples de argumento inválido.
     *
     * @param ex excepción de argumento inválido
     * @return respuesta HTTP 400
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException ex) {
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                ApiErrorCode.VALIDATION_ERROR,
                ex.getMessage()
        );
    }

    /**
     * Maneja errores de autorización disparados por Spring Security.
     *
     * @param ex excepción de acceso denegado
     * @return respuesta HTTP 403
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDenied(AccessDeniedException ex) {
        return buildErrorResponse(
                HttpStatus.FORBIDDEN,
                ApiErrorCode.FORBIDDEN,
                "No tiene permisos para realizar esta operación."
        );
    }

    /**
     * Maneja errores no controlados del backend.
     *
     * @param ex excepción inesperada
     * @return respuesta HTTP 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnexpected(Exception ex) {
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ApiErrorCode.INTERNAL_ERROR,
                ApiErrorCode.INTERNAL_ERROR.getDefaultMessage()
        );
    }

    /**
     * Construye una respuesta de error estándar.
     *
     * @param status estado HTTP
     * @param errorCode código de error transversal
     * @param message mensaje de error
     * @return response entity con cuerpo estándar
     */
    private ResponseEntity<ApiResponse<Void>> buildErrorResponse(
            HttpStatus status,
            ApiErrorCode errorCode,
            String message
    ) {
        ApiResponse<Void> body = ApiResponse.error(
                errorCode,
                message,
                CorrelationIdUtils.getCurrentCorrelationId()
        );

        return ResponseEntity.status(status).body(body);
    }

    /**
     * Formatea un error de campo de Bean Validation a una cadena compacta.
     *
     * @param error error de campo
     * @return representación legible del error
     */
    private String formatFieldError(FieldError error) {
        return error.getField() + ": " + error.getDefaultMessage();
    }
}
