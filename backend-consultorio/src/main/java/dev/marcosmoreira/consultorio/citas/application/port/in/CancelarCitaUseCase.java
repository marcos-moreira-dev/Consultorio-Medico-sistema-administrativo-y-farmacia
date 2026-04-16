package dev.marcosmoreira.consultorio.citas.application.port.in;

import dev.marcosmoreira.consultorio.citas.domain.model.Cita;

/**
 * Caso de uso para cancelar una cita existente.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public interface CancelarCitaUseCase {

    /**
     * Cancela una cita programada.
     *
     * @param citaId identificador de la cita
     * @param observacionOperativa observación operativa asociada, si existe
     * @return cita actualizada
     */
    Cita cancelar(Long citaId, String observacionOperativa);
}
