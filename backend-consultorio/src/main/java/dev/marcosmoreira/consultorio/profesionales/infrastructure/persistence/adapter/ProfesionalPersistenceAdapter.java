package dev.marcosmoreira.consultorio.profesionales.infrastructure.persistence.adapter;

import dev.marcosmoreira.consultorio.profesionales.application.port.out.ProfesionalPersistencePort;
import dev.marcosmoreira.consultorio.profesionales.domain.enums.EstadoProfesional;
import dev.marcosmoreira.consultorio.profesionales.domain.model.Profesional;
import dev.marcosmoreira.consultorio.profesionales.infrastructure.persistence.mapper.ProfesionalPersistenceMapper;
import dev.marcosmoreira.consultorio.profesionales.infrastructure.persistence.repository.ProfesionalJpaRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ProfesionalPersistenceAdapter implements ProfesionalPersistencePort {
    private final ProfesionalJpaRepository profesionalJpaRepository;
    private final ProfesionalPersistenceMapper profesionalPersistenceMapper;
    public ProfesionalPersistenceAdapter(ProfesionalJpaRepository profesionalJpaRepository, ProfesionalPersistenceMapper profesionalPersistenceMapper) {
        this.profesionalJpaRepository = profesionalJpaRepository;
        this.profesionalPersistenceMapper = profesionalPersistenceMapper;
    }
    @Override public Profesional guardar(Profesional profesional) { return profesionalPersistenceMapper.toDomain(profesionalJpaRepository.save(profesionalPersistenceMapper.toEntity(profesional))); }
    @Override public Optional<Profesional> buscarPorId(Long profesionalId) { return profesionalJpaRepository.findById(profesionalId).map(profesionalPersistenceMapper::toDomain); }
    @Override public Page<Profesional> listar(Long usuarioId, EstadoProfesional estadoProfesional, String q, Pageable pageable) { return profesionalJpaRepository.buscarPorFiltros(usuarioId, estadoProfesional, q, pageable).map(profesionalPersistenceMapper::toDomain); }
    @Override public boolean existsByUsuarioId(Long usuarioId) { return profesionalJpaRepository.existsByUsuarioId(usuarioId); }
    @Override public boolean existsByUsuarioIdAndProfesionalIdNot(Long usuarioId, Long profesionalId) { return profesionalJpaRepository.existsByUsuarioIdAndProfesionalIdNot(usuarioId, profesionalId); }
}
