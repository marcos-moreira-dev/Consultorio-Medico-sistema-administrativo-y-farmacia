package dev.marcosmoreira.consultorio.roles.infrastructure.persistence.repository;

import dev.marcosmoreira.consultorio.roles.infrastructure.persistence.entity.RolEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio JPA del catálogo de roles.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public interface RolJpaRepository extends JpaRepository<RolEntity, Long> {

    /**
     * Lista todos los roles ordenados por nombre y luego por identificador.
     *
     * @return catálogo ordenado de roles
     */
    List<RolEntity> findAllByOrderByNombreAscRolIdAsc();
}
