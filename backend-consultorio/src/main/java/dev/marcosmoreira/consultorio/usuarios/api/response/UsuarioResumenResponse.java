package dev.marcosmoreira.consultorio.usuarios.api.response;

import dev.marcosmoreira.consultorio.usuarios.domain.model.Usuario;

/**
 * DTO de salida resumido para listados de usuarios.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class UsuarioResumenResponse {

    private Long usuarioId;
    private Long rolId;
    private String rolCodigo;
    private String rolNombre;
    private String username;
    private String nombreCompleto;
    private String estado;

    /**
     * Constructor vacío requerido por serialización.
     */
    public UsuarioResumenResponse() {
    }

    /**
     * Construye la respuesta resumida.
     *
     * @param usuarioId identificador del usuario
     * @param rolId identificador del rol
     * @param rolCodigo código del rol
     * @param rolNombre nombre del rol
     * @param username nombre de usuario
     * @param nombreCompleto nombre completo
     * @param estado estado lógico del usuario
     */
    public UsuarioResumenResponse(
            Long usuarioId,
            Long rolId,
            String rolCodigo,
            String rolNombre,
            String username,
            String nombreCompleto,
            String estado
    ) {
        this.usuarioId = usuarioId;
        this.rolId = rolId;
        this.rolCodigo = rolCodigo;
        this.rolNombre = rolNombre;
        this.username = username;
        this.nombreCompleto = nombreCompleto;
        this.estado = estado;
    }

    /**
     * Construye el resumen a partir del dominio.
     *
     * @param usuario usuario del dominio
     * @return DTO resumido
     */
    public static UsuarioResumenResponse fromDomain(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }

        return new UsuarioResumenResponse(
                usuario.getUsuarioId(),
                usuario.getRolId(),
                usuario.getRolCodigo(),
                usuario.getRolNombre(),
                usuario.getUsername(),
                usuario.getNombreCompleto(),
                usuario.getEstado() == null ? null : usuario.getEstado().name()
        );
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
