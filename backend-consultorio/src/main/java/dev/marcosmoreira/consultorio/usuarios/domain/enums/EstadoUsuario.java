package dev.marcosmoreira.consultorio.usuarios.domain.enums;

/**
 * Catálogo de estados lógicos del usuario administrativo.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public enum EstadoUsuario {

    /**
     * Usuario habilitado para operar en el sistema.
     */
    ACTIVO,

    /**
     * Usuario deshabilitado lógicamente.
     */
    INACTIVO;

    /**
     * Indica si el estado actual representa operatividad.
     *
     * @return {@code true} si es ACTIVO; {@code false} en otro caso
     */
    public boolean isOperativo() {
        return this == ACTIVO;
    }
}
