package dev.marcosmoreira.consultorio.reportes.application.port.in;

import dev.marcosmoreira.consultorio.reportes.api.response.ReporteGeneradoResponse;
import dev.marcosmoreira.consultorio.reportes.domain.enums.TipoReporte;
import java.time.LocalDateTime;

/**
 * Caso de uso para generar reportes del consultorio.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public interface GenerarReporteUseCase {

    /**
     * Genera un reporte en el formato solicitado.
     *
     * @param tipoReporte formato del reporte
     * @param nombreBaseArchivo nombre base sugerido para el archivo
     * @param titulo título visible del reporte
     * @param pacienteId identificador del paciente, o {@code null} si no aplica
     * @param profesionalId identificador del profesional, o {@code null} si no aplica
     * @param fechaDesde fecha/hora inicial del rango, o {@code null} si no aplica
     * @param fechaHasta fecha/hora final del rango, o {@code null} si no aplica
     * @return resultado de la generación del reporte
     */
    ReporteGeneradoResponse generar(
            TipoReporte tipoReporte,
            String nombreBaseArchivo,
            String titulo,
            Long pacienteId,
            Long profesionalId,
            LocalDateTime fechaDesde,
            LocalDateTime fechaHasta
    );
}
