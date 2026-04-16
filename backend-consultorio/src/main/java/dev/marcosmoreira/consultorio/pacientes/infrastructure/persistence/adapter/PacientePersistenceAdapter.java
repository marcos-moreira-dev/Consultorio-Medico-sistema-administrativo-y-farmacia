package dev.marcosmoreira.consultorio.pacientes.infrastructure.persistence.adapter;

import dev.marcosmoreira.consultorio.pacientes.application.port.out.PacientePersistencePort;
import dev.marcosmoreira.consultorio.pacientes.domain.model.Paciente;
import dev.marcosmoreira.consultorio.pacientes.infrastructure.persistence.entity.PacienteEntity;
import dev.marcosmoreira.consultorio.pacientes.infrastructure.persistence.mapper.PacientePersistenceMapper;
import dev.marcosmoreira.consultorio.pacientes.infrastructure.persistence.repository.PacienteJpaRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class PacientePersistenceAdapter implements PacientePersistencePort {
    private final PacienteJpaRepository pacienteJpaRepository;
    private final PacientePersistenceMapper pacientePersistenceMapper;
    public PacientePersistenceAdapter(PacienteJpaRepository pacienteJpaRepository, PacientePersistenceMapper pacientePersistenceMapper) {
        this.pacienteJpaRepository = pacienteJpaRepository;
        this.pacientePersistenceMapper = pacientePersistenceMapper;
    }
    @Override public Paciente guardar(Paciente paciente) { return pacientePersistenceMapper.toDomain(pacienteJpaRepository.save(pacientePersistenceMapper.toEntity(paciente))); }
    @Override public Optional<Paciente> buscarPorId(Long pacienteId) { return pacienteJpaRepository.findById(pacienteId).map(pacientePersistenceMapper::toDomain); }
    @Override public Page<Paciente> listar(String cedula, LocalDate fechaNacimiento, String q, Pageable pageable) { return pacienteJpaRepository.buscarPorFiltros(cedula, fechaNacimiento, q, pageable).map(pacientePersistenceMapper::toDomain); }
    @Override public boolean existsByCedulaIgnoreCase(String cedula) { return pacienteJpaRepository.existsByCedulaIgnoreCase(cedula); }
    @Override public boolean existsByCedulaIgnoreCaseAndPacienteIdNot(String cedula, Long pacienteId) { return pacienteJpaRepository.existsByCedulaIgnoreCaseAndPacienteIdNot(cedula, pacienteId); }
}
