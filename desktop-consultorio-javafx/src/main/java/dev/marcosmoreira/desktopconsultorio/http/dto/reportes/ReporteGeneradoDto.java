package dev.marcosmoreira.desktopconsultorio.http.dto.reportes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Respuesta de generación de reporte con referencia al archivo.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReporteGeneradoDto {

    @JsonProperty("reporteId")
    private String reporteId;

    @JsonProperty("tipoReporte")
    private String tipoReporte;

    @JsonProperty("formato")
    private String formato;

    @JsonProperty("nombreArchivo")
    private String nombreArchivo;

    @JsonProperty("mimeType")
    private String mimeType;

    @JsonProperty("tamanoBytes")
    private Long tamanoBytes;

    @JsonProperty("fechaGeneracion")
    private String fechaGeneracion;

    @JsonProperty("contenidoBase64")
    private String contenidoBase64;

    public String getReporteId() { return reporteId; }
    public void setReporteId(String reporteId) { this.reporteId = reporteId; }
    public String getTipoReporte() { return tipoReporte; }
    public void setTipoReporte(String tipoReporte) { this.tipoReporte = tipoReporte; }
    public String getFormato() { return formato; }
    public void setFormato(String formato) { this.formato = formato; }
    public String getNombreArchivo() { return nombreArchivo; }
    public void setNombreArchivo(String nombreArchivo) { this.nombreArchivo = nombreArchivo; }
    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }
    public Long getTamanoBytes() { return tamanoBytes; }
    public void setTamanoBytes(Long tamanoBytes) { this.tamanoBytes = tamanoBytes; }
    public String getFechaGeneracion() { return fechaGeneracion; }
    public void setFechaGeneracion(String fechaGeneracion) { this.fechaGeneracion = fechaGeneracion; }
    public String getContenidoBase64() { return contenidoBase64; }
    public void setContenidoBase64(String contenidoBase64) { this.contenidoBase64 = contenidoBase64; }

    public byte[] getContenidoBytes() {
        if (contenidoBase64 == null || contenidoBase64.isEmpty()) return new byte[0];
        return java.util.Base64.getDecoder().decode(contenidoBase64);
    }
}
