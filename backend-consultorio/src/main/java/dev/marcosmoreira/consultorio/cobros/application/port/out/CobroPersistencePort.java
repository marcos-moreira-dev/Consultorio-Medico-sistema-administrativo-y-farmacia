package dev.marcosmoreira.consultorio.cobros.application.port.out;

import dev.marcosmoreira.consultorio.cobros.domain.enums.EstadoCobro;
import dev.marcosmoreira.consultorio.cobros.domain.enums.MetodoPago;
import dev.marcosmoreira.consultorio.cobros.domain.model.Cobro;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CobroPersistencePort {
    Cobro guardar(Cobro cobro);
    Optional<Cobro> buscarPorId(Long cobroId);
    Page<Cobro> listar(Long atencionId, Long registradoPorUsuarioId, EstadoCobro estadoCobro, MetodoPago metodoPago, LocalDateTime fechaDesde, LocalDateTime fechaHasta, Pageable pageable);
    boolean existsByAtencionId(Long atencionId);
}
