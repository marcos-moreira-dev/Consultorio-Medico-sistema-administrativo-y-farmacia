package dev.marcosmoreira.desktopconsultorio.session;

/**
 * Snapshot inmutable de la sesión autenticada en el cliente desktop.
 *
 * <p>Esta clase mantiene una referencia estática a la sesión actual para
 * que los API services puedan acceder a ella sin necesidad de inyección
 * de dependencias, simplificando la arquitectura en esta etapa del proyecto.</p>
 */
public class SessionSnapshot {

    /** Sesión actual compartida para acceso estático desde API services. */
    private static volatile SessionSnapshot current;

    private final String accessToken;
    private final String tokenType;
    private final Long expiresInSeconds;
    private final Long usuarioId;
    private final String username;
    private final String nombreCompleto;
    private final String rolCodigo;
    private final String rolNombre;
    private final boolean activo;
    private final boolean authenticated;

    public SessionSnapshot(
            String accessToken,
            String tokenType,
            Long expiresInSeconds,
            Long usuarioId,
            String username,
            String nombreCompleto,
            String rolCodigo,
            String rolNombre,
            boolean activo,
            boolean authenticated
    ) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresInSeconds = expiresInSeconds;
        this.usuarioId = usuarioId;
        this.username = username;
        this.nombreCompleto = nombreCompleto;
        this.rolCodigo = rolCodigo;
        this.rolNombre = rolNombre;
        this.activo = activo;
        this.authenticated = authenticated;
    }

    public static SessionSnapshot authenticated(
            String accessToken,
            String tokenType,
            Long expiresInSeconds,
            Long usuarioId,
            String username,
            String nombreCompleto,
            String rolCodigo,
            String rolNombre,
            boolean activo
    ) {
        return new SessionSnapshot(
                accessToken,
                tokenType,
                expiresInSeconds,
                usuarioId,
                username,
                nombreCompleto,
                rolCodigo,
                rolNombre,
                activo,
                true
        );
    }

    public static SessionSnapshot uiExploration() {
        return new SessionSnapshot(
                null,
                null,
                null,
                null,
                "modo-ui",
                "Exploración visual local",
                "SIN_AUTENTICAR",
                "Exploración UI",
                true,
                false
        );
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Long getExpiresInSeconds() {
        return expiresInSeconds;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getUsername() {
        return username;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getRolCodigo() {
        return rolCodigo;
    }

    public String getRolNombre() {
        return rolNombre;
    }

    public boolean isActivo() {
        return activo;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public boolean hasBearerToken() {
        return authenticated && accessToken != null && !accessToken.isBlank();
    }

    public String getAuthorizationHeader() {
        if (!hasBearerToken()) {
            return null;
        }
        String type = tokenType == null || tokenType.isBlank() ? "Bearer" : tokenType;
        return type + " " + accessToken;
    }

    public String getDisplayName() {
        if (nombreCompleto != null && !nombreCompleto.isBlank()) {
            return nombreCompleto;
        }
        return username != null ? username : "Sin usuario";
    }

    /**
     * Obtiene la sesión actual compartida.
     * Delega a InMemorySessionStore como única fuente de verdad.
     */
    public static SessionSnapshot getCurrent() {
        return current;
    }

    /**
     * Establece la sesión actual compartida.
     * Package-private: solo InMemorySessionStore debe llamar a este método.
     */
    static synchronized void setCurrent(SessionSnapshot session) {
        current = session;
    }
}
