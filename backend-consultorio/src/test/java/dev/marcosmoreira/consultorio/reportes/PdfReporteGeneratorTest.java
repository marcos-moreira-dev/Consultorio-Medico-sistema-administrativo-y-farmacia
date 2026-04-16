package dev.marcosmoreira.consultorio.reportes;

import dev.marcosmoreira.consultorio.reportes.domain.enums.TipoReporte;
import dev.marcosmoreira.consultorio.reportes.infrastructure.generator.PdfReporteGenerator;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias del generador PDF de reportes.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
class PdfReporteGeneratorTest {

    @Test
    void generate_deberiaRetornarBytesPdfNoVacios() {
        PdfReporteGenerator generator = new PdfReporteGenerator();

        byte[] result = generator.generate(
                "Reporte Operativo",
                Map.of(
                        "totalCitas", 5,
                        "totalAtenciones", 3
                )
        );

        assertNotNull(result);
        assertTrue(result.length > 0);

        String asText = new String(result, StandardCharsets.US_ASCII);
        assertTrue(asText.startsWith("%PDF-1.4"));
    }

    @Test
    void getSupportedType_deberiaRetornarPdf() {
        PdfReporteGenerator generator = new PdfReporteGenerator();
        assertEquals(TipoReporte.PDF, generator.getSupportedType());
        assertEquals("pdf", generator.getFileExtension());
        assertEquals("application/pdf", generator.getMimeType());
    }
}
