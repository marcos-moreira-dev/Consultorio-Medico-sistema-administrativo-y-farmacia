package dev.marcosmoreira.consultorio.auditoria.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity que representa un evento de auditoría registrado en el sistema.
 *
 * <p>Corresponde a la tabla {@code audit_log} del schema {@code consultorio}.
 * Cada registro captura un hecho operativo relevante: login, creación de
 * paciente, registro de cita, cobro, cancelación, reprogramación, etc.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Entity
@Table(name = "audit_log", schema = "consultorio")
public class AuditLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evento_id")
    private Long eventoId;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "username", length = 100)
    private String username;

    @Column(name = "tipo_evento", length = 50, nullable = false)
    private String tipoEvento;

    @Column(name = "modulo", length = 50, nullable = false)
    private String modulo;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "entidad_id")
    private Long entidadId;

    @Column(name = "entidad_tipo", length = 50)
    private String entidadTipo;

    @Column(name = "correlation_id", length = 100)
    private String correlationId;

    @Column(name = "direccion_ip", length = 45)
    private String direccionIp;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    public Long getEventoId() { return eventoId; }
    public void setEventoId(Long eventoId) { this.eventoId = eventoId; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getTipoEvento() { return tipoEvento; }
    public void setTipoEvento(String tipoEvento) { this.tipoEvento = tipoEvento; }
    public String getModulo() { return modulo; }
    public void setModulo(String modulo) { this.modulo = modulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Long getEntidadId() { return entidadId; }
    public void setEntidadId(Long entidadId) { this.entidadId = entidadId; }
    public String getEntidadTipo() { return entidadTipo; }
    public void setEntidadTipo(String entidadTipo) { this.entidadTipo = entidadTipo; }
    public String getCorrelationId() { return correlationId; }
    public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }
    public String getDireccionIp() { return direccionIp; }
    public void setDireccionIp(String direccionIp) { this.direccionIp = direccionIp; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
}
