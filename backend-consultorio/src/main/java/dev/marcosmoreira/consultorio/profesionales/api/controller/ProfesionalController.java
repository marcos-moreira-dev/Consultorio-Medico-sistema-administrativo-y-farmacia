package dev.marcosmoreira.consultorio.profesionales.api.controller;

import dev.marcosmoreira.consultorio.profesionales.api.request.ActualizarProfesionalRequest;
import dev.marcosmoreira.consultorio.profesionales.api.request.CambiarEstadoProfesionalRequest;
import dev.marcosmoreira.consultorio.profesionales.api.request.CrearProfesionalRequest;
import dev.marcosmoreira.consultorio.profesionales.api.response.ProfesionalResponse;
import dev.marcosmoreira.consultorio.profesionales.api.response.ProfesionalResumenResponse;
import dev.marcosmoreira.consultorio.profesionales.application.port.in.*;
import dev.marcosmoreira.consultorio.profesionales.domain.enums.EstadoProfesional;
import dev.marcosmoreira.consultorio.profesionales.domain.model.Profesional;
import dev.marcosmoreira.consultorio.shared.util.CorrelationIdUtils;
import dev.marcosmoreira.consultorio.shared.util.PageUtils;
import dev.marcosmoreira.consultorio.shared.web.ApiResponse;
import dev.marcosmoreira.consultorio.shared.web.PageResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasAnyRole('ADMIN_CONSULTORIO','OPERADOR_CONSULTORIO','PROFESIONAL_CONSULTORIO')")
@RequestMapping("/api/v1/profesionales")
public class ProfesionalController {
    private final CrearProfesionalUseCase crearProfesionalUseCase;
    private final ActualizarProfesionalUseCase actualizarProfesionalUseCase;
    private final BuscarProfesionalUseCase buscarProfesionalUseCase;
    private final ListarProfesionalesUseCase listarProfesionalesUseCase;
    private final CambiarEstadoProfesionalUseCase cambiarEstadoProfesionalUseCase;
    public ProfesionalController(CrearProfesionalUseCase crearProfesionalUseCase, ActualizarProfesionalUseCase actualizarProfesionalUseCase, BuscarProfesionalUseCase buscarProfesionalUseCase, ListarProfesionalesUseCase listarProfesionalesUseCase, CambiarEstadoProfesionalUseCase cambiarEstadoProfesionalUseCase) {
        this.crearProfesionalUseCase = crearProfesionalUseCase; this.actualizarProfesionalUseCase = actualizarProfesionalUseCase; this.buscarProfesionalUseCase = buscarProfesionalUseCase; this.listarProfesionalesUseCase = listarProfesionalesUseCase; this.cambiarEstadoProfesionalUseCase = cambiarEstadoProfesionalUseCase;
    }
    @PostMapping
    public ResponseEntity<ApiResponse<ProfesionalResponse>> crear(@Valid @RequestBody CrearProfesionalRequest request) {
        Profesional creado = crearProfesionalUseCase.crear(request.getUsuarioId(), request.getNombres(), request.getApellidos(), request.getEspecialidadBreve(), request.getRegistroProfesional());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Profesional creado correctamente.", ProfesionalResponse.fromDomain(creado), CorrelationIdUtils.getCurrentCorrelationId()));
    }
    @PutMapping("/{profesionalId}")
    public ResponseEntity<ApiResponse<ProfesionalResponse>> actualizar(@PathVariable Long profesionalId, @Valid @RequestBody ActualizarProfesionalRequest request) {
        Profesional actualizado = actualizarProfesionalUseCase.actualizar(profesionalId, request.getUsuarioId(), request.getNombres(), request.getApellidos(), request.getEspecialidadBreve(), request.getRegistroProfesional());
        return ResponseEntity.ok(ApiResponse.success("Profesional actualizado correctamente.", ProfesionalResponse.fromDomain(actualizado), CorrelationIdUtils.getCurrentCorrelationId()));
    }
    @GetMapping("/{profesionalId}")
    public ResponseEntity<ApiResponse<ProfesionalResponse>> buscarPorId(@PathVariable Long profesionalId) {
        return ResponseEntity.ok(ApiResponse.success(ProfesionalResponse.fromDomain(buscarProfesionalUseCase.buscarPorId(profesionalId)), CorrelationIdUtils.getCurrentCorrelationId()));
    }
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ProfesionalResumenResponse>>> listar(@RequestParam(required = false) Long usuarioId, @RequestParam(required = false) EstadoProfesional estadoProfesional, @RequestParam(required = false) String q, @PageableDefault(size = 20) Pageable pageable) {
        Page<ProfesionalResumenResponse> page = listarProfesionalesUseCase.listar(usuarioId, estadoProfesional, q, pageable).map(ProfesionalResumenResponse::fromDomain);
        return ResponseEntity.ok(ApiResponse.success(PageUtils.fromPage(page), CorrelationIdUtils.getCurrentCorrelationId()));
    }
    @PatchMapping("/{profesionalId}/estado")
    public ResponseEntity<ApiResponse<ProfesionalResponse>> cambiarEstado(@PathVariable Long profesionalId, @Valid @RequestBody CambiarEstadoProfesionalRequest request) {
        Profesional actualizado = cambiarEstadoProfesionalUseCase.cambiarEstado(profesionalId, request.getNuevoEstado());
        return ResponseEntity.ok(ApiResponse.success("Estado de profesional actualizado correctamente.", ProfesionalResponse.fromDomain(actualizado), CorrelationIdUtils.getCurrentCorrelationId()));
    }
}
