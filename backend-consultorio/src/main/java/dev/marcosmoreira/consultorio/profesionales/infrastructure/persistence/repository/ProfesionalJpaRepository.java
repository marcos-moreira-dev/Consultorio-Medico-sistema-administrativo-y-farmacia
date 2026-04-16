package dev.marcosmoreira.consultorio.profesionales.infrastructure.persistence.repository;

import dev.marcosmoreira.consultorio.profesionales.domain.enums.EstadoProfesional;
import dev.marcosmoreira.consultorio.profesionales.infrastructure.persistence.entity.ProfesionalEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfesionalJpaRepository extends JpaRepository<ProfesionalEntity, Long> {
    boolean existsByUsuarioId(Long usuarioId);
    boolean existsByUsuarioIdAndProfesionalIdNot(Long usuarioId, Long profesionalId);

    @Query("""
            SELECT p
            FROM ProfesionalEntity p
            WHERE (:usuarioId IS NULL OR p.usuarioId = :usuarioId)
              AND (:estadoProfesional IS NULL OR p.estadoProfesional = :estadoProfesional)
              AND (
                    :q IS NULL
                    OR LOWER(p.nombres) LIKE LOWER(CONCAT('%', :q, '%'))
                    OR LOWER(p.apellidos) LIKE LOWER(CONCAT('%', :q, '%'))
                    OR LOWER(COALESCE(p.especialidadBreve, '')) LIKE LOWER(CONCAT('%', :q, '%'))
                  )
            """)
    Page<ProfesionalEntity> buscarPorFiltros(@Param("usuarioId") Long usuarioId, @Param("estadoProfesional") EstadoProfesional estadoProfesional, @Param("q") String q, Pageable pageable);

    long countByEstadoProfesional(EstadoProfesional estadoProfesional);
}
