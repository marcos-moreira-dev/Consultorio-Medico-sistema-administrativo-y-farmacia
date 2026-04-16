package dev.marcosmoreira.consultorio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Habilita la infraestructura de auditoría de Spring Data JPA.
 *
 * <p>Aunque en la versión 1.0 todavía no se está explotando al máximo esta
 * capacidad en todas las entidades, conviene dejar el backend preparado para
 * soportar auditoría técnica basada en campos como fecha de creación y fecha
 * de actualización.</p>
 *
 * <p>Esta configuración no reemplaza una auditoría funcional o de negocio.
 * Solo habilita la infraestructura de soporte proporcionada por Spring Data.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}
