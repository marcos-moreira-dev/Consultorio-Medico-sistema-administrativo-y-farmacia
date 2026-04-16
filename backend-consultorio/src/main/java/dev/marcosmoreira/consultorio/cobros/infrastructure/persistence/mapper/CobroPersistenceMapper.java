package dev.marcosmoreira.consultorio.cobros.infrastructure.persistence.mapper;

import dev.marcosmoreira.consultorio.cobros.domain.model.Cobro;
import dev.marcosmoreira.consultorio.cobros.infrastructure.persistence.entity.CobroEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper de persistencia del módulo de cobros.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Component
public class CobroPersistenceMapper {

    /**
     * Convierte una entidad JPA a modelo de dominio.
     *
     * @param entity entidad de persistencia
     * @return modelo de dominio equivalente, o {@code null} si la entidad es nula
     */
    public Cobro toDomain(CobroEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Cobro(
                entity.getCobroId(),
                entity.getAtencionId(),
                entity.getRegistradoPorUsuarioId(),
                entity.getMonto(),
                entity.getMetodoPago(),
                entity.getEstadoCobro(),
                entity.getObservacionAdministrativa(),
                entity.getFechaHoraRegistro(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion()
        );
    }

    /**
     * Convierte un modelo de dominio a entidad JPA.
     *
     * @param domain modelo de dominio
     * @return entidad equivalente, o {@code null} si el dominio es nulo
     */
    public CobroEntity toEntity(Cobro domain) {
        if (domain == null) {
            return null;
        }

        CobroEntity entity = new CobroEntity(
                domain.getCobroId(),
                domain.getAtencionId(),
                domain.getRegistradoPorUsuarioId(),
                domain.getMonto(),
                domain.getMetodoPago(),
                domain.getEstadoCobro(),
                domain.getObservacionAdministrativa(),
                domain.getFechaHoraRegistro()
        );

        entity.setFechaCreacion(domain.getFechaCreacion());
        entity.setFechaActualizacion(domain.getFechaActualizacion());

        return entity;
    }
}
