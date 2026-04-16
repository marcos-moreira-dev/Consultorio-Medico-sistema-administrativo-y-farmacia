package dev.marcosmoreira.consultorio.cobros.infrastructure.persistence.adapter;

import dev.marcosmoreira.consultorio.cobros.application.port.out.CobroPersistencePort;
import dev.marcosmoreira.consultorio.cobros.domain.enums.EstadoCobro;
import dev.marcosmoreira.consultorio.cobros.domain.enums.MetodoPago;
import dev.marcosmoreira.consultorio.cobros.domain.model.Cobro;
import dev.marcosmoreira.consultorio.cobros.infrastructure.persistence.mapper.CobroPersistenceMapper;
import dev.marcosmoreira.consultorio.cobros.infrastructure.persistence.repository.CobroJpaRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class CobroPersistenceAdapter implements CobroPersistencePort {
    private final CobroJpaRepository cobroJpaRepository;
    private final CobroPersistenceMapper cobroPersistenceMapper;
    public CobroPersistenceAdapter(CobroJpaRepository cobroJpaRepository, CobroPersistenceMapper cobroPersistenceMapper) {
        this.cobroJpaRepository = cobroJpaRepository;
        this.cobroPersistenceMapper = cobroPersistenceMapper;
    }
    @Override public Cobro guardar(Cobro cobro) { return cobroPersistenceMapper.toDomain(cobroJpaRepository.save(cobroPersistenceMapper.toEntity(cobro))); }
    @Override public Optional<Cobro> buscarPorId(Long cobroId) { return cobroJpaRepository.findById(cobroId).map(cobroPersistenceMapper::toDomain); }
    @Override public Page<Cobro> listar(Long atencionId, Long registradoPorUsuarioId, EstadoCobro estadoCobro, MetodoPago metodoPago, LocalDateTime fechaDesde, LocalDateTime fechaHasta, Pageable pageable) { return cobroJpaRepository.buscarPorFiltros(atencionId, registradoPorUsuarioId, estadoCobro, metodoPago, fechaDesde, fechaHasta, pageable).map(cobroPersistenceMapper::toDomain); }
    @Override public boolean existsByAtencionId(Long atencionId) { return cobroJpaRepository.existsByAtencionId(atencionId); }
}
