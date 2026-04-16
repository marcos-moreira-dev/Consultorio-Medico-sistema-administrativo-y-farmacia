package dev.marcosmoreira.consultorio.citas;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.marcosmoreira.consultorio.citas.api.controller.CitaController;
import dev.marcosmoreira.consultorio.citas.application.port.in.BuscarCitaUseCase;
import dev.marcosmoreira.consultorio.citas.application.port.in.CancelarCitaUseCase;
import dev.marcosmoreira.consultorio.citas.application.port.in.CrearCitaUseCase;
import dev.marcosmoreira.consultorio.citas.application.port.in.ListarAgendaUseCase;
import dev.marcosmoreira.consultorio.citas.application.port.in.ReprogramarCitaUseCase;
import dev.marcosmoreira.consultorio.citas.domain.enums.EstadoCita;
import dev.marcosmoreira.consultorio.citas.domain.model.Cita;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas unitarias HTTP del controlador de citas usando MockMvc standalone.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
class CitaControllerTest {

    @Mock
    private CrearCitaUseCase crearCitaUseCase;

    @Mock
    private BuscarCitaUseCase buscarCitaUseCase;

    @Mock
    private ListarAgendaUseCase listarAgendaUseCase;

    @Mock
    private CancelarCitaUseCase cancelarCitaUseCase;

    @Mock
    private ReprogramarCitaUseCase reprogramarCitaUseCase;

    private MockMvc mockMvc;

    /**
     * Inicializa MockMvc antes de cada test.
     */
    @BeforeEach
    void setUp() {
        CitaController controller = new CitaController(
                crearCitaUseCase,
                buscarCitaUseCase,
                listarAgendaUseCase,
                cancelarCitaUseCase,
                reprogramarCitaUseCase
        );

        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();
    }

    /**
     * Verifica el alta exitosa de una cita.
     *
     * @throws Exception si MockMvc falla
     */
    @Test
    void crear_deberiaRetornar201() throws Exception {
        Cita cita = new Cita(
                1L,
                1L,
                2L,
                LocalDateTime.of(2026, 4, 10, 9, 0),
                EstadoCita.PROGRAMADA,
                "Control",
                "Primera visita",
                null,
                null
        );

        when(crearCitaUseCase.crear(anyLong(), anyLong(), any(LocalDateTime.class), any(), any()))
                .thenReturn(cita);

        String body = """
                {
                  "pacienteId": 1,
                  "profesionalId": 2,
                  "fechaHoraInicio": "2026-04-10T09:00:00",
                  "motivoBreve": "Control",
                  "observacionOperativa": "Primera visita"
                }
                """;

        mockMvc.perform(post("/api/v1/citas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ok").value(true))
                .andExpect(jsonPath("$.data.citaId").value(1))
                .andExpect(jsonPath("$.data.estadoCita").value("PROGRAMADA"));
    }

    /**
     * Verifica la consulta puntual de una cita.
     *
     * @throws Exception si MockMvc falla
     */
    @Test
    void buscarPorId_deberiaRetornar200() throws Exception {
        Cita cita = new Cita(
                9L,
                1L,
                2L,
                LocalDateTime.of(2026, 4, 11, 10, 0),
                EstadoCita.PROGRAMADA,
                "Control",
                null,
                null,
                null
        );

        when(buscarCitaUseCase.buscarPorId(9L)).thenReturn(cita);

        mockMvc.perform(get("/api/v1/citas/9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.citaId").value(9))
                .andExpect(jsonPath("$.data.pacienteId").value(1))
                .andExpect(jsonPath("$.data.profesionalId").value(2));
    }

    /**
     * Verifica el listado básico de agenda.
     *
     * @throws Exception si MockMvc falla
     */
    @Test
    void listarAgenda_deberiaRetornar200ConLista() throws Exception {
        Cita cita1 = new Cita(
                1L, 1L, 2L, LocalDateTime.of(2026, 4, 10, 9, 0),
                EstadoCita.PROGRAMADA, "Control", null, null, null
        );
        Cita cita2 = new Cita(
                2L, 3L, 2L, LocalDateTime.of(2026, 4, 10, 10, 0),
                EstadoCita.CANCELADA, "Chequeo", null, null, null
        );

        when(listarAgendaUseCase.listar(null, null, null, null, null, any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(cita1, cita2)));

        mockMvc.perform(get("/api/v1/citas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content.length()").value(2))
                .andExpect(jsonPath("$.data.content[0].citaId").value(1))
                .andExpect(jsonPath("$.data.content[1].estadoCita").value("CANCELADA"));
    }

    /**
     * Verifica la cancelación de una cita.
     *
     * @throws Exception si MockMvc falla
     */
    @Test
    void cancelar_deberiaRetornar200() throws Exception {
        Cita cita = new Cita(
                5L,
                1L,
                2L,
                LocalDateTime.of(2026, 4, 10, 12, 0),
                EstadoCita.CANCELADA,
                "Control",
                "Paciente no asistirá",
                null,
                null
        );

        when(cancelarCitaUseCase.cancelar(eq(5L), any()))
                .thenReturn(cita);

        String body = """
                {
                  "observacionOperativa": "Paciente no asistirá"
                }
                """;

        mockMvc.perform(patch("/api/v1/citas/5/cancelar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.estadoCita").value("CANCELADA"));
    }

    /**
     * Verifica la reprogramación de una cita.
     *
     * @throws Exception si MockMvc falla
     */
    @Test
    void reprogramar_deberiaRetornar200() throws Exception {
        Cita cita = new Cita(
                6L,
                1L,
                2L,
                LocalDateTime.of(2026, 4, 10, 14, 0),
                EstadoCita.PROGRAMADA,
                "Control",
                "Reagendada",
                null,
                null
        );

        when(reprogramarCitaUseCase.reprogramar(eq(6L), any(LocalDateTime.class), any()))
                .thenReturn(cita);

        String body = """
                {
                  "nuevaFechaHoraInicio": "2026-04-10T14:00:00",
                  "observacionOperativa": "Reagendada"
                }
                """;

        mockMvc.perform(patch("/api/v1/citas/6/reprogramar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.citaId").value(6))
                .andExpect(jsonPath("$.data.observacionOperativa").value("Reagendada"));
    }
}
