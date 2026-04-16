package dev.marcosmoreira.consultorio.profesionales;

import dev.marcosmoreira.consultorio.profesionales.application.port.out.ProfesionalPersistencePort;
import dev.marcosmoreira.consultorio.profesionales.application.service.ProfesionalApplicationService;
import dev.marcosmoreira.consultorio.profesionales.domain.enums.EstadoProfesional;
import dev.marcosmoreira.consultorio.profesionales.domain.model.Profesional;
import dev.marcosmoreira.consultorio.shared.error.DuplicateResourceException;
import dev.marcosmoreira.consultorio.shared.error.ResourceNotFoundException;
import dev.marcosmoreira.consultorio.usuarios.application.port.in.BuscarUsuarioUseCase;
import dev.marcosmoreira.consultorio.usuarios.domain.enums.EstadoUsuario;
import dev.marcosmoreira.consultorio.usuarios.domain.model.Usuario;
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
 * Pruebas unitarias del servicio de aplicación del módulo de profesionales.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
class ProfesionalApplicationServiceTest {

    @Mock
    private ProfesionalPersistencePort profesionalPersistencePort;

    @Mock
    private BuscarUsuarioUseCase buscarUsuarioUseCase;

    private ProfesionalApplicationService profesionalApplicationService;

    /**
     * Inicializa el servicio bajo prueba antes de cada test.
     */
    @BeforeEach
    void setUp() {
        profesionalApplicationService = new ProfesionalApplicationService(
                profesionalPersistencePort,
                buscarUsuarioUseCase
        );
    }

