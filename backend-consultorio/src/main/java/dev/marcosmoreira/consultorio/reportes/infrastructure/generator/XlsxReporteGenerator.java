package dev.marcosmoreira.consultorio.reportes.infrastructure.generator;

import dev.marcosmoreira.consultorio.reportes.application.port.out.ReporteGeneratorPort;
import dev.marcosmoreira.consultorio.reportes.domain.enums.TipoReporte;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.springframework.stereotype.Component;

/**
 * Generador simple de reportes XLSX.
 *
 * <p>Produce una hoja de cálculo OpenXML mínima y válida usando utilidades
 * estándar de Java, suficiente para la versión 1.0.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Component
public class XlsxReporteGenerator implements ReporteGeneratorPort {

    /**
     * Devuelve el tipo de reporte soportado por este generador.
     *
     * @return tipo XLSX
     */
    @Override
    public TipoReporte getSupportedType() {
        return TipoReporte.XLSX;
    }

    /**
     * Devuelve el MIME type del archivo XLSX.
     *
     * @return MIME type XLSX
     */
    @Override
    public String getMimeType() {
        return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    }

    /**
     * Devuelve la extensión del archivo generado.
     *
     * @return extensión xlsx
     */
    @Override
    public String getFileExtension() {
        return "xlsx";
    }

    /**
     * Genera el contenido binario del XLSX.
     *
     * @param titulo título visible del reporte
     * @param data datos ya preparados para el reporte
     * @return contenido binario XLSX
     */
    @Override
    public byte[] generate(String titulo, Map<String, Object> data) {
        try {
            String contentTypes = """
                    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                    <Types xmlns="http://schemas.openxmlformats.org/package/2006/content-types">
                      <Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml"/>
                      <Default Extension="xml" ContentType="application/xml"/>
                      <Override PartName="/xl/workbook.xml"
                                ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml"/>
                      <Override PartName="/xl/worksheets/sheet1.xml"
                                ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml"/>
                    </Types>
                    """;

            String rootRels = """
                    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                    <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
                      <Relationship Id="rId1"
                                    Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument"
                                    Target="xl/workbook.xml"/>
                    </Relationships>
                    """;

            String workbook = """
                    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                    <workbook xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main"
                              xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships">
                      <sheets>
                        <sheet name="Reporte" sheetId="1" r:id="rId1"/>
                      </sheets>
                    </workbook>
                    """;

            String workbookRels = """
                    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                    <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
                      <Relationship Id="rId1"
                                    Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet"
                                    Target="worksheets/sheet1.xml"/>
                    </Relationships>
                    """;

            String sheet1 = buildSheetXml(titulo, data);

            ByteArrayOutputStream output = new ByteArrayOutputStream();

            try (ZipOutputStream zip = new ZipOutputStream(output)) {
                writeEntry(zip, "[Content_Types].xml", contentTypes);
                writeEntry(zip, "_rels/.rels", rootRels);
                writeEntry(zip, "xl/workbook.xml", workbook);
                writeEntry(zip, "xl/_rels/workbook.xml.rels", workbookRels);
                writeEntry(zip, "xl/worksheets/sheet1.xml", sheet1);
            }

            return output.toByteArray();
        } catch (Exception ex) {
            throw new IllegalStateException("No fue posible generar el archivo XLSX.", ex);
        }
    }

    /**
     * Construye el XML principal de la hoja.
     *
     * @param titulo título visible
     * @param data datos del reporte
     * @return XML de la hoja de cálculo
     */
    private String buildSheetXml(String titulo, Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("""
                <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                <worksheet xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <sheetData>
                """);

        AtomicInteger row = new AtomicInteger(1);

        appendStringRow(sb, row.getAndIncrement(), "Campo", "Valor");
        appendStringRow(sb, row.getAndIncrement(), "Titulo", titulo);

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            appendStringRow(
                    sb,
                    row.getAndIncrement(),
                    entry.getKey(),
                    String.valueOf(entry.getValue())
            );
        }

        sb.append("""
                  </sheetData>
                </worksheet>
                """);

        return sb.toString();
    }

    /**
     * Agrega una fila de dos columnas de texto a la hoja.
     *
     * @param sb builder XML
     * @param rowNumber número de fila
     * @param left valor de la columna A
     * @param right valor de la columna B
     */
    private void appendStringRow(StringBuilder sb, int rowNumber, String left, String right) {
        sb.append("<row r=\"").append(rowNumber).append("\">")
                .append(inlineStringCell("A" + rowNumber, left))
                .append(inlineStringCell("B" + rowNumber, right))
                .append("</row>");
    }

    /**
     * Construye una celda inline string.
     *
     * @param reference referencia tipo A1, B2, etc.
     * @param value valor textual
     * @return XML de celda
     */
    private String inlineStringCell(String reference, String value) {
        return "<c r=\"" + reference + "\" t=\"inlineStr\"><is><t>"
                + escapeXml(value)
                + "</t></is></c>";
    }

    /**
     * Escribe una entrada ZIP de texto UTF-8.
     *
     * @param zip ZIP destino
     * @param name nombre de la entrada
     * @param content contenido textual
     * @throws Exception si ocurre un error de escritura
     */
    private void writeEntry(ZipOutputStream zip, String name, String content) throws Exception {
        zip.putNextEntry(new ZipEntry(name));
        zip.write(content.getBytes(StandardCharsets.UTF_8));
        zip.closeEntry();
    }

    /**
     * Escapa caracteres reservados de XML.
     *
     * @param value texto a escapar
     * @return texto XML-safe
     */
    private String escapeXml(String value) {
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}
