package dev.marcosmoreira.consultorio.atenciones.application.port.in;

import dev.marcosmoreira.consultorio.atenciones.domain.model.Atencion;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarAtencionesUseCase {
    Page<Atencion> listar(Long pacienteId, Long profesionalId, LocalDateTime fechaDesde, LocalDateTime fechaHasta, Pageable pageable);
}
