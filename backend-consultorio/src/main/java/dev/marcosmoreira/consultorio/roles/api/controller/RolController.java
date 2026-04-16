package dev.marcosmoreira.consultorio.roles.api.controller;

import dev.marcosmoreira.consultorio.roles.api.response.RolResponse;
import dev.marcosmoreira.consultorio.roles.api.response.RolResumenResponse;
import dev.marcosmoreira.consultorio.roles.application.port.in.BuscarRolUseCase;
import dev.marcosmoreira.consultorio.roles.application.port.in.ListarRolesUseCase;
import dev.marcosmoreira.consultorio.shared.util.CorrelationIdUtils;
import dev.marcosmoreira.consultorio.shared.web.ApiResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ADMIN_CONSULTORIO')")
@RequestMapping("/api/v1/roles")
public class RolController {
    private final BuscarRolUseCase buscarRolUseCase;
    private final ListarRolesUseCase listarRolesUseCase;
    public RolController(BuscarRolUseCase buscarRolUseCase, ListarRolesUseCase listarRolesUseCase) { this.buscarRolUseCase = buscarRolUseCase; this.listarRolesUseCase = listarRolesUseCase; }
    @GetMapping("/{rolId}")
    public ResponseEntity<ApiResponse<RolResponse>> buscarPorId(@PathVariable Long rolId) { return ResponseEntity.ok(ApiResponse.success(RolResponse.fromDomain(buscarRolUseCase.buscarPorId(rolId)), CorrelationIdUtils.getCurrentCorrelationId())); }
    @GetMapping
    public ResponseEntity<ApiResponse<List<RolResumenResponse>>> listarRoles() {
        List<RolResumenResponse> data = listarRolesUseCase.listar().stream().map(RolResumenResponse::fromDomain).toList();
        return ResponseEntity.ok(ApiResponse.success(data, CorrelationIdUtils.getCurrentCorrelationId()));
    }
}
