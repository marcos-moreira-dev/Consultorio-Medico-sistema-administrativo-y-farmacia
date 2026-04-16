package dev.marcosmoreira.consultorio.shared.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Servicio de rate limiting básico para prevenir ataques de fuerza bruta
 * contra el endpoint de login.
 *
 * <p>Implementa un sliding window simple: por cada username, se registran
 * los timestamps de los intentos fallidos. Si un usuario acumula más de
 * {@code maxAttempts} intentos fallidos dentro de una ventana de tiempo
 * ({@code windowSeconds}), se bloquean nuevos intentos hasta que la
 * ventana expire.</p>
 *
 * <p>Esta implementación usa {@link ConcurrentHashMap} en memoria, por lo
 * que es efectiva solo para una única instancia de la aplicación. Para
 * despliegues distribuidos, se recomendaría Redis o similar.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class LoginRateLimiter {

    private static final Logger log = LoggerFactory.getLogger(LoginRateLimiter.class);

    /** Máximo de intentos fallidos permitidos dentro de la ventana. */
    private final int maxAttempts;

    /** Duración de la ventana en segundos. */
    private final int windowSeconds;

    /**
     * Mapa de intentos fallidos por username.
     * Key = username, Value = lista de timestamps de intentos fallidos.
     */
    private final Map<String, java.util.List<Instant>> failedAttempts = new ConcurrentHashMap<>();

    /**
     * Crea un rate limiter con configuración personalizada.
     *
     * @param maxAttempts máximo de intentos fallidos antes de bloquear
     * @param windowSeconds duración de la ventana en segundos
     */
    public LoginRateLimiter(int maxAttempts, int windowSeconds) {
        this.maxAttempts = maxAttempts;
        this.windowSeconds = windowSeconds;
        log.info("LoginRateLimiter inicial: maxAttempts={}, window={}s", maxAttempts, windowSeconds);
    }

    /**
     * Crea un rate limiter con los valores por defecto:
     * 5 intentos fallidos en ventana de 5 minutos.
     */
    public LoginRateLimiter() {
        this(5, 300);
    }

    /**
     * Registra un intento fallido para el username dado.
     *
     * @param username username del intento fallido
     */
    public void recordFailedAttempt(String username) {
        failedAttempts.computeIfAbsent(username, k -> new java.util.ArrayList<>())
                .add(Instant.now());
    }

    /**
     * Limpia los intentos fallidos tras un login exitoso.
     *
     * @param username username que logró autenticarse
     */
    public void clearAttempts(String username) {
        failedAttempts.remove(username);
    }

    /**
     * Verifica si el username está bloqueado por exceso de intentos fallidos.
     *
     * @param username username a verificar
     * @return true si el usuario está bloqueado temporalmente
     */
    public boolean isBlocked(String username) {
        java.util.List<Instant> attempts = failedAttempts.get(username);

        if (attempts == null || attempts.isEmpty()) {
            return false;
        }

        Instant windowStart = Instant.now().minusSeconds(windowSeconds);
        long recentAttempts = attempts.stream()
                .filter(ts -> ts.isAfter(windowStart))
                .count();

        if (recentAttempts >= maxAttempts) {
            log.warn("Login bloqueado para '{}': {} intentos en {}s", username, recentAttempts, windowSeconds);
            return true;
        }

        return false;
    }

    /**
     * Limpia las entradas expiradas de todos los usuarios.
     * Debería llamarse periódicamente (cada minuto) para evitar memory leak.
     */
    public void cleanupExpiredEntries() {
        Instant cutoff = Instant.now().minusSeconds(windowSeconds * 2);
        failedAttempts.values().removeIf(list ->
                list.isEmpty() || list.stream().allMatch(ts -> ts.isBefore(cutoff))
        );
    }
}
