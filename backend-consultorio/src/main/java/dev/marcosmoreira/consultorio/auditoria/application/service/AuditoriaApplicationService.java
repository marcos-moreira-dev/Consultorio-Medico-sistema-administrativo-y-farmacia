package dev.marcosmoreira.consultorio.auditoria.application.service;

import dev.marcosmoreira.consultorio.auditoria.api.response.EventoAuditoriaResponse;
import dev.marcosmoreira.consultorio.auditoria.application.port.in.ListarEventosAuditoriaUseCase;
import dev.marcosmoreira.consultorio.auditoria.application.port.out.AuditoriaQueryPort;
import dev.marcosmoreira.consultorio.auditoria.domain.enums.TipoEventoAuditoria;
import dev.marcosmoreira.consultorio.shared.util.PageableUtils;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuditoriaApplicationService implements ListarEventosAuditoriaUseCase {
    private final AuditoriaQueryPort auditoriaQueryPort;
    public AuditoriaApplicationService(AuditoriaQueryPort auditoriaQueryPort) { this.auditoriaQueryPort = auditoriaQueryPort; }
    @Override
    public Page<EventoAuditoriaResponse> listar(String modulo, TipoEventoAuditoria tipoEvento, Long usuarioId, String correlationId, LocalDateTime fechaDesde, LocalDateTime fechaHasta, Pageable pageable) {
        validateOptionalPositiveId(usuarioId, "Si se envía usuarioId, debe ser mayor que cero.");
        validateDateRange(fechaDesde, fechaHasta);
        Pageable sanitizedPageable = PageableUtils.sanitize(pageable, Sort.by(Sort.Order.desc("timestamp")), "timestamp");
        return auditoriaQueryPort.listar(normalizeNullableText(modulo), tipoEvento, usuarioId, normalizeNullableText(correlationId), fechaDesde, fechaHasta, sanitizedPageable);
    }
    private void validateOptionalPositiveId(Long value, String message) { if (value != null && value <= 0) throw new IllegalArgumentException(message); }
    private void validateDateRange(LocalDateTime fechaDesde, LocalDateTime fechaHasta) { if (fechaDesde != null && fechaHasta != null && fechaDesde.isAfter(fechaHasta)) throw new IllegalArgumentException("La fecha inicial no puede ser posterior a la fecha final."); }
    private String normalizeNullableText(String value) { if (value == null) return null; String normalized = value.trim(); return normalized.isEmpty() ? null : normalized; }
}
