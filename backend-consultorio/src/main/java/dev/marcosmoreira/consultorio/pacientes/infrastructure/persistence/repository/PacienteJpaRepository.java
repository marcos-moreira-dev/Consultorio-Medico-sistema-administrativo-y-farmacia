package dev.marcosmoreira.consultorio.pacientes.infrastructure.persistence.repository;

import dev.marcosmoreira.consultorio.pacientes.infrastructure.persistence.entity.PacienteEntity;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PacienteJpaRepository extends JpaRepository<PacienteEntity, Long> {
    boolean existsByCedulaIgnoreCase(String cedula);
    boolean existsByCedulaIgnoreCaseAndPacienteIdNot(String cedula, Long pacienteId);

    @Query("""
            SELECT p
            FROM PacienteEntity p
            WHERE (:cedula IS NULL OR LOWER(COALESCE(p.cedula, '')) = LOWER(:cedula))
              AND (:fechaNacimiento IS NULL OR p.fechaNacimiento = :fechaNacimiento)
              AND (
                    :q IS NULL
                    OR LOWER(p.nombres) LIKE LOWER(CONCAT('%', :q, '%'))
                    OR LOWER(p.apellidos) LIKE LOWER(CONCAT('%', :q, '%'))
                    OR LOWER(COALESCE(p.telefono, '')) LIKE LOWER(CONCAT('%', :q, '%'))
                    OR LOWER(COALESCE(p.cedula, '')) LIKE LOWER(CONCAT('%', :q, '%'))
                  )
            """)
    Page<PacienteEntity> buscarPorFiltros(@Param("cedula") String cedula, @Param("fechaNacimiento") LocalDate fechaNacimiento, @Param("q") String q, Pageable pageable);
}
