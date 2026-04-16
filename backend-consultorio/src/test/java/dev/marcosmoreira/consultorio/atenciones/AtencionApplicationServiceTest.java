package dev.marcosmoreira.consultorio.atenciones;

import dev.marcosmoreira.consultorio.atenciones.application.port.out.AtencionPersistencePort;
import dev.marcosmoreira.consultorio.atenciones.application.service.AtencionApplicationService;
import dev.marcosmoreira.consultorio.atenciones.domain.model.Atencion;
import dev.marcosmoreira.consultorio.citas.application.port.in.BuscarCitaUseCase;
import dev.marcosmoreira.consultorio.citas.domain.enums.EstadoCita;
import dev.marcosmoreira.consultorio.citas.domain.model.Cita;
import dev.marcosmoreira.consultorio.pacientes.application.port.in.BuscarPacienteUseCase;
import dev.marcosmoreira.consultorio.pacientes.domain.model.Paciente;
import dev.marcosmoreira.consultorio.profesionales.application.port.in.BuscarProfesionalUseCase;
import dev.marcosmoreira.consultorio.profesionales.domain.enums.EstadoProfesional;
import dev.marcosmoreira.consultorio.profesionales.domain.model.Profesional;
import dev.marcosmoreira.consultorio.shared.error.ResourceNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias del servicio de aplicación del módulo de atenciones.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
class AtencionApplicationServiceTest {

    @Mock
    private AtencionPersistencePort atencionPersistencePort;

    @Mock
    private BuscarPacienteUseCase buscarPacienteUseCase;

    @Mock
    private BuscarProfesionalUseCase buscarProfesionalUseCase;

    @Mock
    private BuscarCitaUseCase buscarCitaUseCase;

    private AtencionApplicationService atencionApplicationService;

    /**
     * Inicializa el servicio bajo prueba antes de cada test.
     */
    @BeforeEach
    void setUp() {
        atencionApplicationService = new AtencionApplicationService(
                atencionPersistencePort,
                buscarPacienteUseCase,
                buscarProfesionalUseCase,
                buscarCitaUseCase
        );
    }

    /**
     * Verifica el registro exitoso de una atención.
     */
    @Test
    void crear_deberiaPersistirAtencionYValidarRelaciones() {
        Atencion input = new Atencion(
                null,
                1L,
                2L,
                3L,
                LocalDateTime.of(2026, 4, 8, 9, 0),
                "Control general",
                "Reposo",
                null,
                null
        );

        Atencion persisted = new Atencion(
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

        when(buscarPacienteUseCase.buscarPorId(1L))
                .thenReturn(new Paciente(
                        1L, "Ana", "Pérez", null, null, LocalDate.of(1998, 5, 10), null, null, null
                ));
        when(buscarProfesionalUseCase.buscarPorId(2L))
                .thenReturn(new Profesional(
                        2L, 10L, "Carlos", "Mendoza", "Medicina general", "REG-001",
                        EstadoProfesional.ACTIVO, null, null
                ));
        when(buscarCitaUseCase.buscarPorId(3L))
                .thenReturn(new Cita(
                        3L, 1L, 2L, LocalDateTime.of(2026, 4, 8, 8, 30),
                        EstadoCita.PROGRAMADA, "Control", null, null, null
                ));
        when(atencionPersistencePort.guardar(any(Atencion.class))).thenReturn(persisted);

        Atencion response = atencionApplicationService.crear(input);

        assertNotNull(response);
        assertEquals(1L, response.getAtencionId());

        verify(buscarPacienteUseCase).buscarPorId(1L);
        verify(buscarProfesionalUseCase).buscarPorId(2L);
        verify(buscarCitaUseCase).buscarPorId(3L);

        ArgumentCaptor<Atencion> captor = ArgumentCaptor.forClass(Atencion.class);
        verify(atencionPersistencePort).guardar(captor.capture());

        Atencion enviada = captor.getValue();
        assertEquals(1L, enviada.getPacienteId());
        assertEquals(2L, enviada.getProfesionalId());
        assertEquals(3L, enviada.getCitaId());
        assertEquals("Control general", enviada.getNotaBreve());
    }

    /**
     * Verifica que falle si la cita no pertenece al paciente indicado.
     */
    @Test
    void crear_deberiaLanzarExcepcionSiCitaNoPerteneceAlPaciente() {
        Atencion input = new Atencion(
                null,
                1L,
                2L,
                3L,
                LocalDateTime.of(2026, 4, 8, 9, 0),
                "Control general",
                null,
                null,
                null
        );

        when(buscarPacienteUseCase.buscarPorId(1L)).thenReturn(new Paciente());
        when(buscarProfesionalUseCase.buscarPorId(2L)).thenReturn(new Profesional());
        when(buscarCitaUseCase.buscarPorId(3L))
                .thenReturn(new Cita(
                        3L, 99L, 2L, LocalDateTime.now(),
                        EstadoCita.PROGRAMADA, "Control", null, null, null
                ));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> atencionApplicationService.crear(input)
        );

        assertEquals("La cita asociada no pertenece al paciente indicado.", exception.getMessage());
        verify(atencionPersistencePort, never()).guardar(any());
    }

