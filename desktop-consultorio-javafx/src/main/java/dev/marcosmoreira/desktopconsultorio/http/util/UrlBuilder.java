package dev.marcosmoreira.desktopconsultorio.http.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Builder para construir URLs con query parameters de forma segura.
 * Maneja URL encoding automáticamente.
 */
public final class UrlBuilder {

    private final StringBuilder url;
    private boolean hasQuery = false;

    public UrlBuilder(String basePath) {
        this.url = new StringBuilder(basePath);
    }

    public UrlBuilder param(String name, Object value) {
        if (value != null) {
            url.append(hasQuery ? "&" : "?").append(name).append("=")
               .append(URLEncoder.encode(value.toString(), StandardCharsets.UTF_8));
            hasQuery = true;
        }
        return this;
    }

    public UrlBuilder param(String name, LocalDate value) {
        if (value != null) {
            url.append(hasQuery ? "&" : "?").append(name).append("=")
               .append(URLEncoder.encode(value.toString(), StandardCharsets.UTF_8));
            hasQuery = true;
        }
        return this;
    }

    public UrlBuilder param(String name, LocalDateTime value) {
        if (value != null) {
            url.append(hasQuery ? "&" : "?").append(name).append("=")
               .append(URLEncoder.encode(value.toString(), StandardCharsets.UTF_8));
            hasQuery = true;
        }
        return this;
    }

    @Override
    public String toString() {
        return url.toString();
    }
}
