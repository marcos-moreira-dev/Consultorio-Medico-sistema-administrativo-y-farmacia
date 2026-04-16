package dev.marcosmoreira.consultorio.cobros.infrastructure.persistence.entity;

import dev.marcosmoreira.consultorio.cobros.domain.enums.EstadoCobro;
import dev.marcosmoreira.consultorio.cobros.domain.enums.MetodoPago;
import dev.marcosmoreira.consultorio.shared.persistence.AuditableEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad JPA del módulo de cobros.
 *
 * <p>Se alinea con la tabla {@code cobro} de la base de datos V2 del proyecto.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Entity
@Table(schema = "consultorio", name = "cobro")
public class CobroEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cobro_id")
    private Long cobroId;

    @Column(name = "atencion_id", nullable = false)
    private Long atencionId;

    @Column(name = "registrado_por_usuario_id")
    private Long registradoPorUsuarioId;

    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false, length = 20)
    private MetodoPago metodoPago;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_cobro", nullable = false, length = 20)
    private EstadoCobro estadoCobro;

    @Column(name = "observacion_administrativa", length = 300)
    private String observacionAdministrativa;

    @Column(name = "fecha_hora_registro", nullable = false)
    private LocalDateTime fechaHoraRegistro;

    /**
     * Constructor vacío requerido por JPA.
     */
    public CobroEntity() {
    }

    /**
     * Construye una entidad completa de cobro.
     *
     * @param cobroId identificador del cobro
     * @param atencionId identificador de la atención asociada
     * @param registradoPorUsuarioId identificador del usuario registrador
     * @param monto monto del cobro
     * @param metodoPago método de pago
     * @param estadoCobro estado lógico
     * @param observacionAdministrativa observación administrativa
     * @param fechaHoraRegistro fecha/hora efectiva de registro
     */
    public CobroEntity(
            Long cobroId,
            Long atencionId,
            Long registradoPorUsuarioId,
            BigDecimal monto,
            MetodoPago metodoPago,
            EstadoCobro estadoCobro,
            String observacionAdministrativa,
            LocalDateTime fechaHoraRegistro
    ) {
        this.cobroId = cobroId;
        this.atencionId = atencionId;
        this.registradoPorUsuarioId = registradoPorUsuarioId;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.estadoCobro = estadoCobro;
        this.observacionAdministrativa = normalizeNullableText(observacionAdministrativa);
        this.fechaHoraRegistro = fechaHoraRegistro;
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

    /**
     * Inicializa la fecha/hora de registro si aún no fue informada.
     */
    @PrePersist
    protected void onCreate() {
        if (this.fechaHoraRegistro == null) {
            this.fechaHoraRegistro = LocalDateTime.now();
        }
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
