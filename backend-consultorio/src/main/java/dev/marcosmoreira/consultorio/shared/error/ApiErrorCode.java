package dev.marcosmoreira.consultorio.shared.error;

/**
 * Catálogo transversal de códigos de error de la API.
 *
 * <p>Su objetivo es estandarizar la clasificación de errores que el backend
 * puede devolver a clientes como el desktop, Swagger u otros consumidores.
 * Esto ayuda a que el contrato HTTP no dependa únicamente de mensajes
 * libres, sino también de identificadores más estables y legibles.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public enum ApiErrorCode {

    VALIDATION_ERROR("VALIDATION_ERROR", "La solicitud contiene datos inválidos."),
    BUSINESS_RULE_VIOLATION("BUSINESS_RULE_VIOLATION", "Se incumplió una regla de negocio."),
    DUPLICATE_RESOURCE("DUPLICATE_RESOURCE", "El recurso ya existe."),
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", "No se encontró el recurso solicitado."),
    UNAUTHORIZED("UNAUTHORIZED", "No autenticado o credenciales inválidas."),
    FORBIDDEN("FORBIDDEN", "No tiene permisos para realizar esta operación."),
    UNAUTHORIZED_OPERATION("UNAUTHORIZED_OPERATION", "Operación no autorizada."),
    INTERNAL_ERROR("INTERNAL_ERROR", "Ocurrió un error interno inesperado.");

    private final String code;
    private final String defaultMessage;

    /**
     * Construye un código de error con su identificador estable y su mensaje por defecto.
     *
     * @param code código estable orientado a cliente y logs
     * @param defaultMessage mensaje por defecto asociado al error
     */
    ApiErrorCode(String code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    public String getCode() {
        return code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
