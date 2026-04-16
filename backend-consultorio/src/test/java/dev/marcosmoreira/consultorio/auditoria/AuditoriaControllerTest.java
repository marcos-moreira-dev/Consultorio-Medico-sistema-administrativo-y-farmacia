package dev.marcosmoreira.consultorio.auditoria;

import dev.marcosmoreira.consultorio.auditoria.api.controller.AuditoriaController;
import dev.marcosmoreira.consultorio.auditoria.api.response.EventoAuditoriaResponse;
import dev.marcosmoreira.consultorio.auditoria.application.port.in.ListarEventosAuditoriaUseCase;
import dev.marcosmoreira.consultorio.auditoria.domain.enums.TipoEventoAuditoria;
import dev.marcosmoreira.consultorio.shared.web.GlobalExceptionHandler;
import java.time.LocalDateTime;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas unitarias HTTP del controlador de auditoría usando MockMvc standalone.
 */
@ExtendWith(MockitoExtension.class)
class AuditoriaControllerTest {

    @Mock
    private ListarEventosAuditoriaUseCase listarEventosAuditoriaUseCase;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        AuditoriaController controller = new AuditoriaController(listarEventosAuditoriaUseCase);

        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void listar_deberiaRetornar200ConLista() throws Exception {
        EventoAuditoriaResponse evento1 = new EventoAuditoriaResponse(
                "1", "PACIENTES", TipoEventoAuditoria.CREACION, 5L,
                "admin", "Se creó el paciente Carlos", "paciente", "10", null,
                LocalDateTime.of(2026, 4, 8, 9, 0)
        );

        EventoAuditoriaResponse evento2 = new EventoAuditoriaResponse(
                "2", "CITAS", TipoEventoAuditoria.ACTUALIZACION, 5L,
                "admin", "Se actualizó la cita", "cita", "20", null,
                LocalDateTime.of(2026, 4, 8, 10, 0)
        );

        Pageable pageable = PageRequest.of(0, 20);
        when(listarEventosAuditoriaUseCase.listar(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(evento1, evento2), pageable, 2));

        mockMvc.perform(get("/api/v1/auditoria"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content.length()").value(2))
                .andExpect(jsonPath("$.data.content[0].eventoId").value("1"))
                .andExpect(jsonPath("$.data.content[1].tipoEvento").value("ACTUALIZACION"));
    }
}