    /**
     * Verifica el alta exitosa de un profesional.
     */
    @Test
    void crear_deberiaPersistirProfesionalActivoYValidarUsuarioSiFueEnviado() {
        when(buscarUsuarioUseCase.buscarPorId(10L))
                .thenReturn(new Usuario(
                        10L,
                        1L,
                        "ADMIN_CONSULTORIO",
                        "Administrador consultorio",
                        "admin",
                        "HASH",
                        "Administrador consultorio",
                        EstadoUsuario.ACTIVO,
                        null,
                        null
                ));
        when(profesionalPersistencePort.existsByUsuarioId(10L)).thenReturn(false);

        Profesional persisted = new Profesional(
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

        when(profesionalPersistencePort.guardar(any(Profesional.class)))
                .thenReturn(persisted);

        Profesional response = profesionalApplicationService.crear(
                10L,
                " Carlos ",
                " Mendoza ",
                " Medicina general ",
                " REG-001 "
        );

        assertNotNull(response);
        assertEquals(1L, response.getProfesionalId());
        assertEquals(EstadoProfesional.ACTIVO, response.getEstadoProfesional());

        ArgumentCaptor<Profesional> captor = ArgumentCaptor.forClass(Profesional.class);
        verify(profesionalPersistencePort).guardar(captor.capture());
        verify(buscarUsuarioUseCase).buscarPorId(10L);

        Profesional enviado = captor.getValue();
        assertEquals(10L, enviado.getUsuarioId());
        assertEquals("Carlos", enviado.getNombres());
        assertEquals("Mendoza", enviado.getApellidos());
        assertEquals("Medicina general", enviado.getEspecialidadBreve());
        assertEquals("REG-001", enviado.getRegistroProfesional());
        assertEquals(EstadoProfesional.ACTIVO, enviado.getEstadoProfesional());
    }

    /**
     * Verifica que no se permita asociar dos profesionales al mismo usuario.
     */
    @Test
    void crear_deberiaLanzarExcepcionSiUsuarioYaEstaAsociadoAOtroProfesional() {
        when(buscarUsuarioUseCase.buscarPorId(10L))
                .thenReturn(new Usuario());
        when(profesionalPersistencePort.existsByUsuarioId(10L)).thenReturn(true);

        DuplicateResourceException exception = assertThrows(
                DuplicateResourceException.class,
                () -> profesionalApplicationService.crear(
                        10L,
                        "Carlos",
                        "Mendoza",
                        "Medicina general",
                        "REG-001"
                )
        );

        assertEquals(
                "Ya existe un profesional asociado al usuario con ID 10.",
                exception.getMessage()
        );

        verify(profesionalPersistencePort, never()).guardar(any());
    }

    /**
     * Verifica la actualización exitosa de un profesional existente.
     */
    @Test
    void actualizar_deberiaModificarDatosBasicosDelProfesionalYValidarUsuario() {
        Profesional existente = new Profesional(
                5L,
                10L,
                "Carlos",
                "Mendoza",
                "Medicina general",
                "REG-001",
                EstadoProfesional.ACTIVO,
                null,
                null
        );

        Profesional persisted = new Profesional(
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

        when(profesionalPersistencePort.buscarPorId(5L))
                .thenReturn(Optional.of(existente));
        when(buscarUsuarioUseCase.buscarPorId(11L))
                .thenReturn(new Usuario());
        when(profesionalPersistencePort.existsByUsuarioIdAndProfesionalIdNot(11L, 5L))
                .thenReturn(false);
        when(profesionalPersistencePort.guardar(any(Profesional.class)))
                .thenReturn(persisted);

        Profesional response = profesionalApplicationService.actualizar(
                5L,
                11L,
                "Carlos Alberto",
                "Mendoza",
                "Cardiología",
                "REG-002"
        );

        assertNotNull(response);
        assertEquals(11L, response.getUsuarioId());
        assertEquals("Carlos Alberto", response.getNombres());
        assertEquals("Cardiología", response.getEspecialidadBreve());
        assertEquals("REG-002", response.getRegistroProfesional());

        verify(buscarUsuarioUseCase).buscarPorId(11L);
    }

    /**
     * Verifica que la actualización falle si el nuevo usuario ya está asociado a otro profesional.
     */
    @Test
    void actualizar_deberiaLanzarExcepcionSiUsuarioYaEstaAsociadoAOtroProfesional() {
        Profesional existente = new Profesional(
                5L,
                10L,
                "Carlos",
                "Mendoza",
                "Medicina general",
                "REG-001",
                EstadoProfesional.ACTIVO,
                null,
                null
        );

        when(profesionalPersistencePort.buscarPorId(5L))
                .thenReturn(Optional.of(existente));
        when(buscarUsuarioUseCase.buscarPorId(11L))
                .thenReturn(new Usuario());
        when(profesionalPersistencePort.existsByUsuarioIdAndProfesionalIdNot(11L, 5L))
                .thenReturn(true);

        DuplicateResourceException exception = assertThrows(
                DuplicateResourceException.class,
                () -> profesionalApplicationService.actualizar(
                        5L,
                        11L,
                        "Carlos",
                        "Mendoza",
                        "Cardiología",
                        "REG-002"
                )
        );

        assertEquals(
                "Ya existe otro profesional asociado al usuario con ID 11.",
                exception.getMessage()
        );

        verify(profesionalPersistencePort, never()).guardar(any());
    }

    /**
     * Verifica la búsqueda exitosa de un profesional.
     */
    @Test
    void buscarPorId_deberiaRetornarProfesionalSiExiste() {
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

        when(profesionalPersistencePort.buscarPorId(7L))
                .thenReturn(Optional.of(profesional));

        Profesional response = profesionalApplicationService.buscarPorId(7L);

        assertNotNull(response);
        assertEquals(7L, response.getProfesionalId());
        assertEquals("Carla", response.getNombres());
    }

    /**
     * Verifica que la búsqueda falle cuando el profesional no existe.
     */
    @Test
    void buscarPorId_deberiaLanzarExcepcionSiNoExiste() {
        when(profesionalPersistencePort.buscarPorId(99L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> profesionalApplicationService.buscarPorId(99L)
        );

        assertEquals("No se encontró un profesional con ID 99.", exception.getMessage());
    }

    /**
     * Verifica el cambio de estado lógico del profesional.
     */
    @Test
    void cambiarEstado_deberiaActualizarEstadoDelProfesional() {
        Profesional existente = new Profesional(
                8L,
                10L,
                "José",
                "Núñez",
                "Dermatología",
                "DERM-1",
                EstadoProfesional.ACTIVO,
                null,
                null
        );

        Profesional persisted = new Profesional(
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

        when(profesionalPersistencePort.buscarPorId(8L))
                .thenReturn(Optional.of(existente));
        when(profesionalPersistencePort.guardar(any(Profesional.class)))
                .thenReturn(persisted);

        Profesional response = profesionalApplicationService.cambiarEstado(
                8L,
                EstadoProfesional.INACTIVO
        );

        assertEquals(EstadoProfesional.INACTIVO, response.getEstadoProfesional());
    }

    /**
     * Verifica el listado con filtros opcionales.
     */
    @Test
    void listar_deberiaDelegarEnPersistenciaConFiltrosNormalizados() {
        Pageable pageable = PageRequest.of(0, 20);
        Profesional profesional = new Profesional(
                1L,
                10L,
                "Ana",
                "Vera",
                "Pediatría",
                "PED-1",
                EstadoProfesional.ACTIVO,
                null,
                null
        );

        when(profesionalPersistencePort.listar(10L, EstadoProfesional.ACTIVO, "ana", pageable))
                .thenReturn(org.springframework.data.domain.Page.empty());

        Page<Profesional> result = profesionalApplicationService.listar(
                10L,
                EstadoProfesional.ACTIVO,
                " ana ",
                pageable
        );

        verify(profesionalPersistencePort).listar(10L, EstadoProfesional.ACTIVO, "ana", pageable);
    }
}
