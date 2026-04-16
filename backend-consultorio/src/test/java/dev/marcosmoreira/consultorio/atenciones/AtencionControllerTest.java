package dev.marcosmoreira.consultorio.atenciones;

import dev.marcosmoreira.consultorio.atenciones.api.controller.AtencionController;
import dev.marcosmoreira.consultorio.atenciones.application.port.in.BuscarAtencionUseCase;
import dev.marcosmoreira.consultorio.atenciones.application.port.in.CrearAtencionUseCase;
import dev.marcosmoreira.consultorio.atenciones.application.port.in.ListarAtencionesUseCase;
import dev.marcosmoreira.consultorio.atenciones.domain.model.Atencion;
import dev.marcosmoreira.consultorio.shared.web.GlobalExceptionHandler;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas unitarias HTTP del controlador de atenciones usando MockMvc standalone.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
class AtencionControllerTest {

    @Mock
    private CrearAtencionUseCase crearAtencionUseCase;

    @Mock
    private BuscarAtencionUseCase buscarAtencionUseCase;

    @Mock
    private ListarAtencionesUseCase listarAtencionesUseCase;

    private MockMvc mockMvc;

    /**
     * Inicializa MockMvc antes de cada test.
     */
    @BeforeEach
    void setUp() {
        AtencionController controller = new AtencionController(
                crearAtencionUseCase,
                buscarAtencionUseCase,
                listarAtencionesUseCase
        );

        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();
    }

    /**
     * Verifica el alta exitosa de una atención.
     *
     * @throws Exception si MockMvc falla
     */
    @Test
    void crear_deberiaRetornar201() throws Exception {
        Atencion atencion = new Atencion(
                1L,
                1L,
                2L,
                3L,
                LocalDateTime.of(2026, 4, 8, 9, 0),
                "Control general",
                "Reposo",
                null,
                null
        );

        when(crearAtencionUseCase.crear(any(Atencion.class))).thenReturn(atencion);

        String body = """
                {
                  "pacienteId": 1,
                  "profesionalId": 2,
                  "citaId": 3,
                  "fechaHoraAtencion": "2026-04-08T09:00:00",
                  "notaBreve": "Control general",
                  "indicacionesBreves": "Reposo"
                }
                """;

        mockMvc.perform(post("/api/v1/atenciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ok").value(true))
                .andExpect(jsonPath("$.data.atencionId").value(1))
                .andExpect(jsonPath("$.data.notaBreve").value("Control general"));
    }

    /**
     * Verifica la consulta puntual de una atención.
     *
     * @throws Exception si MockMvc falla
     */
    @Test
    void buscarPorId_deberiaRetornar200() throws Exception {
        Atencion atencion = new Atencion(
                9L,
                1L,
                2L,
                null,
                LocalDateTime.of(2026, 4, 8, 10, 0),
                "Chequeo",
                null,
                null,
                null
        );

        when(buscarAtencionUseCase.buscarPorId(9L)).thenReturn(atencion);

        mockMvc.perform(get("/api/v1/atenciones/9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.atencionId").value(9))
                .andExpect(jsonPath("$.data.pacienteId").value(1))
                .andExpect(jsonPath("$.data.profesionalId").value(2));
    }

    /**
     * Verifica el listado básico de atenciones.
     *
     * @throws Exception si MockMvc falla
     */
    @Test
    void listar_deberiaRetornar200ConLista() throws Exception {
        Atencion at1 = new Atencion(
                1L, 1L, 2L, null, LocalDateTime.of(2026, 4, 8, 10, 0),
                "Chequeo", null, null, null
        );
        Atencion at2 = new Atencion(
                2L, 3L, 4L, null, LocalDateTime.of(2026, 4, 8, 11, 0),
                "Control", null, null, null
        );

        when(listarAtencionesUseCase.listar(null, null, null, null, any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(at1, at2)));

        mockMvc.perform(get("/api/v1/atenciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content.length()").value(2))
                .andExpect(jsonPath("$.data.content[0].atencionId").value(1))
                .andExpect(jsonPath("$.data.content[1].atencionId").value(2));
    }
}
