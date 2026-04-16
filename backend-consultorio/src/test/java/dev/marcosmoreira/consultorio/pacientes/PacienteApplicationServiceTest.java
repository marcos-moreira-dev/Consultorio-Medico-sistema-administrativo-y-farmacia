package dev.marcosmoreira.consultorio.pacientes;

import dev.marcosmoreira.consultorio.pacientes.application.port.out.PacientePersistencePort;
import dev.marcosmoreira.consultorio.pacientes.application.service.PacienteApplicationService;
import dev.marcosmoreira.consultorio.pacientes.domain.exception.PacienteDuplicadoException;
import dev.marcosmoreira.consultorio.pacientes.domain.model.Paciente;
import dev.marcosmoreira.consultorio.shared.error.ResourceNotFoundException;
import java.time.LocalDate;
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
 * Pruebas unitarias del servicio de aplicación del módulo de pacientes.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
class PacienteApplicationServiceTest {

    @Mock
    private PacientePersistencePort pacientePersistencePort;

    private PacienteApplicationService pacienteApplicationService;

    /**
     * Inicializa el servicio bajo prueba antes de cada test.
     */
    @BeforeEach
    void setUp() {
        pacienteApplicationService = new PacienteApplicationService(pacientePersistencePort);
    }

    /**
     * Verifica el alta exitosa de un paciente.
     */
    @Test
    void crear_deberiaPersistirPaciente() {
        when(pacientePersistencePort.existsByCedulaIgnoreCase("0922334455"))
                .thenReturn(false);

        Paciente persisted = new Paciente(
                1L,
                "Ana",
                "Pérez",
                "0991112233",
                "0922334455",
                LocalDate.of(1998, 5, 10),
                "Centro",
                null,
                null
        );

        when(pacientePersistencePort.guardar(any(Paciente.class)))
                .thenReturn(persisted);

        Paciente response = pacienteApplicationService.crear(
                " Ana ",
                " Pérez ",
                "0991112233",
                "0922334455",
                LocalDate.of(1998, 5, 10),
                " Centro "
        );

        assertNotNull(response);
        assertEquals(1L, response.getPacienteId());
        assertEquals("Ana", response.getNombres());
        assertEquals("Pérez", response.getApellidos());

        ArgumentCaptor<Paciente> captor = ArgumentCaptor.forClass(Paciente.class);
        verify(pacientePersistencePort).guardar(captor.capture());

        Paciente enviado = captor.getValue();
        assertEquals("Ana", enviado.getNombres());
        assertEquals("Pérez", enviado.getApellidos());
        assertEquals("0922334455", enviado.getCedula());
        assertEquals("Centro", enviado.getDireccionBasica());
    }

    /**
     * Verifica que no se permita registrar dos pacientes con la misma cédula.
     */
    @Test
    void crear_deberiaLanzarExcepcionSiCedulaYaExiste() {
        when(pacientePersistencePort.existsByCedulaIgnoreCase("0922334455"))
                .thenReturn(true);

        PacienteDuplicadoException exception = assertThrows(
                PacienteDuplicadoException.class,
                () -> pacienteApplicationService.crear(
                        "Ana",
                        "Pérez",
                        "0991112233",
                        "0922334455",
                        LocalDate.of(1998, 5, 10),
                        "Centro"
                )
        );

        assertEquals("Ya existe un paciente con la cédula '0922334455'.", exception.getMessage());
        verify(pacientePersistencePort, never()).guardar(any());
    }

    /**
     * Verifica la actualización exitosa de un paciente existente.
     */
    @Test
    void actualizar_deberiaModificarDatosBasicosDelPaciente() {
        Paciente existente = new Paciente(
                9L,
                "Ana",
                "Pérez",
                "0991112233",
                "0922334455",
                LocalDate.of(1998, 5, 10),
                "Centro",
                null,
                null
        );

        Paciente persisted = new Paciente(
                9L,
                "Ana María",
                "Pérez",
                "0999999999",
                "0922334455",
                LocalDate.of(1998, 5, 10),
                "Norte",
                null,
                null
        );

        when(pacientePersistencePort.buscarPorId(9L))
                .thenReturn(Optional.of(existente));
        when(pacientePersistencePort.existsByCedulaIgnoreCaseAndPacienteIdNot("0922334455", 9L))
                .thenReturn(false);
        when(pacientePersistencePort.guardar(any(Paciente.class)))
                .thenReturn(persisted);

        Paciente response = pacienteApplicationService.actualizar(
                9L,
                "Ana María",
                "Pérez",
                "0999999999",
                "0922334455",
                LocalDate.of(1998, 5, 10),
                "Norte"
        );

        assertNotNull(response);
        assertEquals("Ana María", response.getNombres());
        assertEquals("0999999999", response.getTelefono());
        assertEquals("Norte", response.getDireccionBasica());
    }

    /**
     * Verifica que la búsqueda falle cuando el paciente no existe.
     */
    @Test
    void buscarPorId_deberiaLanzarExcepcionSiNoExiste() {
        when(pacientePersistencePort.buscarPorId(100L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> pacienteApplicationService.buscarPorId(100L)
        );

        assertEquals("No se encontró un paciente con ID 100.", exception.getMessage());
    }

    /**
     * Verifica el listado básico de pacientes con paso de filtros.
     */
    @Test
    void listar_deberiaDelegarEnPersistenciaConFiltrosNormalizados() {
        Pageable pageable = PageRequest.of(0, 20);
        Paciente paciente = new Paciente(
                1L,
                "Ana",
                "Pérez",
                "0991112233",
                "0922334455",
                LocalDate.of(1998, 5, 10),
                "Centro",
                null,
                null
        );

        when(pacientePersistencePort.listar("0922334455", null, "ana", pageable))
                .thenReturn(org.springframework.data.domain.Page.empty());

        Page<Paciente> result = pacienteApplicationService.listar(" 0922334455 ", null, " ana ", pageable);

        verify(pacientePersistencePort).listar("0922334455", null, "ana", pageable);
    }
}
