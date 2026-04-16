package dev.marcosmoreira.consultorio.profesionales.application.port.in;

import dev.marcosmoreira.consultorio.profesionales.domain.model.Profesional;

/**
 * Caso de uso para registrar un nuevo profesional.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public interface CrearProfesionalUseCase {

    /**
     * Registra un nuevo profesional del consultorio.
     *
     * @param usuarioId identificador del usuario asociado, si existe
     * @param nombres nombres del profesional
     * @param apellidos apellidos del profesional
     * @param especialidadBreve especialidad breve
     * @param registroProfesional registro profesional, si aplica
     * @return profesional creado
     */
    Profesional crear(
            Long usuarioId,
            String nombres,
            String apellidos,
            String especialidadBreve,
            String registroProfesional
    );
}
