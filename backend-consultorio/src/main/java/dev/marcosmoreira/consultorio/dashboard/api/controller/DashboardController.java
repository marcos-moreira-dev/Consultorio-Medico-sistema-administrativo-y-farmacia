package dev.marcosmoreira.consultorio.dashboard.api.controller;

import dev.marcosmoreira.consultorio.dashboard.api.response.DashboardResumenResponse;
import dev.marcosmoreira.consultorio.dashboard.application.port.in.ObtenerDashboardResumenUseCase;
import dev.marcosmoreira.consultorio.shared.util.CorrelationIdUtils;
import dev.marcosmoreira.consultorio.shared.web.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAnyRole('ADMIN_CONSULTORIO','OPERADOR_CONSULTORIO','PROFESIONAL_CONSULTORIO')")
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    private final ObtenerDashboardResumenUseCase obtenerDashboardResumenUseCase;

    public DashboardController(ObtenerDashboardResumenUseCase obtenerDashboardResumenUseCase) {
        this.obtenerDashboardResumenUseCase = obtenerDashboardResumenUseCase;
    }

    @GetMapping("/resumen")
    public ResponseEntity<ApiResponse<DashboardResumenResponse>> obtenerResumen() {
        DashboardResumenResponse resumen = obtenerDashboardResumenUseCase.obtenerResumen();
        return ResponseEntity.ok(ApiResponse.success("Resumen operativo del consultorio.", resumen, CorrelationIdUtils.getCurrentCorrelationId()));
    }
}
