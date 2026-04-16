package dev.marcosmoreira.consultorio.reportes;

import dev.marcosmoreira.consultorio.reportes.api.response.ReporteGeneradoResponse;
import dev.marcosmoreira.consultorio.reportes.application.port.out.ReporteDataPort;
import dev.marcosmoreira.consultorio.reportes.application.port.out.ReporteGeneratorPort;
import dev.marcosmoreira.consultorio.reportes.application.service.ReporteApplicationService;
import dev.marcosmoreira.consultorio.reportes.domain.enums.TipoReporte;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias del servicio de aplicación del módulo de reportes.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
class ReporteApplicationServiceTest {

    private ReporteDataPort reporteDataPort;
    private ReporteApplicationService reporteApplicationService;

    /**
     * Inicializa un data port y generadores fake antes de cada test.
     */
    @BeforeEach
    void setUp() {
        reporteDataPort = (pacienteId, profesionalId, fechaDesde, fechaHasta) -> Map.of(
                "totalCitas", 5L,
                "totalAtenciones", 3L,
                "totalCobros", 2L,
                "montoTotalCobrado", "120.50"
        );

        ReporteGeneratorPort pdfGenerator = new FakeReporteGenerator(
                TipoReporte.PDF,
                "application/pdf",
                "pdf",
                "PDF_BYTES"
        );

        ReporteGeneratorPort docxGenerator = new FakeReporteGenerator(
                TipoReporte.DOCX,
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "docx",
                "DOCX_BYTES"
        );

        ReporteGeneratorPort xlsxGenerator = new FakeReporteGenerator(
                TipoReporte.XLSX,
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                "xlsx",
                "XLSX_BYTES"
        );

        reporteApplicationService = new ReporteApplicationService(
                reporteDataPort,
                List.of(pdfGenerator, docxGenerator, xlsxGenerator)
        );
    }

    /**
     * Verifica la generación exitosa de un reporte PDF.
     */
    @Test
    void generar_deberiaConstruirRespuestaParaPdf() {
        ReporteGeneradoResponse response = reporteApplicationService.generar(
                TipoReporte.PDF,
                "resumen_consultorio",
                "Reporte Operativo",
                1L,
                2L,
                LocalDateTime.of(2026, 4, 1, 0, 0),
                LocalDateTime.of(2026, 4, 30, 23, 59)
        );

        assertNotNull(response);
        assertEquals(TipoReporte.PDF, response.getTipoReporte());
        assertEquals("resumen_consultorio.pdf", response.getNombreArchivo());
        assertEquals("application/pdf", response.getMimeType());

        String decoded = new String(
                Base64.getDecoder().decode(response.getContenidoBase64()),
                StandardCharsets.UTF_8
        );

        assertEquals("PDF_BYTES", decoded);
        assertEquals("PDF_BYTES".getBytes(StandardCharsets.UTF_8).length, response.getTamanoBytes());
        assertNotNull(response.getGeneradoEn());
    }

    /**
     * Verifica que el nombre base del archivo se normalice si contiene caracteres no seguros.
     */
    @Test
    void generar_deberiaNormalizarNombreBaseArchivo() {
        ReporteGeneradoResponse response = reporteApplicationService.generar(
                TipoReporte.DOCX,
                "Reporte Abril 2026!!!",
                "Reporte Operativo",
                null,
                null,
                null,
                null
        );

        assertEquals("reporte_abril_2026_.docx", response.getNombreArchivo());
    }

    /**
     * Verifica que se use un título por defecto cuando no se envía uno.
     */
    @Test
    void generar_deberiaAceptarTituloNuloYUsarValorPorDefecto() {
        ReporteGeneradoResponse response = reporteApplicationService.generar(
                TipoReporte.XLSX,
                null,
                null,
                null,
                null,
                null,
                null
        );

        assertNotNull(response);
        assertEquals(TipoReporte.XLSX, response.getTipoReporte());
        assertEquals("reporte_consultorio.xlsx", response.getNombreArchivo());
    }

    /**
     * Verifica que falle cuando el rango de fechas es incoherente.
     */
    @Test
    void generar_deberiaLanzarExcepcionSiRangoFechasEsInvalido() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> reporteApplicationService.generar(
                        TipoReporte.PDF,
                        "reporte",
                        "Titulo",
                        null,
                        null,
                        LocalDateTime.of(2026, 4, 30, 23, 59),
                        LocalDateTime.of(2026, 4, 1, 0, 0)
                )
        );

        assertEquals("La fecha inicial no puede ser posterior a la fecha final.", exception.getMessage());
    }

    /**
     * Generador fake simple para pruebas unitarias del servicio.
     */
    private static class FakeReporteGenerator implements ReporteGeneratorPort {

        private final TipoReporte supportedType;
        private final String mimeType;
        private final String extension;
        private final String payload;

        private FakeReporteGenerator(
                TipoReporte supportedType,
                String mimeType,
                String extension,
                String payload
        ) {
            this.supportedType = supportedType;
            this.mimeType = mimeType;
            this.extension = extension;
            this.payload = payload;
        }

        @Override
        public TipoReporte getSupportedType() {
            return supportedType;
        }

        @Override
        public String getMimeType() {
            return mimeType;
        }

        @Override
        public String getFileExtension() {
            return extension;
        }

        @Override
        public byte[] generate(String titulo, Map<String, Object> data) {
            return payload.getBytes(StandardCharsets.UTF_8);
        }
    }
}
