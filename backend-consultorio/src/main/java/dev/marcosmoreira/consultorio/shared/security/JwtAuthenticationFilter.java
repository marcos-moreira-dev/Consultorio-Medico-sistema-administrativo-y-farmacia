package dev.marcosmoreira.consultorio.shared.security;

import dev.marcosmoreira.consultorio.shared.util.CorrelationIdUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import org.slf4j.MDC;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filtro JWT encargado de inspeccionar cada request, extraer el token Bearer
 * cuando exista, validarlo y poblar el contexto de seguridad de Spring.
 *
 * <p>Este filtro no ejecuta login. Solo interpreta tokens ya emitidos y, si son
 * válidos, reconstruye la autenticación del usuario para la request actual.</p>
 *
 * <p>Se excluyen explícitamente los endpoints de actuator (/actuator/health,
 * /actuator/info) y de documentación (Swagger/OpenAPI) para evitar overhead
 * innecesario en healthchecks y documentación, ya que estos paths ya están
 * autorizados públicamente en {@code SecurityConfig}.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    /**
     * Paths que no requieren procesamiento JWT porque ya están permitidos
     * públicamente en la configuración de seguridad.
     *
     * <p>Excluir estos paths del filtro reduce overhead innecesario en cada
     * healthcheck de Kubernetes, Docker, o monitoreo externo.</p>
     */
    private static final Set<String> EXCLUDED_PATHS = Set.of(
            "/actuator/health",
            "/actuator/info",
            "/v3/api-docs",
            "/api/v1/api-docs",
            "/api-docs",
            "/swagger-ui.html"
    );

    private final JwtTokenService jwtTokenService;
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Construye el filtro JWT.
     *
     * @param jwtTokenService servicio encargado de operar sobre tokens JWT
     * @param customUserDetailsService servicio que recarga al usuario desde persistencia
     */
    public JwtAuthenticationFilter(
            JwtTokenService jwtTokenService,
            CustomUserDetailsService customUserDetailsService
    ) {
        this.jwtTokenService = jwtTokenService;
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Determina si la request actual debe saltarse el procesamiento JWT.
     *
     * @param requestUri URI de la request actual
     * @return true si el path está excluido del filtro
     */
    private boolean isExcludedPath(String requestUri) {
        return EXCLUDED_PATHS.stream().anyMatch(requestUri::startsWith);
    }

    /**
     * Procesa la request actual intentando autenticarla mediante un token JWT.
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

        // Saltar procesamiento JWT para paths públicos ya autorizados
        if (isExcludedPath(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(BEARER_PREFIX.length()).trim();

        if (token.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String username = jwtTokenService.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                if (jwtTokenService.isTokenValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    /*
                     * Guardamos también el usuario en MDC para facilitar trazabilidad
                     * en logs cuando exista configuración de logging estructurado.
                     */
                    MDC.put("username", userDetails.getUsername());
                    MDC.put(CorrelationIdUtils.MDC_KEY, CorrelationIdUtils.getCurrentCorrelationId());
                }
            }
        } catch (Exception ex) {
            /*
             * No lanzamos aquí una excepción nueva porque preferimos dejar que el
             * flujo continúe y que la falta de autenticación se resuelva por la capa
             * de seguridad como un 401 si el recurso lo requiere.
             */
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
