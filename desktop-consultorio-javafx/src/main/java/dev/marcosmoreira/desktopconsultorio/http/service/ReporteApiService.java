package dev.marcosmoreira.desktopconsultorio.http.service;

import dev.marcosmoreira.desktopconsultorio.app.ConsultorioDesktopApi;
import dev.marcosmoreira.desktopconsultorio.http.dto.reportes.ReporteGeneradoDto;

/**
 * Servicio de reportes contra el backend del consultorio.
 */
public class ReporteApiService {

    private final ConsultorioDesktopApi api;

    public ReporteApiService(ConsultorioDesktopApi api) {
        this.api = api;
    }

    /**
     * Genera un reporte del tipo y formato especificados.
     * El backend devuelve el contenido en Base64 dentro de la respuesta.
     *
     * @param tipoReporte tipo: "atenciones", "cobros", "citas", "pacientes"
     * @param formato formato: "PDF", "XLSX", "DOCX"
     * @param fechaDesde fecha inicio del período (ISO format)
     * @param fechaHasta fecha fin del período (ISO format)
     * @return reporte generado con contenido Base64 incluido
     */
    public ReporteGeneradoDto generar(String tipoReporte, String formato, String fechaDesde, String fechaHasta) {
        var body = new java.util.HashMap<String, Object>();
        body.put("tipoReporte", tipoReporte);
        body.put("formato", formato);
        body.put("fechaDesde", fechaDesde);
        body.put("fechaHasta", fechaHasta);
        return api.post("/reportes/generar", body, ReporteGeneradoDto.class);
    }

    /**
     * Guarda el contenido Base64 de un reporte generado en un archivo local.
     *
     * @param dto reporte generado con contenido Base64
     * @param destino ruta del archivo de destino
     */
    public void guardarArchivo(ReporteGeneradoDto dto, java.nio.file.Path destino) throws java.io.IOException {
        byte[] bytes = dto.getContenidoBytes();
        java.nio.file.Files.createDirectories(destino.getParent());
        java.nio.file.Files.write(destino, bytes);
    }
}
