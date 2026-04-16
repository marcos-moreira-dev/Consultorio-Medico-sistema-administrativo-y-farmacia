package dev.marcosmoreira.consultorio.citas.application.port.out;

import dev.marcosmoreira.consultorio.citas.domain.enums.EstadoCita;
import dev.marcosmoreira.consultorio.citas.domain.model.Cita;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CitaPersistencePort {
    Cita guardar(Cita cita);
    Optional<Cita> buscarPorId(Long citaId);
    Page<Cita> listar(Long pacienteId, Long profesionalId, EstadoCita estadoCita, LocalDateTime fechaDesde, LocalDateTime fechaHasta, Pageable pageable);
    boolean existsByProfesionalIdAndFechaHoraInicio(Long profesionalId, LocalDateTime fechaHoraInicio);
    boolean existsByProfesionalIdAndFechaHoraInicioAndCitaIdNot(Long profesionalId, LocalDateTime fechaHoraInicio, Long citaId);
}
