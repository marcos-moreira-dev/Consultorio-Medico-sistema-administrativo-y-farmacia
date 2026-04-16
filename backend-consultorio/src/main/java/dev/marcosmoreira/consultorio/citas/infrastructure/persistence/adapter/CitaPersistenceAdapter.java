package dev.marcosmoreira.consultorio.citas.infrastructure.persistence.adapter;

import dev.marcosmoreira.consultorio.citas.application.port.out.CitaPersistencePort;
import dev.marcosmoreira.consultorio.citas.domain.enums.EstadoCita;
import dev.marcosmoreira.consultorio.citas.domain.model.Cita;
import dev.marcosmoreira.consultorio.citas.infrastructure.persistence.mapper.CitaPersistenceMapper;
import dev.marcosmoreira.consultorio.citas.infrastructure.persistence.repository.CitaJpaRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class CitaPersistenceAdapter implements CitaPersistencePort {
    private final CitaJpaRepository citaJpaRepository;
    private final CitaPersistenceMapper citaPersistenceMapper;
    public CitaPersistenceAdapter(CitaJpaRepository citaJpaRepository, CitaPersistenceMapper citaPersistenceMapper) {
        this.citaJpaRepository = citaJpaRepository;
        this.citaPersistenceMapper = citaPersistenceMapper;
    }
    @Override public Cita guardar(Cita cita) { return citaPersistenceMapper.toDomain(citaJpaRepository.save(citaPersistenceMapper.toEntity(cita))); }
    @Override public Optional<Cita> buscarPorId(Long citaId) { return citaJpaRepository.findById(citaId).map(citaPersistenceMapper::toDomain); }
    @Override public Page<Cita> listar(Long pacienteId, Long profesionalId, EstadoCita estadoCita, LocalDateTime fechaDesde, LocalDateTime fechaHasta, Pageable pageable) { return citaJpaRepository.buscarPorFiltros(pacienteId, profesionalId, estadoCita, fechaDesde, fechaHasta, pageable).map(citaPersistenceMapper::toDomain); }
    @Override public boolean existsByProfesionalIdAndFechaHoraInicio(Long profesionalId, LocalDateTime fechaHoraInicio) { return citaJpaRepository.existsByProfesionalIdAndFechaHoraInicio(profesionalId, fechaHoraInicio); }
    @Override public boolean existsByProfesionalIdAndFechaHoraInicioAndCitaIdNot(Long profesionalId, LocalDateTime fechaHoraInicio, Long citaId) { return citaJpaRepository.existsByProfesionalIdAndFechaHoraInicioAndCitaIdNot(profesionalId, fechaHoraInicio, citaId); }
}
