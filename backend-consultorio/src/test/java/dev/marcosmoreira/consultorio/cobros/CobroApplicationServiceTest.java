package dev.marcosmoreira.consultorio.cobros;

import dev.marcosmoreira.consultorio.atenciones.application.port.in.BuscarAtencionUseCase;
import dev.marcosmoreira.consultorio.atenciones.domain.model.Atencion;
import dev.marcosmoreira.consultorio.cobros.application.port.out.CobroPersistencePort;
import dev.marcosmoreira.consultorio.cobros.application.service.CobroApplicationService;
import dev.marcosmoreira.consultorio.cobros.domain.enums.EstadoCobro;
import dev.marcosmoreira.consultorio.cobros.domain.enums.MetodoPago;
import dev.marcosmoreira.consultorio.cobros.domain.model.Cobro;
import dev.marcosmoreira.consultorio.shared.error.DuplicateResourceException;
import dev.marcosmoreira.consultorio.shared.error.ResourceNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias del servicio de aplicación del módulo de cobros.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
class CobroApplicationServiceTest {

    @Mock
    private CobroPersistencePort cobroPersistencePort;

    @Mock
    private BuscarAtencionUseCase buscarAtencionUseCase;

    private CobroApplicationService cobroApplicationService;

    /**
     * Inicializa el servicio bajo prueba antes de cada test.
     */
    @BeforeEach
    void setUp() {
        cobroApplicationService = new CobroApplicationService(
                cobroPersistencePort,
                buscarAtencionUseCase
        );
    }

    /**
     * Verifica el registro exitoso de un cobro.
     */
    @Test
    void registrar_deberiaPersistirCobroYValidarAtencion() {
        when(buscarAtencionUseCase.buscarPorId(5L))
                .thenReturn(new Atencion(
                        5L,
                        1L,
                        2L,
                        null,
                        LocalDateTime.of(2026, 4, 8, 9, 0),
                        "Control general",
                        null,
                        null,
                        null
                ));
        when(cobroPersistencePort.existsByAtencionId(5L)).thenReturn(false);

        Cobro persisted = new Cobro(
                1L,
                5L,
                2L,
                new BigDecimal("25.50"),
                MetodoPago.EFECTIVO,
                EstadoCobro.PAGADO,
                "Pago completo",
                LocalDateTime.of(2026, 4, 8, 10, 0),
                null,
                null
        );

        when(cobroPersistencePort.guardar(any(Cobro.class))).thenReturn(persisted);

        Cobro response = cobroApplicationService.registrar(
                5L,
                2L,
                new BigDecimal("25.50"),
                MetodoPago.EFECTIVO,
                EstadoCobro.PAGADO,
                " Pago completo "
        );

        assertNotNull(response);
        assertEquals(1L, response.getCobroId());
        assertEquals(new BigDecimal("25.50"), response.getMonto());

        verify(buscarAtencionUseCase).buscarPorId(5L);

        ArgumentCaptor<Cobro> captor = ArgumentCaptor.forClass(Cobro.class);
        verify(cobroPersistencePort).guardar(captor.capture());

        Cobro enviado = captor.getValue();
        assertEquals(5L, enviado.getAtencionId());
        assertEquals(2L, enviado.getRegistradoPorUsuarioId());
        assertEquals(MetodoPago.EFECTIVO, enviado.getMetodoPago());
        assertEquals(EstadoCobro.PAGADO, enviado.getEstadoCobro());
        assertEquals("Pago completo", enviado.getObservacionAdministrativa());
    }

    /**
     * Verifica que no se permita registrar dos cobros para la misma atención.
     */
    @Test
    void registrar_deberiaLanzarExcepcionSiAtencionYaTieneCobro() {
        when(buscarAtencionUseCase.buscarPorId(5L)).thenReturn(new Atencion());
        when(cobroPersistencePort.existsByAtencionId(5L)).thenReturn(true);

        DuplicateResourceException exception = assertThrows(
                DuplicateResourceException.class,
                () -> cobroApplicationService.registrar(
                        5L,
                        2L,
                        new BigDecimal("25.50"),
                        MetodoPago.EFECTIVO,
                        EstadoCobro.PAGADO,
                        null
                )
        );

        assertEquals(
                "Ya existe un cobro asociado a la atención con ID 5.",
                exception.getMessage()
        );
        verify(cobroPersistencePort, never()).guardar(any());
    }

    /**
     * Verifica la búsqueda exitosa de un cobro.
     */
    @Test
    void buscarPorId_deberiaRetornarCobroSiExiste() {
        Cobro cobro = new Cobro(
                10L,
                5L,
                2L,
                new BigDecimal("30.00"),
                MetodoPago.TARJETA,
                EstadoCobro.PENDIENTE,
                null,
                LocalDateTime.of(2026, 4, 8, 11, 0),
                null,
                null
        );

        when(cobroPersistencePort.buscarPorId(10L)).thenReturn(Optional.of(cobro));

        Cobro response = cobroApplicationService.buscarPorId(10L);

        assertNotNull(response);
        assertEquals(10L, response.getCobroId());
    }

    /**
     * Verifica que la búsqueda falle cuando el cobro no existe.
     */
    @Test
    void buscarPorId_deberiaLanzarExcepcionSiNoExiste() {
        when(cobroPersistencePort.buscarPorId(77L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> cobroApplicationService.buscarPorId(77L)
        );

        assertEquals("No se encontró un cobro con ID 77.", exception.getMessage());
    }

    /**
     * Verifica el listado con filtros opcionales.
     */
    @Test
    void listar_deberiaDelegarEnPersistenciaConFiltros() {
        LocalDateTime desde = LocalDateTime.of(2026, 4, 1, 0, 0);
        LocalDateTime hasta = LocalDateTime.of(2026, 4, 30, 23, 59);
        Pageable pageable = PageRequest.of(0, 20);

        Cobro cobro = new Cobro(
                1L,
                5L,
                2L,
                new BigDecimal("10.00"),
                MetodoPago.EFECTIVO,
                EstadoCobro.PAGADO,
                null,
                LocalDateTime.of(2026, 4, 8, 11, 0),
                null,
                null
        );

        when(cobroPersistencePort.listar(5L, 2L, EstadoCobro.PAGADO, MetodoPago.EFECTIVO, desde, hasta, pageable))
                .thenReturn(org.springframework.data.domain.Page.empty());

        Page<Cobro> result = cobroApplicationService.listar(
                5L,
                2L,
                EstadoCobro.PAGADO,
                MetodoPago.EFECTIVO,
                desde,
                hasta,
                pageable
        );

        verify(cobroPersistencePort).listar(5L, 2L, EstadoCobro.PAGADO, MetodoPago.EFECTIVO, desde, hasta, pageable);
    }
}
