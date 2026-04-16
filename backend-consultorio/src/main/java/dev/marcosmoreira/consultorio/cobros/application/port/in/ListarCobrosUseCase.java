package dev.marcosmoreira.consultorio.cobros.application.port.in;

import dev.marcosmoreira.consultorio.cobros.domain.enums.EstadoCobro;
import dev.marcosmoreira.consultorio.cobros.domain.enums.MetodoPago;
import dev.marcosmoreira.consultorio.cobros.domain.model.Cobro;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarCobrosUseCase {
    Page<Cobro> listar(Long atencionId, Long registradoPorUsuarioId, EstadoCobro estadoCobro, MetodoPago metodoPago, LocalDateTime fechaDesde, LocalDateTime fechaHasta, Pageable pageable);
}
