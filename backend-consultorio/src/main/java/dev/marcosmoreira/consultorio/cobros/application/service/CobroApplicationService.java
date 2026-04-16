package dev.marcosmoreira.consultorio.cobros.application.service;

import dev.marcosmoreira.consultorio.atenciones.application.port.in.BuscarAtencionUseCase;
import dev.marcosmoreira.consultorio.cobros.application.port.in.BuscarCobroUseCase;
import dev.marcosmoreira.consultorio.cobros.application.port.in.ListarCobrosUseCase;
import dev.marcosmoreira.consultorio.cobros.application.port.in.RegistrarCobroUseCase;
import dev.marcosmoreira.consultorio.cobros.application.port.out.CobroPersistencePort;
import dev.marcosmoreira.consultorio.cobros.domain.enums.EstadoCobro;
import dev.marcosmoreira.consultorio.cobros.domain.enums.MetodoPago;
import dev.marcosmoreira.consultorio.cobros.domain.model.Cobro;
import dev.marcosmoreira.consultorio.shared.error.DuplicateResourceException;
import dev.marcosmoreira.consultorio.shared.error.ResourceNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio de aplicación del módulo de cobros.
 *
 * <p>Orquesta el registro, la consulta y el listado administrativo de cobros
 * asociados a atenciones del consultorio.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
public class CobroApplicationService implements
        RegistrarCobroUseCase,
        BuscarCobroUseCase,
        ListarCobrosUseCase {

    private final CobroPersistencePort cobroPersistencePort;
    private final BuscarAtencionUseCase buscarAtencionUseCase;

    /**
     * Construye el servicio de aplicación del módulo.
     *
     * @param cobroPersistencePort puerto de persistencia de cobros
     * @param buscarAtencionUseCase caso de uso para validar existencia de atención
     */
    public CobroApplicationService(
            CobroPersistencePort cobroPersistencePort,
            BuscarAtencionUseCase buscarAtencionUseCase
    ) {
        this.cobroPersistencePort = cobroPersistencePort;
        this.buscarAtencionUseCase = buscarAtencionUseCase;
    }

    /**
     * Registra un nuevo cobro.
     *
     * @param atencionId identificador de la atención asociada
     * @param registradoPorUsuarioId identificador del usuario que registra, si existe
     * @param monto monto del cobro
     * @param metodoPago método de pago
     * @param estadoCobro estado lógico inicial
     * @param observacionAdministrativa observación administrativa
     * @return cobro registrado
     */
    @Override
    @Transactional
    public Cobro registrar(
            Long atencionId,
            Long registradoPorUsuarioId,
            BigDecimal monto,
            MetodoPago metodoPago,
            EstadoCobro estadoCobro,
            String observacionAdministrativa
    ) {
        validatePositiveId(atencionId, "La atención es obligatoria y debe ser válida.");
        validateOptionalPositiveId(
                registradoPorUsuarioId,
                "Si se envía registradoPorUsuarioId, debe ser mayor que cero."
        );
        validateMonto(monto);

        if (metodoPago == null) {
            throw new IllegalArgumentException("El método de pago es obligatorio.");
        }

        if (estadoCobro == null) {
            throw new IllegalArgumentException("El estado del cobro es obligatorio.");
        }

        buscarAtencionUseCase.buscarPorId(atencionId);

        if (cobroPersistencePort.existsByAtencionId(atencionId)) {
            throw new DuplicateResourceException(
                    "Ya existe un cobro asociado a la atención con ID " + atencionId + "."
            );
        }

        Cobro cobro = new Cobro();
        cobro.setAtencionId(atencionId);
        cobro.setRegistradoPorUsuarioId(registradoPorUsuarioId);
        cobro.setMonto(monto);
        cobro.setMetodoPago(metodoPago);
        cobro.setEstadoCobro(estadoCobro);
        cobro.setObservacionAdministrativa(normalizeNullableText(observacionAdministrativa));

        return cobroPersistencePort.guardar(cobro);
    }

    /**
     * Busca un cobro por su identificador único.
     *
     * @param cobroId identificador del cobro
     * @return cobro encontrado
     */
    @Override
    public Cobro buscarPorId(Long cobroId) {
        validatePositiveId(cobroId, "El identificador del cobro debe ser mayor que cero.");

        return cobroPersistencePort.buscarPorId(cobroId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró un cobro con ID " + cobroId + "."
                ));
    }

    /**
     * Lista cobros aplicando filtros opcionales.
     *
     * @param atencionId identificador de la atención, o {@code null} si no aplica
     * @param registradoPorUsuarioId identificador del usuario registrador, o {@code null} si no aplica
     * @param estadoCobro estado lógico, o {@code null} si no aplica
     * @param metodoPago método de pago, o {@code null} si no aplica
     * @param fechaDesde fecha/hora inicial de registro, o {@code null} si no aplica
     * @param fechaHasta fecha/hora final de registro, o {@code null} si no aplica
     * @return lista de cobros encontrados
     */
    @Override
    public Page<Cobro> listar(Long atencionId, Long registradoPorUsuarioId, EstadoCobro estadoCobro, MetodoPago metodoPago, LocalDateTime fechaDesde, LocalDateTime fechaHasta, Pageable pageable) {
        validateOptionalPositiveId(atencionId, "Si se envía atencionId, debe ser mayor que cero.");
        validateOptionalPositiveId(registradoPorUsuarioId, "Si se envía registradoPorUsuarioId, debe ser mayor que cero.");
        validateDateRange(fechaDesde, fechaHasta);
        Pageable sanitizedPageable = dev.marcosmoreira.consultorio.shared.util.PageableUtils.sanitize(
                pageable,
                Sort.by(Sort.Order.desc("fechaHoraRegistro"), Sort.Order.desc("cobroId")),
                "fechaHoraRegistro", "cobroId", "estadoCobro", "metodoPago", "registradoPorUsuarioId"
        );
        return cobroPersistencePort.listar(atencionId, registradoPorUsuarioId, estadoCobro, metodoPago, fechaDesde, fechaHasta, sanitizedPageable);
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
     * @param value identificador a validar
     * @param message mensaje de error
     */
    private void validateOptionalPositiveId(Long value, String message) {
        if (value != null && value <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Valida que el monto no sea nulo ni negativo.
     *
     * @param monto monto a validar
     */
    private void validateMonto(BigDecimal monto) {
        if (monto == null) {
            throw new IllegalArgumentException("El monto es obligatorio.");
        }

        if (monto.signum() < 0) {
            throw new IllegalArgumentException("El monto no puede ser negativo.");
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
