package dev.marcosmoreira.desktopconsultorio.http.error;

/**
 * Excepción lanzada cuando una llamada a la API falla.
 * Reemplaza el patrón de retornar null silenciosamente.
 */
public class ApiCallException extends RuntimeException {

    private final int statusCode;
    private final String endpoint;

    public ApiCallException(String message, Throwable cause, int statusCode, String endpoint) {
        super(message, cause);
        this.statusCode = statusCode;
        this.endpoint = endpoint;
    }

    public ApiCallException(String message, int statusCode, String endpoint) {
        super(message);
        this.statusCode = statusCode;
        this.endpoint = endpoint;
    }

    public int getStatusCode() { return statusCode; }
    public String getEndpoint() { return endpoint; }

    public boolean isAuthError() { return statusCode == 401 || statusCode == 403; }
    public boolean isNotFound() { return statusCode == 404; }
    public boolean isServerError() { return statusCode >= 500; }
}
