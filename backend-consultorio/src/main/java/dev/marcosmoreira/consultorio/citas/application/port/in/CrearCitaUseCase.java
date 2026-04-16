package dev.marcosmoreira.consultorio.citas.application.port.in;

import dev.marcosmoreira.consultorio.citas.domain.model.Cita;
import java.time.LocalDateTime;

/**
 * Caso de uso para registrar una nueva cita.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public interface CrearCitaUseCase {

    /**
     * Registra una nueva cita en la agenda del consultorio.
     *
     * @param pacienteId identificador del paciente
     * @param profesionalId identificador del profesional
     * @param fechaHoraInicio fecha y hora de inicio
     * @param motivoBreve motivo breve de la cita
     * @param observacionOperativa observación operativa
     * @return cita creada
     */
    Cita crear(
            Long pacienteId,
            Long profesionalId,
            LocalDateTime fechaHoraInicio,
            String motivoBreve,
            String observacionOperativa
    );
}
