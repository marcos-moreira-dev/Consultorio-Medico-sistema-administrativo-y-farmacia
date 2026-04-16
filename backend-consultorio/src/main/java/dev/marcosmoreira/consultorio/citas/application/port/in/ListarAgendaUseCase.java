package dev.marcosmoreira.consultorio.citas.application.port.in;

import dev.marcosmoreira.consultorio.citas.domain.enums.EstadoCita;
import dev.marcosmoreira.consultorio.citas.domain.model.Cita;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarAgendaUseCase {
    Page<Cita> listar(Long pacienteId, Long profesionalId, EstadoCita estadoCita, LocalDateTime fechaDesde, LocalDateTime fechaHasta, Pageable pageable);
}
