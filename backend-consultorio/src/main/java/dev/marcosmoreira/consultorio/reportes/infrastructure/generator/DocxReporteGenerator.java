package dev.marcosmoreira.consultorio.reportes.infrastructure.generator;

import dev.marcosmoreira.consultorio.reportes.application.port.out.ReporteGeneratorPort;
import dev.marcosmoreira.consultorio.reportes.domain.enums.TipoReporte;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.springframework.stereotype.Component;

/**
 * Generador simple de reportes DOCX usando únicamente utilidades estándar de Java.
 *
 * <p>La implementación produce un documento OpenXML mínimo pero válido,
 * suficiente para la versión 1.0 sin depender todavía de bibliotecas externas.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Component
public class DocxReporteGenerator implements ReporteGeneratorPort {

    /**
     * Devuelve el tipo de reporte soportado por este generador.
     *
     * @return tipo DOCX
     */
    @Override
    public TipoReporte getSupportedType() {
        return TipoReporte.DOCX;
    }

    /**
     * Devuelve el MIME type del archivo DOCX.
     *
     * @return MIME type DOCX
     */
    @Override
    public String getMimeType() {
        return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    }

    /**
     * Devuelve la extensión del archivo generado.
     *
     * @return extensión docx
     */
    @Override
    public String getFileExtension() {
        return "docx";
    }

    /**
     * Genera el contenido binario del documento DOCX.
     *
     * @param titulo título visible del reporte
     * @param data datos ya preparados para el reporte
     * @return contenido binario DOCX
     */
    @Override
    public byte[] generate(String titulo, Map<String, Object> data) {
        try {
            List<String> lines = buildLines(titulo, data);

            String contentTypes = """
                    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                    <Types xmlns="http://schemas.openxmlformats.org/package/2006/content-types">
                      <Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml"/>
                      <Default Extension="xml" ContentType="application/xml"/>
                      <Override PartName="/word/document.xml"
                                ContentType="application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml"/>
                    </Types>
                    """;

            String rootRels = """
                    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                    <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
                      <Relationship Id="rId1"
                                    Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument"
                                    Target="word/document.xml"/>
                    </Relationships>
                    """;

            String documentXml = buildDocumentXml(lines);

            ByteArrayOutputStream output = new ByteArrayOutputStream();

            try (ZipOutputStream zip = new ZipOutputStream(output)) {
                writeEntry(zip, "[Content_Types].xml", contentTypes);
                writeEntry(zip, "_rels/.rels", rootRels);
                writeEntry(zip, "word/document.xml", documentXml);
            }

            return output.toByteArray();
        } catch (Exception ex) {
            throw new IllegalStateException("No fue posible generar el documento DOCX.", ex);
        }
    }

    /**
     * Construye el XML principal del documento DOCX.
     *
     * @param lines líneas legibles del reporte
     * @return XML del documento principal
     */
    private String buildDocumentXml(List<String> lines) {
        StringBuilder sb = new StringBuilder();
        sb.append("""
                <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                <w:document xmlns:wpc="http://schemas.microsoft.com/office/word/2010/wordprocessingCanvas"
                            xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
                            xmlns:o="urn:schemas-microsoft-com:office:office"
                            xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"
                            xmlns:m="http://schemas.openxmlformats.org/officeDocument/2006/math"
                            xmlns:v="urn:schemas-microsoft-com:vml"
                            xmlns:wp14="http://schemas.microsoft.com/office/word/2010/wordprocessingDrawing"
                            xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing"
                            xmlns:w10="urn:schemas-microsoft-com:office:word"
                            xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main"
                            xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml"
                            xmlns:wpg="http://schemas.microsoft.com/office/word/2010/wordprocessingGroup"
                            xmlns:wpi="http://schemas.microsoft.com/office/word/2010/wordprocessingInk"
                            xmlns:wne="http://schemas.microsoft.com/office/word/2006/wordml"
                            xmlns:wps="http://schemas.microsoft.com/office/word/2010/wordprocessingShape"
                            mc:Ignorable="w14 wp14">
                  <w:body>
                """);

        for (String line : lines) {
            sb.append("<w:p><w:r><w:t xml:space=\"preserve\">")
                    .append(escapeXml(line))
                    .append("</w:t></w:r></w:p>");
        }

        sb.append("""
                    <w:sectPr>
                      <w:pgSz w:w="11906" w:h="16838"/>
                      <w:pgMar w:top="1440" w:right="1440" w:bottom="1440" w:left="1440"
                               w:header="708" w:footer="708" w:gutter="0"/>
                    </w:sectPr>
                  </w:body>
                </w:document>
                """);

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
