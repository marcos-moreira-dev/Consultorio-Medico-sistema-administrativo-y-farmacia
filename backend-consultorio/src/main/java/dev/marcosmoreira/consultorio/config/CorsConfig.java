package dev.marcosmoreira.consultorio.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Configuración de CORS (Cross-Origin Resource Sharing) para el backend.
 *
 * <p>Esta configuración define qué orígenes, métodos y headers pueden interactuar
 * con la API desde clientes externos, por ejemplo el cliente desktop, herramientas
 * de prueba o futuros frontends web.</p>
 *
 * <p>En la versión 1.0 se adopta una política relativamente permisiva para facilitar
 * el desarrollo e integración inicial del sistema. En entornos productivos conviene
 * restringir explícitamente los orígenes autorizados.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Configuration
public class CorsConfig {

    /**
     * Registra la configuración CORS global del backend.
     *
     * <p>Se permiten los métodos HTTP principales y los headers más comunes.
     * También se expone el header {@code Authorization} para facilitar integraciones
     * donde el cliente necesite inspeccionarlo.</p>
     *
     * @return fuente de configuración CORS aplicada a todas las rutas de la API
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        /*
         * Para V1.0 se dejan patrones amplios para reducir fricción durante pruebas
         * e integración manual. Más adelante esto debería endurecerse por ambiente.
         */
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
