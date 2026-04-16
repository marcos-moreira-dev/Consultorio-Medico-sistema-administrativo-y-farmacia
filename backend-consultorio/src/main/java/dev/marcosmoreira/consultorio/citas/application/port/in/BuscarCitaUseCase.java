package dev.marcosmoreira.consultorio.citas.application.port.in;

import dev.marcosmoreira.consultorio.citas.domain.model.Cita;

/**
 * Caso de uso para consultar una cita por su identificador único.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public interface BuscarCitaUseCase {

    /**
     * Busca una cita por su identificador único.
     *
     * @param citaId identificador de la cita
     * @return cita encontrada
     */
    Cita buscarPorId(Long citaId);
}
