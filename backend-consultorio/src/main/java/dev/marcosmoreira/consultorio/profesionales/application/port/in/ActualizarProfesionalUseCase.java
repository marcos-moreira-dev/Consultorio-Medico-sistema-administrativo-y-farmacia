package dev.marcosmoreira.consultorio.profesionales.application.port.in;

import dev.marcosmoreira.consultorio.profesionales.domain.model.Profesional;

/**
 * Caso de uso para actualizar los datos básicos de un profesional.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public interface ActualizarProfesionalUseCase {

    /**
     * Actualiza los datos básicos de un profesional existente.
     *
     * @param profesionalId identificador del profesional
     * @param usuarioId identificador del usuario asociado, si existe
     * @param nombres nombres del profesional
     * @param apellidos apellidos del profesional
     * @param especialidadBreve especialidad breve
     * @param registroProfesional registro profesional, si aplica
     * @return profesional actualizado
     */
    Profesional actualizar(
            Long profesionalId,
            Long usuarioId,
            String nombres,
            String apellidos,
            String especialidadBreve,
            String registroProfesional
    );
}
