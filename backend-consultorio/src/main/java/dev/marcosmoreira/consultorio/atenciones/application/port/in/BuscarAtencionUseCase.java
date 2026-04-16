package dev.marcosmoreira.consultorio.atenciones.application.port.in;

import dev.marcosmoreira.consultorio.atenciones.domain.model.Atencion;

/**
 * Caso de uso de consulta puntual de una atención.
 *
 * <p>Este puerto de entrada representa la operación de aplicación que permite
 * recuperar una atención específica a partir de su identificador único.</p>
 *
 * <p>Desde la perspectiva de arquitectura hexagonal, este contrato pertenece
 * a la capa de aplicación y define lo que el sistema puede hacer, sin exponer
 * cómo se resuelve internamente la búsqueda.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public interface BuscarAtencionUseCase {

    /**
     * Busca una atención por su identificador único.
     *
     * @param atencionId identificador de la atención a consultar
     * @return atención encontrada
     */
    Atencion buscarPorId(Long atencionId);
}
