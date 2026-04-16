package dev.marcosmoreira.consultorio.atenciones.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.marcosmoreira.consultorio.atenciones.domain.model.Atencion;
import java.time.LocalDateTime;

/**
 * DTO de salida para exponer información de una atención.
 *
 * <p>Este response representa la vista HTTP de una atención dentro del módulo
 * de consultorio. Su propósito es devolver al cliente datos claros, estables
 * y desacoplados de cualquier detalle accidental de la persistencia.</p>
 *
 * <p>En V1.0 se expone un detalle suficientemente útil para consultas individuales
 * y listados simples:</p>
 *
 * <ul>
 *   <li>identificadores principales;</li>
 *   <li>fecha y hora de atención;</li>
 *   <li>nota breve;</li>
 *   <li>indicaciones breves;</li>
 *   <li>timestamps de creación y actualización.</li>
 * </ul>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class AtencionResponse {

    private Long atencionId;
    private Long pacienteId;
    private Long profesionalId;
    private Long citaId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaHoraAtencion;

    private String notaBreve;
    private String indicacionesBreves;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaCreacion;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaActualizacion;

    /**
     * Constructor vacío requerido por herramientas de serialización.
     */
    public AtencionResponse() {
    }

    /**
     * Construye una respuesta completa de atención.
     *
     * @param atencionId identificador único de la atención
     * @param pacienteId identificador del paciente atendido
     * @param profesionalId identificador del profesional que realizó la atención
     * @param citaId identificador de la cita asociada, si existe
     * @param fechaHoraAtencion fecha y hora efectiva de la atención
     * @param notaBreve nota breve registrada para la atención
     * @param indicacionesBreves indicaciones breves registradas, si existen
     * @param fechaCreacion fecha y hora de creación del registro
     * @param fechaActualizacion fecha y hora de última actualización del registro
     */
    public AtencionResponse(
            Long atencionId,
            Long pacienteId,
            Long profesionalId,
            Long citaId,
            LocalDateTime fechaHoraAtencion,
            String notaBreve,
            String indicacionesBreves,
            LocalDateTime fechaCreacion,
            LocalDateTime fechaActualizacion
    ) {
        this.atencionId = atencionId;
        this.pacienteId = pacienteId;
        this.profesionalId = profesionalId;
        this.citaId = citaId;
        this.fechaHoraAtencion = fechaHoraAtencion;
        this.notaBreve = notaBreve;
        this.indicacionesBreves = indicacionesBreves;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    /**
     * Crea un DTO de respuesta a partir de un objeto del dominio.
     *
     * <p>Este método centraliza una transformación simple de dominio a contrato HTTP.
     * Más adelante, si el proyecto crece en complejidad, esta responsabilidad podría
     * moverse a un mapper explícito.</p>
     *
     * @param atencion objeto del dominio a transformar
     * @return DTO de respuesta listo para serializar a JSON
     * @throws IllegalArgumentException si la atención recibida es nula
     */
    public static AtencionResponse fromDomain(Atencion atencion) {
        if (atencion == null) {
            throw new IllegalArgumentException("La atención no puede ser nula para construir la respuesta.");
        }

        return new AtencionResponse(
                atencion.getAtencionId(),
                atencion.getPacienteId(),
                atencion.getProfesionalId(),
                atencion.getCitaId(),
                atencion.getFechaHoraAtencion(),
                atencion.getNotaBreve(),
                atencion.getIndicacionesBreves(),
                atencion.getFechaCreacion(),
                atencion.getFechaActualizacion()
        );
    }

    public Long getAtencionId() {
        return atencionId;
    }

    public void setAtencionId(Long atencionId) {
        this.atencionId = atencionId;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getProfesionalId() {
        return profesionalId;
    }

    public void setProfesionalId(Long profesionalId) {
        this.profesionalId = profesionalId;
    }

    public Long getCitaId() {
        return citaId;
    }

    public void setCitaId(Long citaId) {
        this.citaId = citaId;
    }

    public LocalDateTime getFechaHoraAtencion() {
        return fechaHoraAtencion;
    }

    public void setFechaHoraAtencion(LocalDateTime fechaHoraAtencion) {
        this.fechaHoraAtencion = fechaHoraAtencion;
    }

    public String getNotaBreve() {
        return notaBreve;
    }

    public void setNotaBreve(String notaBreve) {
        this.notaBreve = notaBreve;
    }

    public String getIndicacionesBreves() {
        return indicacionesBreves;
    }

    public void setIndicacionesBreves(String indicacionesBreves) {
        this.indicacionesBreves = indicacionesBreves;
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
