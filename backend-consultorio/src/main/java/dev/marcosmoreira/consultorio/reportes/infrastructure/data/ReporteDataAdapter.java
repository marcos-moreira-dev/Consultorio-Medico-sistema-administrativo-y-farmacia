package dev.marcosmoreira.consultorio.reportes.infrastructure.data;

import dev.marcosmoreira.consultorio.reportes.application.port.out.ReporteDataPort;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * Adaptador de datos del módulo de reportes.
 *
 * <p>Obtiene un resumen operativo del consultorio a partir de consultas
 * agregadas sobre tablas ya existentes del sistema.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Component
public class ReporteDataAdapter implements ReporteDataPort {

    private final EntityManager entityManager;

    /**
     * Construye el adaptador de datos del módulo.
     *
     * @param entityManager entity manager usado para consultas agregadas
     */
    public ReporteDataAdapter(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Obtiene los datos agregados del reporte usando filtros opcionales.
     *
     * @param pacienteId identificador del paciente, o {@code null} si no aplica
     * @param profesionalId identificador del profesional, o {@code null} si no aplica
     * @param fechaDesde fecha/hora inicial del rango, o {@code null} si no aplica
     * @param fechaHasta fecha/hora final del rango, o {@code null} si no aplica
     * @return estructura de datos ordenada y lista para generación de archivo
     */
    @Override
    public Map<String, Object> obtenerDatos(
            Long pacienteId,
            Long profesionalId,
            LocalDateTime fechaDesde,
            LocalDateTime fechaHasta
    ) {
        LinkedHashMap<String, Object> data = new LinkedHashMap<>();

        data.put("pacienteIdFiltro", pacienteId != null ? pacienteId : "TODOS");
        data.put("profesionalIdFiltro", profesionalId != null ? profesionalId : "TODOS");
        data.put("fechaDesdeFiltro", fechaDesde != null ? fechaDesde : "SIN LIMITE INFERIOR");
        data.put("fechaHastaFiltro", fechaHasta != null ? fechaHasta : "SIN LIMITE SUPERIOR");
        data.put("totalCitas", countCitas(pacienteId, profesionalId, fechaDesde, fechaHasta));
        data.put("totalAtenciones", countAtenciones(pacienteId, profesionalId, fechaDesde, fechaHasta));

        Map<String, Object> cobroSummary = queryCobroSummary(
                pacienteId,
                profesionalId,
                fechaDesde,
                fechaHasta
        );

        data.put("totalCobros", cobroSummary.get("totalCobros"));
        data.put("montoTotalCobrado", cobroSummary.get("montoTotalCobrado"));

        return data;
    }

    /**
     * Cuenta citas aplicando filtros opcionales.
     *
     * @param pacienteId filtro de paciente
     * @param profesionalId filtro de profesional
     * @param fechaDesde filtro inferior de fecha
     * @param fechaHasta filtro superior de fecha
     * @return total de citas
     */
    private long countCitas(
            Long pacienteId,
            Long profesionalId,
            LocalDateTime fechaDesde,
            LocalDateTime fechaHasta
    ) {
        StringBuilder sql = new StringBuilder("""
                SELECT COUNT(*)
                FROM consultorio.cita c
                WHERE 1 = 1
                """);

        Query query = buildAgendaLikeQuery(
                sql,
                "c.paciente_id",
                "c.profesional_id",
                "c.fecha_hora_inicio",
                pacienteId,
                profesionalId,
                fechaDesde,
                fechaHasta
        );

        return toLong(query.getSingleResult());
    }

    /**
     * Cuenta atenciones aplicando filtros opcionales.
     *
     * @param pacienteId filtro de paciente
     * @param profesionalId filtro de profesional
     * @param fechaDesde filtro inferior de fecha
     * @param fechaHasta filtro superior de fecha
     * @return total de atenciones
     */
    private long countAtenciones(
            Long pacienteId,
            Long profesionalId,
            LocalDateTime fechaDesde,
            LocalDateTime fechaHasta
    ) {
        StringBuilder sql = new StringBuilder("""
                SELECT COUNT(*)
                FROM consultorio.atencion a
                WHERE 1 = 1
                """);

        Query query = buildAgendaLikeQuery(
                sql,
                "a.paciente_id",
                "a.profesional_id",
                "a.fecha_hora_atencion",
                pacienteId,
                profesionalId,
                fechaDesde,
                fechaHasta
        );

        return toLong(query.getSingleResult());
    }

    /**
     * Obtiene el conteo y monto total de cobros aplicando filtros opcionales.
     *
     * @param pacienteId filtro de paciente
     * @param profesionalId filtro de profesional
     * @param fechaDesde filtro inferior de fecha
     * @param fechaHasta filtro superior de fecha
     * @return mapa con total de cobros y monto total cobrado
     */
    private Map<String, Object> queryCobroSummary(
            Long pacienteId,
            Long profesionalId,
            LocalDateTime fechaDesde,
            LocalDateTime fechaHasta
    ) {
        StringBuilder sql = new StringBuilder("""
                SELECT COUNT(*), COALESCE(SUM(c.monto), 0)
                FROM consultorio.cobro c
                INNER JOIN consultorio.atencion a ON a.atencion_id = c.atencion_id
                WHERE 1 = 1
                """);

        Query query = buildAgendaLikeQuery(
                sql,
                "a.paciente_id",
                "a.profesional_id",
                "c.fecha_hora_registro",
                pacienteId,
                profesionalId,
                fechaDesde,
                fechaHasta
        );

        Object[] row = (Object[]) query.getSingleResult();

        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        result.put("totalCobros", toLong(row[0]));
        result.put("montoTotalCobrado", toBigDecimal(row[1]));
        return result;
    }

    /**
     * Construye una query agregada con filtros opcionales parecidos para agenda.
     *
     * @param sql base SQL a completar
     * @param pacienteColumn nombre de columna de paciente
     * @param profesionalColumn nombre de columna de profesional
     * @param fechaColumn nombre de columna temporal
     * @param pacienteId filtro de paciente
     * @param profesionalId filtro de profesional
     * @param fechaDesde filtro inferior de fecha
     * @param fechaHasta filtro superior de fecha
     * @return query nativa lista para ejecutar
     */
    private Query buildAgendaLikeQuery(
            StringBuilder sql,
            String pacienteColumn,
            String profesionalColumn,
            String fechaColumn,
            Long pacienteId,
            Long profesionalId,
            LocalDateTime fechaDesde,
            LocalDateTime fechaHasta
    ) {
        if (pacienteId != null) {
            sql.append(" AND ").append(pacienteColumn).append(" = :pacienteId");
        }

        if (profesionalId != null) {
            sql.append(" AND ").append(profesionalColumn).append(" = :profesionalId");
        }

        if (fechaDesde != null) {
            sql.append(" AND ").append(fechaColumn).append(" >= :fechaDesde");
        }

        if (fechaHasta != null) {
            sql.append(" AND ").append(fechaColumn).append(" <= :fechaHasta");
        }

        Query query = entityManager.createNativeQuery(sql.toString());

        if (pacienteId != null) {
            query.setParameter("pacienteId", pacienteId);
        }

        if (profesionalId != null) {
            query.setParameter("profesionalId", profesionalId);
        }

        if (fechaDesde != null) {
            query.setParameter("fechaDesde", fechaDesde);
        }

        if (fechaHasta != null) {
            query.setParameter("fechaHasta", fechaHasta);
        }

        return query;
    }

    /**
     * Convierte un valor numérico heterogéneo a {@code long}.
     *
     * @param value valor devuelto por una consulta agregada
     * @return valor convertido a long
     */
    private long toLong(Object value) {
        if (value == null) {
            return 0L;
        }

        if (value instanceof Long v) {
            return v;
        }

        if (value instanceof Integer v) {
            return v.longValue();
        }

        if (value instanceof BigInteger v) {
            return v.longValue();
        }

        if (value instanceof Number v) {
            return v.longValue();
        }

        return Long.parseLong(String.valueOf(value));
    }

    /**
     * Convierte un valor numérico heterogéneo a {@link BigDecimal}.
     *
     * @param value valor devuelto por una consulta agregada
     * @return valor convertido a BigDecimal
     */
    private BigDecimal toBigDecimal(Object value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }

        if (value instanceof BigDecimal v) {
            return v;
        }

        if (value instanceof BigInteger v) {
            return new BigDecimal(v);
        }

        if (value instanceof Number v) {
            return BigDecimal.valueOf(v.doubleValue());
        }

        return new BigDecimal(String.valueOf(value));
    }
}
