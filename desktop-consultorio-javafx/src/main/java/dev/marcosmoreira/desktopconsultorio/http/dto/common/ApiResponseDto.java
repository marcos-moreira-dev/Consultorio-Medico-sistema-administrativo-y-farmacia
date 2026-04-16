package dev.marcosmoreira.desktopconsultorio.http.dto.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Respuesta estandarizada del backend del consultorio.
 *
 * <p>Todos los endpoints del backend devuelven esta estructura con
 * {@code ok}, {@code data} y metadatos opcionales como {@code correlationId}.</p>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponseDto<T> {

    @JsonProperty("ok")
    private boolean ok;

    @JsonProperty("data")
    private T data;

    @JsonProperty("correlationId")
    private String correlationId;

    @JsonProperty("message")
    private String message;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
