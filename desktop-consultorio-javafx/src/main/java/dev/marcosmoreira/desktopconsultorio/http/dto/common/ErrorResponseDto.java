package dev.marcosmoreira.desktopconsultorio.http.dto.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Respuesta de error del backend.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponseDto {

    @JsonProperty("ok")
    private boolean ok;

    @JsonProperty("statusCode")
    private int statusCode;

    @JsonProperty("error")
    private String error;

    @JsonProperty("message")
    private String message;

    @JsonProperty("correlationId")
    private String correlationId;

    @JsonProperty("path")
    private String path;

    @JsonProperty("timestamp")
    private String timestamp;

    public boolean isOk() { return ok; }
    public void setOk(boolean ok) { this.ok = ok; }
    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getCorrelationId() { return correlationId; }
    public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
