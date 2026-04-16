package dev.marcosmoreira.desktopconsultorio.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.marcosmoreira.desktopconsultorio.config.AppConfig;
import dev.marcosmoreira.desktopconsultorio.http.dto.auth.LoginRequestDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.common.PageResponseDto;
import dev.marcosmoreira.desktopconsultorio.http.error.ApiCallException;
import dev.marcosmoreira.desktopconsultorio.session.SessionSnapshot;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Cliente HTTP mínimo para el runtime real del desktop.
 */
public class ConsultorioDesktopApi {

    private final AppConfig config;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ConsultorioDesktopApi(AppConfig config) {
        this.config = config;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(config.getConnectTimeoutMs()))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public boolean pingBackend() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(healthUri())
                .timeout(Duration.ofMillis(config.getReadTimeoutMs()))
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() >= 200 && response.statusCode() < 300;
        } catch (IOException | InterruptedException ex) {
            if (ex instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            return false;
        }
    }

    public SessionSnapshot login(String username, String password) {
        try {
            String payload = objectMapper.createObjectNode()
                    .put("username", username == null ? "" : username.trim())
                    .put("password", password == null ? "" : password)
                    .toString();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri("/auth/login"))
                    .timeout(Duration.ofMillis(config.getReadTimeoutMs()))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode root = readJson(response.body());

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new IllegalStateException(extractMessage(root, "No se pudo iniciar sesión con el backend."));
            }

            JsonNode data = root.path("data");
            JsonNode usuario = data.path("usuario");
            if (data.isMissingNode() || usuario.isMissingNode()) {
                throw new IllegalStateException("La respuesta del backend no contiene la estructura esperada de login.");
            }

            return SessionSnapshot.authenticated(
                    data.path("accessToken").asText(null),
                    data.path("tokenType").asText("Bearer"),
                    data.path("expiresInSeconds").isMissingNode() ? null : data.path("expiresInSeconds").asLong(),
                    usuario.path("usuarioId").isMissingNode() ? null : usuario.path("usuarioId").asLong(),
                    usuario.path("username").asText(null),
                    usuario.path("nombreCompleto").asText(null),
                    usuario.path("rolCodigo").asText(null),
                    usuario.path("rolNombre").asText(null),
                    usuario.path("activo").asBoolean(true)
            );
        } catch (IOException | InterruptedException ex) {
            if (ex instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            throw new IllegalStateException("No se pudo conectar con el backend del consultorio.", ex);
        }
    }

    public long fetchTotalElements(String relativePath, SessionSnapshot session) {
        JsonNode data = get(relativePath, session).path("data");
        return data.path("totalElements").asLong(0L);
    }

    public List<String> fetchPreviewItems(String relativePath, SessionSnapshot session, String... candidateFields) {
        JsonNode data = get(relativePath, session).path("data");
        JsonNode items = data.path("items");
        List<String> preview = new ArrayList<>();
        if (!items.isArray()) {
            return preview;
        }
        for (JsonNode item : items) {
            String line = buildPreviewLine(item, candidateFields);
            if (line != null && !line.isBlank()) {
                preview.add(line);
            }
            if (preview.size() >= 5) {
                break;
            }
        }
        return preview;
    }

    public Map<String, Long> fetchDashboardMetrics(SessionSnapshot session) {
        Map<String, Long> metrics = new LinkedHashMap<>();
        metrics.put("Pacientes", fetchTotalElements("/pacientes?page=0&size=5", session));
        metrics.put("Citas", fetchTotalElements("/citas?page=0&size=5", session));
        metrics.put("Profesionales", fetchTotalElements("/profesionales?page=0&size=5", session));
        metrics.put("Cobros", fetchTotalElements("/cobros?page=0&size=5", session));
        return metrics;
    }

    public String fetchUserSummary(SessionSnapshot session) {
        if (session == null || !session.hasBearerToken()) {
            return "Sin sesión autenticada.";
        }
        try {
            JsonNode root = get("/auth/me", session);
            JsonNode data = root.path("data");
            if (data.isMissingNode()) {
                return session.getDisplayName();
            }
            String username = data.path("username").asText(session.getUsername());
            String rol = data.path("rolCodigo").asText(session.getRolCodigo());
            return username + " · " + rol;
        } catch (RuntimeException ex) {
            return session.getDisplayName();
        }
    }

    private JsonNode get(String relativePath, SessionSnapshot session) {
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(uri(relativePath))
                    .timeout(Duration.ofMillis(config.getReadTimeoutMs()))
                    .GET();
            if (session != null && session.hasBearerToken()) {
                builder.header("Authorization", session.getAuthorizationHeader());
            }
            HttpResponse<String> response = httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());
            JsonNode root = readJson(response.body());
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new IllegalStateException(extractMessage(root, "El backend devolvió un error al consultar " + relativePath + "."));
            }
            return root;
        } catch (IOException | InterruptedException ex) {
            if (ex instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            throw new IllegalStateException("No se pudo consultar el backend del consultorio.", ex);
        }
    }

    private JsonNode readJson(String body) throws IOException {
        if (body == null || body.isBlank()) {
            return objectMapper.createObjectNode();
        }
        return objectMapper.readTree(body);
    }


    private URI healthUri() {
        URI baseUri = URI.create(config.getBackendBaseUrl());
        String origin = baseUri.getScheme() + "://" + baseUri.getHost() + (baseUri.getPort() > 0 ? ":" + baseUri.getPort() : "");
        return URI.create(origin + "/actuator/health");
    }

    private URI uri(String relativePath) {
        String base = config.getBackendBaseUrl();
        String normalizedBase = base.endsWith("/") ? base.substring(0, base.length() - 1) : base;
        String normalizedPath = relativePath.startsWith("/") ? relativePath : "/" + relativePath;
        return URI.create(normalizedBase + normalizedPath);
    }

    private String extractMessage(JsonNode root, String fallback) {
        String message = root.path("message").asText(null);
        if (message != null && !message.isBlank()) {
            return message;
        }
        JsonNode details = root.path("details");
        if (details.isArray() && !details.isEmpty()) {
            return details.get(0).asText(fallback);
        }
        return fallback;
    }

    private String buildPreviewLine(JsonNode item, String... candidateFields) {
        List<String> values = new ArrayList<>();
        for (String field : candidateFields) {
            JsonNode node = item.path(field);
            if (!node.isMissingNode() && !node.asText().isBlank()) {
                values.add(node.asText());
            }
        }
        if (!values.isEmpty()) {
            return String.join(" · ", values);
        }
        List<String> auto = new ArrayList<>();
        item.fieldNames().forEachRemaining(name -> {
            if (auto.size() >= 3) {
                return;
            }
            JsonNode node = item.path(name);
            if (node.isValueNode()) {
                String value = node.asText();
                if (value != null && !value.isBlank() && auto.size() < 3) {
                    auto.add(value);
                }
            }
        });
        return auto.isEmpty() ? "Elemento disponible" : String.join(" · ", auto);
    }

    // ============================================================
    // Métodos públicos para API Services typed
    // ============================================================

    /**
     * Login usando un DTO tipado.
     */
    public dev.marcosmoreira.desktopconsultorio.http.dto.auth.LoginResponseDto login(LoginRequestDto request) {
        try {
            String payload = objectMapper.writeValueAsString(request);
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(uri("/auth/login"))
                    .timeout(Duration.ofMillis(config.getReadTimeoutMs()))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            JsonNode root = readJson(response.body());

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                String msg = extractMessage(root, "Login failed");
                throw new ApiCallException(msg, response.statusCode(), "/auth/login");
            }

            return objectMapper.treeToValue(root.path("data"), dev.marcosmoreira.desktopconsultorio.http.dto.auth.LoginResponseDto.class);
        } catch (ApiCallException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiCallException("Login request failed: " + e.getMessage(), e, 0, "/auth/login");
        }
    }

    /**
     * Fetch current user session.
     */
    public dev.marcosmoreira.desktopconsultorio.http.dto.auth.MeResponseDto fetchCurrentUser() {
        try {
            JsonNode root = get("/auth/me", SessionSnapshot.getCurrent());
            return objectMapper.treeToValue(root.path("data"), dev.marcosmoreira.desktopconsultorio.http.dto.auth.MeResponseDto.class);
        } catch (ApiCallException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiCallException("Failed to fetch current user: " + e.getMessage(), e, 0, "/auth/me");
        }
    }

    /**
     * Fetch a paginated page from the backend.
     * The backend wraps responses in ApiResponse{data: PageResponse{items: [...]}}.
     */
    public <T> PageResponseDto<T> fetchPage(String path, int page, int size, Class<T> elementType) {
        try {
            JsonNode root = get(path, SessionSnapshot.getCurrent());
            JsonNode dataNode = root.path("data");
            if (dataNode.isMissingNode() || dataNode.isNull()) {
                PageResponseDto<T> empty = new PageResponseDto<>();
                empty.setItems(new java.util.ArrayList<>());
                return empty;
            }
            return objectMapper.treeToValue(dataNode, objectMapper.getTypeFactory().constructParametricType(PageResponseDto.class, elementType));
        } catch (ApiCallException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiCallException("Failed to fetch page from " + path + ": " + e.getMessage(), e, 0, path);
        }
    }

    /**
     * Fetch a single entity by ID.
     */
    public <T> T fetchOne(String path, Class<T> type) {
        try {
            JsonNode root = get(path, SessionSnapshot.getCurrent());
            return objectMapper.treeToValue(root.path("data"), type);
        } catch (ApiCallException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiCallException("Failed to fetch from " + path + ": " + e.getMessage(), e, 0, path);
        }
    }

    /**
     * POST a body and return the created entity.
     */
    public <T> T post(String path, Object body, Class<T> type) {
        return sendWithBody("POST", path, body, type);
    }

    /**
     * PUT a body and return the updated entity.
     */
    public <T> T put(String path, Object body, Class<T> type) {
        return sendWithBody("PUT", path, body, type);
    }

    /**
     * PATCH a body and return the updated entity.
     */
    public <T> T patch(String path, Object body, Class<T> type) {
        return sendWithBody("PATCH", path, body, type);
    }

    /**
     * Download a binary file from the backend.
     */
    public byte[] downloadFile(String path) {
        try {
            SessionSnapshot session = SessionSnapshot.getCurrent();
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(uri(path))
                    .timeout(Duration.ofMillis(config.getReadTimeoutMs()))
                    .GET();
            if (session != null && session.hasBearerToken()) {
                builder.header("Authorization", session.getAuthorizationHeader());
            }
            HttpResponse<byte[]> response = httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofByteArray());
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return response.body();
            }
            throw new ApiCallException("Download failed with status " + response.statusCode(), response.statusCode(), path);
        } catch (ApiCallException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiCallException("Download failed: " + e.getMessage(), e, 0, path);
        }
    }

    private <T> T sendWithBody(String method, String path, Object body, Class<T> type) {
        try {
            SessionSnapshot session = SessionSnapshot.getCurrent();
            String payload = objectMapper.writeValueAsString(body);
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(uri(path))
                    .timeout(Duration.ofMillis(config.getReadTimeoutMs()))
                    .header("Content-Type", "application/json");

            if (session != null && session.hasBearerToken()) {
                builder.header("Authorization", session.getAuthorizationHeader());
            }

            HttpRequest httpRequest;
            switch (method) {
                case "POST" -> httpRequest = builder.POST(HttpRequest.BodyPublishers.ofString(payload)).build();
                case "PUT" -> httpRequest = builder.PUT(HttpRequest.BodyPublishers.ofString(payload)).build();
                case "PATCH" -> httpRequest = builder.method("PATCH", HttpRequest.BodyPublishers.ofString(payload)).build();
                default -> throw new IllegalArgumentException("Method not supported: " + method);
            }

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            JsonNode root = readJson(response.body());
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return objectMapper.treeToValue(root.path("data"), type);
            }
            String msg = extractMessage(root, method + " " + path + " failed");
            throw new ApiCallException(msg, response.statusCode(), path);
        } catch (ApiCallException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiCallException(method + " " + path + " failed: " + e.getMessage(), e, 0, path);
        }
    }
}
