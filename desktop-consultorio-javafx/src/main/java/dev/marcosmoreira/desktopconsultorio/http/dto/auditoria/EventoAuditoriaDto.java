package dev.marcosmoreira.desktopconsultorio.http.dto.auditoria;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/**
 * Evento de auditoría registrado por el sistema.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventoAuditoriaDto {

    @JsonProperty("eventoId")
    private Long eventoId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("tipoEvento")
    private String tipoEvento;

    @JsonProperty("modulo")
    private String modulo;

    @JsonProperty("descripcion")
    private String descripcion;

    @JsonProperty("entidadId")
    private Long entidadId;

    @JsonProperty("entidadTipo")
    private String entidadTipo;

    @JsonProperty("fechaHora")
    private LocalDateTime fechaHora;

    @JsonProperty("direccionIp")
    private String direccionIp;

    public Long getEventoId() { return eventoId; }
    public void setEventoId(Long eventoId) { this.eventoId = eventoId; }
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
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public String getDireccionIp() { return direccionIp; }
    public void setDireccionIp(String direccionIp) { this.direccionIp = direccionIp; }
}
