package dev.marcosmoreira.consultorio.cobros.api.controller;

import dev.marcosmoreira.consultorio.cobros.api.request.RegistrarCobroRequest;
import dev.marcosmoreira.consultorio.cobros.api.response.CobroResponse;
import dev.marcosmoreira.consultorio.cobros.application.port.in.BuscarCobroUseCase;
import dev.marcosmoreira.consultorio.cobros.application.port.in.ListarCobrosUseCase;
import dev.marcosmoreira.consultorio.cobros.application.port.in.RegistrarCobroUseCase;
import dev.marcosmoreira.consultorio.cobros.domain.enums.EstadoCobro;
import dev.marcosmoreira.consultorio.cobros.domain.enums.MetodoPago;
import dev.marcosmoreira.consultorio.cobros.domain.model.Cobro;
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
@PreAuthorize("hasAnyRole('ADMIN_CONSULTORIO','OPERADOR_CONSULTORIO')")
@RequestMapping("/api/v1/cobros")
public class CobroController {
    private final RegistrarCobroUseCase registrarCobroUseCase;
    private final BuscarCobroUseCase buscarCobroUseCase;
    private final ListarCobrosUseCase listarCobrosUseCase;
    public CobroController(RegistrarCobroUseCase registrarCobroUseCase, BuscarCobroUseCase buscarCobroUseCase, ListarCobrosUseCase listarCobrosUseCase) { this.registrarCobroUseCase = registrarCobroUseCase; this.buscarCobroUseCase = buscarCobroUseCase; this.listarCobrosUseCase = listarCobrosUseCase; }
    @PostMapping
    public ResponseEntity<ApiResponse<CobroResponse>> registrar(@Valid @RequestBody RegistrarCobroRequest request) {
        Cobro cobro = registrarCobroUseCase.registrar(request.getAtencionId(), request.getRegistradoPorUsuarioId(), request.getMonto(), request.getMetodoPago(), request.getEstadoCobro(), request.getObservacionAdministrativa());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Cobro registrado correctamente.", CobroResponse.fromDomain(cobro), CorrelationIdUtils.getCurrentCorrelationId()));
    }
    @GetMapping("/{cobroId}")
    public ResponseEntity<ApiResponse<CobroResponse>> buscarPorId(@PathVariable Long cobroId) {
        return ResponseEntity.ok(ApiResponse.success(CobroResponse.fromDomain(buscarCobroUseCase.buscarPorId(cobroId)), CorrelationIdUtils.getCurrentCorrelationId()));
    }
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CobroResponse>>> listar(@RequestParam(required = false) Long atencionId, @RequestParam(required = false) Long registradoPorUsuarioId, @RequestParam(required = false) EstadoCobro estadoCobro, @RequestParam(required = false) MetodoPago metodoPago, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaDesde, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaHasta, @PageableDefault(size = 20) Pageable pageable) {
        Page<CobroResponse> page = listarCobrosUseCase.listar(atencionId, registradoPorUsuarioId, estadoCobro, metodoPago, fechaDesde, fechaHasta, pageable).map(CobroResponse::fromDomain);
        return ResponseEntity.ok(ApiResponse.success(PageUtils.fromPage(page), CorrelationIdUtils.getCurrentCorrelationId()));
    }
}
