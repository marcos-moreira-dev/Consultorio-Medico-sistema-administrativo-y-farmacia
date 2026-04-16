package dev.marcosmoreira.consultorio.pacientes.infrastructure.persistence.mapper;

import dev.marcosmoreira.consultorio.pacientes.domain.model.Paciente;
import dev.marcosmoreira.consultorio.pacientes.infrastructure.persistence.entity.PacienteEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper de persistencia del módulo de pacientes.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Component
public class PacientePersistenceMapper {

    /**
     * Convierte una entidad JPA a modelo de dominio.
     *
     * @param entity entidad de persistencia
     * @return modelo de dominio equivalente, o {@code null} si la entidad es nula
     */
    public Paciente toDomain(PacienteEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Paciente(
                entity.getPacienteId(),
                entity.getNombres(),
                entity.getApellidos(),
                entity.getTelefono(),
                entity.getCedula(),
                entity.getFechaNacimiento(),
                entity.getDireccionBasica(),
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
    public PacienteEntity toEntity(Paciente domain) {
        if (domain == null) {
            return null;
        }

        PacienteEntity entity = new PacienteEntity(
                domain.getPacienteId(),
                domain.getNombres(),
                domain.getApellidos(),
                domain.getTelefono(),
                domain.getCedula(),
                domain.getFechaNacimiento(),
                domain.getDireccionBasica()
        );

        entity.setFechaCreacion(domain.getFechaCreacion());
        entity.setFechaActualizacion(domain.getFechaActualizacion());

        return entity;
    }
}
