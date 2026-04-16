package dev.marcosmoreira.consultorio.usuarios.domain.model;

import dev.marcosmoreira.consultorio.usuarios.domain.enums.EstadoUsuario;
import java.time.LocalDateTime;

/**
 * Modelo de dominio que representa a un usuario administrativo del sistema.
 *
 * <p>Este modelo pertenece al dominio y no depende de detalles HTTP ni de
 * persistencia. Incluye la información relevante para administración de acceso
 * interno del consultorio.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class Usuario {

    private Long usuarioId;
    private Long rolId;
    private String rolCodigo;
    private String rolNombre;
    private String username;
    private String passwordHash;
    private String nombreCompleto;
    private EstadoUsuario estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    /**
     * Constructor vacío.
     */
    public Usuario() {
    }

    /**
     * Construye un usuario completo.
     *
     * @param usuarioId identificador del usuario
     * @param rolId identificador del rol
     * @param rolCodigo código del rol
     * @param rolNombre nombre del rol
     * @param username nombre de usuario
     * @param passwordHash hash de contraseña
     * @param nombreCompleto nombre completo visible
     * @param estado estado lógico del usuario
     * @param fechaCreacion fecha de creación
     * @param fechaActualizacion fecha de última actualización
     */
    public Usuario(
            Long usuarioId,
            Long rolId,
            String rolCodigo,
            String rolNombre,
            String username,
            String passwordHash,
            String nombreCompleto,
            EstadoUsuario estado,
            LocalDateTime fechaCreacion,
            LocalDateTime fechaActualizacion
    ) {
        this.usuarioId = usuarioId;
        this.rolId = rolId;
        this.rolCodigo = normalizeNullableText(rolCodigo);
        this.rolNombre = normalizeNullableText(rolNombre);
        this.username = normalizeNullableText(username);
        this.passwordHash = passwordHash;
        this.nombreCompleto = normalizeNullableText(nombreCompleto);
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
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
        this.rolCodigo = normalizeNullableText(rolCodigo);
    }

    public String getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = normalizeNullableText(rolNombre);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = normalizeNullableText(username);
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = normalizeNullableText(nombreCompleto);
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuario estado) {
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

    /**
     * Indica si el usuario se encuentra activo.
     *
     * @return {@code true} si su estado es ACTIVO; {@code false} en otro caso
     */
    public boolean isActivo() {
        return estado != null && estado.isOperativo();
    }

    /**
     * Indica si el usuario tiene rol informado.
     *
     * @return {@code true} si existe rol asignado; {@code false} en caso contrario
     */
    public boolean hasRol() {
        return rolId != null && rolId > 0;
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
