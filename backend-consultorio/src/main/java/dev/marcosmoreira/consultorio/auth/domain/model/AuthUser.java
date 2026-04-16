package dev.marcosmoreira.consultorio.auth.domain.model;

/**
 * Modelo de dominio reducido para representar al usuario autenticado.
 *
 * <p>No pretende reemplazar al agregado o entidad completa de usuarios del sistema.
 * Su finalidad es encapsular únicamente la información que el módulo de autenticación
 * necesita para construir respuestas, emitir tokens y exponer el contexto del usuario
 * autenticado actual.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class AuthUser {

    private Long usuarioId;
    private String username;
    private String nombreCompleto;
    private String rolCodigo;
    private String rolNombre;
    private Boolean activo;

    /**
     * Constructor vacío.
     */
    public AuthUser() {
    }

    /**
     * Construye un usuario autenticado completo.
     *
     * @param usuarioId identificador del usuario
     * @param username nombre de usuario
     * @param nombreCompleto nombre completo visible
     * @param rolCodigo código del rol principal
     * @param rolNombre nombre legible del rol principal
     * @param activo estado lógico del usuario
     */
    public AuthUser(
            Long usuarioId,
            String username,
            String nombreCompleto,
            String rolCodigo,
            String rolNombre,
            Boolean activo
    ) {
        this.usuarioId = usuarioId;
        this.username = normalizeNullableText(username);
        this.nombreCompleto = normalizeNullableText(nombreCompleto);
        this.rolCodigo = normalizeNullableText(rolCodigo);
        this.rolNombre = normalizeNullableText(rolNombre);
        this.activo = activo;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = normalizeNullableText(username);
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = normalizeNullableText(nombreCompleto);
    }

    public String getRolCodigo() {
        return rolCodigo;
    }

    public void setRolCodigo(String rolCodigo) {
        this.rolCodigo = normalizeNullableText(rolCodigo);
    }

    public String getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = normalizeNullableText(rolNombre);
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    /**
     * Indica si el usuario tiene un rol informado.
     *
     * @return {@code true} si existe código de rol; {@code false} en caso contrario
     */
    public boolean hasRol() {
        return rolCodigo != null && !rolCodigo.isBlank();
    }

    /**
     * Indica si el usuario está marcado como activo.
     *
     * @return {@code true} si está activo; {@code false} en otro caso
     */
    public boolean isActivo() {
        return Boolean.TRUE.equals(activo);
    }

    /**
     * Normaliza un texto opcional.
     *
     * @param value texto a normalizar
     * @return texto con trim aplicado o {@code null} si queda vacío
     */
    private String normalizeNullableText(String value) {
        if (value == null) {
            return null;
        }

        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }
}
