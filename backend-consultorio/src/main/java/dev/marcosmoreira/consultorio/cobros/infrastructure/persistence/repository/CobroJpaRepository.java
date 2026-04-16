package dev.marcosmoreira.consultorio.cobros.infrastructure.persistence.repository;

import dev.marcosmoreira.consultorio.cobros.domain.enums.EstadoCobro;
import dev.marcosmoreira.consultorio.cobros.domain.enums.MetodoPago;
import dev.marcosmoreira.consultorio.cobros.infrastructure.persistence.entity.CobroEntity;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CobroJpaRepository extends JpaRepository<CobroEntity, Long> {
    boolean existsByAtencionId(Long atencionId);

    @Query("""
            SELECT c
            FROM CobroEntity c
            WHERE (:atencionId IS NULL OR c.atencionId = :atencionId)
              AND (:registradoPorUsuarioId IS NULL OR c.registradoPorUsuarioId = :registradoPorUsuarioId)
              AND (:estadoCobro IS NULL OR c.estadoCobro = :estadoCobro)
              AND (:metodoPago IS NULL OR c.metodoPago = :metodoPago)
              AND (:fechaDesde IS NULL OR c.fechaHoraRegistro >= :fechaDesde)
              AND (:fechaHasta IS NULL OR c.fechaHoraRegistro <= :fechaHasta)
            """)
    Page<CobroEntity> buscarPorFiltros(@Param("atencionId") Long atencionId, @Param("registradoPorUsuarioId") Long registradoPorUsuarioId, @Param("estadoCobro") EstadoCobro estadoCobro, @Param("metodoPago") MetodoPago metodoPago, @Param("fechaDesde") LocalDateTime fechaDesde, @Param("fechaHasta") LocalDateTime fechaHasta, Pageable pageable);
}