    /**
     * Verifica que falle si la cita no pertenece al profesional indicado.
     */
    @Test
    void crear_deberiaLanzarExcepcionSiCitaNoPerteneceAlProfesional() {
        Atencion input = new Atencion(
                null,
                1L,
                2L,
                3L,
                LocalDateTime.of(2026, 4, 8, 9, 0),
                "Control general",
                null,
                null,
                null
        );

        when(buscarPacienteUseCase.buscarPorId(1L)).thenReturn(new Paciente());
        when(buscarProfesionalUseCase.buscarPorId(2L)).thenReturn(new Profesional());
        when(buscarCitaUseCase.buscarPorId(3L))
                .thenReturn(new Cita(
                        3L, 1L, 99L, LocalDateTime.now(),
                        EstadoCita.PROGRAMADA, "Control", null, null, null
                ));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> atencionApplicationService.crear(input)
        );

        assertEquals("La cita asociada no pertenece al profesional indicado.", exception.getMessage());
        verify(atencionPersistencePort, never()).guardar(any());
    }

    /**
     * Verifica que la búsqueda retorne una atención existente.
     */
    @Test
    void buscarPorId_deberiaRetornarAtencionSiExiste() {
        Atencion atencion = new Atencion(
                5L,
                1L,
                2L,
                null,
                LocalDateTime.of(2026, 4, 8, 10, 0),
                "Chequeo",
                null,
                null,
                null
        );

        when(atencionPersistencePort.buscarPorId(5L)).thenReturn(Optional.of(atencion));

        Atencion response = atencionApplicationService.buscarPorId(5L);

        assertNotNull(response);
        assertEquals(5L, response.getAtencionId());
    }

    /**
     * Verifica que la búsqueda falle si la atención no existe.
     */
    @Test
    void buscarPorId_deberiaLanzarExcepcionSiNoExiste() {
        when(atencionPersistencePort.buscarPorId(100L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> atencionApplicationService.buscarPorId(100L)
        );

        assertEquals("No se encontró una atención con ID 100.", exception.getMessage());
    }

    /**
     * Verifica el listado con filtros opcionales.
     */
    @Test
    void listar_deberiaDelegarEnPersistenciaConFiltros() {
        LocalDateTime desde = LocalDateTime.of(2026, 4, 1, 0, 0);
        LocalDateTime hasta = LocalDateTime.of(2026, 4, 30, 23, 59);
        Pageable pageable = PageRequest.of(0, 20);

        Atencion atencion = new Atencion(
                1L,
                1L,
                2L,
                null,
                LocalDateTime.of(2026, 4, 8, 10, 0),
                "Chequeo",
                null,
                null,
                null
        );

        when(atencionPersistencePort.listar(1L, 2L, desde, hasta, pageable))
                .thenReturn(org.springframework.data.domain.Page.empty());

        Page<Atencion> result = atencionApplicationService.listar(1L, 2L, desde, hasta, pageable);

        verify(atencionPersistencePort).listar(1L, 2L, desde, hasta, pageable);
    }

    /**
     * Verifica que falle si el rango de fechas es incoherente.
     */
    @Test
    void listar_deberiaLanzarExcepcionSiRangoFechasEsInvalido() {
        LocalDateTime desde = LocalDateTime.of(2026, 4, 30, 23, 59);
        LocalDateTime hasta = LocalDateTime.of(2026, 4, 1, 0, 0);
        Pageable pageable = PageRequest.of(0, 20);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> atencionApplicationService.listar(null, null, desde, hasta, pageable)
        );

        assertEquals("La fecha inicial no puede ser posterior a la fecha final.", exception.getMessage());
        verify(atencionPersistencePort, never()).listar(any(), any(), any(), any(), any());
    }
}
