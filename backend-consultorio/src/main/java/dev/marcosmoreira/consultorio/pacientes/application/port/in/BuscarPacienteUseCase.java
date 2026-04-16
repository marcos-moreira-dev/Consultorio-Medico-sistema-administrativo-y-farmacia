package dev.marcosmoreira.consultorio.pacientes.application.port.in;

import dev.marcosmoreira.consultorio.pacientes.domain.model.Paciente;

/**
 * Caso de uso para consultar un paciente por su identificador único.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public interface BuscarPacienteUseCase {

    /**
     * Busca un paciente por su identificador único.
     *
     * @param pacienteId identificador del paciente
     * @return paciente encontrado
     */
    Paciente buscarPorId(Long pacienteId);
}
