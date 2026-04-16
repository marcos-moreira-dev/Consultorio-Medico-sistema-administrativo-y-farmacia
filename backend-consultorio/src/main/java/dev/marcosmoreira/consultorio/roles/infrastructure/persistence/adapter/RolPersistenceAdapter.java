package dev.marcosmoreira.consultorio.roles.infrastructure.persistence.adapter;

import dev.marcosmoreira.consultorio.roles.application.port.out.RolPersistencePort;
import dev.marcosmoreira.consultorio.roles.domain.model.Rol;
import dev.marcosmoreira.consultorio.roles.infrastructure.persistence.mapper.RolPersistenceMapper;
import dev.marcosmoreira.consultorio.roles.infrastructure.persistence.repository.RolJpaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * Adaptador de persistencia del módulo de roles.
 *
 * <p>Implementa el puerto de salida de la capa de aplicación y delega
 * el acceso real a la base de datos en Spring Data JPA.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Repository
public class RolPersistenceAdapter implements RolPersistencePort {

    private final RolJpaRepository rolJpaRepository;
    private final RolPersistenceMapper rolPersistenceMapper;

    /**
     * Construye el adaptador de persistencia del módulo.
     *
     * @param rolJpaRepository repositorio JPA de roles
     * @param rolPersistenceMapper mapper entre entidad y dominio
     */
    public RolPersistenceAdapter(
            RolJpaRepository rolJpaRepository,
            RolPersistenceMapper rolPersistenceMapper
    ) {
        this.rolJpaRepository = rolJpaRepository;
        this.rolPersistenceMapper = rolPersistenceMapper;
    }

    /**
     * Busca un rol por su identificador único.
     *
     * @param rolId identificador del rol
     * @return rol encontrado, o vacío si no existe
     */
    @Override
    public Optional<Rol> buscarPorId(Long rolId) {
        return rolJpaRepository.findById(rolId)
                .map(rolPersistenceMapper::toDomain);
    }

    /**
     * Lista todos los roles disponibles.
     *
     * @return catálogo de roles
     */
    @Override
    public List<Rol> listar() {
        return rolJpaRepository.findAllByOrderByNombreAscRolIdAsc()
                .stream()
                .map(rolPersistenceMapper::toDomain)
                .toList();
    }
}
