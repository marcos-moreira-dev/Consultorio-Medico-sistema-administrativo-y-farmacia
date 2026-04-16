package dev.marcosmoreira.consultorio.atenciones.application.port.out;

import dev.marcosmoreira.consultorio.atenciones.domain.model.Atencion;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AtencionPersistencePort {
    Atencion guardar(Atencion atencion);
    Optional<Atencion> buscarPorId(Long atencionId);
    Page<Atencion> listar(Long pacienteId, Long profesionalId, LocalDateTime fechaDesde, LocalDateTime fechaHasta, Pageable pageable);
}
