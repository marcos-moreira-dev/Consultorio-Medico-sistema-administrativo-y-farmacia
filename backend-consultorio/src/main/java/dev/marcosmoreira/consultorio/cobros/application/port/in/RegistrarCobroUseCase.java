package dev.marcosmoreira.consultorio.cobros.application.port.in;

import dev.marcosmoreira.consultorio.cobros.domain.enums.EstadoCobro;
import dev.marcosmoreira.consultorio.cobros.domain.enums.MetodoPago;
import dev.marcosmoreira.consultorio.cobros.domain.model.Cobro;
import java.math.BigDecimal;

/**
 * Caso de uso para registrar un cobro asociado a una atención.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public interface RegistrarCobroUseCase {

    /**
     * Registra un nuevo cobro.
     *
     * @param atencionId identificador de la atención asociada
     * @param registradoPorUsuarioId identificador del usuario que registra, si existe
     * @param monto monto del cobro
     * @param metodoPago método de pago
     * @param estadoCobro estado lógico inicial del cobro
     * @param observacionAdministrativa observación administrativa, si existe
     * @return cobro registrado
     */
    Cobro registrar(
            Long atencionId,
            Long registradoPorUsuarioId,
            BigDecimal monto,
            MetodoPago metodoPago,
            EstadoCobro estadoCobro,
            String observacionAdministrativa
    );
}
