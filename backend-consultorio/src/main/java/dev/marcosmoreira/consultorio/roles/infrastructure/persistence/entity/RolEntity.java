package dev.marcosmoreira.consultorio.roles.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad JPA del catálogo de roles.
 *
 * <p>Se alinea con la BD del consultorio usando {@code nombre_rol} como código estable
 * y {@code descripcion_breve} como nombre legible. Con esto se evita duplicar semántica
 * en respuestas del backend y se aprovecha mejor el modelo relacional ya definido.</p>
 */
@Entity
@Table(schema = "consultorio", name = "rol")
public class RolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id")
    private Long rolId;

    @Column(name = "nombre_rol", nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(name = "descripcion_breve", length = 200)
    private String nombre;

    public RolEntity() {
    }

    public RolEntity(Long rolId, String codigo, String nombre) {
        this.rolId = rolId;
        this.codigo = normalizeNullableText(codigo);
        this.nombre = normalizeNullableText(nombre != null ? nombre : codigo);
    }

    public Long getRolId() { return rolId; }
    public void setRolId(Long rolId) { this.rolId = rolId; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = normalizeNullableText(codigo); }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = normalizeNullableText(nombre); }

    private String normalizeNullableText(String value) {
        if (value == null) { return null; }
        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }
}
