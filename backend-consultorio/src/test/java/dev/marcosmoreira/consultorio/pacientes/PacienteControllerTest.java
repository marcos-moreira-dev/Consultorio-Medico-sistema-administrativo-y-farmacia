package dev.marcosmoreira.consultorio.pacientes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.marcosmoreira.consultorio.pacientes.api.controller.PacienteController;
import dev.marcosmoreira.consultorio.pacientes.application.port.in.ActualizarPacienteUseCase;
import dev.marcosmoreira.consultorio.pacientes.application.port.in.BuscarPacienteUseCase;
import dev.marcosmoreira.consultorio.pacientes.application.port.in.CrearPacienteUseCase;
import dev.marcosmoreira.consultorio.pacientes.application.port.in.ListarPacientesUseCase;
import dev.marcosmoreira.consultorio.pacientes.domain.model.Paciente;
import dev.marcosmoreira.consultorio.shared.web.GlobalExceptionHandler;
import java.time.LocalDate;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas unitarias HTTP del controlador de pacientes usando MockMvc standalone.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
class PacienteControllerTest {

    @Mock
    private CrearPacienteUseCase crearPacienteUseCase;

    @Mock
    private ActualizarPacienteUseCase actualizarPacienteUseCase;

    @Mock
    private BuscarPacienteUseCase buscarPacienteUseCase;

    @Mock
    private ListarPacientesUseCase listarPacientesUseCase;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    /**
     * Inicializa MockMvc y el mapper JSON antes de cada test.
     */
    @BeforeEach
    void setUp() {
        PacienteController controller = new PacienteController(
                crearPacienteUseCase,
                actualizarPacienteUseCase,
                buscarPacienteUseCase,
                listarPacientesUseCase
        );

        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();

        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Verifica el alta exitosa de un paciente.
     *
     * @throws Exception si MockMvc falla al ejecutar la petición
     */
    @Test
    void crear_deberiaRetornar201() throws Exception {
        Paciente paciente = new Paciente(
                1L,
                "Ana",
                "Pérez",
                "0991112233",
                "0922334455",
                LocalDate.of(1998, 5, 10),
                "Centro",
                null,
                null
        );

        when(crearPacienteUseCase.crear(
                anyString(),
                anyString(),
                any(),
                any(),
                any(),
                any()
        )).thenReturn(paciente);

        String body = """
                {
                  "nombres": "Ana",
                  "apellidos": "Pérez",
                  "telefono": "0991112233",
                  "cedula": "0922334455",
                  "fechaNacimiento": "1998-05-10",
                  "direccionBasica": "Centro"
                }
                """;

        mockMvc.perform(post("/api/v1/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ok").value(true))
                .andExpect(jsonPath("$.data.pacienteId").value(1))
                .andExpect(jsonPath("$.data.nombres").value("Ana"))
                .andExpect(jsonPath("$.data.cedula").value("0922334455"));
    }

    /**
     * Verifica la consulta puntual de un paciente por ID.
     *
     * @throws Exception si MockMvc falla al ejecutar la petición
     */
    @Test
    void buscarPorId_deberiaRetornar200() throws Exception {
        Paciente paciente = new Paciente(
                8L,
                "Luis",
                "Mora",
                "0992223344",
                "0911223344",
                LocalDate.of(1990, 1, 15),
                "Norte",
                null,
                null
        );

        when(buscarPacienteUseCase.buscarPorId(8L)).thenReturn(paciente);

        mockMvc.perform(get("/api/v1/pacientes/8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok").value(true))
                .andExpect(jsonPath("$.data.pacienteId").value(8))
                .andExpect(jsonPath("$.data.nombreCompleto").value("Luis Mora"));
    }

    /**
     * Verifica el listado básico de pacientes.
     *
     * @throws Exception si MockMvc falla al ejecutar la petición
     */
    @Test
    void listar_deberiaRetornar200ConLista() throws Exception {
        Paciente paciente1 = new Paciente(
                1L, "Ana", "Pérez", "0991112233", "0922334455",
                LocalDate.of(1998, 5, 10), "Centro", null, null
        );
        Paciente paciente2 = new Paciente(
                2L, "Luis", "Mora", "0992223344", "0911223344",
                LocalDate.of(1990, 1, 15), "Norte", null, null
        );

        when(listarPacientesUseCase.listar(null, null, null, any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(paciente1, paciente2)));

        mockMvc.perform(get("/api/v1/pacientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok").value(true))
                .andExpect(jsonPath("$.data.content.length()").value(2))
                .andExpect(jsonPath("$.data.content[0].nombreCompleto").value("Ana Pérez"))
                .andExpect(jsonPath("$.data.content[1].nombreCompleto").value("Luis Mora"));
    }

    /**
     * Verifica la actualización básica de un paciente.
     *
     * @throws Exception si MockMvc falla al ejecutar la petición
     */
    @Test
    void actualizar_deberiaRetornar200() throws Exception {
        Paciente paciente = new Paciente(
                5L,
                "Ana María",
                "Pérez",
                "0999999999",
                "0922334455",
                LocalDate.of(1998, 5, 10),
                "Norte",
                null,
                null
        );

        when(actualizarPacienteUseCase.actualizar(
                eq(5L),
                anyString(),
                anyString(),
                any(),
                any(),
                any(),
                any()
        )).thenReturn(paciente);

        String body = """
                {
                  "nombres": "Ana María",
                  "apellidos": "Pérez",
                  "telefono": "0999999999",
                  "cedula": "0922334455",
                  "fechaNacimiento": "1998-05-10",
                  "direccionBasica": "Norte"
                }
                """;

        mockMvc.perform(put("/api/v1/pacientes/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok").value(true))
                .andExpect(jsonPath("$.data.nombres").value("Ana María"))
                .andExpect(jsonPath("$.data.telefono").value("0999999999"));
    }
}
