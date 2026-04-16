package dev.marcosmoreira.consultorio.reportes.application.port.out;

import java.time.LocalDateTime;
import java.util.Map;

public interface ReporteDataPort {
    Map<String, Object> obtenerDatos(Long pacienteId, Long profesionalId, LocalDateTime fechaDesde, LocalDateTime fechaHasta);
}
