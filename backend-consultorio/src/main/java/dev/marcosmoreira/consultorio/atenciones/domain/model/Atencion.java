package dev.marcosmoreira.consultorio.atenciones.domain.model;

import java.time.LocalDateTime;

/**
 * Modelo de dominio que representa una atención realizada dentro del consultorio.
 *
 * <p>Una atención representa el acto ya efectuado de atención clínica u operativa
 * sobre un paciente. Puede estar asociada a una cita previa o haberse registrado
 * de forma directa, dependiendo del flujo de negocio.</p>
 *
 * <p>Este modelo pertenece al dominio y, por tanto, no debe depender de detalles
 * de persistencia, HTTP ni frameworks. Su objetivo es representar el concepto
 * central con sus datos más relevantes dentro de la versión 1.0.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class Atencion {

    private Long atencionId;
    private Long pacienteId;
    private Long profesionalId;
    private Long citaId;
    private LocalDateTime fechaHoraAtencion;
    private String notaBreve;
    private String indicacionesBreves;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    /**
     * Constructor vacío.
     *
     * <p>Se mantiene para facilitar el uso del modelo en mapeos manuales
     * y flujos internos del proyecto.</p>
     */
    public Atencion() {
    }

    /**
     * Construye una atención con todos sus atributos principales.
     *
     * @param atencionId identificador único de la atención
     * @param pacienteId identificador del paciente atendido
     * @param profesionalId identificador del profesional que realizó la atención
     * @param citaId identificador de la cita asociada, si existe
     * @param fechaHoraAtencion fecha y hora en la que ocurrió la atención
     * @param notaBreve nota breve registrada para la atención
     * @param indicacionesBreves indicaciones breves entregadas al paciente, si existen
     * @param fechaCreacion fecha y hora de creación del registro
     * @param fechaActualizacion fecha y hora de última actualización del registro
     */
    public Atencion(
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
        this.notaBreve = normalizeNullableText(notaBreve);
        this.indicacionesBreves = normalizeNullableText(indicacionesBreves);
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
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
        this.notaBreve = normalizeNullableText(notaBreve);
    }

    public String getIndicacionesBreves() {
        return indicacionesBreves;
    }

    public void setIndicacionesBreves(String indicacionesBreves) {
        this.indicacionesBreves = normalizeNullableText(indicacionesBreves);
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
     * Indica si la atención tiene una cita asociada.
     *
     * <p>Esto ayuda a distinguir, a nivel conceptual, entre una atención derivada
     * de agenda previa y una atención registrada de forma directa.</p>
     *
     * @return {@code true} si existe cita asociada; {@code false} en caso contrario
     */
    public boolean hasCitaAsociada() {
        return citaId != null;
    }

    /**
     * Indica si la atención posee indicaciones breves informadas.
     *
     * @return {@code true} si existen indicaciones no vacías; {@code false} en caso contrario
     */
    public boolean hasIndicacionesBreves() {
        return indicacionesBreves != null && !indicacionesBreves.isBlank();
    }

    /**
     * Normaliza un texto opcional.
     *
     * <p>Se usa para evitar persistir o transportar cadenas vacías cuando
     * conceptualmente el dato debería considerarse ausente.</p>
     *
     * @param value texto a normalizar
     * @return texto con trim aplicado o {@code null} si no contiene información útil
     */
    private String normalizeNullableText(String value) {
        if (value == null) {
            return null;
        }

        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }
}
