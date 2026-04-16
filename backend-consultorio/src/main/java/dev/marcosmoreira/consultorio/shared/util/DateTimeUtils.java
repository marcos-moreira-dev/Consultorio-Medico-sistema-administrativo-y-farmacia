package dev.marcosmoreira.consultorio.shared.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Utilidades transversales para fechas y horas.
 *
 * <p>Centralizar pequeñas operaciones temporales ayuda a evitar duplicación
 * y a mantener consistencia cuando varios módulos trabajan con rangos,
 * normalización o formateo básico.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public final class DateTimeUtils {

    /**
     * Formato ISO local usado frecuentemente en la API.
     */
    public static final DateTimeFormatter ISO_LOCAL_DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private DateTimeUtils() {
    }

    /**
     * Devuelve la fecha y hora actual del sistema.
     *
     * @return fecha y hora actual
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * Devuelve el inicio del día para una fecha dada.
     *
     * @param date fecha de referencia
     * @return fecha y hora al inicio del día
     */
    public static LocalDateTime startOfDay(LocalDate date) {
        return date == null ? null : date.atStartOfDay();
    }

    /**
     * Devuelve el final conceptual del día para una fecha dada.
     *
     * @param date fecha de referencia
     * @return fecha y hora al final del día
     */
    public static LocalDateTime endOfDay(LocalDate date) {
        return date == null ? null : date.atTime(LocalTime.MAX);
    }

    /**
     * Formatea un {@link LocalDateTime} a string ISO local simple.
     *
     * @param value fecha y hora a formatear
     * @return string formateado o {@code null} si el valor es nulo
     */
    public static String formatIso(LocalDateTime value) {
        return value == null ? null : value.format(ISO_LOCAL_DATE_TIME_FORMATTER);
    }

    /**
     * Indica si un rango temporal es coherente.
     *
     * @param from extremo inicial
     * @param to extremo final
     * @return {@code true} si el rango es válido o incompleto; {@code false} si es inconsistente
     */
    public static boolean isValidRange(LocalDateTime from, LocalDateTime to) {
        return from == null || to == null || !from.isAfter(to);
    }
}
