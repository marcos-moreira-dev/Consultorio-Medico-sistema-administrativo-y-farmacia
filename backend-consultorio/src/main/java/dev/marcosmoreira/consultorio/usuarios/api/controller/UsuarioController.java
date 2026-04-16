package dev.marcosmoreira.consultorio.usuarios.api.controller;

import dev.marcosmoreira.consultorio.shared.util.CorrelationIdUtils;
import dev.marcosmoreira.consultorio.shared.util.PageUtils;
import dev.marcosmoreira.consultorio.shared.web.ApiResponse;
import dev.marcosmoreira.consultorio.shared.web.PageResponse;
import dev.marcosmoreira.consultorio.usuarios.api.request.CambiarEstadoUsuarioRequest;
import dev.marcosmoreira.consultorio.usuarios.api.request.CrearUsuarioRequest;
import dev.marcosmoreira.consultorio.usuarios.api.request.ResetPasswordUsuarioRequest;
import dev.marcosmoreira.consultorio.usuarios.api.response.UsuarioResponse;
import dev.marcosmoreira.consultorio.usuarios.api.response.UsuarioResumenResponse;
import dev.marcosmoreira.consultorio.usuarios.application.port.in.BuscarUsuarioUseCase;
import dev.marcosmoreira.consultorio.usuarios.application.port.in.CambiarEstadoUsuarioUseCase;
import dev.marcosmoreira.consultorio.usuarios.application.port.in.CrearUsuarioUseCase;
import dev.marcosmoreira.consultorio.usuarios.application.port.in.ListarUsuariosUseCase;
import dev.marcosmoreira.consultorio.usuarios.application.port.in.ResetPasswordUsuarioUseCase;
import dev.marcosmoreira.consultorio.usuarios.domain.enums.EstadoUsuario;
import dev.marcosmoreira.consultorio.usuarios.domain.model.Usuario;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ADMIN_CONSULTORIO')")
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    private final CrearUsuarioUseCase crearUsuarioUseCase;
    private final BuscarUsuarioUseCase buscarUsuarioUseCase;
    private final ListarUsuariosUseCase listarUsuariosUseCase;
    private final CambiarEstadoUsuarioUseCase cambiarEstadoUsuarioUseCase;
    private final ResetPasswordUsuarioUseCase resetPasswordUsuarioUseCase;
    public UsuarioController(CrearUsuarioUseCase crearUsuarioUseCase, BuscarUsuarioUseCase buscarUsuarioUseCase, ListarUsuariosUseCase listarUsuariosUseCase, CambiarEstadoUsuarioUseCase cambiarEstadoUsuarioUseCase, ResetPasswordUsuarioUseCase resetPasswordUsuarioUseCase) { this.crearUsuarioUseCase = crearUsuarioUseCase; this.buscarUsuarioUseCase = buscarUsuarioUseCase; this.listarUsuariosUseCase = listarUsuariosUseCase; this.cambiarEstadoUsuarioUseCase = cambiarEstadoUsuarioUseCase; this.resetPasswordUsuarioUseCase = resetPasswordUsuarioUseCase; }
    @PostMapping
    public ResponseEntity<ApiResponse<UsuarioResponse>> crearUsuario(@Valid @RequestBody CrearUsuarioRequest request) {
        Usuario usuarioCreado = crearUsuarioUseCase.crear(request.getRolId(), request.getUsername(), request.getPasswordTemporal());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Usuario creado correctamente.", UsuarioResponse.fromDomain(usuarioCreado), CorrelationIdUtils.getCurrentCorrelationId()));
    }
    @GetMapping("/{usuarioId}")
    public ResponseEntity<ApiResponse<UsuarioResponse>> buscarPorId(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(ApiResponse.success(UsuarioResponse.fromDomain(buscarUsuarioUseCase.buscarPorId(usuarioId)), CorrelationIdUtils.getCurrentCorrelationId()));
    }
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<UsuarioResumenResponse>>> listarUsuarios(@RequestParam(required = false) Long rolId, @RequestParam(required = false) EstadoUsuario estado, @RequestParam(required = false) String q, @PageableDefault(size = 20) Pageable pageable) {
        Page<UsuarioResumenResponse> page = listarUsuariosUseCase.listar(rolId, estado, q, pageable).map(UsuarioResumenResponse::fromDomain);
        return ResponseEntity.ok(ApiResponse.success(PageUtils.fromPage(page), CorrelationIdUtils.getCurrentCorrelationId()));
    }
    @PatchMapping("/{usuarioId}/estado")
    public ResponseEntity<ApiResponse<UsuarioResponse>> cambiarEstado(@PathVariable Long usuarioId, @Valid @RequestBody CambiarEstadoUsuarioRequest request) {
        Usuario usuarioActualizado = cambiarEstadoUsuarioUseCase.cambiarEstado(usuarioId, request.getNuevoEstado());
        return ResponseEntity.ok(ApiResponse.success("Estado de usuario actualizado correctamente.", UsuarioResponse.fromDomain(usuarioActualizado), CorrelationIdUtils.getCurrentCorrelationId()));
    }
    @PatchMapping("/{usuarioId}/reset-password")
    public ResponseEntity<ApiResponse<UsuarioResponse>> resetPassword(@PathVariable Long usuarioId, @Valid @RequestBody ResetPasswordUsuarioRequest request) {
        Usuario usuarioActualizado = resetPasswordUsuarioUseCase.resetPassword(usuarioId, request.getNuevoPassword());
        return ResponseEntity.ok(ApiResponse.success("Contraseña reseteada correctamente.", UsuarioResponse.fromDomain(usuarioActualizado), CorrelationIdUtils.getCurrentCorrelationId()));
    }
}
