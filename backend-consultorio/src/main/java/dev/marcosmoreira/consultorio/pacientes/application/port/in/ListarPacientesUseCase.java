package dev.marcosmoreira.consultorio.pacientes.application.port.in;

import dev.marcosmoreira.consultorio.pacientes.domain.model.Paciente;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarPacientesUseCase {
    Page<Paciente> listar(String cedula, LocalDate fechaNacimiento, String q, Pageable pageable);
}
