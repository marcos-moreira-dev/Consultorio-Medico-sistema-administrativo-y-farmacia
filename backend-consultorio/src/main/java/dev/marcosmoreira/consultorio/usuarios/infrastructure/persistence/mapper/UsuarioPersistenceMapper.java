package dev.marcosmoreira.consultorio.usuarios.infrastructure.persistence.mapper;

import dev.marcosmoreira.consultorio.usuarios.domain.model.Usuario;
import dev.marcosmoreira.consultorio.usuarios.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper de persistencia del módulo de usuarios.
 *
 * <p>Traduce entre el modelo de dominio y la entidad JPA correspondiente,
 * manteniendo separadas la capa de negocio y la capa de infraestructura.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Component
public class UsuarioPersistenceMapper {

    /**
     * Convierte una entidad JPA al modelo de dominio.
     *
     * @param entity entidad de persistencia
     * @return modelo de dominio equivalente, o {@code null} si la entidad es nula
     */
    public Usuario toDomain(UsuarioEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Usuario(
                entity.getUsuarioId(),
                entity.getRolId(),
                entity.getRolCodigo(),
                entity.getRolNombre(),
                entity.getUsername(),
                entity.getPasswordHash(),
                entity.getNombreCompleto(),
                entity.getEstado(),
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
    public UsuarioEntity toEntity(Usuario domain) {
        if (domain == null) {
            return null;
        }

        UsuarioEntity entity = new UsuarioEntity(
                domain.getUsuarioId(),
                domain.getRolId(),
                domain.getUsername(),
                domain.getPasswordHash(),
                domain.getNombreCompleto(),
                domain.getEstado()
        );

        entity.setFechaCreacion(domain.getFechaCreacion());
        entity.setFechaActualizacion(domain.getFechaActualizacion());

        return entity;
    }
}
