package dev.marcosmoreira.consultorio.citas.infrastructure.persistence.repository;

import dev.marcosmoreira.consultorio.citas.domain.enums.EstadoCita;
import dev.marcosmoreira.consultorio.citas.infrastructure.persistence.entity.CitaEntity;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CitaJpaRepository extends JpaRepository<CitaEntity, Long> {
    boolean existsByProfesionalIdAndFechaHoraInicio(Long profesionalId, LocalDateTime fechaHoraInicio);
    boolean existsByProfesionalIdAndFechaHoraInicioAndCitaIdNot(Long profesionalId, LocalDateTime fechaHoraInicio, Long citaId);

    @Query("""
            SELECT c
            FROM CitaEntity c
            WHERE (:pacienteId IS NULL OR c.pacienteId = :pacienteId)
              AND (:profesionalId IS NULL OR c.profesionalId = :profesionalId)
              AND (:estadoCita IS NULL OR c.estadoCita = :estadoCita)
              AND (:fechaDesde IS NULL OR c.fechaHoraInicio >= :fechaDesde)
              AND (:fechaHasta IS NULL OR c.fechaHoraInicio <= :fechaHasta)
            """)
    Page<CitaEntity> buscarPorFiltros(@Param("pacienteId") Long pacienteId, @Param("profesionalId") Long profesionalId, @Param("estadoCita") EstadoCita estadoCita, @Param("fechaDesde") LocalDateTime fechaDesde, @Param("fechaHasta") LocalDateTime fechaHasta, Pageable pageable);
}
