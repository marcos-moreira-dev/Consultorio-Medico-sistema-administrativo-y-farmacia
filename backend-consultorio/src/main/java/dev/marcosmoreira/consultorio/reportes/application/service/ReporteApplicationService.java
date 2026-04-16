package dev.marcosmoreira.consultorio.reportes.application.service;

import dev.marcosmoreira.consultorio.reportes.api.response.ReporteGeneradoResponse;
import dev.marcosmoreira.consultorio.reportes.application.port.in.GenerarReporteUseCase;
import dev.marcosmoreira.consultorio.reportes.application.port.out.ReporteDataPort;
import dev.marcosmoreira.consultorio.reportes.application.port.out.ReporteGeneratorPort;
import dev.marcosmoreira.consultorio.reportes.domain.enums.TipoReporte;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio de aplicación del módulo de reportes.
 *
 * <p>Orquesta la obtención de datos, la selección del generador correcto
 * y la construcción de la respuesta final para el cliente.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
public class ReporteApplicationService implements GenerarReporteUseCase {

    private final ReporteDataPort reporteDataPort;
    private final List<ReporteGeneratorPort> reporteGenerators;

    /**
     * Construye el servicio de aplicación del módulo.
     *
     * @param reporteDataPort puerto de obtención de datos del reporte
     * @param reporteGenerators generadores disponibles por formato
     */
    public ReporteApplicationService(
            ReporteDataPort reporteDataPort,
            List<ReporteGeneratorPort> reporteGenerators
    ) {
        this.reporteDataPort = reporteDataPort;
        this.reporteGenerators = reporteGenerators;
    }

    /**
     * Genera un reporte en el formato solicitado.
     *
     * @param tipoReporte formato del reporte
     * @param nombreBaseArchivo nombre base sugerido
     * @param titulo título visible
     * @param pacienteId identificador del paciente, o {@code null} si no aplica
     * @param profesionalId identificador del profesional, o {@code null} si no aplica
     * @param fechaDesde fecha/hora inicial, o {@code null} si no aplica
     * @param fechaHasta fecha/hora final, o {@code null} si no aplica
     * @return resultado de la generación del reporte
     */
    @Override
    public ReporteGeneradoResponse generar(
            TipoReporte tipoReporte,
            String nombreBaseArchivo,
            String titulo,
            Long pacienteId,
            Long profesionalId,
            LocalDateTime fechaDesde,
            LocalDateTime fechaHasta
    ) {
        if (tipoReporte == null) {
            throw new IllegalArgumentException("El tipo de reporte es obligatorio.");
        }

        validateOptionalPositiveId(pacienteId, "Si se envía pacienteId, debe ser mayor que cero.");
        validateOptionalPositiveId(profesionalId, "Si se envía profesionalId, debe ser mayor que cero.");
        validateDateRange(fechaDesde, fechaHasta);

        String resolvedTitle = resolveTitle(titulo);
        String resolvedBaseName = resolveBaseFileName(nombreBaseArchivo);

        Map<String, Object> data = reporteDataPort.obtenerDatos(
                pacienteId,
                profesionalId,
                fechaDesde,
                fechaHasta
        );

        ReporteGeneratorPort generator = resolveGenerator(tipoReporte);
        byte[] bytes = generator.generate(resolvedTitle, data);

        String fileName = resolvedBaseName + "." + generator.getFileExtension();
        String base64 = Base64.getEncoder().encodeToString(bytes);

        return ReporteGeneradoResponse.of(
                tipoReporte,
                fileName,
                generator.getMimeType(),
                base64,
                bytes.length,
                LocalDateTime.now()
        );
    }

    /**
     * Resuelve el generador adecuado según el formato solicitado.
     *
     * @param tipoReporte formato solicitado
     * @return generador encontrado
     */
    private ReporteGeneratorPort resolveGenerator(TipoReporte tipoReporte) {
        return reporteGenerators.stream()
                .filter(generator -> generator.getSupportedType() == tipoReporte)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe un generador configurado para el tipo de reporte " + tipoReporte + "."
                ));
    }

    /**
     * Resuelve el título final del reporte.
     *
     * @param titulo título sugerido
     * @return título final no vacío
     */
    private String resolveTitle(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            return "Reporte operativo del consultorio";
        }

        return titulo.trim();
    }

    /**
     * Resuelve el nombre base del archivo.
     *
     * @param nombreBaseArchivo nombre base sugerido
     * @return nombre base seguro y no vacío
     */
    private String resolveBaseFileName(String nombreBaseArchivo) {
        if (nombreBaseArchivo == null || nombreBaseArchivo.isBlank()) {
            return "reporte_consultorio";
        }

        return nombreBaseArchivo.trim()
                .toLowerCase()
                .replaceAll("[^a-z0-9_\\-]+", "_");
    }

    /**
     * Valida que un identificador opcional, si existe, sea positivo.
     *
     * @param value identificador a validar
     * @param message mensaje de error
     */
    private void validateOptionalPositiveId(Long value, String message) {
        if (value != null && value <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Valida que un rango temporal sea coherente.
     *
     * @param fechaDesde fecha inicial
     * @param fechaHasta fecha final
     */
    private void validateDateRange(LocalDateTime fechaDesde, LocalDateTime fechaHasta) {
        if (fechaDesde != null && fechaHasta != null && fechaDesde.isAfter(fechaHasta)) {
            throw new IllegalArgumentException("La fecha inicial no puede ser posterior a la fecha final.");
        }
    }
}
