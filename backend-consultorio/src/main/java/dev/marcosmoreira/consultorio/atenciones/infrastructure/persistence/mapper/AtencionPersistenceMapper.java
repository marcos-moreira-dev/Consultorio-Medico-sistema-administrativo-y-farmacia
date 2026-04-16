package dev.marcosmoreira.consultorio.atenciones.infrastructure.persistence.mapper;

import dev.marcosmoreira.consultorio.atenciones.domain.model.Atencion;
import dev.marcosmoreira.consultorio.atenciones.infrastructure.persistence.entity.AtencionEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper de persistencia del módulo de atenciones.
 *
 * <p>Su responsabilidad es traducir entre:</p>
 *
 * <ul>
 *   <li>el modelo de dominio {@link Atencion}, que representa la lógica del negocio;</li>
 *   <li>la entidad {@link AtencionEntity}, que representa la forma de persistir en JPA.</li>
 * </ul>
 *
 * <p>Separar este mapeo ayuda a mantener limpio el dominio y evita que la capa
 * de aplicación dependa directamente de detalles de infraestructura.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Component
public class AtencionPersistenceMapper {

    /**
     * Convierte una entidad JPA a modelo de dominio.
     *
     * @param entity entidad de persistencia
     * @return modelo de dominio equivalente, o {@code null} si la entidad es nula
     */
    public Atencion toDomain(AtencionEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Atencion(
                entity.getAtencionId(),
                entity.getPacienteId(),
                entity.getProfesionalId(),
                entity.getCitaId(),
                entity.getFechaHoraAtencion(),
                entity.getNotaBreve(),
                entity.getIndicacionesBreves(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion()
        );
    }

    /**
     * Convierte un modelo de dominio a entidad JPA.
     *
     * @param domain modelo de dominio
     * @return entidad de persistencia equivalente, o {@code null} si el dominio es nulo
     */
    public AtencionEntity toEntity(Atencion domain) {
        if (domain == null) {
            return null;
        }

        return new AtencionEntity(
                domain.getAtencionId(),
                domain.getPacienteId(),
                domain.getProfesionalId(),
                domain.getCitaId(),
                domain.getFechaHoraAtencion(),
                domain.getNotaBreve(),
                domain.getIndicacionesBreves(),
                domain.getFechaCreacion(),
                domain.getFechaActualizacion()
        );
    }
}
