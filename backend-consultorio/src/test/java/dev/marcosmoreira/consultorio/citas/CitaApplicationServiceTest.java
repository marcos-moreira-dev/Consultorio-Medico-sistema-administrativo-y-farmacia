package dev.marcosmoreira.consultorio.citas;

import dev.marcosmoreira.consultorio.citas.application.port.out.CitaPersistencePort;
import dev.marcosmoreira.consultorio.citas.application.service.CitaApplicationService;
import dev.marcosmoreira.consultorio.citas.domain.enums.EstadoCita;
import dev.marcosmoreira.consultorio.citas.domain.exception.AgendaOcupadaException;
import dev.marcosmoreira.consultorio.citas.domain.exception.CitaNoReprogramableException;
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
 * Pruebas unitarias del servicio de aplicación del módulo de citas.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
class CitaApplicationServiceTest {

    @Mock
    private CitaPersistencePort citaPersistencePort;

    @Mock
    private BuscarPacienteUseCase buscarPacienteUseCase;

    @Mock
    private BuscarProfesionalUseCase buscarProfesionalUseCase;

    private CitaApplicationService citaApplicationService;

    /**
     * Inicializa el servicio bajo prueba antes de cada test.
     */
    @BeforeEach
    void setUp() {
        citaApplicationService = new CitaApplicationService(
                citaPersistencePort,
                buscarPacienteUseCase,
                buscarProfesionalUseCase
        );
    }

    /**
     * Verifica el alta exitosa de una cita programada.
     */
    @Test
    void crear_deberiaPersistirCitaProgramadaYValidarPacienteYProfesional() {
        LocalDateTime inicio = LocalDateTime.of(2026, 4, 10, 9, 0);

        when(buscarPacienteUseCase.buscarPorId(1L))
                .thenReturn(new Paciente(
                        1L, "Ana", "Pérez", null, null, LocalDate.of(1998, 5, 10), null, null, null
                ));
        when(buscarProfesionalUseCase.buscarPorId(2L))
                .thenReturn(new Profesional(
                        2L, 10L, "Carlos", "Mendoza", "Medicina general", "REG-001",
                        EstadoProfesional.ACTIVO, null, null
                ));
        when(citaPersistencePort.existsByProfesionalIdAndFechaHoraInicio(2L, inicio))
                .thenReturn(false);

        Cita persisted = new Cita(
                1L,
                1L,
                2L,
                inicio,
                EstadoCita.PROGRAMADA,
                "Control general",
                "Primera visita",
                null,
                null
        );

        when(citaPersistencePort.guardar(any(Cita.class))).thenReturn(persisted);

        Cita response = citaApplicationService.crear(
                1L,
                2L,
                inicio,
                " Control general ",
                " Primera visita "
        );

        assertNotNull(response);
        assertEquals(1L, response.getCitaId());
        assertEquals(EstadoCita.PROGRAMADA, response.getEstadoCita());

        verify(buscarPacienteUseCase).buscarPorId(1L);
        verify(buscarProfesionalUseCase).buscarPorId(2L);

        ArgumentCaptor<Cita> captor = ArgumentCaptor.forClass(Cita.class);
        verify(citaPersistencePort).guardar(captor.capture());

        Cita enviada = captor.getValue();
        assertEquals(1L, enviada.getPacienteId());
        assertEquals(2L, enviada.getProfesionalId());
        assertEquals(inicio, enviada.getFechaHoraInicio());
        assertEquals(EstadoCita.PROGRAMADA, enviada.getEstadoCita());
        assertEquals("Control general", enviada.getMotivoBreve());
        assertEquals("Primera visita", enviada.getObservacionOperativa());
    }

    /**
     * Verifica que no se permita crear una cita sobre un slot ocupado.
     */
    @Test
    void crear_deberiaLanzarExcepcionSiAgendaEstaOcupada() {
        LocalDateTime inicio = LocalDateTime.of(2026, 4, 10, 9, 0);

        when(buscarPacienteUseCase.buscarPorId(1L)).thenReturn(new Paciente());
        when(buscarProfesionalUseCase.buscarPorId(2L)).thenReturn(new Profesional());
        when(citaPersistencePort.existsByProfesionalIdAndFechaHoraInicio(2L, inicio))
                .thenReturn(true);

        AgendaOcupadaException exception = assertThrows(
                AgendaOcupadaException.class,
                () -> citaApplicationService.crear(
                        1L,
                        2L,
                        inicio,
                        "Control general",
                        null
                )
        );

        assertEquals(
                "El profesional ya tiene una cita registrada para la fecha y hora indicada.",
                exception.getMessage()
        );
        verify(citaPersistencePort, never()).guardar(any());
    }

    /**
     * Verifica la búsqueda exitosa de una cita.
     */
    @Test
    void buscarPorId_deberiaRetornarCitaSiExiste() {
        Cita cita = new Cita(
                5L,
                1L,
                2L,
                LocalDateTime.of(2026, 4, 10, 10, 0),
                EstadoCita.PROGRAMADA,
                "Control",
                null,
                null,
                null
        );

        when(citaPersistencePort.buscarPorId(5L)).thenReturn(Optional.of(cita));

        Cita response = citaApplicationService.buscarPorId(5L);

        assertNotNull(response);
        assertEquals(5L, response.getCitaId());
    }

