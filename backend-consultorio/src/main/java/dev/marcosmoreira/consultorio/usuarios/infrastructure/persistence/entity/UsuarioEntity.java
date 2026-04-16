package dev.marcosmoreira.consultorio.usuarios.infrastructure.persistence.entity;

import dev.marcosmoreira.consultorio.shared.persistence.AuditableEntity;
import dev.marcosmoreira.consultorio.usuarios.domain.enums.EstadoUsuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Formula;

/**
 * Entidad JPA del módulo de usuarios.
 *
 * <p>Se alinea con la base de datos del consultorio usando fórmulas de solo lectura
 * para exponer información derivada de rol y nombre visible sin inflar todavía el
 * esquema relacional base.</p>
 */
@Entity
@Table(schema = "consultorio", name = "usuario")
public class UsuarioEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "rol_id", nullable = false)
    private Long rolId;

    @Formula("(select r.nombre_rol from consultorio.rol r where r.rol_id = rol_id)")
    private String rolCodigo;

    @Formula("(select coalesce(r.descripcion_breve, r.nombre_rol) from consultorio.rol r where r.rol_id = rol_id)")
    private String rolNombre;

    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Formula("coalesce((select concat(trim(p.nombres), ' ', trim(p.apellidos)) from consultorio.profesional p where p.usuario_id = usuario_id), username)")
    private String nombreCompleto;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_usuario", nullable = false, length = 20)
    private EstadoUsuario estado;

    public UsuarioEntity() {
    }

    public UsuarioEntity(Long usuarioId, Long rolId, String username, String passwordHash, String nombreCompleto, EstadoUsuario estado) {
        this.usuarioId = usuarioId;
        this.rolId = rolId;
        this.username = normalizeNullableText(username);
        this.passwordHash = passwordHash;
        this.nombreCompleto = normalizeNullableText(nombreCompleto);
        this.estado = estado;
    }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public Long getRolId() { return rolId; }
    public void setRolId(Long rolId) { this.rolId = rolId; }
    public String getRolCodigo() { return rolCodigo; }
    public void setRolCodigo(String rolCodigo) { this.rolCodigo = rolCodigo; }
    public String getRolNombre() { return rolNombre; }
    public void setRolNombre(String rolNombre) { this.rolNombre = rolNombre; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = normalizeNullableText(username); }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = normalizeNullableText(nombreCompleto); }
    public EstadoUsuario getEstado() { return estado; }
    public void setEstado(EstadoUsuario estado) { this.estado = estado; }

    private String normalizeNullableText(String value) {
        if (value == null) { return null; }
        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }
}
