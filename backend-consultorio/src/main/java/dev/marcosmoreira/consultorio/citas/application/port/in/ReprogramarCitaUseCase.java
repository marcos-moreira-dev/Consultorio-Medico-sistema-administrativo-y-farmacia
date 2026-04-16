package dev.marcosmoreira.consultorio.citas.application.port.in;

import dev.marcosmoreira.consultorio.citas.domain.model.Cita;
import java.time.LocalDateTime;

/**
 * Caso de uso para reprogramar una cita existente.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public interface ReprogramarCitaUseCase {

    /**
     * Reprograma una cita existente hacia una nueva fecha y hora.
     *
     * @param citaId identificador de la cita
     * @param nuevaFechaHoraInicio nueva fecha/hora de inicio
     * @param observacionOperativa observación operativa asociada, si existe
     * @return cita actualizada
     */
    Cita reprogramar(
            Long citaId,
            LocalDateTime nuevaFechaHoraInicio,
            String observacionOperativa
    );
}
