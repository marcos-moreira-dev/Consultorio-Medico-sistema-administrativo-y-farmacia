package dev.marcosmoreira.consultorio.citas.application.service;

import dev.marcosmoreira.consultorio.citas.application.port.in.BuscarCitaUseCase;
import dev.marcosmoreira.consultorio.citas.application.port.in.CancelarCitaUseCase;
import dev.marcosmoreira.consultorio.citas.application.port.in.CrearCitaUseCase;
import dev.marcosmoreira.consultorio.citas.application.port.in.ListarAgendaUseCase;
import dev.marcosmoreira.consultorio.citas.application.port.in.ReprogramarCitaUseCase;
import dev.marcosmoreira.consultorio.citas.application.port.out.CitaPersistencePort;
import dev.marcosmoreira.consultorio.citas.domain.enums.EstadoCita;
import dev.marcosmoreira.consultorio.citas.domain.exception.AgendaOcupadaException;
import dev.marcosmoreira.consultorio.citas.domain.exception.CitaNoReprogramableException;
import dev.marcosmoreira.consultorio.citas.domain.model.Cita;
import dev.marcosmoreira.consultorio.pacientes.application.port.in.BuscarPacienteUseCase;
import dev.marcosmoreira.consultorio.profesionales.application.port.in.BuscarProfesionalUseCase;
import dev.marcosmoreira.consultorio.shared.error.ResourceNotFoundException;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio de aplicación del módulo de citas.
 *
 * <p>Orquesta la agenda operativa del consultorio: creación, consulta,
 * listado, cancelación y reprogramación.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
