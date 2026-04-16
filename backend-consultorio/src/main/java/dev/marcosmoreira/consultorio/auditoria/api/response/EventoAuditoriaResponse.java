package dev.marcosmoreira.consultorio.auditoria.api.response;

import dev.marcosmoreira.consultorio.auditoria.domain.enums.TipoEventoAuditoria;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

/**
 * DTO de salida para representar un evento de auditoría.
 *
 * <p>Este objeto funciona como modelo de lectura del módulo de auditoría en
 * la versión 1.0. Dado que el submódulo todavía no tiene una fuente de eventos
 * formalizada como agregado o entidad independiente dentro del dominio, este
 * response se usa también como proyección de consulta.</p>
 *
 * <p>Esto no es lo ideal en una arquitectura madura, pero sí es una decisión
 * razonable para una primera versión orientada a lectura, donde aún no vale la
 * pena introducir más tipos internos solo por formalismo.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class EventoAuditoriaResponse {

    private String eventoId;
    private String modulo;
    private TipoEventoAuditoria tipoEvento;
    private Long usuarioId;
    private String username;
    private String descripcion;
    private String entidad;
    private String entidadId;
    private String correlationId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaHoraEvento;

    /**
     * Constructor vacío requerido por serialización.
     */
    public EventoAuditoriaResponse() {
    }

    /**
     * Construye una respuesta completa de evento de auditoría.
     *
     * @param eventoId identificador lógico del evento
     * @param modulo módulo funcional asociado al evento
     * @param tipoEvento tipo de evento ocurrido
     * @param usuarioId identificador del usuario relacionado con el evento
     * @param username nombre de usuario relacionado, si se conoce
     * @param descripcion descripción legible del evento
     * @param entidad nombre de la entidad de negocio relacionada
     * @param entidadId identificador lógico de la entidad afectada
     * @param correlationId identificador de correlación transversal
     * @param fechaHoraEvento fecha y hora del evento
     */
    public EventoAuditoriaResponse(
            String eventoId,
            String modulo,
            TipoEventoAuditoria tipoEvento,
            Long usuarioId,
            String username,
            String descripcion,
            String entidad,
            String entidadId,
            String correlationId,
            LocalDateTime fechaHoraEvento
    ) {
        this.eventoId = normalizeNullableText(eventoId);
        this.modulo = normalizeNullableText(modulo);
        this.tipoEvento = tipoEvento;
        this.usuarioId = usuarioId;
        this.username = normalizeNullableText(username);
        this.descripcion = normalizeNullableText(descripcion);
        this.entidad = normalizeNullableText(entidad);
        this.entidadId = normalizeNullableText(entidadId);
        this.correlationId = normalizeNullableText(correlationId);
        this.fechaHoraEvento = fechaHoraEvento;
    }

    public String getEventoId() {
        return eventoId;
    }

    public void setEventoId(String eventoId) {
        this.eventoId = normalizeNullableText(eventoId);
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = normalizeNullableText(modulo);
    }

    public TipoEventoAuditoria getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(TipoEventoAuditoria tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = normalizeNullableText(username);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = normalizeNullableText(descripcion);
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = normalizeNullableText(entidad);
    }

    public String getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(String entidadId) {
        this.entidadId = normalizeNullableText(entidadId);
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = normalizeNullableText(correlationId);
    }

    public LocalDateTime getFechaHoraEvento() {
        return fechaHoraEvento;
    }

    public void setFechaHoraEvento(LocalDateTime fechaHoraEvento) {
        this.fechaHoraEvento = fechaHoraEvento;
    }

    /**
     * Indica si el evento está asociado a un usuario concreto.
     *
     * @return {@code true} si existe usuario asociado; {@code false} en caso contrario
     */
    public boolean hasUsuarioAsociado() {
        return usuarioId != null;
    }

    /**
     * Indica si el evento tiene correlación registrada.
     *
     * @return {@code true} si existe correlation id; {@code false} en caso contrario
     */
    public boolean hasCorrelationId() {
        return correlationId != null && !correlationId.isBlank();
    }

    /**
     * Normaliza un texto opcional.
     *
     * @param value valor a normalizar
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
