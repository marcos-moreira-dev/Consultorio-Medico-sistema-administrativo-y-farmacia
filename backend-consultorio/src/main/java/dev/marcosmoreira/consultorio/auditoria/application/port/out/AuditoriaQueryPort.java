package dev.marcosmoreira.consultorio.auditoria.application.port.out;

import dev.marcosmoreira.consultorio.auditoria.api.response.EventoAuditoriaResponse;
import dev.marcosmoreira.consultorio.auditoria.domain.enums.TipoEventoAuditoria;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuditoriaQueryPort {
    Page<EventoAuditoriaResponse> listar(
            String modulo,
            TipoEventoAuditoria tipoEvento,
            Long usuarioId,
            String correlationId,
            LocalDateTime fechaDesde,
            LocalDateTime fechaHasta,
            Pageable pageable
    );
}
