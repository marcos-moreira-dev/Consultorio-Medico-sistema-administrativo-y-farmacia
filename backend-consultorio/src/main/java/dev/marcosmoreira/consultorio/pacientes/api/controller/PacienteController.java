package dev.marcosmoreira.consultorio.pacientes.api.controller;

import dev.marcosmoreira.consultorio.pacientes.api.request.ActualizarPacienteRequest;
import dev.marcosmoreira.consultorio.pacientes.api.request.CrearPacienteRequest;
import dev.marcosmoreira.consultorio.pacientes.api.response.PacienteResponse;
import dev.marcosmoreira.consultorio.pacientes.api.response.PacienteResumenResponse;
import dev.marcosmoreira.consultorio.pacientes.application.port.in.ActualizarPacienteUseCase;
import dev.marcosmoreira.consultorio.pacientes.application.port.in.BuscarPacienteUseCase;
import dev.marcosmoreira.consultorio.pacientes.application.port.in.CrearPacienteUseCase;
import dev.marcosmoreira.consultorio.pacientes.application.port.in.ListarPacientesUseCase;
import dev.marcosmoreira.consultorio.pacientes.domain.model.Paciente;
import dev.marcosmoreira.consultorio.shared.util.CorrelationIdUtils;
import dev.marcosmoreira.consultorio.shared.util.PageUtils;
import dev.marcosmoreira.consultorio.shared.web.ApiResponse;
import dev.marcosmoreira.consultorio.shared.web.PageResponse;
import jakarta.validation.Valid;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasAnyRole('ADMIN_CONSULTORIO','OPERADOR_CONSULTORIO','PROFESIONAL_CONSULTORIO')")
@RequestMapping("/api/v1/pacientes")
public class PacienteController {
    private final CrearPacienteUseCase crearPacienteUseCase;
    private final ActualizarPacienteUseCase actualizarPacienteUseCase;
    private final BuscarPacienteUseCase buscarPacienteUseCase;
    private final ListarPacientesUseCase listarPacientesUseCase;

    public PacienteController(CrearPacienteUseCase crearPacienteUseCase, ActualizarPacienteUseCase actualizarPacienteUseCase, BuscarPacienteUseCase buscarPacienteUseCase, ListarPacientesUseCase listarPacientesUseCase) {
        this.crearPacienteUseCase = crearPacienteUseCase;
        this.actualizarPacienteUseCase = actualizarPacienteUseCase;
        this.buscarPacienteUseCase = buscarPacienteUseCase;
        this.listarPacientesUseCase = listarPacientesUseCase;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PacienteResponse>> crear(@Valid @RequestBody CrearPacienteRequest request) {
        Paciente creado = crearPacienteUseCase.crear(request.getNombres(), request.getApellidos(), request.getTelefono(), request.getCedula(), request.getFechaNacimiento(), request.getDireccionBasica());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Paciente creado correctamente.", PacienteResponse.fromDomain(creado), CorrelationIdUtils.getCurrentCorrelationId()));
    }

    @PutMapping("/{pacienteId}")
    public ResponseEntity<ApiResponse<PacienteResponse>> actualizar(@PathVariable Long pacienteId, @Valid @RequestBody ActualizarPacienteRequest request) {
        Paciente actualizado = actualizarPacienteUseCase.actualizar(pacienteId, request.getNombres(), request.getApellidos(), request.getTelefono(), request.getCedula(), request.getFechaNacimiento(), request.getDireccionBasica());
        return ResponseEntity.ok(ApiResponse.success("Paciente actualizado correctamente.", PacienteResponse.fromDomain(actualizado), CorrelationIdUtils.getCurrentCorrelationId()));
    }

    @GetMapping("/{pacienteId}")
    public ResponseEntity<ApiResponse<PacienteResponse>> buscarPorId(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(ApiResponse.success(PacienteResponse.fromDomain(buscarPacienteUseCase.buscarPorId(pacienteId)), CorrelationIdUtils.getCurrentCorrelationId()));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<PacienteResumenResponse>>> listar(@RequestParam(required = false) String cedula, @RequestParam(required = false) LocalDate fechaNacimiento, @RequestParam(required = false) String q, @PageableDefault(size = 20) Pageable pageable) {
        Page<PacienteResumenResponse> page = listarPacientesUseCase.listar(cedula, fechaNacimiento, q, pageable).map(PacienteResumenResponse::fromDomain);
        return ResponseEntity.ok(ApiResponse.success(PageUtils.fromPage(page), CorrelationIdUtils.getCurrentCorrelationId()));
    }
}
