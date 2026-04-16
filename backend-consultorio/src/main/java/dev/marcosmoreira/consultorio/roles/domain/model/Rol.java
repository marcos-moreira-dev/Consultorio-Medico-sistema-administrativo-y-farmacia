package dev.marcosmoreira.consultorio.roles.domain.model;

import java.util.Objects;

/**
 * Modelo de dominio que representa un rol del sistema.
 *
 * <p>Un rol funciona como catálogo de autorización y clasificación funcional
 * de usuarios administrativos del consultorio.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class Rol {

    private Long rolId;
    private String codigo;
    private String nombre;

    /**
     * Constructor vacío.
     */
    public Rol() {
    }

    /**
     * Construye un rol completo.
     *
     * @param rolId identificador del rol
     * @param codigo código estable del rol
     * @param nombre nombre legible del rol
     */
    public Rol(Long rolId, String codigo, String nombre) {
        this.rolId = rolId;
        this.codigo = normalizeNullableText(codigo);
        this.nombre = normalizeNullableText(nombre);
    }

    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = normalizeNullableText(codigo);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = normalizeNullableText(nombre);
    }

    /**
     * Indica si el rol contiene un código informado.
     *
     * @return {@code true} si existe código útil; {@code false} en caso contrario
     */
    public boolean hasCodigo() {
        return codigo != null && !codigo.isBlank();
    }

    /**
     * Indica si el rol contiene un nombre informado.
     *
     * @return {@code true} si existe nombre útil; {@code false} en caso contrario
     */
    public boolean hasNombre() {
        return nombre != null && !nombre.isBlank();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rol rol)) {
            return false;
        }
        return Objects.equals(rolId, rol.rolId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rolId);
    }
}
