package dev.marcosmoreira.consultorio.reportes.application.port.out;

import dev.marcosmoreira.consultorio.reportes.domain.enums.TipoReporte;
import java.util.Map;

public interface ReporteGeneratorPort {
    TipoReporte getSupportedType();
    String getMimeType();
    String getFileExtension();
    byte[] generate(String titulo, Map<String, Object> data);
}
