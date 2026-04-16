package dev.marcosmoreira.consultorio.auditoria;

import dev.marcosmoreira.consultorio.auditoria.api.response.EventoAuditoriaResponse;
import dev.marcosmoreira.consultorio.auditoria.application.port.out.AuditoriaQueryPort;
import dev.marcosmoreira.consultorio.auditoria.application.service.AuditoriaApplicationService;
import dev.marcosmoreira.consultorio.auditoria.domain.enums.TipoEventoAuditoria;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias del servicio de aplicación del módulo de auditoría.
 */
@ExtendWith(MockitoExtension.class)
class AuditoriaApplicationServiceTest {

    @Mock
    private AuditoriaQueryPort auditoriaQueryPort;

    private AuditoriaApplicationService auditoriaApplicationService;

    @BeforeEach
    void setUp() {
        auditoriaApplicationService = new AuditoriaApplicationService(auditoriaQueryPort);
    }

    @Test
    void listar_deberiaDelegarEnPuertoConFiltrosValidos() {
        LocalDateTime desde = LocalDateTime.of(2026, 4, 1, 0, 0);
        LocalDateTime hasta = LocalDateTime.of(2026, 4, 30, 23, 59);

        EventoAuditoriaResponse evento = new EventoAuditoriaResponse(
                "1", "PACIENTES", TipoEventoAuditoria.CREACION, 1L,
                "admin", "Paciente creado", "paciente", "1", null, LocalDateTime.now()
        );

        Pageable pageable = PageRequest.of(0, 20);
        when(auditoriaQueryPort.listar(eq("PACIENTES"), eq(TipoEventoAuditoria.CREACION), eq(1L), isNull(), eq(desde), eq(hasta), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(evento)));

        Page<EventoAuditoriaResponse> result = auditoriaApplicationService.listar(
                "PACIENTES", TipoEventoAuditoria.CREACION, 1L, null, desde, hasta, pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Paciente creado", result.getContent().get(0).getDescripcion());
    }

    @Test
    void listar_deberiaPermitirConsultaSinFiltros() {
        Pageable pageable = PageRequest.of(0, 20);
        when(auditoriaQueryPort.listar(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), any(Pageable.class)))
                .thenReturn(Page.empty());

        Page<EventoAuditoriaResponse> result = auditoriaApplicationService.listar(
                null, null, null, null, null, null, pageable);

        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
    }

    @Test
    void listar_deberiaLanzarExcepcionSiUsuarioIdEsInvalido() {
        Pageable pageable = PageRequest.of(0, 20);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> auditoriaApplicationService.listar(
                        null, null, 0L, null, null, null, pageable
                )
        );

        assertEquals("Si se envía usuarioId, debe ser mayor que cero.", exception.getMessage());
        verifyNoInteractions(auditoriaQueryPort);
    }

    @Test
    void listar_deberiaLanzarExcepcionSiRangoFechasEsInvalido() {
        LocalDateTime desde = LocalDateTime.of(2026, 4, 30, 23, 59);
        LocalDateTime hasta = LocalDateTime.of(2026, 4, 1, 0, 0);
        Pageable pageable = PageRequest.of(0, 20);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> auditoriaApplicationService.listar(
                        null, null, 5L, null, desde, hasta, pageable
                )
        );

        assertEquals("La fecha inicial no puede ser posterior a la fecha final.", exception.getMessage());
        verifyNoInteractions(auditoriaQueryPort);
    }
}
