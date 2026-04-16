package dev.marcosmoreira.consultorio.usuarios;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.marcosmoreira.consultorio.shared.web.GlobalExceptionHandler;
import dev.marcosmoreira.consultorio.usuarios.api.controller.UsuarioController;
import dev.marcosmoreira.consultorio.usuarios.application.port.in.BuscarUsuarioUseCase;
import dev.marcosmoreira.consultorio.usuarios.application.port.in.CambiarEstadoUsuarioUseCase;
import dev.marcosmoreira.consultorio.usuarios.application.port.in.CrearUsuarioUseCase;
import dev.marcosmoreira.consultorio.usuarios.application.port.in.ListarUsuariosUseCase;
import dev.marcosmoreira.consultorio.usuarios.application.port.in.ResetPasswordUsuarioUseCase;
import dev.marcosmoreira.consultorio.usuarios.domain.enums.EstadoUsuario;
import dev.marcosmoreira.consultorio.usuarios.domain.model.Usuario;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock private CrearUsuarioUseCase crearUsuarioUseCase;
    @Mock private BuscarUsuarioUseCase buscarUsuarioUseCase;
    @Mock private ListarUsuariosUseCase listarUsuariosUseCase;
    @Mock private CambiarEstadoUsuarioUseCase cambiarEstadoUsuarioUseCase;
    @Mock private ResetPasswordUsuarioUseCase resetPasswordUsuarioUseCase;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        UsuarioController controller = new UsuarioController(
                crearUsuarioUseCase,
                buscarUsuarioUseCase,
                listarUsuariosUseCase,
                cambiarEstadoUsuarioUseCase,
                resetPasswordUsuarioUseCase
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

    @Test
    void crearUsuario_deberiaRetornar201() throws Exception {
        Usuario usuario = new Usuario(
                1L,
                10L,
                "ADMIN_CONSULTORIO",
                "Administrador interno del consultorio",
                "admin.consultorio",
                "HASH",
                "admin.consultorio",
                EstadoUsuario.ACTIVO,
                null,
                null
        );

        when(crearUsuarioUseCase.crear(anyLong(), anyString(), anyString())).thenReturn(usuario);

        String body = """
                {
                  "rolId": 10,
                  "username": "admin.consultorio",
                  "passwordTemporal": "Temporal123"
                }
                """;

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ok").value(true))
                .andExpect(jsonPath("$.data.usuarioId").value(1))
                .andExpect(jsonPath("$.data.username").value("admin.consultorio"))
                .andExpect(jsonPath("$.data.rolCodigo").value("ADMIN_CONSULTORIO"));
    }

    @Test
    void buscarPorId_deberiaRetornar200() throws Exception {
        Usuario usuario = new Usuario(
                8L,
                2L,
                "OPERADOR_CONSULTORIO",
                "Usuario de recepción y operación administrativa",
                "recepcion.ana",
                "HASH",
                "recepcion.ana",
                EstadoUsuario.ACTIVO,
                null,
                null
        );

        when(buscarUsuarioUseCase.buscarPorId(8L)).thenReturn(usuario);

        mockMvc.perform(get("/api/v1/usuarios/8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok").value(true))
                .andExpect(jsonPath("$.data.usuarioId").value(8))
                .andExpect(jsonPath("$.data.username").value("recepcion.ana"));
    }

    @Test
    void listarUsuarios_deberiaRetornar200ConPageResponse() throws Exception {
        Usuario admin = new Usuario(1L, 10L, "ADMIN_CONSULTORIO", "Administrador interno del consultorio", "admin.consultorio", "HASH", "admin.consultorio", EstadoUsuario.ACTIVO, null, null);
        Usuario operador = new Usuario(2L, 20L, "OPERADOR_CONSULTORIO", "Usuario de recepción y operación administrativa", "recepcion.ana", "HASH", "recepcion.ana", EstadoUsuario.ACTIVO, null, null);
        Page<Usuario> page = new PageImpl<>(List.of(admin, operador), PageRequest.of(0, 20), 2);

        when(listarUsuariosUseCase.listar(eq(null), eq(null), eq(null), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok").value(true))
                .andExpect(jsonPath("$.data.items.length()").value(2))
                .andExpect(jsonPath("$.data.items[0].username").value("admin.consultorio"))
                .andExpect(jsonPath("$.data.totalElements").value(2));
    }

    @Test
    void cambiarEstado_deberiaRetornar200() throws Exception {
        Usuario usuario = new Usuario(1L, 10L, "ADMIN_CONSULTORIO", "Administrador interno del consultorio", "admin.consultorio", "HASH", "admin.consultorio", EstadoUsuario.INACTIVO, null, null);
        when(cambiarEstadoUsuarioUseCase.cambiarEstado(1L, EstadoUsuario.INACTIVO)).thenReturn(usuario);

        String body = objectMapper.writeValueAsString(java.util.Map.of("nuevoEstado", "INACTIVO"));

        mockMvc.perform(patch("/api/v1/usuarios/1/estado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.estado").value("INACTIVO"));
    }

    @Test
    void resetPassword_deberiaRetornar200() throws Exception {
        Usuario usuario = new Usuario(1L, 10L, "ADMIN_CONSULTORIO", "Administrador interno del consultorio", "admin.consultorio", "HASH_NUEVO", "admin.consultorio", EstadoUsuario.ACTIVO, null, null);
        when(resetPasswordUsuarioUseCase.resetPassword(1L, "TemporalNueva123")).thenReturn(usuario);

        String body = objectMapper.writeValueAsString(java.util.Map.of("nuevoPassword", "TemporalNueva123"));

        mockMvc.perform(patch("/api/v1/usuarios/1/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.passwordHash").value("HASH_NUEVO"));
    }
}
