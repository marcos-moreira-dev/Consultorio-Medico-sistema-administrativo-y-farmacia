package dev.marcosmoreira.consultorio.atenciones.application.port.in;

import dev.marcosmoreira.consultorio.atenciones.domain.model.Atencion;

/**
 * Caso de uso de registro de una nueva atención.
 *
 * <p>Este puerto de entrada modela la intención de negocio de crear una atención
 * dentro del sistema. La implementación concreta será responsable de validar
 * reglas mínimas de aplicación y delegar la persistencia al puerto de salida correspondiente.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public interface CrearAtencionUseCase {

    /**
     * Registra una nueva atención.
     *
     * @param atencion atención a registrar
     * @return atención registrada con sus datos persistidos
     */
    Atencion crear(Atencion atencion);
}
