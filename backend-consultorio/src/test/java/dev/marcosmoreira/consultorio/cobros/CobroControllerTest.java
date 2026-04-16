package dev.marcosmoreira.consultorio.cobros;

import dev.marcosmoreira.consultorio.cobros.api.controller.CobroController;
import dev.marcosmoreira.consultorio.cobros.application.port.in.BuscarCobroUseCase;
import dev.marcosmoreira.consultorio.cobros.application.port.in.ListarCobrosUseCase;
import dev.marcosmoreira.consultorio.cobros.application.port.in.RegistrarCobroUseCase;
import dev.marcosmoreira.consultorio.cobros.domain.enums.EstadoCobro;
import dev.marcosmoreira.consultorio.cobros.domain.enums.MetodoPago;
import dev.marcosmoreira.consultorio.cobros.domain.model.Cobro;
import dev.marcosmoreira.consultorio.shared.web.GlobalExceptionHandler;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas unitarias HTTP del controlador de cobros usando MockMvc standalone.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
class CobroControllerTest {

    @Mock
    private RegistrarCobroUseCase registrarCobroUseCase;

    @Mock
    private BuscarCobroUseCase buscarCobroUseCase;

    @Mock
    private ListarCobrosUseCase listarCobrosUseCase;

    private MockMvc mockMvc;

    /**
     * Inicializa MockMvc antes de cada test.
     */
    @BeforeEach
    void setUp() {
        CobroController controller = new CobroController(
                registrarCobroUseCase,
                buscarCobroUseCase,
                listarCobrosUseCase
        );

        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();
    }

    /**
     * Verifica el registro exitoso de un cobro.
     *
     * @throws Exception si MockMvc falla
     */
    @Test
    void registrar_deberiaRetornar201() throws Exception {
        Cobro cobro = new Cobro(
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

        when(registrarCobroUseCase.registrar(
                anyLong(), any(), any(BigDecimal.class), any(MetodoPago.class), any(EstadoCobro.class), any()
        )).thenReturn(cobro);

        String body = """
                {
                  "atencionId": 5,
                  "registradoPorUsuarioId": 2,
                  "monto": 25.50,
                  "metodoPago": "EFECTIVO",
                  "estadoCobro": "PAGADO",
                  "observacionAdministrativa": "Pago completo"
                }
                """;

        mockMvc.perform(post("/api/v1/cobros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.cobroId").value(1))
                .andExpect(jsonPath("$.data.metodoPago").value("EFECTIVO"))
                .andExpect(jsonPath("$.data.estadoCobro").value("PAGADO"));
    }

    /**
     * Verifica la consulta puntual de un cobro.
     *
     * @throws Exception si MockMvc falla
     */
    @Test
    void buscarPorId_deberiaRetornar200() throws Exception {
        Cobro cobro = new Cobro(
                7L,
                9L,
                2L,
                new BigDecimal("40.00"),
                MetodoPago.TARJETA,
                EstadoCobro.PENDIENTE,
                null,
                LocalDateTime.of(2026, 4, 8, 12, 0),
                null,
                null
        );

        when(buscarCobroUseCase.buscarPorId(7L)).thenReturn(cobro);

        mockMvc.perform(get("/api/v1/cobros/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.cobroId").value(7))
                .andExpect(jsonPath("$.data.atencionId").value(9))
                .andExpect(jsonPath("$.data.metodoPago").value("TARJETA"));
    }

    /**
     * Verifica el listado básico de cobros.
     *
     * @throws Exception si MockMvc falla
     */
    @Test
    void listar_deberiaRetornar200ConLista() throws Exception {
        Cobro cobro1 = new Cobro(
                1L, 5L, 2L, new BigDecimal("25.50"), MetodoPago.EFECTIVO,
                EstadoCobro.PAGADO, null, LocalDateTime.of(2026, 4, 8, 10, 0), null, null
        );
        Cobro cobro2 = new Cobro(
                2L, 6L, 3L, new BigDecimal("40.00"), MetodoPago.TARJETA,
                EstadoCobro.PENDIENTE, null, LocalDateTime.of(2026, 4, 8, 12, 0), null, null
        );

        when(listarCobrosUseCase.listar(null, null, null, null, null, null, any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(cobro1, cobro2)));

        mockMvc.perform(get("/api/v1/cobros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content.length()").value(2))
                .andExpect(jsonPath("$.data.content[0].cobroId").value(1))
                .andExpect(jsonPath("$.data.content[1].estadoCobro").value("PENDIENTE"));
    }
}
