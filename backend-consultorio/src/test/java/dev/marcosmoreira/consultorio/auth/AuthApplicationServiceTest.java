package dev.marcosmoreira.consultorio.auth;

import dev.marcosmoreira.consultorio.auth.api.response.LoginResponse;
import dev.marcosmoreira.consultorio.auth.api.response.MeResponse;
import dev.marcosmoreira.consultorio.auth.application.port.out.LoadAuthUserPort;
import dev.marcosmoreira.consultorio.auth.application.service.AuthApplicationService;
import dev.marcosmoreira.consultorio.auth.domain.exception.InvalidCredentialsException;
import dev.marcosmoreira.consultorio.auth.domain.model.AuthUser;
import dev.marcosmoreira.consultorio.shared.security.JwtTokenService;
import dev.marcosmoreira.consultorio.shared.security.LoginRateLimiter;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias del servicio de aplicación del módulo de autenticación.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
class AuthApplicationServiceTest {

    @Mock
    private LoadAuthUserPort loadAuthUserPort;

    @Mock
    private JwtTokenService jwtTokenService;

    @Mock
    private LoginRateLimiter loginRateLimiter;

    private AuthApplicationService authApplicationService;

    /**
     * Inicializa el servicio bajo prueba antes de cada test.
     */
    @BeforeEach
    void setUp() {
        authApplicationService = new AuthApplicationService(loadAuthUserPort, jwtTokenService, loginRateLimiter);
    }

    /**
     * Verifica que el login autentique, genere token y devuelva
     * la respuesta completa esperada.
     */
    @Test
    void login_deberiaAutenticarYGenerarToken() {
        AuthUser authUser = new AuthUser(
                1L,
                "admin",
                "Administrador consultorio General",
                "ADMIN_CONSULTORIO",
                "Administrador consultorio",
                true
        );

        when(loadAuthUserPort.authenticateAndLoad("admin", "123456"))
                .thenReturn(authUser);
        when(jwtTokenService.generateAccessToken(authUser))
                .thenReturn("jwt-token-demo");
        when(jwtTokenService.getAccessTokenExpirationSeconds())
                .thenReturn(3600L);

        LoginResponse response = authApplicationService.login(" admin ", "123456");

        assertNotNull(response);
        assertEquals("jwt-token-demo", response.getAccessToken());
        assertEquals("Bearer", response.getTokenType());
        assertEquals(3600L, response.getExpiresInSeconds());

        assertNotNull(response.getUsuario());
        assertEquals(1L, response.getUsuario().getUsuarioId());
        assertEquals("admin", response.getUsuario().getUsername());
        assertEquals("ADMIN_CONSULTORIO", response.getUsuario().getRolCodigo());

        verify(loadAuthUserPort).authenticateAndLoad("admin", "123456");
        verify(jwtTokenService).generateAccessToken(authUser);
        verify(jwtTokenService).getAccessTokenExpirationSeconds();
    }

    /**
     * Verifica que el servicio rechace usernames vacíos antes de intentar autenticar.
     */
    @Test
    void login_deberiaLanzarExcepcionSiUsernameEsInvalido() {
        InvalidCredentialsException exception = assertThrows(
                InvalidCredentialsException.class,
                () -> authApplicationService.login("   ", "123456")
        );

        assertEquals("El username es obligatorio.", exception.getMessage());
        verifyNoInteractions(loadAuthUserPort);
        verifyNoInteractions(jwtTokenService);
    }

    /**
     * Verifica que el servicio recupere correctamente al usuario autenticado actual.
     */
    @Test
    void getCurrentUser_deberiaRetornarUsuarioAutenticado() {
        AuthUser authUser = new AuthUser(
                10L,
                "recepcion1",
                "Operador consultorio Principal",
                "OPERADOR_CONSULTORIO",
                "Operador consultorio",
                true
        );

        when(loadAuthUserPort.getCurrentAuthenticatedUser())
                .thenReturn(Optional.of(authUser));

        MeResponse response = authApplicationService.getCurrentUser();

        assertNotNull(response);
        assertEquals(10L, response.getUsuarioId());
        assertEquals("recepcion1", response.getUsername());
        assertEquals("OPERADOR_CONSULTORIO", response.getRolCodigo());

        verify(loadAuthUserPort).getCurrentAuthenticatedUser();
    }

    /**
     * Verifica que el servicio falle si no existe usuario autenticado actual.
     */
    @Test
    void getCurrentUser_deberiaLanzarExcepcionSiNoHayUsuarioAutenticado() {
        when(loadAuthUserPort.getCurrentAuthenticatedUser())
                .thenReturn(Optional.empty());

        InvalidCredentialsException exception = assertThrows(
                InvalidCredentialsException.class,
                () -> authApplicationService.getCurrentUser()
        );

        assertEquals(
                "No existe un usuario autenticado en el contexto actual.",
                exception.getMessage()
        );

        verify(loadAuthUserPort).getCurrentAuthenticatedUser();
    }
}
