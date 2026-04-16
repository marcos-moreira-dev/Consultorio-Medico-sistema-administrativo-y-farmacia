package dev.marcosmoreira.consultorio.roles.application.port.out;

import dev.marcosmoreira.consultorio.roles.domain.model.Rol;
import java.util.List;
import java.util.Optional;

public interface RolPersistencePort {
    Optional<Rol> buscarPorId(Long rolId);
    List<Rol> listar();
}
