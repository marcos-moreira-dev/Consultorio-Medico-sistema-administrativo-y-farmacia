package dev.marcosmoreira.consultorio.auditoria.domain.enums;

/**
 * Catálogo de tipos de evento auditables dentro del sistema.
 *
 * <p>Este enum no representa una tabla ni una entidad persistente. Su finalidad
 * es estandarizar la clasificación semántica de eventos relevantes para trazabilidad
 * operativa y administrativa.</p>
 *
 * <p>La lista puede crecer en versiones futuras, pero para V1.0 conviene mantener
 * un conjunto pequeño y legible.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public enum TipoEventoAuditoria {

    /**
     * Evento de creación de un recurso o registro.
     */
    CREACION,

    /**
     * Evento de actualización o edición de un recurso existente.
     */
    ACTUALIZACION,

    /**
     * Evento de cambio de estado lógico.
     */
    CAMBIO_ESTADO,

    /**
     * Evento de cancelación de una operación o recurso.
     */
    CANCELACION,

    /**
     * Evento de reprogramación de agenda o equivalente.
     */
    REPROGRAMACION,

    /**
     * Evento relacionado con autenticación.
     */
    AUTENTICACION,

    /**
     * Evento relacionado con generación de reportes.
     */
    GENERACION_REPORTE;

    /**
     * Indica si el tipo de evento corresponde a una acción operativa sobre datos
     * del negocio y no a infraestructura transversal.
     *
     * @return {@code true} si es un evento operativo; {@code false} si es transversal
     */
    public boolean esEventoOperativo() {
        return this != AUTENTICACION && this != GENERACION_REPORTE;
    }
}
