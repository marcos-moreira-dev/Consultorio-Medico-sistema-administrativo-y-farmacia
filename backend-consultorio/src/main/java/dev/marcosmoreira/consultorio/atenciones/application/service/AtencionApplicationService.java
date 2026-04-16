package dev.marcosmoreira.consultorio.atenciones.application.service;

import dev.marcosmoreira.consultorio.atenciones.application.port.in.BuscarAtencionUseCase;
import dev.marcosmoreira.consultorio.atenciones.application.port.in.CrearAtencionUseCase;
import dev.marcosmoreira.consultorio.atenciones.application.port.in.ListarAtencionesUseCase;
import dev.marcosmoreira.consultorio.atenciones.application.port.out.AtencionPersistencePort;
import dev.marcosmoreira.consultorio.atenciones.domain.model.Atencion;
import dev.marcosmoreira.consultorio.citas.application.port.in.BuscarCitaUseCase;
import dev.marcosmoreira.consultorio.citas.domain.model.Cita;
import dev.marcosmoreira.consultorio.pacientes.application.port.in.BuscarPacienteUseCase;
import dev.marcosmoreira.consultorio.profesionales.application.port.in.BuscarProfesionalUseCase;
import dev.marcosmoreira.consultorio.shared.error.ResourceNotFoundException;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio de aplicación del módulo de atenciones.
 *
 * <p>Orquesta el registro, la consulta y el listado de atenciones del consultorio.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
public class AtencionApplicationService implements
        CrearAtencionUseCase,
        BuscarAtencionUseCase,
        ListarAtencionesUseCase {

    private final AtencionPersistencePort atencionPersistencePort;
    private final BuscarPacienteUseCase buscarPacienteUseCase;
    private final BuscarProfesionalUseCase buscarProfesionalUseCase;
    private final BuscarCitaUseCase buscarCitaUseCase;

    /**
     * Construye el servicio de aplicación del módulo.
     *
     * @param atencionPersistencePort puerto de persistencia de atenciones
     * @param buscarPacienteUseCase caso de uso para validar existencia de paciente
     * @param buscarProfesionalUseCase caso de uso para validar existencia de profesional
     * @param buscarCitaUseCase caso de uso para validar existencia y coherencia de cita
     */
    public AtencionApplicationService(
            AtencionPersistencePort atencionPersistencePort,
            BuscarPacienteUseCase buscarPacienteUseCase,
            BuscarProfesionalUseCase buscarProfesionalUseCase,
            BuscarCitaUseCase buscarCitaUseCase
    ) {
        this.atencionPersistencePort = atencionPersistencePort;
        this.buscarPacienteUseCase = buscarPacienteUseCase;
        this.buscarProfesionalUseCase = buscarProfesionalUseCase;
        this.buscarCitaUseCase = buscarCitaUseCase;
    }

    /**
     * Registra una nueva atención.
     *
     * @param atencion atención a registrar
     * @return atención persistida
     */
    @Override
    @Transactional
    public Atencion crear(Atencion atencion) {
        if (atencion == null) {
            throw new IllegalArgumentException("La atención es obligatoria.");
        }

        validatePositiveId(atencion.getPacienteId(), "El paciente es obligatorio y debe ser válido.");
        validatePositiveId(atencion.getProfesionalId(), "El profesional es obligatorio y debe ser válido.");
        validateOptionalPositiveId(atencion.getCitaId(), "Si se envía citaId, debe ser mayor que cero.");

        if (atencion.getFechaHoraAtencion() == null) {
            throw new IllegalArgumentException("La fecha y hora de atención es obligatoria.");
        }

        buscarPacienteUseCase.buscarPorId(atencion.getPacienteId());
        buscarProfesionalUseCase.buscarPorId(atencion.getProfesionalId());

        if (atencion.getCitaId() != null) {
            Cita cita = buscarCitaUseCase.buscarPorId(atencion.getCitaId());

            if (!Objects.equals(cita.getPacienteId(), atencion.getPacienteId())) {
                throw new IllegalArgumentException(
                        "La cita asociada no pertenece al paciente indicado."
                );
            }

            if (!Objects.equals(cita.getProfesionalId(), atencion.getProfesionalId())) {
                throw new IllegalArgumentException(
                        "La cita asociada no pertenece al profesional indicado."
                );
            }
        }

        atencion.setNotaBreve(normalizeNullableText(atencion.getNotaBreve()));
        atencion.setIndicacionesBreves(normalizeNullableText(atencion.getIndicacionesBreves()));

        return atencionPersistencePort.guardar(atencion);
    }

    /**
     * Busca una atención por su identificador único.
     *
     * @param atencionId identificador de la atención
     * @return atención encontrada
     */
    @Override
    public Atencion buscarPorId(Long atencionId) {
        validatePositiveId(atencionId, "El identificador de la atención debe ser mayor que cero.");

        return atencionPersistencePort.buscarPorId(atencionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró una atención con ID " + atencionId + "."
                ));
    }

    /**
     * Lista atenciones aplicando filtros opcionales.
     *
     * @param pacienteId identificador del paciente, o {@code null} si no aplica
     * @param profesionalId identificador del profesional, o {@code null} si no aplica
     * @param fechaDesde fecha/hora inicial, o {@code null} si no aplica
     * @param fechaHasta fecha/hora final, o {@code null} si no aplica
     * @return lista de atenciones encontradas
     */
    @Override
    public Page<Atencion> listar(Long pacienteId, Long profesionalId, LocalDateTime fechaDesde, LocalDateTime fechaHasta, Pageable pageable) {
        validateOptionalPositiveId(pacienteId, "Si se envía pacienteId, debe ser mayor que cero.");
        validateOptionalPositiveId(profesionalId, "Si se envía profesionalId, debe ser mayor que cero.");
        validateDateRange(fechaDesde, fechaHasta);
        Pageable sanitizedPageable = dev.marcosmoreira.consultorio.shared.util.PageableUtils.sanitize(
                pageable,
                Sort.by(Sort.Order.desc("fechaHoraAtencion"), Sort.Order.desc("atencionId")),
                "fechaHoraAtencion", "atencionId", "pacienteId", "profesionalId"
        );
        return atencionPersistencePort.listar(pacienteId, profesionalId, fechaDesde, fechaHasta, sanitizedPageable);
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
