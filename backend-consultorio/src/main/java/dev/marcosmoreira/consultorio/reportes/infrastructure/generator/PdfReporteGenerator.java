package dev.marcosmoreira.consultorio.reportes.infrastructure.generator;

import dev.marcosmoreira.consultorio.reportes.application.port.out.ReporteGeneratorPort;
import dev.marcosmoreira.consultorio.reportes.domain.enums.TipoReporte;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * Generador simple de reportes PDF.
 *
 * <p>Produce un PDF mínimo de una sola página usando una estructura manual
 * suficiente para la versión 1.0 y sin depender todavía de bibliotecas externas.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Component
public class PdfReporteGenerator implements ReporteGeneratorPort {

    /**
     * Devuelve el tipo de reporte soportado por este generador.
     *
     * @return tipo PDF
     */
    @Override
    public TipoReporte getSupportedType() {
        return TipoReporte.PDF;
    }

    /**
     * Devuelve el MIME type del archivo PDF.
     *
     * @return MIME type PDF
     */
    @Override
    public String getMimeType() {
        return "application/pdf";
    }

    /**
     * Devuelve la extensión del archivo generado.
     *
     * @return extensión pdf
     */
    @Override
    public String getFileExtension() {
        return "pdf";
    }

    /**
     * Genera el contenido binario del PDF.
     *
     * @param titulo título visible del reporte
     * @param data datos ya preparados para el reporte
     * @return contenido binario PDF
     */
    @Override
    public byte[] generate(String titulo, Map<String, Object> data) {
        try {
            List<String> lines = buildLines(titulo, data);
            String contentStream = buildPdfTextStream(lines);
            byte[] contentBytes = contentStream.getBytes(StandardCharsets.US_ASCII);

            List<byte[]> objects = new ArrayList<>();
            objects.add("1 0 obj << /Type /Catalog /Pages 2 0 R >> endobj\n".getBytes(StandardCharsets.US_ASCII));
            objects.add("2 0 obj << /Type /Pages /Kids [3 0 R] /Count 1 >> endobj\n".getBytes(StandardCharsets.US_ASCII));
            objects.add("""
                    3 0 obj << /Type /Page /Parent 2 0 R
                    /MediaBox [0 0 595 842]
                    /Resources << /Font << /F1 4 0 R >> >>
                    /Contents 5 0 R >>
                    endobj
                    """.getBytes(StandardCharsets.US_ASCII));
            objects.add("4 0 obj << /Type /Font /Subtype /Type1 /BaseFont /Helvetica >> endobj\n"
                    .getBytes(StandardCharsets.US_ASCII));

            ByteArrayOutputStream streamObject = new ByteArrayOutputStream();
            streamObject.write(("5 0 obj << /Length " + contentBytes.length + " >>\nstream\n")
                    .getBytes(StandardCharsets.US_ASCII));
            streamObject.write(contentBytes);
            streamObject.write("\nendstream\nendobj\n".getBytes(StandardCharsets.US_ASCII));
            objects.add(streamObject.toByteArray());

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            output.write("%PDF-1.4\n".getBytes(StandardCharsets.US_ASCII));

            List<Integer> offsets = new ArrayList<>();
            offsets.add(0);

            for (byte[] objectBytes : objects) {
                offsets.add(output.size());
                output.write(objectBytes);
            }

            int xrefOffset = output.size();

            output.write(("xref\n0 " + (objects.size() + 1) + "\n").getBytes(StandardCharsets.US_ASCII));
            output.write("0000000000 65535 f \n".getBytes(StandardCharsets.US_ASCII));

            for (int i = 1; i < offsets.size(); i++) {
                output.write(String.format("%010d 00000 n \n", offsets.get(i))
                        .getBytes(StandardCharsets.US_ASCII));
            }

            output.write(("""
                    trailer << /Size %d /Root 1 0 R >>
                    startxref
                    %d
                    %%EOF
                    """.formatted(objects.size() + 1, xrefOffset)).getBytes(StandardCharsets.US_ASCII));

            return output.toByteArray();
        } catch (Exception ex) {
            throw new IllegalStateException("No fue posible generar el archivo PDF.", ex);
        }
    }

    /**
     * Construye el contenido del stream de texto del PDF.
     *
     * @param lines líneas legibles del reporte
     * @return stream PDF en sintaxis de contenido
     */
    private String buildPdfTextStream(List<String> lines) {
        StringBuilder sb = new StringBuilder();
        sb.append("BT\n/F1 12 Tf\n50 790 Td\n16 TL\n");

        int maxLines = Math.min(lines.size(), 40);
        for (int i = 0; i < maxLines; i++) {
            if (i > 0) {
                sb.append("T*\n");
            }
            sb.append("(").append(escapePdf(lines.get(i))).append(") Tj\n");
        }

        sb.append("ET");
        return sb.toString();
    }

    /**
     * Construye líneas legibles a partir del mapa de datos del reporte.
     *
     * @param titulo título visible
     * @param data datos del reporte
     * @return líneas ordenadas para el documento
     */
    private List<String> buildLines(String titulo, Map<String, Object> data) {
        List<String> lines = new ArrayList<>();
        lines.add(titulo);
        lines.add("");

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            lines.add(entry.getKey() + ": " + String.valueOf(entry.getValue()));
        }

        return lines;
    }

    /**
     * Escapa caracteres reservados dentro de cadenas de texto PDF.
     *
     * @param value texto a escapar
     * @return texto compatible con sintaxis PDF
     */
    private String escapePdf(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("(", "\\(")
                .replace(")", "\\)");
    }
}
