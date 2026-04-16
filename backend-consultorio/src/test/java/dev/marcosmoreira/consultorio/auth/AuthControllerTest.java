package dev.marcosmoreira.consultorio.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.marcosmoreira.consultorio.auth.api.controller.AuthController;
import dev.marcosmoreira.consultorio.auth.api.response.LoginResponse;
import dev.marcosmoreira.consultorio.auth.api.response.MeResponse;
import dev.marcosmoreira.consultorio.auth.application.port.in.GetCurrentUserUseCase;
import dev.marcosmoreira.consultorio.auth.application.port.in.LoginUseCase;
import dev.marcosmoreira.consultorio.shared.web.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas unitarias HTTP del controlador de autenticación usando MockMvc standalone.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private LoginUseCase loginUseCase;

    @Mock
    private GetCurrentUserUseCase getCurrentUserUseCase;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    /**
     * Inicializa MockMvc y el mapper JSON antes de cada test.
     */
    @BeforeEach
    void setUp() {
        AuthController controller = new AuthController(loginUseCase, getCurrentUserUseCase);

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
     * Verifica el flujo exitoso del endpoint de login.
     *
     * @throws Exception si MockMvc falla al ejecutar la petición
     */
    @Test
    void login_deberiaRetornar200ConRespuestaEsperada() throws Exception {
        LoginResponse response = new LoginResponse(
                "jwt-token-demo",
                "Bearer",
                3600L,
                new MeResponse(
                        1L,
                        "admin",
                        "Administrador consultorio General",
                        "ADMIN_CONSULTORIO",
                        "Administrador consultorio",
                        true
                )
        );

        when(loginUseCase.login(eq("admin"), eq("123456")))
                .thenReturn(response);

        String body = """
                {
                  "username": "admin",
                  "password": "123456"
                }
                """;

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accessToken").value("jwt-token-demo"))
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.expiresInSeconds").value(3600))
                .andExpect(jsonPath("$.usuario.usuarioId").value(1))
                .andExpect(jsonPath("$.usuario.username").value("admin"))
                .andExpect(jsonPath("$.usuario.rolCodigo").value("ADMIN_CONSULTORIO"));
    }

    /**
     * Verifica el flujo exitoso del endpoint /auth/me.
     *
     * @throws Exception si MockMvc falla al ejecutar la petición
     */
    @Test
    void me_deberiaRetornar200ConUsuarioAutenticado() throws Exception {
        MeResponse response = new MeResponse(
                7L,
                "recepcion1",
                "Operador consultorio Principal",
                "OPERADOR_CONSULTORIO",
                "Operador consultorio",
                true
        );

        when(getCurrentUserUseCase.getCurrentUser()).thenReturn(response);

        mockMvc.perform(get("/api/v1/auth/me"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.usuarioId").value(7))
                .andExpect(jsonPath("$.username").value("recepcion1"))
                .andExpect(jsonPath("$.rolCodigo").value("OPERADOR_CONSULTORIO"))
                .andExpect(jsonPath("$.activo").value(true));
    }

    /**
     * Verifica que el endpoint de login responda 400 cuando el request no cumple validación.
     *
     * @throws Exception si MockMvc falla al ejecutar la petición
     */
    @Test
    void login_deberiaRetornar400SiRequestEsInvalido() throws Exception {
        String body = """
                {
                  "username": "   ",
                  "password": "123456"
                }
                """;

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.ok").value(false))
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"));
    }
}