    /**
     * Verifica que la búsqueda falle cuando la cita no existe.
     */
    @Test
    void buscarPorId_deberiaLanzarExcepcionSiNoExiste() {
        when(citaPersistencePort.buscarPorId(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> citaApplicationService.buscarPorId(99L)
        );

        assertEquals("No se encontró una cita con ID 99.", exception.getMessage());
    }

    /**
     * Verifica la cancelación exitosa de una cita programada.
     */
    @Test
    void cancelar_deberiaCambiarEstadoACancelada() {
        Cita cita = new Cita(
                7L,
                1L,
                2L,
                LocalDateTime.of(2026, 4, 10, 11, 0),
                EstadoCita.PROGRAMADA,
                "Control",
                "Inicial",
                null,
                null
        );

        Cita persisted = new Cita(
                7L,
                1L,
                2L,
                LocalDateTime.of(2026, 4, 10, 11, 0),
                EstadoCita.CANCELADA,
                "Control",
                "Paciente no asistirá",
                null,
                null
        );

        when(citaPersistencePort.buscarPorId(7L)).thenReturn(Optional.of(cita));
        when(citaPersistencePort.guardar(any(Cita.class))).thenReturn(persisted);

        Cita response = citaApplicationService.cancelar(7L, " Paciente no asistirá ");

        assertEquals(EstadoCita.CANCELADA, response.getEstadoCita());
        assertEquals("Paciente no asistirá", response.getObservacionOperativa());
    }

    /**
     * Verifica que no se permita cancelar una cita que ya no está programada.
     */
    @Test
    void cancelar_deberiaLanzarExcepcionSiCitaNoEstaProgramada() {
        Cita cita = new Cita(
                8L,
                1L,
                2L,
                LocalDateTime.of(2026, 4, 10, 11, 0),
                EstadoCita.CANCELADA,
                "Control",
                null,
                null,
                null
        );

        when(citaPersistencePort.buscarPorId(8L)).thenReturn(Optional.of(cita));

        CitaNoReprogramableException exception = assertThrows(
                CitaNoReprogramableException.class,
                () -> citaApplicationService.cancelar(8L, null)
        );

        assertEquals(
                "Solo se pueden cancelar citas que estén en estado PROGRAMADA.",
                exception.getMessage()
        );
    }

    /**
     * Verifica la reprogramación exitosa de una cita.
     */
    @Test
    void reprogramar_deberiaActualizarFechaHora() {
        LocalDateTime original = LocalDateTime.of(2026, 4, 10, 12, 0);
        LocalDateTime nueva = LocalDateTime.of(2026, 4, 10, 14, 0);

        Cita cita = new Cita(
                9L,
                1L,
                2L,
                original,
                EstadoCita.PROGRAMADA,
                "Control",
                null,
                null,
                null
        );

        Cita persisted = new Cita(
                9L,
                1L,
                2L,
                nueva,
                EstadoCita.PROGRAMADA,
                "Control",
                "Reagendada",
                null,
                null
        );

        when(citaPersistencePort.buscarPorId(9L)).thenReturn(Optional.of(cita));
        when(citaPersistencePort.existsByProfesionalIdAndFechaHoraInicioAndCitaIdNot(2L, nueva, 9L))
                .thenReturn(false);
        when(citaPersistencePort.guardar(any(Cita.class))).thenReturn(persisted);

        Cita response = citaApplicationService.reprogramar(9L, nueva, " Reagendada ");

        assertEquals(nueva, response.getFechaHoraInicio());
        assertEquals("Reagendada", response.getObservacionOperativa());
    }

    /**
     * Verifica el listado con filtros opcionales.
     */
    @Test
    void listar_deberiaDelegarEnPersistenciaConFiltros() {
        LocalDateTime desde = LocalDateTime.of(2026, 4, 1, 0, 0);
        LocalDateTime hasta = LocalDateTime.of(2026, 4, 30, 23, 59);
        Pageable pageable = PageRequest.of(0, 20);

        Cita cita = new Cita(
                1L,
                1L,
                2L,
                LocalDateTime.of(2026, 4, 10, 9, 0),
                EstadoCita.PROGRAMADA,
                "Control",
                null,
                null,
                null
        );

        when(citaPersistencePort.listar(1L, 2L, EstadoCita.PROGRAMADA, desde, hasta, pageable))
                .thenReturn(org.springframework.data.domain.Page.empty());

        Page<Cita> result = citaApplicationService.listar(
                1L,
                2L,
                EstadoCita.PROGRAMADA,
                desde,
                hasta,
                pageable
        );

        verify(citaPersistencePort).listar(1L, 2L, EstadoCita.PROGRAMADA, desde, hasta, pageable);
    }
}
