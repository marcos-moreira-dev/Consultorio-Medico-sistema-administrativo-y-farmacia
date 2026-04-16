package dev.marcosmoreira.consultorio.dashboard.application.service;

import dev.marcosmoreira.consultorio.atenciones.infrastructure.persistence.repository.AtencionJpaRepository;
import dev.marcosmoreira.consultorio.citas.infrastructure.persistence.repository.CitaJpaRepository;
import dev.marcosmoreira.consultorio.cobros.domain.enums.EstadoCobro;
import dev.marcosmoreira.consultorio.cobros.infrastructure.persistence.repository.CobroJpaRepository;
import dev.marcosmoreira.consultorio.dashboard.api.response.DashboardResumenResponse;
import dev.marcosmoreira.consultorio.dashboard.application.port.in.ObtenerDashboardResumenUseCase;
import dev.marcosmoreira.consultorio.pacientes.infrastructure.persistence.repository.PacienteJpaRepository;
import dev.marcosmoreira.consultorio.profesionales.infrastructure.persistence.repository.ProfesionalJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
public class DashboardApplicationService implements ObtenerDashboardResumenUseCase {

    private final PacienteJpaRepository pacienteRepo;
    private final CitaJpaRepository citaRepo;
    private final AtencionJpaRepository atencionRepo;
    private final CobroJpaRepository cobroRepo;
    private final ProfesionalJpaRepository profesionalRepo;

    public DashboardApplicationService(PacienteJpaRepository pacienteRepo,
                                        CitaJpaRepository citaRepo,
                                        AtencionJpaRepository atencionRepo,
                                        CobroJpaRepository cobroRepo,
                                        ProfesionalJpaRepository profesionalRepo) {
        this.pacienteRepo = pacienteRepo;
        this.citaRepo = citaRepo;
        this.atencionRepo = atencionRepo;
        this.cobroRepo = cobroRepo;
        this.profesionalRepo = profesionalRepo;
    }

    @Override
    public DashboardResumenResponse obtenerResumen() {
        LocalDate hoy = LocalDate.now();
        LocalDateTime inicioHoy = hoy.atStartOfDay();
        LocalDateTime finHoy = hoy.plusDays(1).atStartOfDay();
        LocalDateTime inicioSemana = hoy.minusDays(7).atStartOfDay();
        LocalDateTime finSemana = hoy.plusDays(1).atStartOfDay();
        LocalDateTime inicioMes = hoy.withDayOfMonth(1).atStartOfDay();
        LocalDateTime finMes = hoy.plusDays(1).atStartOfDay();

        Long totalPacientes = pacienteRepo.count();
        Long citasHoy = citaRepo.buscarPorFiltros(null, null, null, inicioHoy, finHoy, org.springframework.data.domain.Pageable.ofSize(1)).getTotalElements();
        Long citasSemana = citaRepo.buscarPorFiltros(null, null, null, inicioSemana, finSemana, org.springframework.data.domain.Pageable.ofSize(1)).getTotalElements();
        Long atencionesMes = atencionRepo.buscarPorFiltros(null, null, inicioMes, finMes, org.springframework.data.domain.Pageable.ofSize(1)).getTotalElements();
        Long cobrosMes = cobroRepo.buscarPorFiltros(null, null, null, null, inicioMes, finMes, org.springframework.data.domain.Pageable.ofSize(1)).getTotalElements();
        Long cobrosPendientes = cobroRepo.buscarPorFiltros(null, null, EstadoCobro.PENDIENTE, null, null, null, org.springframework.data.domain.Pageable.ofSize(1)).getTotalElements();
        Long profesionalesActivos = profesionalRepo.countByEstadoProfesional(dev.marcosmoreira.consultorio.profesionales.domain.enums.EstadoProfesional.ACTIVO);

        BigDecimal totalCobradoMes = cobroRepo.buscarPorFiltros(null, null, EstadoCobro.PAGADO, null, inicioMes, finMes, org.springframework.data.domain.Pageable.unpaged())
                .stream()
                .map(c -> c.getMonto() != null ? c.getMonto() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new DashboardResumenResponse(
                totalPacientes,
                citasHoy,
                citasSemana,
                atencionesMes,
                cobrosMes,
                totalCobradoMes,
                cobrosPendientes,
                profesionalesActivos
        );
    }
}
