package dev.marcosmoreira.consultorio.profesionales.application.port.in;

import dev.marcosmoreira.consultorio.profesionales.domain.model.Profesional;

/**
 * Caso de uso para consultar un profesional por su identificador único.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public interface BuscarProfesionalUseCase {

    /**
     * Busca un profesional por su identificador único.
     *
     * @param profesionalId identificador del profesional
     * @return profesional encontrado
     */
    Profesional buscarPorId(Long profesionalId);
}
