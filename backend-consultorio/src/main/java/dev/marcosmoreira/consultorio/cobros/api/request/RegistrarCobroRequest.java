package dev.marcosmoreira.consultorio.cobros.api.request;

import dev.marcosmoreira.consultorio.cobros.domain.enums.EstadoCobro;
import dev.marcosmoreira.consultorio.cobros.domain.enums.MetodoPago;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * DTO de entrada para registrar un cobro.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class RegistrarCobroRequest {

    @NotNull(message = "La atención es obligatoria.")
    @Positive(message = "La atención debe ser mayor que cero.")
    private Long atencionId;

    @Positive(message = "Si se envía registradoPorUsuarioId, debe ser mayor que cero.")
    private Long registradoPorUsuarioId;

    @NotNull(message = "El monto es obligatorio.")
    @DecimalMin(value = "0.00", inclusive = true, message = "El monto no puede ser negativo.")
    private BigDecimal monto;

    @NotNull(message = "El método de pago es obligatorio.")
    private MetodoPago metodoPago;

    @NotNull(message = "El estado del cobro es obligatorio.")
    private EstadoCobro estadoCobro;

    @Size(max = 300, message = "La observación administrativa no puede exceder los 300 caracteres.")
    private String observacionAdministrativa;

    /**
     * Constructor vacío requerido por serialización.
     */
    public RegistrarCobroRequest() {
    }

    /**
     * Construye el request completo de registro.
     *
     * @param atencionId identificador de la atención
     * @param registradoPorUsuarioId identificador del usuario que registra el cobro
     * @param monto monto del cobro
     * @param metodoPago método de pago
     * @param estadoCobro estado lógico del cobro
     * @param observacionAdministrativa observación administrativa
     */
    public RegistrarCobroRequest(
            Long atencionId,
            Long registradoPorUsuarioId,
            BigDecimal monto,
            MetodoPago metodoPago,
            EstadoCobro estadoCobro,
            String observacionAdministrativa
    ) {
        this.atencionId = atencionId;
        this.registradoPorUsuarioId = registradoPorUsuarioId;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.estadoCobro = estadoCobro;
        this.observacionAdministrativa = normalizeNullableText(observacionAdministrativa);
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
