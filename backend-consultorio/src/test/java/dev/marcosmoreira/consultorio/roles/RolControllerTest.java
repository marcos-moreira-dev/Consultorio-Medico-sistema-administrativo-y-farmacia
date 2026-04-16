package dev.marcosmoreira.consultorio.roles;

import dev.marcosmoreira.consultorio.roles.api.controller.RolController;
import dev.marcosmoreira.consultorio.roles.application.port.in.BuscarRolUseCase;
import dev.marcosmoreira.consultorio.roles.application.port.in.ListarRolesUseCase;
import dev.marcosmoreira.consultorio.roles.domain.model.Rol;
import dev.marcosmoreira.consultorio.shared.web.GlobalExceptionHandler;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas unitarias HTTP del controlador de roles usando MockMvc standalone.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
class RolControllerTest {

    @Mock
    private BuscarRolUseCase buscarRolUseCase;

    @Mock
    private ListarRolesUseCase listarRolesUseCase;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RolController controller = new RolController(
                buscarRolUseCase,
                listarRolesUseCase
        );

        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void buscarPorId_deberiaRetornar200() throws Exception {
        Rol rol = new Rol(1L, "ADMIN_CONSULTORIO", "Administrador consultorio");

        when(buscarRolUseCase.buscarPorId(1L)).thenReturn(rol);

        mockMvc.perform(get("/api/v1/roles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.rolId").value(1))
                .andExpect(jsonPath("$.data.codigo").value("ADMIN_CONSULTORIO"));
    }

    @Test
    void listar_deberiaRetornar200ConLista() throws Exception {
        Rol r1 = new Rol(1L, "ADMIN_CONSULTORIO", "Administrador consultorio");
        Rol r2 = new Rol(2L, "OPERADOR_CONSULTORIO", "Operador consultorio");

        when(listarRolesUseCase.listar()).thenReturn(List.of(r1, r2));

        mockMvc.perform(get("/api/v1/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].codigo").value("ADMIN_CONSULTORIO"))
                .andExpect(jsonPath("$.data[1].codigo").value("OPERADOR_CONSULTORIO"));
    }
}
