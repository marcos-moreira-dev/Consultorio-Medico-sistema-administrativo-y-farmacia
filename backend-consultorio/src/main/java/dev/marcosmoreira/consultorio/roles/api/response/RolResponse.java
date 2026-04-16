package dev.marcosmoreira.consultorio.roles.api.response;

import dev.marcosmoreira.consultorio.roles.domain.model.Rol;

/**
 * DTO de salida con el detalle de un rol del sistema.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class RolResponse {

    private Long rolId;
    private String codigo;
    private String nombre;

    /**
     * Constructor vacío requerido por serialización.
     */
    public RolResponse() {
    }

    /**
     * Construye la respuesta completa del rol.
     *
     * @param rolId identificador del rol
     * @param codigo código estable del rol
     * @param nombre nombre legible del rol
     */
    public RolResponse(Long rolId, String codigo, String nombre) {
        this.rolId = rolId;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    /**
     * Construye un DTO de salida a partir del dominio.
     *
     * @param rol rol del dominio
     * @return respuesta lista para serializar
     */
    public static RolResponse fromDomain(Rol rol) {
        if (rol == null) {
            throw new IllegalArgumentException("El rol no puede ser nulo.");
        }

        return new RolResponse(
                rol.getRolId(),
                rol.getCodigo(),
                rol.getNombre()
        );
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
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
