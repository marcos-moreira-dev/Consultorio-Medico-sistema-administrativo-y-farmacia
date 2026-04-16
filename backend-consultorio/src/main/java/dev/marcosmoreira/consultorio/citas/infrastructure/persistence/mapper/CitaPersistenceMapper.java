package dev.marcosmoreira.consultorio.citas.infrastructure.persistence.mapper;

import dev.marcosmoreira.consultorio.citas.domain.model.Cita;
import dev.marcosmoreira.consultorio.citas.infrastructure.persistence.entity.CitaEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper de persistencia del módulo de citas.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Component
public class CitaPersistenceMapper {

    /**
     * Convierte una entidad JPA a modelo de dominio.
     *
     * @param entity entidad de persistencia
     * @return modelo de dominio equivalente, o {@code null} si la entidad es nula
     */
    public Cita toDomain(CitaEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Cita(
                entity.getCitaId(),
                entity.getPacienteId(),
                entity.getProfesionalId(),
                entity.getFechaHoraInicio(),
                entity.getEstadoCita(),
                entity.getMotivoBreve(),
                entity.getObservacionOperativa(),
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
    public CitaEntity toEntity(Cita domain) {
        if (domain == null) {
            return null;
        }

        CitaEntity entity = new CitaEntity(
                domain.getCitaId(),
                domain.getPacienteId(),
                domain.getProfesionalId(),
                domain.getFechaHoraInicio(),
                domain.getEstadoCita(),
                domain.getMotivoBreve(),
                domain.getObservacionOperativa()
        );

        entity.setFechaCreacion(domain.getFechaCreacion());
        entity.setFechaActualizacion(domain.getFechaActualizacion());

        return entity;
    }
}
