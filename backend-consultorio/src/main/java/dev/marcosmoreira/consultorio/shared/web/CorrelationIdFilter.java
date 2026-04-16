package dev.marcosmoreira.consultorio.shared.web;

import dev.marcosmoreira.consultorio.shared.util.CorrelationIdUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filtro HTTP encargado de asegurar la presencia de un correlation id en cada request.
 *
 * <p>Si el cliente envía el header correspondiente, se reutiliza. Si no lo envía,
 * el backend genera uno nuevo. El valor se expone de vuelta en la respuesta y
 * también se registra en el MDC para facilitar trazabilidad en logs.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Component
public class CorrelationIdFilter extends OncePerRequestFilter {

    /**
     * Procesa la request actual resolviendo o generando el correlation id.
     *
     * @param request request HTTP actual
     * @param response response HTTP actual
     * @param filterChain cadena de filtros
     * @throws ServletException si ocurre un error del contenedor
     * @throws IOException si ocurre un error de I/O
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String incoming = request.getHeader(CorrelationIdUtils.HEADER_NAME);
        String correlationId = CorrelationIdUtils.resolveOrGenerate(incoming);

        try {
            CorrelationIdUtils.setCurrentCorrelationId(correlationId);
            response.setHeader(CorrelationIdUtils.HEADER_NAME, correlationId);

            filterChain.doFilter(request, response);
        } finally {
            CorrelationIdUtils.clear();
        }
    }
}
