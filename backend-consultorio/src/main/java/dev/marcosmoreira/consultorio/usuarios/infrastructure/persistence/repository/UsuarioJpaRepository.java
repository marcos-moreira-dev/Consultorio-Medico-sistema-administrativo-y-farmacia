package dev.marcosmoreira.consultorio.usuarios.infrastructure.persistence.repository;

import dev.marcosmoreira.consultorio.usuarios.domain.enums.EstadoUsuario;
import dev.marcosmoreira.consultorio.usuarios.infrastructure.persistence.entity.UsuarioEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {
    boolean existsByUsernameIgnoreCase(String username);
    Optional<UsuarioEntity> findByUsernameIgnoreCase(String username);

    @Query("""
            SELECT u
            FROM UsuarioEntity u
            WHERE (:rolId IS NULL OR u.rolId = :rolId)
              AND (:estado IS NULL OR u.estado = :estado)
              AND (
                    :q IS NULL
                    OR LOWER(u.username) LIKE LOWER(CONCAT('%', :q, '%'))
                    OR LOWER(COALESCE(u.nombreCompleto, '')) LIKE LOWER(CONCAT('%', :q, '%'))
                  )
            """)
    Page<UsuarioEntity> buscarPorFiltros(@Param("rolId") Long rolId, @Param("estado") EstadoUsuario estado, @Param("q") String q, Pageable pageable);
}
