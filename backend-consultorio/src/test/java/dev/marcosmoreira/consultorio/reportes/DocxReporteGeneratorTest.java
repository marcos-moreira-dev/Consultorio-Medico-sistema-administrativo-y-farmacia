package dev.marcosmoreira.consultorio.reportes;

import dev.marcosmoreira.consultorio.reportes.domain.enums.TipoReporte;
import dev.marcosmoreira.consultorio.reportes.infrastructure.generator.DocxReporteGenerator;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias del generador DOCX de reportes.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
class DocxReporteGeneratorTest {

    @Test
    void generate_deberiaRetornarBytesDocxNoVacios() {
        DocxReporteGenerator generator = new DocxReporteGenerator();

        byte[] result = generator.generate(
                "Reporte Operativo",
                Map.of(
                        "totalCitas", 5,
                        "totalAtenciones", 3
                )
        );

        assertNotNull(result);
        assertTrue(result.length > 0);
        assertEquals((byte) 'P', result[0]);
        assertEquals((byte) 'K', result[1]);
    }

    @Test
    void getSupportedType_deberiaRetornarDocx() {
        DocxReporteGenerator generator = new DocxReporteGenerator();
        assertEquals(TipoReporte.DOCX, generator.getSupportedType());
        assertEquals("docx", generator.getFileExtension());
    }
}
