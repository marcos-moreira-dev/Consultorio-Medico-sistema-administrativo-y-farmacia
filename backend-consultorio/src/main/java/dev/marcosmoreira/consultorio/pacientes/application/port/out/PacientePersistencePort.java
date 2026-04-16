package dev.marcosmoreira.consultorio.pacientes.application.port.out;

import dev.marcosmoreira.consultorio.pacientes.domain.model.Paciente;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PacientePersistencePort {
    Paciente guardar(Paciente paciente);
    Optional<Paciente> buscarPorId(Long pacienteId);
    Page<Paciente> listar(String cedula, LocalDate fechaNacimiento, String q, Pageable pageable);
    boolean existsByCedulaIgnoreCase(String cedula);
    boolean existsByCedulaIgnoreCaseAndPacienteIdNot(String cedula, Long pacienteId);
}
