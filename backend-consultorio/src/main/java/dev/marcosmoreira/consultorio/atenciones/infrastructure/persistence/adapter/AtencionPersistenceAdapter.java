package dev.marcosmoreira.consultorio.atenciones.infrastructure.persistence.adapter;

import dev.marcosmoreira.consultorio.atenciones.application.port.out.AtencionPersistencePort;
import dev.marcosmoreira.consultorio.atenciones.domain.model.Atencion;
import dev.marcosmoreira.consultorio.atenciones.infrastructure.persistence.mapper.AtencionPersistenceMapper;
import dev.marcosmoreira.consultorio.atenciones.infrastructure.persistence.repository.AtencionJpaRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class AtencionPersistenceAdapter implements AtencionPersistencePort {
    private final AtencionJpaRepository atencionJpaRepository;
    private final AtencionPersistenceMapper atencionPersistenceMapper;
    public AtencionPersistenceAdapter(AtencionJpaRepository atencionJpaRepository, AtencionPersistenceMapper atencionPersistenceMapper) {
        this.atencionJpaRepository = atencionJpaRepository;
        this.atencionPersistenceMapper = atencionPersistenceMapper;
    }
    @Override public Atencion guardar(Atencion atencion) { return atencionPersistenceMapper.toDomain(atencionJpaRepository.save(atencionPersistenceMapper.toEntity(atencion))); }
    @Override public Optional<Atencion> buscarPorId(Long atencionId) { return atencionJpaRepository.findById(atencionId).map(atencionPersistenceMapper::toDomain); }
    @Override public Page<Atencion> listar(Long pacienteId, Long profesionalId, LocalDateTime fechaDesde, LocalDateTime fechaHasta, Pageable pageable) { return atencionJpaRepository.buscarPorFiltros(pacienteId, profesionalId, fechaDesde, fechaHasta, pageable).map(atencionPersistenceMapper::toDomain); }
}
