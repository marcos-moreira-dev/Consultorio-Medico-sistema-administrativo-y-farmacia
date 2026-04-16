package dev.marcosmoreira.consultorio.atenciones.infrastructure.persistence.repository;

import dev.marcosmoreira.consultorio.atenciones.infrastructure.persistence.entity.AtencionEntity;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AtencionJpaRepository extends JpaRepository<AtencionEntity, Long> {
    @Query("""
            SELECT a
            FROM AtencionEntity a
            WHERE (:pacienteId IS NULL OR a.pacienteId = :pacienteId)
              AND (:profesionalId IS NULL OR a.profesionalId = :profesionalId)
              AND (:fechaDesde IS NULL OR a.fechaHoraAtencion >= :fechaDesde)
              AND (:fechaHasta IS NULL OR a.fechaHoraAtencion <= :fechaHasta)
            """)
    Page<AtencionEntity> buscarPorFiltros(@Param("pacienteId") Long pacienteId, @Param("profesionalId") Long profesionalId, @Param("fechaDesde") LocalDateTime fechaDesde, @Param("fechaHasta") LocalDateTime fechaHasta, Pageable pageable);
}
