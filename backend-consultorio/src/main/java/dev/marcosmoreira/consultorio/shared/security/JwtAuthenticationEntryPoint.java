package dev.marcosmoreira.consultorio.shared.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.marcosmoreira.consultorio.shared.error.ApiErrorCode;
import dev.marcosmoreira.consultorio.shared.util.CorrelationIdUtils;
import dev.marcosmoreira.consultorio.shared.web.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Punto de entrada de seguridad que responde cuando una petición no autenticada
 * intenta acceder a un recurso protegido.
 *
 * <p>Su objetivo es devolver una respuesta JSON consistente en vez de dejar
 * que Spring Security responda con HTML o formatos menos controlados.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    /**
     * Construye el entry point de autenticación.
     *
     * @param objectMapper serializador JSON usado para escribir la respuesta HTTP
     */
    public JwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Maneja el acceso no autenticado a recursos protegidos.
     *
     * @param request request HTTP actual
     * @param response response HTTP actual
     * @param authException excepción de autenticación disparada por Spring Security
     * @throws IOException si ocurre un error al escribir la respuesta
     */
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<Void> body = ApiResponse.error(
                ApiErrorCode.UNAUTHORIZED,
                "Debe autenticarse para acceder a este recurso.",
                CorrelationIdUtils.getCurrentCorrelationId()
        );

        objectMapper.writeValue(response.getOutputStream(), body);
    }
}
