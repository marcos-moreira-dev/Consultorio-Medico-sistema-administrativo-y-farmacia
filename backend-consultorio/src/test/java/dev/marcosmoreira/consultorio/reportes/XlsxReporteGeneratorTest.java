package dev.marcosmoreira.consultorio.reportes;

import dev.marcosmoreira.consultorio.reportes.domain.enums.TipoReporte;
import dev.marcosmoreira.consultorio.reportes.infrastructure.generator.XlsxReporteGenerator;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias del generador XLSX de reportes.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
class XlsxReporteGeneratorTest {

    @Test
    void generate_deberiaRetornarBytesXlsxNoVacios() {
        XlsxReporteGenerator generator = new XlsxReporteGenerator();

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
    void getSupportedType_deberiaRetornarXlsx() {
        XlsxReporteGenerator generator = new XlsxReporteGenerator();
        assertEquals(TipoReporte.XLSX, generator.getSupportedType());
        assertEquals("xlsx", generator.getFileExtension());
    }
}
