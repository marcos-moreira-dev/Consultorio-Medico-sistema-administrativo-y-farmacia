package dev.marcosmoreira.consultorio.cobros.domain.model;

import dev.marcosmoreira.consultorio.cobros.domain.enums.EstadoCobro;
import dev.marcosmoreira.consultorio.cobros.domain.enums.MetodoPago;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Modelo de dominio que representa un cobro administrativo
 * asociado a una atención del consultorio.
 *
 * <p>El cobro es la contraparte administrativa de una atención: no puede existir
 * sin una atención previa (regla de negocio RN-040) y cada atención solo puede
 * tener un cobro asociado (validado en {@code CobroApplicationService}).
 * Esta separación entre atención clínica y cobro administrativo refleja la
 * realidad operativa del consultorio, donde un paciente puede recibir atención
 * pero posponer el pago.</p>
 *
 * <p>Los métodos {@link #isPagado()} y {@link #hasRegistradoPorUsuarioId()}
 * encapsulan la lógica de consulta de estado, evitando que los servicios
 * accedan directamente a los campos internos.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 * @see dev.marcosmoreira.consultorio.cobros.application.service.CobroApplicationService
 *      para las reglas de validación y creación
 */
public class Cobro {

    private Long cobroId;
    private Long atencionId;
    private Long registradoPorUsuarioId;
    private BigDecimal monto;
    private MetodoPago metodoPago;
    private EstadoCobro estadoCobro;
    private String observacionAdministrativa;
    private LocalDateTime fechaHoraRegistro;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    /**
     * Constructor vacío.
     */
    public Cobro() {
    }

    /**
     * Construye un cobro completo.
     *
     * @param cobroId identificador del cobro
     * @param atencionId identificador de la atención asociada
     * @param registradoPorUsuarioId identificador del usuario que registró el cobro
     * @param monto monto registrado
     * @param metodoPago método de pago
     * @param estadoCobro estado lógico del cobro
     * @param observacionAdministrativa observación administrativa
     * @param fechaHoraRegistro fecha/hora efectiva de registro
     * @param fechaCreacion fecha técnica de creación
     * @param fechaActualizacion fecha técnica de última actualización
     */
    public Cobro(
            Long cobroId,
            Long atencionId,
            Long registradoPorUsuarioId,
            BigDecimal monto,
            MetodoPago metodoPago,
            EstadoCobro estadoCobro,
            String observacionAdministrativa,
            LocalDateTime fechaHoraRegistro,
            LocalDateTime fechaCreacion,
            LocalDateTime fechaActualizacion
    ) {
        this.cobroId = cobroId;
        this.atencionId = atencionId;
        this.registradoPorUsuarioId = registradoPorUsuarioId;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.estadoCobro = estadoCobro;
        this.observacionAdministrativa = normalizeNullableText(observacionAdministrativa);
        this.fechaHoraRegistro = fechaHoraRegistro;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Long getCobroId() {
        return cobroId;
    }

    public void setCobroId(Long cobroId) {
        this.cobroId = cobroId;
    }

    public Long getAtencionId() {
        return atencionId;
    }

    public void setAtencionId(Long atencionId) {
        this.atencionId = atencionId;
    }

    public Long getRegistradoPorUsuarioId() {
        return registradoPorUsuarioId;
    }

    public void setRegistradoPorUsuarioId(Long registradoPorUsuarioId) {
        this.registradoPorUsuarioId = registradoPorUsuarioId;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public EstadoCobro getEstadoCobro() {
        return estadoCobro;
    }

    public void setEstadoCobro(EstadoCobro estadoCobro) {
        this.estadoCobro = estadoCobro;
    }

    public String getObservacionAdministrativa() {
        return observacionAdministrativa;
    }

    public void setObservacionAdministrativa(String observacionAdministrativa) {
        this.observacionAdministrativa = normalizeNullableText(observacionAdministrativa);
    }

    public LocalDateTime getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    /**
     * Indica si el cobro está marcado como pagado.
     *
     * @return {@code true} si el estado es PAGADO; {@code false} en otro caso
     */
    public boolean isPagado() {
        return estadoCobro != null && estadoCobro.isPagado();
    }

    /**
     * Indica si existe usuario registrador informado.
     *
     * @return {@code true} si existe usuario registrador; {@code false} en caso contrario
     */
    public boolean hasRegistradoPorUsuarioId() {
        return registradoPorUsuarioId != null;
    }

    /**
     * Normaliza un texto opcional.
     *
     * @param value texto a normalizar
     * @return texto con trim aplicado o {@code null} si queda vacío
     */
    private String normalizeNullableText(String value) {
        if (value == null) {
            return null;
        }

        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }
}
