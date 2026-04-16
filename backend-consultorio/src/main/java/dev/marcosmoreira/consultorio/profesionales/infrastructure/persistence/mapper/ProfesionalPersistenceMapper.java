package dev.marcosmoreira.consultorio.profesionales.infrastructure.persistence.mapper;

import dev.marcosmoreira.consultorio.profesionales.domain.model.Profesional;
import dev.marcosmoreira.consultorio.profesionales.infrastructure.persistence.entity.ProfesionalEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper de persistencia del módulo de profesionales.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Component
public class ProfesionalPersistenceMapper {

    /**
     * Convierte una entidad JPA a modelo de dominio.
     *
     * @param entity entidad de persistencia
     * @return modelo de dominio equivalente, o {@code null} si la entidad es nula
     */
    public Profesional toDomain(ProfesionalEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Profesional(
                entity.getProfesionalId(),
                entity.getUsuarioId(),
                entity.getNombres(),
                entity.getApellidos(),
                entity.getEspecialidadBreve(),
                entity.getRegistroProfesional(),
                entity.getEstadoProfesional(),
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
    public ProfesionalEntity toEntity(Profesional domain) {
        if (domain == null) {
            return null;
        }

        ProfesionalEntity entity = new ProfesionalEntity(
                domain.getProfesionalId(),
                domain.getUsuarioId(),
                domain.getNombres(),
                domain.getApellidos(),
                domain.getEspecialidadBreve(),
                domain.getRegistroProfesional(),
                domain.getEstadoProfesional()
        );

        entity.setFechaCreacion(domain.getFechaCreacion());
        entity.setFechaActualizacion(domain.getFechaActualizacion());

        return entity;
    }
}
