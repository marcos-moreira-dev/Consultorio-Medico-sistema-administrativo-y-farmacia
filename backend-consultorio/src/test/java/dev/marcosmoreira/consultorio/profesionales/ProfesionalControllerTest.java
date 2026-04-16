package dev.marcosmoreira.consultorio.profesionales;

import dev.marcosmoreira.consultorio.profesionales.api.controller.ProfesionalController;
import dev.marcosmoreira.consultorio.profesionales.application.port.in.ActualizarProfesionalUseCase;
import dev.marcosmoreira.consultorio.profesionales.application.port.in.BuscarProfesionalUseCase;
import dev.marcosmoreira.consultorio.profesionales.application.port.in.CambiarEstadoProfesionalUseCase;
import dev.marcosmoreira.consultorio.profesionales.application.port.in.CrearProfesionalUseCase;
import dev.marcosmoreira.consultorio.profesionales.application.port.in.ListarProfesionalesUseCase;
import dev.marcosmoreira.consultorio.profesionales.domain.enums.EstadoProfesional;
import dev.marcosmoreira.consultorio.profesionales.domain.model.Profesional;
import dev.marcosmoreira.consultorio.shared.web.GlobalExceptionHandler;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas unitarias HTTP del controlador de profesionales usando MockMvc standalone.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
class ProfesionalControllerTest {

    @Mock
    private CrearProfesionalUseCase crearProfesionalUseCase;

    @Mock
    private ActualizarProfesionalUseCase actualizarProfesionalUseCase;

    @Mock
    private BuscarProfesionalUseCase buscarProfesionalUseCase;

    @Mock
    private ListarProfesionalesUseCase listarProfesionalesUseCase;

    @Mock
    private CambiarEstadoProfesionalUseCase cambiarEstadoProfesionalUseCase;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        ProfesionalController controller = new ProfesionalController(
                crearProfesionalUseCase,
                actualizarProfesionalUseCase,
                buscarProfesionalUseCase,
                listarProfesionalesUseCase,
                cambiarEstadoProfesionalUseCase
        );

        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();
    }

    @Test
    void crear_deberiaRetornar201() throws Exception {
        Profesional profesional = new Profesional(
                1L,
                10L,
                "Carlos",
                "Mendoza",
                "Medicina general",
                "REG-001",
                EstadoProfesional.ACTIVO,
                null,
                null
        );

        when(crearProfesionalUseCase.crear(any(), anyString(), anyString(), any(), any()))
                .thenReturn(profesional);

        String body = """
                {
                  "usuarioId": 10,
                  "nombres": "Carlos",
                  "apellidos": "Mendoza",
                  "especialidadBreve": "Medicina general",
                  "registroProfesional": "REG-001"
                }
                """;

        mockMvc.perform(post("/api/v1/profesionales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ok").value(true))
                .andExpect(jsonPath("$.data.profesionalId").value(1))
                .andExpect(jsonPath("$.data.nombreCompleto").value("Carlos Mendoza"));
    }

    @Test
    void actualizar_deberiaRetornar200() throws Exception {
        Profesional profesional = new Profesional(
                5L,
                11L,
                "Carlos Alberto",
                "Mendoza",
                "Cardiología",
                "REG-002",
                EstadoProfesional.ACTIVO,
                null,
                null
        );

        when(actualizarProfesionalUseCase.actualizar(eq(5L), any(), anyString(), anyString(), any(), any()))
                .thenReturn(profesional);

        String body = """
                {
                  "usuarioId": 11,
                  "nombres": "Carlos Alberto",
                  "apellidos": "Mendoza",
                  "especialidadBreve": "Cardiología",
                  "registroProfesional": "REG-002"
                }
                """;

        mockMvc.perform(put("/api/v1/profesionales/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.profesionalId").value(5))
                .andExpect(jsonPath("$.data.especialidadBreve").value("Cardiología"));
    }

    @Test
    void buscarPorId_deberiaRetornar200() throws Exception {
        Profesional profesional = new Profesional(
                7L,
                10L,
                "Carla",
                "Ponce",
                "Odontología",
                "OD-77",
                EstadoProfesional.ACTIVO,
                null,
                null
        );

        when(buscarProfesionalUseCase.buscarPorId(7L)).thenReturn(profesional);

        mockMvc.perform(get("/api/v1/profesionales/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.profesionalId").value(7))
                .andExpect(jsonPath("$.data.nombreCompleto").value("Carla Ponce"));
    }

    @Test
    void listar_deberiaRetornar200ConLista() throws Exception {
        Profesional p1 = new Profesional(
                1L, 10L, "Ana", "Vera", "Pediatría", "PED-1",
                EstadoProfesional.ACTIVO, null, null
        );
        Profesional p2 = new Profesional(
                2L, 11L, "Luis", "Mora", "Dermatología", "DER-1",
                EstadoProfesional.INACTIVO, null, null
        );

        when(listarProfesionalesUseCase.listar(null, null, null, any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(p1, p2)));

        mockMvc.perform(get("/api/v1/profesionales"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content.length()").value(2))
                .andExpect(jsonPath("$.data.content[0].nombreCompleto").value("Ana Vera"))
                .andExpect(jsonPath("$.data.content[1].estadoProfesional").value("INACTIVO"));
    }

    @Test
    void cambiarEstado_deberiaRetornar200() throws Exception {
        Profesional profesional = new Profesional(
                8L,
                10L,
                "José",
                "Núñez",
                "Dermatología",
                "DERM-1",
                EstadoProfesional.INACTIVO,
                null,
                null
        );

        when(cambiarEstadoProfesionalUseCase.cambiarEstado(eq(8L), eq(EstadoProfesional.INACTIVO)))
                .thenReturn(profesional);

        String body = """
                {
                  "nuevoEstado": "INACTIVO"
                }
                """;

        mockMvc.perform(patch("/api/v1/profesionales/8/estado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.estadoProfesional").value("INACTIVO"));
    }
}
