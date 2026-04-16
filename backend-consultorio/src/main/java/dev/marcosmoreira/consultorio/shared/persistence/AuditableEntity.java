package dev.marcosmoreira.consultorio.shared.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Clase base auditable para entidades JPA que necesiten timestamps técnicos
 * de creación y última modificación, además de control de concurrencia
 * optimista mediante {@code @Version}.
 *
 * <p>Su función es centralizar atributos repetidos entre entidades persistentes.
 * No reemplaza la auditoría funcional del negocio, sino que cubre solamente
 * metadatos técnicos de persistencia.</p>
 *
 * <p>El campo {@code version} previene lost updates: si dos transacciones
 * intentan modificar el mismo registro simultáneamente, la segunda fallará
 * con {@link jakarta.persistence.OptimisticLockException}, protegiendo
 * la integridad de los datos sin necesidad de bloqueos de base de datos.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity {

    @CreatedDate
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @LastModifiedDate
    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;

    /**
     * Versión de concurrencia optimista.
     *
     * <p>JPA incrementa automáticamente este valor en cada actualización.
     * Si una transacción intenta actualizar un registro cuya versión ha
     * cambiado desde que fue leído, se lanza
     * {@link jakarta.persistence.OptimisticLockException}.</p>
     */
    @Version
    @Column(name = "version", nullable = false)
    private Long version = 0L;

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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
