package dev.marcosmoreira.consultorio.roles.application.port.in;

import dev.marcosmoreira.consultorio.roles.domain.model.Rol;
import java.util.List;

/**
 * Caso de uso para listar el catálogo de roles del sistema.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public interface ListarRolesUseCase {

    /**
     * Lista todos los roles disponibles.
     *
     * @return catálogo de roles
     */
    List<Rol> listar();
}
