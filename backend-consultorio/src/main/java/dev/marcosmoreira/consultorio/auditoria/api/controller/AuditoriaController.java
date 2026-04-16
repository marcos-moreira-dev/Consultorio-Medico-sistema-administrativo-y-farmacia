package dev.marcosmoreira.consultorio.auditoria.api.controller;

import dev.marcosmoreira.consultorio.auditoria.api.response.EventoAuditoriaResponse;
import dev.marcosmoreira.consultorio.auditoria.application.port.in.ListarEventosAuditoriaUseCase;
import dev.marcosmoreira.consultorio.auditoria.domain.enums.TipoEventoAuditoria;
import dev.marcosmoreira.consultorio.shared.util.CorrelationIdUtils;
import dev.marcosmoreira.consultorio.shared.util.PageUtils;
import dev.marcosmoreira.consultorio.shared.web.ApiResponse;
import dev.marcosmoreira.consultorio.shared.web.PageResponse;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ADMIN_CONSULTORIO')")
@RequestMapping("/api/v1/auditoria")
public class AuditoriaController {
    private final ListarEventosAuditoriaUseCase listarEventosAuditoriaUseCase;
    public AuditoriaController(ListarEventosAuditoriaUseCase listarEventosAuditoriaUseCase) { this.listarEventosAuditoriaUseCase = listarEventosAuditoriaUseCase; }

    @GetMapping("/eventos")
    public ResponseEntity<ApiResponse<PageResponse<EventoAuditoriaResponse>>> listarEventos(
            @RequestParam(required = false) String modulo,
            @RequestParam(required = false) TipoEventoAuditoria tipoEvento,
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) String correlationId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaHasta,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<EventoAuditoriaResponse> page = listarEventosAuditoriaUseCase.listar(modulo, tipoEvento, usuarioId, correlationId, fechaDesde, fechaHasta, pageable);
        return ResponseEntity.ok(ApiResponse.success(PageUtils.fromPage(page), CorrelationIdUtils.getCurrentCorrelationId()));
    }
}
