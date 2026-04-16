package dev.marcosmoreira.consultorio.reportes.api.response;

import dev.marcosmoreira.consultorio.reportes.domain.enums.TipoReporte;
import java.time.LocalDateTime;

/**
 * DTO de salida con el resultado de la generación de un reporte.
 *
 * <p>Incluye metadatos del archivo y el contenido serializado en Base64
 * para que el cliente pueda reconstruir o descargar el archivo.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class ReporteGeneradoResponse {

    private TipoReporte tipoReporte;
    private String nombreArchivo;
    private String mimeType;
    private String contenidoBase64;
    private long tamanoBytes;
    private LocalDateTime generadoEn;

    /**
     * Constructor vacío requerido por serialización.
     */
    public ReporteGeneradoResponse() {
    }

    /**
     * Construye la respuesta completa del reporte generado.
     *
     * @param tipoReporte formato del reporte
     * @param nombreArchivo nombre final del archivo
     * @param mimeType MIME type del archivo
     * @param contenidoBase64 contenido serializado en Base64
     * @param tamanoBytes tamaño del archivo en bytes
     * @param generadoEn fecha y hora de generación
     */
    public ReporteGeneradoResponse(
            TipoReporte tipoReporte,
            String nombreArchivo,
            String mimeType,
            String contenidoBase64,
            long tamanoBytes,
            LocalDateTime generadoEn
    ) {
        this.tipoReporte = tipoReporte;
        this.nombreArchivo = nombreArchivo;
        this.mimeType = mimeType;
        this.contenidoBase64 = contenidoBase64;
        this.tamanoBytes = tamanoBytes;
        this.generadoEn = generadoEn;
    }

    /**
     * Método fábrica para construir la respuesta.
     *
     * @param tipoReporte formato del reporte
     * @param nombreArchivo nombre final del archivo
     * @param mimeType MIME type del archivo
     * @param contenidoBase64 contenido serializado en Base64
     * @param tamanoBytes tamaño del archivo en bytes
     * @param generadoEn fecha y hora de generación
     * @return respuesta de reporte generado
     */
    public static ReporteGeneradoResponse of(
            TipoReporte tipoReporte,
            String nombreArchivo,
            String mimeType,
            String contenidoBase64,
            long tamanoBytes,
            LocalDateTime generadoEn
    ) {
        return new ReporteGeneradoResponse(
                tipoReporte,
                nombreArchivo,
                mimeType,
                contenidoBase64,
                tamanoBytes,
                generadoEn
        );
    }

    public TipoReporte getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(TipoReporte tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getContenidoBase64() {
        return contenidoBase64;
    }

    public void setContenidoBase64(String contenidoBase64) {
        this.contenidoBase64 = contenidoBase64;
    }

    public long getTamanoBytes() {
        return tamanoBytes;
    }

    public void setTamanoBytes(long tamanoBytes) {
        this.tamanoBytes = tamanoBytes;
    }

    public LocalDateTime getGeneradoEn() {
        return generadoEn;
    }

    public void setGeneradoEn(LocalDateTime generadoEn) {
        this.generadoEn = generadoEn;
    }
}
