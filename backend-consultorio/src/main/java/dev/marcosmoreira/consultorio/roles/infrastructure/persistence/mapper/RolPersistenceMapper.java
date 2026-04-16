package dev.marcosmoreira.consultorio.roles.infrastructure.persistence.mapper;

import dev.marcosmoreira.consultorio.roles.domain.model.Rol;
import dev.marcosmoreira.consultorio.roles.infrastructure.persistence.entity.RolEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper de persistencia del módulo de roles.
 *
 * <p>Traduce entre el modelo de dominio y la entidad JPA correspondiente,
 * manteniendo separadas la capa de negocio y la capa de infraestructura.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Component
public class RolPersistenceMapper {

    /**
     * Convierte una entidad JPA a modelo de dominio.
     *
     * @param entity entidad de persistencia
     * @return modelo de dominio equivalente, o {@code null} si la entidad es nula
     */
    public Rol toDomain(RolEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Rol(
                entity.getRolId(),
                entity.getCodigo(),
                entity.getNombre()
        );
    }

    /**
     * Convierte un modelo de dominio a entidad JPA.
     *
     * @param domain modelo de dominio
     * @return entidad equivalente, o {@code null} si el dominio es nulo
     */
    public RolEntity toEntity(Rol domain) {
        if (domain == null) {
            return null;
        }

        return new RolEntity(
                domain.getRolId(),
                domain.getCodigo(),
                domain.getNombre()
        );
    }
}