public class CitaApplicationService implements
        CrearCitaUseCase,
        BuscarCitaUseCase,
        ListarAgendaUseCase,
        CancelarCitaUseCase,
        ReprogramarCitaUseCase {

    private final CitaPersistencePort citaPersistencePort;
    private final BuscarPacienteUseCase buscarPacienteUseCase;
    private final BuscarProfesionalUseCase buscarProfesionalUseCase;

    /**
     * Construye el servicio de aplicación del módulo.
     *
     * @param citaPersistencePort puerto de persistencia de citas
     * @param buscarPacienteUseCase caso de uso para validar existencia de paciente
     * @param buscarProfesionalUseCase caso de uso para validar existencia de profesional
     */
    public CitaApplicationService(
            CitaPersistencePort citaPersistencePort,
            BuscarPacienteUseCase buscarPacienteUseCase,
            BuscarProfesionalUseCase buscarProfesionalUseCase
    ) {
        this.citaPersistencePort = citaPersistencePort;
        this.buscarPacienteUseCase = buscarPacienteUseCase;
        this.buscarProfesionalUseCase = buscarProfesionalUseCase;
    }

    /**
     * Registra una nueva cita.
     *
     * @param pacienteId identificador del paciente
     * @param profesionalId identificador del profesional
     * @param fechaHoraInicio fecha y hora de inicio
     * @param motivoBreve motivo breve
     * @param observacionOperativa observación operativa
     * @return cita creada
     */
    @Override
    @Transactional
    public Cita crear(
            Long pacienteId,
            Long profesionalId,
            LocalDateTime fechaHoraInicio,
            String motivoBreve,
            String observacionOperativa
    ) {
        validatePositiveId(pacienteId, "El paciente es obligatorio y debe ser válido.");
        validatePositiveId(profesionalId, "El profesional es obligatorio y debe ser válido.");

        if (fechaHoraInicio == null) {
            throw new IllegalArgumentException("La fecha y hora de inicio es obligatoria.");
        }

        buscarPacienteUseCase.buscarPorId(pacienteId);
        buscarProfesionalUseCase.buscarPorId(profesionalId);

        validateAgendaDisponibleForCreate(profesionalId, fechaHoraInicio);

        Cita cita = new Cita();
        cita.setPacienteId(pacienteId);
        cita.setProfesionalId(profesionalId);
        cita.setFechaHoraInicio(fechaHoraInicio);
        cita.setEstadoCita(EstadoCita.PROGRAMADA);
        cita.setMotivoBreve(normalizeNullableText(motivoBreve));
        cita.setObservacionOperativa(normalizeNullableText(observacionOperativa));

        return citaPersistencePort.guardar(cita);
    }

    /**
     * Busca una cita por su identificador único.
     *
     * @param citaId identificador de la cita
     * @return cita encontrada
     */
    @Override
    public Cita buscarPorId(Long citaId) {
        validatePositiveId(citaId, "El identificador de la cita debe ser mayor que cero.");

        return citaPersistencePort.buscarPorId(citaId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró una cita con ID " + citaId + "."
                ));
    }

    /**
     * Lista la agenda de citas con filtros opcionales.
     *
     * @param pacienteId identificador del paciente, o {@code null} si no aplica
     * @param profesionalId identificador del profesional, o {@code null} si no aplica
     * @param estadoCita estado lógico, o {@code null} si no aplica
     * @param fechaDesde fecha/hora inicial, o {@code null} si no aplica
     * @param fechaHasta fecha/hora final, o {@code null} si no aplica
     * @return lista de citas encontradas
     */
    @Override
    public Page<Cita> listar(Long pacienteId, Long profesionalId, EstadoCita estadoCita, LocalDateTime fechaDesde, LocalDateTime fechaHasta, Pageable pageable) {
        validateOptionalPositiveId(pacienteId, "Si se envía pacienteId, debe ser mayor que cero.");
        validateOptionalPositiveId(profesionalId, "Si se envía profesionalId, debe ser mayor que cero.");
        validateDateRange(fechaDesde, fechaHasta);
        Pageable sanitizedPageable = dev.marcosmoreira.consultorio.shared.util.PageableUtils.sanitize(
                pageable,
                Sort.by(Sort.Order.asc("fechaHoraInicio"), Sort.Order.asc("citaId")),
                "fechaHoraInicio", "citaId", "estadoCita", "pacienteId", "profesionalId"
        );
        return citaPersistencePort.listar(pacienteId, profesionalId, estadoCita, fechaDesde, fechaHasta, sanitizedPageable);
    }

    /**
     * Cancela una cita existente.
     *
     * @param citaId identificador de la cita
     * @param observacionOperativa observación operativa asociada
     * @return cita actualizada
     */
    @Override
    @Transactional
    public Cita cancelar(Long citaId, String observacionOperativa) {
        Cita cita = buscarPorId(citaId);

        if (!cita.isProgramada()) {
            throw new CitaNoReprogramableException(
                    "Solo se pueden cancelar citas que estén en estado PROGRAMADA."
            );
        }

        cita.setEstadoCita(EstadoCita.CANCELADA);
        cita.setObservacionOperativa(normalizeNullableText(observacionOperativa));

        return citaPersistencePort.guardar(cita);
    }

    /**
     * Reprograma una cita existente.
     *
     * @param citaId identificador de la cita
     * @param nuevaFechaHoraInicio nueva fecha y hora de inicio
     * @param observacionOperativa observación operativa asociada
     * @return cita actualizada
     */
    @Override
    @Transactional
    public Cita reprogramar(
            Long citaId,
            LocalDateTime nuevaFechaHoraInicio,
            String observacionOperativa
    ) {
        if (nuevaFechaHoraInicio == null) {
            throw new IllegalArgumentException("La nueva fecha y hora de inicio es obligatoria.");
        }

        Cita cita = buscarPorId(citaId);

        if (!cita.isProgramada()) {
            throw new CitaNoReprogramableException(
                    "Solo se pueden reprogramar citas que estén en estado PROGRAMADA."
            );
        }

        validateAgendaDisponibleForUpdate(
                cita.getProfesionalId(),
                nuevaFechaHoraInicio,
                citaId
        );

        cita.setFechaHoraInicio(nuevaFechaHoraInicio);
        cita.setObservacionOperativa(normalizeNullableText(observacionOperativa));

        return citaPersistencePort.guardar(cita);
    }

    /**
     * Valida que exista disponibilidad al crear una nueva cita.
     *
     * @param profesionalId identificador del profesional
     * @param fechaHoraInicio fecha y hora solicitada
     */
    private void validateAgendaDisponibleForCreate(Long profesionalId, LocalDateTime fechaHoraInicio) {
        if (citaPersistencePort.existsByProfesionalIdAndFechaHoraInicio(profesionalId, fechaHoraInicio)) {
            throw new AgendaOcupadaException(
                    "El profesional ya tiene una cita registrada para la fecha y hora indicada."
            );
        }
    }

    /**
     * Valida que exista disponibilidad al reprogramar una cita.
     *
     * @param profesionalId identificador del profesional
     * @param fechaHoraInicio nueva fecha y hora solicitada
     * @param citaId identificador de la cita actual
     */
    private void validateAgendaDisponibleForUpdate(
            Long profesionalId,
            LocalDateTime fechaHoraInicio,
            Long citaId
    ) {
        if (citaPersistencePort.existsByProfesionalIdAndFechaHoraInicioAndCitaIdNot(
                profesionalId,
                fechaHoraInicio,
                citaId
        )) {
            throw new AgendaOcupadaException(
                    "El profesional ya tiene otra cita registrada para la nueva fecha y hora indicada."
            );
        }
    }

    /**
     * Valida que un identificador obligatorio sea positivo.
     *
     * @param value identificador a validar
     * @param message mensaje de error
     */
    private void validatePositiveId(Long value, String message) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Valida que un identificador opcional, si existe, sea positivo.
     *
     * @param value identificador opcional a validar
     * @param message mensaje de error
     */
    private void validateOptionalPositiveId(Long value, String message) {
        if (value != null && value <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Valida que un rango temporal sea coherente.
     *
     * @param fechaDesde fecha inicial
     * @param fechaHasta fecha final
     */
    private void validateDateRange(LocalDateTime fechaDesde, LocalDateTime fechaHasta) {
        if (fechaDesde != null && fechaHasta != null && fechaDesde.isAfter(fechaHasta)) {
            throw new IllegalArgumentException("La fecha inicial no puede ser posterior a la fecha final.");
        }
    }

    /**
     * Normaliza un texto opcional.
     *
     * @param value texto a normalizar
     * @return texto normalizado o {@code null} si queda vacío
     */
    private String normalizeNullableText(String value) {
        if (value == null) {
            return null;
        }

        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }
}
