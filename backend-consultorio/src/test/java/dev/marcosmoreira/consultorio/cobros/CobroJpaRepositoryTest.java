package dev.marcosmoreira.consultorio.cobros;

import dev.marcosmoreira.consultorio.cobros.domain.enums.EstadoCobro;
import dev.marcosmoreira.consultorio.cobros.domain.enums.MetodoPago;
import dev.marcosmoreira.consultorio.cobros.infrastructure.persistence.entity.CobroEntity;
import dev.marcosmoreira.consultorio.cobros.infrastructure.persistence.repository.CobroJpaRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas JPA del repositorio de cobros.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@DataJpaTest
class CobroJpaRepositoryTest {

    @Autowired
    private CobroJpaRepository cobroJpaRepository;

    @Test
    void existsByAtencionId_deberiaRetornarTrueSiYaExisteCobro() {
        CobroEntity entity = buildCobro(
                5L,
                2L,
                new BigDecimal("25.50"),
                MetodoPago.EFECTIVO,
                EstadoCobro.PAGADO,
                "Pago completo",
                LocalDateTime.of(2026, 4, 8, 10, 0)
        );

        cobroJpaRepository.save(entity);

        boolean exists = cobroJpaRepository.existsByAtencionId(5L);

        assertTrue(exists);
    }

    @Test
    void buscarPorFiltros_deberiaRetornarCoincidencias() {
        CobroEntity c1 = buildCobro(
                5L,
                2L,
                new BigDecimal("25.50"),
                MetodoPago.EFECTIVO,
                EstadoCobro.PAGADO,
                null,
                LocalDateTime.of(2026, 4, 8, 10, 0)
        );
        CobroEntity c2 = buildCobro(
                6L,
                3L,
                new BigDecimal("40.00"),
                MetodoPago.TARJETA,
                EstadoCobro.PENDIENTE,
                null,
                LocalDateTime.of(2026, 4, 8, 12, 0)
        );

        cobroJpaRepository.save(c1);
        cobroJpaRepository.save(c2);

        Pageable pageable = PageRequest.of(0, 20);
        var result = cobroJpaRepository.buscarPorFiltros(
                5L,
                2L,
                EstadoCobro.PAGADO,
                MetodoPago.EFECTIVO,
                LocalDateTime.of(2026, 4, 1, 0, 0),
                LocalDateTime.of(2026, 4, 30, 23, 59),
                pageable
        );

        assertEquals(1, result.getContent().size());
        assertEquals(new BigDecimal("25.50"), result.getContent().get(0).getMonto());
    }

    private CobroEntity buildCobro(
            Long atencionId,
            Long registradoPorUsuarioId,
            BigDecimal monto,
            MetodoPago metodoPago,
            EstadoCobro estadoCobro,
            String observacionAdministrativa,
            LocalDateTime fechaHoraRegistro
    ) {
        CobroEntity entity = new CobroEntity();
        entity.setAtencionId(atencionId);
        entity.setRegistradoPorUsuarioId(registradoPorUsuarioId);
        entity.setMonto(monto);
        entity.setMetodoPago(metodoPago);
        entity.setEstadoCobro(estadoCobro);
        entity.setObservacionAdministrativa(observacionAdministrativa);
        entity.setFechaHoraRegistro(fechaHoraRegistro);
        return entity;
    }
}
