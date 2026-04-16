package dev.marcosmoreira.consultorio.citas.api.controller;

import dev.marcosmoreira.consultorio.citas.api.request.CancelarCitaRequest;
import dev.marcosmoreira.consultorio.citas.api.request.CrearCitaRequest;
import dev.marcosmoreira.consultorio.citas.api.request.ReprogramarCitaRequest;
import dev.marcosmoreira.consultorio.citas.api.response.AgendaItemResponse;
import dev.marcosmoreira.consultorio.citas.api.response.CitaResponse;
import dev.marcosmoreira.consultorio.citas.application.port.in.*;
import dev.marcosmoreira.consultorio.citas.domain.enums.EstadoCita;
import dev.marcosmoreira.consultorio.citas.domain.model.Cita;
import dev.marcosmoreira.consultorio.shared.util.CorrelationIdUtils;
import dev.marcosmoreira.consultorio.shared.util.PageUtils;
import dev.marcosmoreira.consultorio.shared.web.ApiResponse;
import dev.marcosmoreira.consultorio.shared.web.PageResponse;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasAnyRole('ADMIN_CONSULTORIO','OPERADOR_CONSULTORIO','PROFESIONAL_CONSULTORIO')")
@RequestMapping("/api/v1/citas")
public class CitaController {
    private final CrearCitaUseCase crearCitaUseCase;
    private final BuscarCitaUseCase buscarCitaUseCase;
    private final ListarAgendaUseCase listarAgendaUseCase;
    private final CancelarCitaUseCase cancelarCitaUseCase;
    private final ReprogramarCitaUseCase reprogramarCitaUseCase;
    public CitaController(CrearCitaUseCase crearCitaUseCase, BuscarCitaUseCase buscarCitaUseCase, ListarAgendaUseCase listarAgendaUseCase, CancelarCitaUseCase cancelarCitaUseCase, ReprogramarCitaUseCase reprogramarCitaUseCase) { this.crearCitaUseCase = crearCitaUseCase; this.buscarCitaUseCase = buscarCitaUseCase; this.listarAgendaUseCase = listarAgendaUseCase; this.cancelarCitaUseCase = cancelarCitaUseCase; this.reprogramarCitaUseCase = reprogramarCitaUseCase; }
    @PostMapping
    public ResponseEntity<ApiResponse<CitaResponse>> crear(@Valid @RequestBody CrearCitaRequest request) {
        Cita creada = crearCitaUseCase.crear(request.getPacienteId(), request.getProfesionalId(), request.getFechaHoraInicio(), request.getMotivoBreve(), request.getObservacionOperativa());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Cita creada correctamente.", CitaResponse.fromDomain(creada), CorrelationIdUtils.getCurrentCorrelationId()));
    }
    @GetMapping("/{citaId}")
    public ResponseEntity<ApiResponse<CitaResponse>> buscarPorId(@PathVariable Long citaId) {
        return ResponseEntity.ok(ApiResponse.success(CitaResponse.fromDomain(buscarCitaUseCase.buscarPorId(citaId)), CorrelationIdUtils.getCurrentCorrelationId()));
    }
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<AgendaItemResponse>>> listarAgenda(@RequestParam(required = false) Long pacienteId, @RequestParam(required = false) Long profesionalId, @RequestParam(required = false) EstadoCita estadoCita, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaDesde, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaHasta, @PageableDefault(size = 20) Pageable pageable) {
        Page<AgendaItemResponse> page = listarAgendaUseCase.listar(pacienteId, profesionalId, estadoCita, fechaDesde, fechaHasta, pageable).map(AgendaItemResponse::fromDomain);
        return ResponseEntity.ok(ApiResponse.success(PageUtils.fromPage(page), CorrelationIdUtils.getCurrentCorrelationId()));
    }
    @PatchMapping("/{citaId}/cancelar")
    public ResponseEntity<ApiResponse<CitaResponse>> cancelar(@PathVariable Long citaId, @Valid @RequestBody CancelarCitaRequest request) {
        Cita actualizada = cancelarCitaUseCase.cancelar(citaId, request.getObservacionOperativa());
        return ResponseEntity.ok(ApiResponse.success("Cita cancelada correctamente.", CitaResponse.fromDomain(actualizada), CorrelationIdUtils.getCurrentCorrelationId()));
    }
    @PatchMapping("/{citaId}/reprogramar")
    public ResponseEntity<ApiResponse<CitaResponse>> reprogramar(@PathVariable Long citaId, @Valid @RequestBody ReprogramarCitaRequest request) {
        Cita actualizada = reprogramarCitaUseCase.reprogramar(citaId, request.getNuevaFechaHoraInicio(), request.getObservacionOperativa());
        return ResponseEntity.ok(ApiResponse.success("Cita reprogramada correctamente.", CitaResponse.fromDomain(actualizada), CorrelationIdUtils.getCurrentCorrelationId()));
    }
}
