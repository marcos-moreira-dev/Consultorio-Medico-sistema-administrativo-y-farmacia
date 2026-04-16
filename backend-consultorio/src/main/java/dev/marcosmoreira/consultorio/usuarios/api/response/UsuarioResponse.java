package dev.marcosmoreira.consultorio.usuarios.api.response;

import dev.marcosmoreira.consultorio.usuarios.domain.model.Usuario;
import java.time.LocalDateTime;

/**
 * DTO de salida con el detalle completo de un usuario administrativo.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class UsuarioResponse {

    private Long usuarioId;
    private Long rolId;
    private String rolCodigo;
    private String rolNombre;
    private String username;
    private String nombreCompleto;
    private String estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    /**
     * Constructor vacío requerido por serialización.
     */
    public UsuarioResponse() {
    }

    /**
     * Construye la respuesta completa.
     *
     * @param usuarioId identificador del usuario
     * @param rolId identificador del rol
     * @param rolCodigo código del rol
     * @param rolNombre nombre legible del rol
     * @param username nombre de usuario
     * @param nombreCompleto nombre completo visible
     * @param estado estado lógico del usuario
     * @param fechaCreacion fecha de creación
     * @param fechaActualizacion fecha de última actualización
     */
    public UsuarioResponse(
            Long usuarioId,
            Long rolId,
            String rolCodigo,
            String rolNombre,
            String username,
            String nombreCompleto,
            String estado,
            LocalDateTime fechaCreacion,
            LocalDateTime fechaActualizacion
    ) {
        this.usuarioId = usuarioId;
        this.rolId = rolId;
        this.rolCodigo = rolCodigo;
        this.rolNombre = rolNombre;
        this.username = username;
        this.nombreCompleto = nombreCompleto;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    /**
     * Construye un DTO de salida a partir del dominio.
     *
     * @param usuario usuario del dominio
     * @return respuesta lista para serializar
     */
    public static UsuarioResponse fromDomain(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }

        return new UsuarioResponse(
                usuario.getUsuarioId(),
                usuario.getRolId(),
                usuario.getRolCodigo(),
                usuario.getRolNombre(),
                usuario.getUsername(),
                usuario.getNombreCompleto(),
                usuario.getEstado() == null ? null : usuario.getEstado().name(),
                usuario.getFechaCreacion(),
                usuario.getFechaActualizacion()
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

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
