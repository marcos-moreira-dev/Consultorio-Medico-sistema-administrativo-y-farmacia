package dev.marcosmoreira.consultorio.profesionales.application.port.in;

import dev.marcosmoreira.consultorio.profesionales.domain.enums.EstadoProfesional;
import dev.marcosmoreira.consultorio.profesionales.domain.model.Profesional;

/**
 * Caso de uso para cambiar el estado lógico de un profesional.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public interface CambiarEstadoProfesionalUseCase {

    /**
     * Cambia el estado lógico del profesional indicado.
     *
     * @param profesionalId identificador del profesional
     * @param nuevoEstado nuevo estado a asignar
     * @return profesional actualizado
     */
    Profesional cambiarEstado(Long profesionalId, EstadoProfesional nuevoEstado);
}
