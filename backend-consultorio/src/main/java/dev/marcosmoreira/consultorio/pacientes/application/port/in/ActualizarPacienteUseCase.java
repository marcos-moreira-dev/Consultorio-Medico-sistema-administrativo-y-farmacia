package dev.marcosmoreira.consultorio.pacientes.application.port.in;

import dev.marcosmoreira.consultorio.pacientes.domain.model.Paciente;
import java.time.LocalDate;

/**
 * Caso de uso para actualizar los datos básicos de un paciente.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public interface ActualizarPacienteUseCase {

    /**
     * Actualiza los datos básicos de un paciente existente.
     *
     * @param pacienteId identificador del paciente
     * @param nombres nombres del paciente
     * @param apellidos apellidos del paciente
     * @param telefono teléfono, si existe
     * @param cedula cédula, si existe
     * @param fechaNacimiento fecha de nacimiento, si existe
     * @param direccionBasica dirección básica, si existe
     * @return paciente actualizado
     */
    Paciente actualizar(
            Long pacienteId,
            String nombres,
            String apellidos,
            String telefono,
            String cedula,
            LocalDate fechaNacimiento,
            String direccionBasica
    );
}
