package dev.marcosmoreira.consultorio.atenciones.api.controller;

import dev.marcosmoreira.consultorio.atenciones.api.request.CrearAtencionRequest;
import dev.marcosmoreira.consultorio.atenciones.api.response.AtencionResponse;
import dev.marcosmoreira.consultorio.atenciones.application.port.in.BuscarAtencionUseCase;
import dev.marcosmoreira.consultorio.atenciones.application.port.in.CrearAtencionUseCase;
import dev.marcosmoreira.consultorio.atenciones.application.port.in.ListarAtencionesUseCase;
import dev.marcosmoreira.consultorio.atenciones.domain.model.Atencion;
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
@RequestMapping("/api/v1/atenciones")
public class AtencionController {
    private final CrearAtencionUseCase crearAtencionUseCase;
    private final BuscarAtencionUseCase buscarAtencionUseCase;
    private final ListarAtencionesUseCase listarAtencionesUseCase;
    public AtencionController(CrearAtencionUseCase crearAtencionUseCase, BuscarAtencionUseCase buscarAtencionUseCase, ListarAtencionesUseCase listarAtencionesUseCase) { this.crearAtencionUseCase = crearAtencionUseCase; this.buscarAtencionUseCase = buscarAtencionUseCase; this.listarAtencionesUseCase = listarAtencionesUseCase; }
    @PostMapping
    public ResponseEntity<ApiResponse<AtencionResponse>> registrarAtencion(@Valid @RequestBody CrearAtencionRequest request) {
        Atencion atencion = new Atencion();
        atencion.setPacienteId(request.getPacienteId());
        atencion.setProfesionalId(request.getProfesionalId());
        atencion.setCitaId(request.getCitaId());
        atencion.setFechaHoraAtencion(request.getFechaHoraAtencion());
        atencion.setNotaBreve(request.getNotaBreve());
        atencion.setIndicacionesBreves(request.getIndicacionesBreves());
        Atencion creada = crearAtencionUseCase.crear(atencion);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Atención registrada correctamente.", AtencionResponse.fromDomain(creada), CorrelationIdUtils.getCurrentCorrelationId()));
    }
    @GetMapping("/{atencionId}")
    public ResponseEntity<ApiResponse<AtencionResponse>> buscarAtencionPorId(@PathVariable Long atencionId) {
        return ResponseEntity.ok(ApiResponse.success(AtencionResponse.fromDomain(buscarAtencionUseCase.buscarPorId(atencionId)), CorrelationIdUtils.getCurrentCorrelationId()));
    }
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<AtencionResponse>>> listarAtenciones(@RequestParam(required = false) Long pacienteId, @RequestParam(required = false) Long profesionalId, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaDesde, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaHasta, @PageableDefault(size = 20) Pageable pageable) {
        Page<AtencionResponse> page = listarAtencionesUseCase.listar(pacienteId, profesionalId, fechaDesde, fechaHasta, pageable).map(AtencionResponse::fromDomain);
        return ResponseEntity.ok(ApiResponse.success(PageUtils.fromPage(page), CorrelationIdUtils.getCurrentCorrelationId()));
    }
}
