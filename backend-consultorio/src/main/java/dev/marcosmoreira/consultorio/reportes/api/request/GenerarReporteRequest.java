package dev.marcosmoreira.consultorio.reportes.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.marcosmoreira.consultorio.reportes.domain.enums.TipoReporte;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * DTO de entrada para generar un reporte.
 *
 * <p>En la versión 1.0 se genera un resumen operativo del consultorio
 * aplicando filtros básicos opcionales.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class GenerarReporteRequest {

    @NotNull(message = "El tipo de reporte es obligatorio.")
    private TipoReporte tipoReporte;

    @Size(max = 80, message = "El nombre base del archivo no puede exceder los 80 caracteres.")
    private String nombreBaseArchivo;

    @Size(max = 150, message = "El título no puede exceder los 150 caracteres.")
    private String titulo;

    @Positive(message = "Si se envía pacienteId, debe ser mayor que cero.")
    private Long pacienteId;

    @Positive(message = "Si se envía profesionalId, debe ser mayor que cero.")
    private Long profesionalId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaDesde;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaHasta;

    /**
     * Constructor vacío requerido por serialización.
     */
    public GenerarReporteRequest() {
    }

    /**
     * Construye el request completo de generación.
     *
     * @param tipoReporte formato del reporte
     * @param nombreBaseArchivo nombre base sugerido para el archivo
     * @param titulo título visible del reporte
     * @param pacienteId identificador del paciente, si aplica
     * @param profesionalId identificador del profesional, si aplica
     * @param fechaDesde fecha/hora inicial del rango, si aplica
     * @param fechaHasta fecha/hora final del rango, si aplica
     */
    public GenerarReporteRequest(
            TipoReporte tipoReporte,
            String nombreBaseArchivo,
            String titulo,
            Long pacienteId,
            Long profesionalId,
            LocalDateTime fechaDesde,
            LocalDateTime fechaHasta
    ) {
        this.tipoReporte = tipoReporte;
        this.nombreBaseArchivo = normalizeNullableText(nombreBaseArchivo);
        this.titulo = normalizeNullableText(titulo);
        this.pacienteId = pacienteId;
        this.profesionalId = profesionalId;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
    }

    public TipoReporte getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(TipoReporte tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public String getNombreBaseArchivo() {
        return nombreBaseArchivo;
    }

    public void setNombreBaseArchivo(String nombreBaseArchivo) {
        this.nombreBaseArchivo = normalizeNullableText(nombreBaseArchivo);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = normalizeNullableText(titulo);
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

    public LocalDateTime getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(LocalDateTime fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public LocalDateTime getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(LocalDateTime fechaHasta) {
        this.fechaHasta = fechaHasta;
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
