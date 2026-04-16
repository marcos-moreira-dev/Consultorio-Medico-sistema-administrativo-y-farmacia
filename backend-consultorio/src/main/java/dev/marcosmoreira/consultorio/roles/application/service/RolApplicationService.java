package dev.marcosmoreira.consultorio.roles.application.service;

import dev.marcosmoreira.consultorio.roles.application.port.in.BuscarRolUseCase;
import dev.marcosmoreira.consultorio.roles.application.port.in.ListarRolesUseCase;
import dev.marcosmoreira.consultorio.roles.application.port.out.RolPersistencePort;
import dev.marcosmoreira.consultorio.roles.domain.model.Rol;
import dev.marcosmoreira.consultorio.shared.error.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio de aplicación del módulo de roles.
 *
 * <p>Orquesta las operaciones de consulta del catálogo de roles.
 * En esta versión, el módulo se mantiene deliberadamente simple porque
 * su función principal es servir de catálogo de apoyo para otros módulos.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
public class RolApplicationService implements BuscarRolUseCase, ListarRolesUseCase {

    private final RolPersistencePort rolPersistencePort;

    /**
     * Construye el servicio de aplicación del módulo de roles.
     *
     * @param rolPersistencePort puerto de persistencia del catálogo de roles
     */
    public RolApplicationService(RolPersistencePort rolPersistencePort) {
        this.rolPersistencePort = rolPersistencePort;
    }

    /**
     * Busca un rol por su identificador único.
     *
     * @param rolId identificador del rol
     * @return rol encontrado
     */
    @Override
    public Rol buscarPorId(Long rolId) {
        validatePositiveId(rolId, "El identificador del rol debe ser mayor que cero.");

        return rolPersistencePort.buscarPorId(rolId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró un rol con ID " + rolId + "."
                ));
    }

    /**
     * Lista todos los roles disponibles.
     *
     * @return catálogo de roles
     */
    @Override
    public List<Rol> listar() {
        return rolPersistencePort.listar();
    }

    /**
     * Valida que un identificador obligatorio sea positivo.
     *
     * @param value identificador a validar
     * @param message mensaje de error
     */
    private void validatePositiveId(Long value, String message) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException(message);
        }
    }
}
