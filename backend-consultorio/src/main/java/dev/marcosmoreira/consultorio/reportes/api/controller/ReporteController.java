package dev.marcosmoreira.consultorio.reportes.api.controller;

import dev.marcosmoreira.consultorio.reportes.api.request.GenerarReporteRequest;
import dev.marcosmoreira.consultorio.reportes.api.response.ReporteGeneradoResponse;
import dev.marcosmoreira.consultorio.reportes.application.port.in.GenerarReporteUseCase;
import dev.marcosmoreira.consultorio.shared.util.CorrelationIdUtils;
import dev.marcosmoreira.consultorio.shared.web.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasAnyRole('ADMIN_CONSULTORIO','OPERADOR_CONSULTORIO')")
@RequestMapping("/api/v1/reportes")
public class ReporteController {
    private final GenerarReporteUseCase generarReporteUseCase;
    public ReporteController(GenerarReporteUseCase generarReporteUseCase) { this.generarReporteUseCase = generarReporteUseCase; }
    @PostMapping("/generar")
    public ResponseEntity<ApiResponse<ReporteGeneradoResponse>> generar(@Valid @RequestBody GenerarReporteRequest request) {
        ReporteGeneradoResponse response = generarReporteUseCase.generar(request.getTipoReporte(), request.getNombreBaseArchivo(), request.getTitulo(), request.getPacienteId(), request.getProfesionalId(), request.getFechaDesde(), request.getFechaHasta());
        return ResponseEntity.ok(ApiResponse.success("Reporte generado correctamente.", response, CorrelationIdUtils.getCurrentCorrelationId()));
    }
}
