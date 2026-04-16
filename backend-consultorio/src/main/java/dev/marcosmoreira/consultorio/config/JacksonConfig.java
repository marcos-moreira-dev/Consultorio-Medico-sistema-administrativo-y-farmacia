package dev.marcosmoreira.consultorio.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración global de Jackson para serialización y deserialización JSON.
 *
 * <p>Su objetivo es unificar el comportamiento de conversión entre objetos Java
 * y JSON dentro del backend, especialmente para fechas modernas de Java
 * como {@code LocalDateTime}.</p>
 *
 * <p>Esta clase pertenece a la capa de configuración transversal del sistema
 * y ayuda a evitar comportamientos inconsistentes entre módulos.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Configuration
public class JacksonConfig {

    /**
     * Personaliza el {@code ObjectMapper} gestionado por Spring Boot.
     *
     * <p>Decisiones principales:</p>
     *
     * <ul>
     *   <li>soporte explícito para Java Time;</li>
     *   <li>no serializar fechas como timestamps numéricos;</li>
     *   <li>ignorar propiedades desconocidas al deserializar;</li>
     *   <li>omitir campos nulos cuando sea razonable.</li>
     * </ul>
     *
     * @return customizador del builder de Jackson
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
        return builder -> builder
                .modules(new JavaTimeModule())
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
}
