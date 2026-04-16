package dev.marcosmoreira.consultorio.reportes;

import dev.marcosmoreira.consultorio.reportes.api.controller.ReporteController;
import dev.marcosmoreira.consultorio.reportes.api.response.ReporteGeneradoResponse;
import dev.marcosmoreira.consultorio.reportes.application.port.in.GenerarReporteUseCase;
import dev.marcosmoreira.consultorio.reportes.domain.enums.TipoReporte;
import dev.marcosmoreira.consultorio.shared.web.GlobalExceptionHandler;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas unitarias HTTP del controlador de reportes usando MockMvc standalone.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
class ReporteControllerTest {

    @Mock
    private GenerarReporteUseCase generarReporteUseCase;

    private MockMvc mockMvc;

    /**
     * Inicializa MockMvc antes de cada test.
     */
    @BeforeEach
    void setUp() {
        ReporteController controller = new ReporteController(generarReporteUseCase);

        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();
    }

    /**
     * Verifica la generación exitosa de un reporte.
     *
     * @throws Exception si MockMvc falla
     */
    @Test
    void generar_deberiaRetornar200ConReporteGenerado() throws Exception {
        ReporteGeneradoResponse response = ReporteGeneradoResponse.of(
                TipoReporte.PDF,
                "reporte_consultorio.pdf",
                "application/pdf",
                "UERGX0JZVEVT",
                9,
                LocalDateTime.of(2026, 4, 8, 10, 0)
        );

        when(generarReporteUseCase.generar(
                any(TipoReporte.class),
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
        )).thenReturn(response);

        String body = """
                {
                  "tipoReporte": "PDF",
                  "nombreBaseArchivo": "reporte_consultorio",
                  "titulo": "Reporte Operativo",
                  "pacienteId": 1,
                  "profesionalId": 2,
                  "fechaDesde": "2026-04-01T00:00:00",
                  "fechaHasta": "2026-04-30T23:59:00"
                }
                """;

        mockMvc.perform(post("/api/v1/reportes/generar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok").value(true))
                .andExpect(jsonPath("$.data.tipoReporte").value("PDF"))
                .andExpect(jsonPath("$.data.nombreArchivo").value("reporte_consultorio.pdf"))
                .andExpect(jsonPath("$.data.mimeType").value("application/pdf"))
                .andExpect(jsonPath("$.data.tamanoBytes").value(9));
    }

    /**
     * Verifica que el endpoint falle con 400 cuando falta el tipo de reporte.
     *
     * @throws Exception si MockMvc falla
     */
    @Test
    void generar_deberiaRetornar400SiRequestEsInvalido() throws Exception {
        String body = """
                {
                  "nombreBaseArchivo": "reporte_consultorio"
                }
                """;

        mockMvc.perform(post("/api/v1/reportes/generar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.ok").value(false))
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"));
    }
}
