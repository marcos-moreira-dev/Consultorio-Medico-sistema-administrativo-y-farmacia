package dev.marcosmoreira.desktopconsultorio.http.service;

import dev.marcosmoreira.desktopconsultorio.app.ConsultorioDesktopApi;
import dev.marcosmoreira.desktopconsultorio.http.dto.auth.LoginRequestDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.auth.LoginResponseDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.auth.MeResponseDto;

/**
 * Servicio de autenticación contra el backend del consultorio.
 *
 * <p>Centraliza las llamadas a los endpoints de login y verificación de sesión,
 * delegando la lógica de HTTP a {@link ConsultorioDesktopApi}.</p>
 */
public class AuthApiService {

    private final ConsultorioDesktopApi api;

    public AuthApiService(ConsultorioDesktopApi api) {
        this.api = api;
    }

    /**
     * Intenta iniciar sesión con las credenciales proporcionadas.
     *
     * @param request credenciales (username + password)
     * @return respuesta con token JWT y datos del usuario, o null si falla
     */
    public LoginResponseDto login(LoginRequestDto request) {
        return api.login(request);
    }

    /**
     * Obtiene el resumen del usuario actualmente autenticado.
     *
     * @return datos del usuario o null si no hay sesión activa
     */
    public MeResponseDto fetchCurrentUser() {
        return api.fetchCurrentUser();
    }

    /**
     * Verifica si el backend está respondiendo.
     */
    public boolean pingBackend() {
        return api.pingBackend();
    }
}
