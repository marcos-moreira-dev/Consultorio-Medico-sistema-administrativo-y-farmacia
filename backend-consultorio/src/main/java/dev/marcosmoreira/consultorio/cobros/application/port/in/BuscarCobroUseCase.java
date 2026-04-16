package dev.marcosmoreira.consultorio.cobros.application.port.in;

import dev.marcosmoreira.consultorio.cobros.domain.model.Cobro;

/**
 * Caso de uso para consultar un cobro por su identificador único.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public interface BuscarCobroUseCase {

    /**
     * Busca un cobro por su identificador único.
     *
     * @param cobroId identificador del cobro
     * @return cobro encontrado
     */
    Cobro buscarPorId(Long cobroId);
}
