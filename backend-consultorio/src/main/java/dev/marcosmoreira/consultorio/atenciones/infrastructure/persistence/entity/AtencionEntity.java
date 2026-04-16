package dev.marcosmoreira.consultorio.atenciones.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/**
 * Entidad JPA del módulo de atenciones.
 *
 * <p>Esta clase representa la forma en que una atención se persiste dentro
 * de la base de datos relacional del sistema. A diferencia del modelo de dominio,
 * esta entidad sí conoce detalles técnicos como nombres de tabla, columnas
 * y ciclo de vida de persistencia.</p>
 *
 * <p>Para esta versión se modela usando solo identificadores escalares
 * en lugar de relaciones JPA complejas. Eso mantiene la implementación
 * más simple y más cercana al enfoque explícito que vienes trabajando.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Entity
@Table(schema = "consultorio", name = "atencion")
public class AtencionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atencion_id")
    private Long atencionId;

    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @Column(name = "profesional_id", nullable = false)
    private Long profesionalId;

    @Column(name = "cita_id")
    private Long citaId;

    @Column(name = "fecha_hora_atencion", nullable = false)
    private LocalDateTime fechaHoraAtencion;

    @Column(name = "nota_breve", nullable = false, length = 500)
    private String notaBreve;

    @Column(name = "indicaciones_breves", length = 500)
    private String indicacionesBreves;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;

    /**
     * Constructor vacío requerido por JPA.
     */
    public AtencionEntity() {
    }

    /**
     * Construye una entidad completa de atención.
     *
     * @param atencionId identificador único de la atención
     * @param pacienteId identificador del paciente atendido
     * @param profesionalId identificador del profesional que realizó la atención
     * @param citaId identificador de la cita asociada, si existe
     * @param fechaHoraAtencion fecha y hora de la atención
     * @param notaBreve nota breve de la atención
     * @param indicacionesBreves indicaciones breves, si existen
     * @param fechaCreacion fecha de creación del registro
     * @param fechaActualizacion fecha de última actualización del registro
     */
    public AtencionEntity(
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
     * Inicializa timestamps antes de persistir una nueva entidad.
     *
     * <p>Esto permite que la app mantenga coherencia temporal básica incluso
     * si todavía no se está usando una infraestructura de auditoría más formal.</p>
     */
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();

        if (this.fechaCreacion == null) {
            this.fechaCreacion = now;
        }

        if (this.fechaActualizacion == null) {
            this.fechaActualizacion = now;
        }
    }

    /**
     * Actualiza el timestamp de modificación antes de cada update.
     */
    @PreUpdate
    protected void onUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
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
