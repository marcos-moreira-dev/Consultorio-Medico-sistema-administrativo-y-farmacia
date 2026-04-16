package dev.marcosmoreira.consultorio.auth.api.controller;

import dev.marcosmoreira.consultorio.auth.api.request.LoginRequest;
import dev.marcosmoreira.consultorio.auth.api.response.LoginResponse;
import dev.marcosmoreira.consultorio.auth.api.response.MeResponse;
import dev.marcosmoreira.consultorio.auth.application.port.in.GetCurrentUserUseCase;
import dev.marcosmoreira.consultorio.auth.application.port.in.LoginUseCase;
import dev.marcosmoreira.consultorio.shared.util.CorrelationIdUtils;
import dev.marcosmoreira.consultorio.shared.web.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final GetCurrentUserUseCase getCurrentUserUseCase;

    public AuthController(LoginUseCase loginUseCase, GetCurrentUserUseCase getCurrentUserUseCase) {
        this.loginUseCase = loginUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = loginUseCase.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(ApiResponse.success("Inicio de sesión exitoso.", response, CorrelationIdUtils.getCurrentCorrelationId()));
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ADMIN_CONSULTORIO','OPERADOR_CONSULTORIO','PROFESIONAL_CONSULTORIO')")
    public ResponseEntity<ApiResponse<MeResponse>> me() {
        return ResponseEntity.ok(ApiResponse.success(getCurrentUserUseCase.getCurrentUser(), CorrelationIdUtils.getCurrentCorrelationId()));
    }
}
