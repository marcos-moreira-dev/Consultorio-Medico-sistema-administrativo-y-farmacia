package dev.marcosmoreira.consultorio.shared.util;

import java.util.UUID;
import org.slf4j.MDC;

/**
 * Utilidades transversales para manejo de correlation id.
 *
 * <p>El correlation id ayuda a rastrear una misma operación a través de filtros,
 * controladores, servicios y logs. Es especialmente útil cuando el sistema crece
 * o cuando se necesita diagnosticar errores con mayor precisión.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public final class CorrelationIdUtils {

    /**
     * Header HTTP estándar del proyecto para transportar el correlation id.
     */
    public static final String HEADER_NAME = "X-Correlation-Id";

    /**
     * Clave usada dentro del MDC para logging.
     */
    public static final String MDC_KEY = "correlationId";

    private CorrelationIdUtils() {
    }

    /**
     * Genera un nuevo correlation id.
     *
     * @return identificador único en formato string
     */
    public static String generate() {
        return UUID.randomUUID().toString();
    }

    /**
     * Devuelve el correlation id actual del MDC, si existe.
     *
     * @return correlation id actual o {@code null} si no existe
     */
    public static String getCurrentCorrelationId() {
        return MDC.get(MDC_KEY);
    }

    /**
     * Registra el correlation id actual en el MDC.
     *
     * @param correlationId identificador a registrar
     */
    public static void setCurrentCorrelationId(String correlationId) {
        if (correlationId != null && !correlationId.isBlank()) {
            MDC.put(MDC_KEY, correlationId.trim());
        }
    }

    /**
     * Elimina el correlation id del MDC.
     */
    public static void clear() {
        MDC.remove(MDC_KEY);
    }

    /**
     * Devuelve el correlation id recibido o genera uno nuevo si no existe.
     *
     * @param incomingCorrelationId valor entrante desde header o contexto
     * @return correlation id válido
     */
    public static String resolveOrGenerate(String incomingCorrelationId) {
        if (incomingCorrelationId == null || incomingCorrelationId.isBlank()) {
            return generate();
        }

        return incomingCorrelationId.trim();
    }
}
