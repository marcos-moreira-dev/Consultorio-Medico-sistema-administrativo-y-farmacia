package dev.marcosmoreira.consultorio.cobros.api.response;

import dev.marcosmoreira.consultorio.cobros.domain.model.Cobro;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de salida con el detalle completo de un cobro.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class CobroResponse {

    private Long cobroId;
    private Long atencionId;
    private Long registradoPorUsuarioId;
    private BigDecimal monto;
    private String metodoPago;
    private String estadoCobro;
    private String observacionAdministrativa;
    private LocalDateTime fechaHoraRegistro;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    /**
     * Constructor vacío requerido por serialización.
     */
    public CobroResponse() {
    }

    /**
     * Construye la respuesta completa.
     *
     * @param cobroId identificador del cobro
     * @param atencionId identificador de la atención asociada
     * @param registradoPorUsuarioId identificador del usuario que registró el cobro
     * @param monto monto registrado
     * @param metodoPago método de pago
     * @param estadoCobro estado lógico del cobro
     * @param observacionAdministrativa observación administrativa
     * @param fechaHoraRegistro fecha/hora efectiva de registro del cobro
     * @param fechaCreacion fecha técnica de creación
     * @param fechaActualizacion fecha técnica de última actualización
     */
    public CobroResponse(
            Long cobroId,
            Long atencionId,
            Long registradoPorUsuarioId,
            BigDecimal monto,
            String metodoPago,
            String estadoCobro,
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
        this.observacionAdministrativa = observacionAdministrativa;
        this.fechaHoraRegistro = fechaHoraRegistro;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    /**
     * Construye la respuesta a partir del dominio.
     *
     * @param cobro cobro del dominio
     * @return DTO listo para serializar
     */
    public static CobroResponse fromDomain(Cobro cobro) {
        if (cobro == null) {
            throw new IllegalArgumentException("El cobro no puede ser nulo.");
        }

        return new CobroResponse(
                cobro.getCobroId(),
                cobro.getAtencionId(),
                cobro.getRegistradoPorUsuarioId(),
                cobro.getMonto(),
                cobro.getMetodoPago() == null ? null : cobro.getMetodoPago().name(),
                cobro.getEstadoCobro() == null ? null : cobro.getEstadoCobro().name(),
                cobro.getObservacionAdministrativa(),
                cobro.getFechaHoraRegistro(),
                cobro.getFechaCreacion(),
                cobro.getFechaActualizacion()
        );
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

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getEstadoCobro() {
        return estadoCobro;
    }

    public void setEstadoCobro(String estadoCobro) {
        this.estadoCobro = estadoCobro;
    }

    public String getObservacionAdministrativa() {
        return observacionAdministrativa;
    }

    public void setObservacionAdministrativa(String observacionAdministrativa) {
        this.observacionAdministrativa = observacionAdministrativa;
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
}
