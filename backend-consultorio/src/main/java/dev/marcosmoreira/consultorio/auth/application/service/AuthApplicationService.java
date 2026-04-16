package dev.marcosmoreira.consultorio.auth.application.service;

import dev.marcosmoreira.consultorio.auth.api.response.LoginResponse;
import dev.marcosmoreira.consultorio.auth.api.response.MeResponse;
import dev.marcosmoreira.consultorio.auth.application.port.in.GetCurrentUserUseCase;
import dev.marcosmoreira.consultorio.auth.application.port.in.LoginUseCase;
import dev.marcosmoreira.consultorio.auth.application.port.out.LoadAuthUserPort;
import dev.marcosmoreira.consultorio.auth.domain.exception.InvalidCredentialsException;
import dev.marcosmoreira.consultorio.auth.domain.model.AuthUser;
import dev.marcosmoreira.consultorio.shared.security.JwtTokenService;
import dev.marcosmoreira.consultorio.shared.security.LoginRateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio de aplicación del módulo de autenticación.
 *
 * <p>Coordina los casos de uso principales de autenticación en la versión 1.0:</p>
 *
 * <ul>
 *   <li>autenticar credenciales e iniciar sesión;</li>
 *   <li>obtener el usuario autenticado actual.</li>
 * </ul>
 *
 * <p>La lógica de seguridad concreta no vive aquí. Esta clase delega la
 * autenticación al puerto de salida y la emisión del token al servicio JWT.
 * Además, aplica rate limiting para prevenir fuerza bruta: tras 5 intentos
 * fallidos en 5 minutos, el username queda bloqueado temporalmente.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
public class AuthApplicationService implements LoginUseCase, GetCurrentUserUseCase {

    private static final Logger log = LoggerFactory.getLogger(AuthApplicationService.class);

    /**
     * Rate limiter configurado con 5 intentos máximos en ventana de 5 minutos.
     * Este bean se declara en la configuración de seguridad del módulo.
     */
    private final LoginRateLimiter loginRateLimiter;

    private final LoadAuthUserPort loadAuthUserPort;
    private final JwtTokenService jwtTokenService;

    /**
     * Construye el servicio de aplicación del módulo de autenticación.
     *
     * @param loadAuthUserPort puerto de salida para autenticar y cargar el usuario
     * @param jwtTokenService servicio encargado de emitir tokens JWT
     * @param loginRateLimiter servicio de rate limiting para login (inyectado como opcional)
     */
    public AuthApplicationService(
            LoadAuthUserPort loadAuthUserPort,
            JwtTokenService jwtTokenService,
            @Lazy LoginRateLimiter loginRateLimiter
    ) {
        this.loadAuthUserPort = loadAuthUserPort;
        this.jwtTokenService = jwtTokenService;
        this.loginRateLimiter = loginRateLimiter;
    }

    /**
     * Autentica al usuario y genera el token de acceso correspondiente.
     *
     * <p>Antes de intentar la autenticación, verifica que el username no esté
     * bloqueado por rate limiting. Si los credenciales son incorrectos,
     * registra el intento fallido. Si son correctos, limpia el historial.</p>
     *
     * @param username nombre de usuario
     * @param rawPassword contraseña en texto plano
     * @return respuesta de login con token y usuario autenticado
     * @throws InvalidCredentialsException si las credenciales son inválidas o el usuario está bloqueado
     */
    @Override
    @Transactional
    public LoginResponse login(String username, String rawPassword) {
        String normalizedUsername = normalizeRequiredText(username, "El username es obligatorio.");
        validateRequiredText(rawPassword, "La contraseña es obligatoria.");

        /*
         * Verificar rate limiting antes de intentar autenticación.
         * Esto previene ataques de fuerza bruta limitando a 5 intentos
         * fallidos por ventana de 5 minutos.
         */
        if (loginRateLimiter.isBlocked(normalizedUsername)) {
            throw new InvalidCredentialsException(
                    "Demasiados intentos fallidos. Espera unos minutos antes de volver a intentarlo."
            );
        }

        try {
            AuthUser authUser = loadAuthUserPort.authenticateAndLoad(
                    normalizedUsername,
                    rawPassword
            );

            /*
             * Login exitoso: limpiar historial de intentos fallidos
             * para permitir nuevos intentos en el futuro.
             */
            loginRateLimiter.clearAttempts(normalizedUsername);

            String accessToken = jwtTokenService.generateAccessToken(authUser);
            long expiresInSeconds = jwtTokenService.getAccessTokenExpirationSeconds();

            return LoginResponse.of(authUser, accessToken, expiresInSeconds);
        } catch (InvalidCredentialsException e) {
            /*
             * Login fallido: registrar el intento para rate limiting.
             * Re-lanzamos la misma excepción para que el controller
             * devuelva el 401 esperado.
             */
            loginRateLimiter.recordFailedAttempt(normalizedUsername);
            log.warn("Intento fallido de login para '{}': {}", normalizedUsername, e.getMessage());
            throw e;
        }
    }

    /**
     * Recupera el usuario autenticado actual desde el contexto de seguridad.
     *
     * @return respuesta con los datos básicos del usuario autenticado
     * @throws InvalidCredentialsException si no existe un usuario autenticado válido
     */
    @Override
    public MeResponse getCurrentUser() {
        AuthUser authUser = loadAuthUserPort.getCurrentAuthenticatedUser()
                .orElseThrow(() -> new InvalidCredentialsException(
                        "No existe un usuario autenticado en el contexto actual."
                ));

        return MeResponse.fromDomain(authUser);
    }

    /**
     * Valida que un texto obligatorio contenga información útil.
     *
     * @param value texto a validar
     * @param message mensaje de error
     */
    private void validateRequiredText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidCredentialsException(message);
        }
    }

    /**
     * Normaliza un texto obligatorio aplicando trim y validación básica.
     *
     * @param value texto a normalizar
     * @param message mensaje de error en caso de ausencia
     * @return texto normalizado
     */
    private String normalizeRequiredText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidCredentialsException(message);
        }

        return value.trim();
    }
}
