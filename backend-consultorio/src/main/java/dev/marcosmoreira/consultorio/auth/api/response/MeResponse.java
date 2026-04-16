package dev.marcosmoreira.consultorio.auth.api.response;

import dev.marcosmoreira.consultorio.auth.domain.model.AuthUser;

/**
 * DTO de salida para representar al usuario autenticado actual.
 *
 * <p>Se usa tanto en el endpoint {@code /auth/me} como dentro de la respuesta
 * de login. Su propósito es exponer una vista estable y compacta del usuario
 * autenticado sin arrastrar detalles innecesarios del dominio completo de usuarios.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class MeResponse {

    private Long usuarioId;
    private String username;
    private String nombreCompleto;
    private String rolCodigo;
    private String rolNombre;
    private Boolean activo;

    /**
     * Constructor vacío requerido por serialización.
     */
    public MeResponse() {
    }

    /**
     * Construye una respuesta completa del usuario autenticado.
     *
     * @param usuarioId identificador del usuario
     * @param username nombre de usuario
     * @param nombreCompleto nombre completo visible
     * @param rolCodigo código del rol principal
     * @param rolNombre nombre legible del rol
     * @param activo estado lógico del usuario
     */
    public MeResponse(
            Long usuarioId,
            String username,
            String nombreCompleto,
            String rolCodigo,
            String rolNombre,
            Boolean activo
    ) {
        this.usuarioId = usuarioId;
        this.username = username;
        this.nombreCompleto = nombreCompleto;
        this.rolCodigo = rolCodigo;
        this.rolNombre = rolNombre;
        this.activo = activo;
    }

    /**
     * Construye una respuesta a partir del modelo de autenticación.
     *
     * @param authUser usuario autenticado del dominio
     * @return DTO de salida para la API
     * @throws IllegalArgumentException si el usuario recibido es nulo
     */
    public static MeResponse fromDomain(AuthUser authUser) {
        if (authUser == null) {
            throw new IllegalArgumentException("El usuario autenticado no puede ser nulo.");
        }

        return new MeResponse(
                authUser.getUsuarioId(),
                authUser.getUsername(),
                authUser.getNombreCompleto(),
                authUser.getRolCodigo(),
                authUser.getRolNombre(),
                authUser.getActivo()
        );
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
        this.username = username;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getRolCodigo() {
        return rolCodigo;
    }

    public void setRolCodigo(String rolCodigo) {
        this.rolCodigo = rolCodigo;
    }

    public String getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
