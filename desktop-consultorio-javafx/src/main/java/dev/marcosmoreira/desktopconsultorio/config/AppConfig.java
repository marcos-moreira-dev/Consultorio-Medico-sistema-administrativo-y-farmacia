package dev.marcosmoreira.desktopconsultorio.config;

/**
 * Configuración base del desktop del consultorio.
 *
 * <p>Esta clase todavía es pequeña a propósito. Su función en esta etapa es
 * dejar claro dónde viven las decisiones mínimas de entorno del cliente JavaFX.</p>
 */
public class AppConfig {

    private final String appName;
    private final String profile;
    private final String backendBaseUrl;
    private final int connectTimeoutMs;
    private final int readTimeoutMs;

    public AppConfig(String appName, String profile, String backendBaseUrl, int connectTimeoutMs, int readTimeoutMs) {
        this.appName = appName;
        this.profile = profile;
        this.backendBaseUrl = backendBaseUrl;
        this.connectTimeoutMs = connectTimeoutMs;
        this.readTimeoutMs = readTimeoutMs;
    }

    public static AppConfig load() {
        return new AppConfig(
                EnvConfig.get("DESKTOP_APP_NAME", "Consultorio Medico"),
                EnvConfig.get("DESKTOP_PROFILE", "dev"),
                EnvConfig.get("BACKEND_BASE_URL", "http://localhost:8080/api/v1"),
                EnvConfig.getInt("HTTP_CONNECT_TIMEOUT_MS", 5000),
                EnvConfig.getInt("HTTP_READ_TIMEOUT_MS", 15000)
        );
    }

    public String getAppName() {
        return appName;
    }

    public String getProfile() {
        return profile;
    }

    public String getBackendBaseUrl() {
        return backendBaseUrl;
    }

    public int getConnectTimeoutMs() {
        return connectTimeoutMs;
    }

    public int getReadTimeoutMs() {
        return readTimeoutMs;
    }
}
