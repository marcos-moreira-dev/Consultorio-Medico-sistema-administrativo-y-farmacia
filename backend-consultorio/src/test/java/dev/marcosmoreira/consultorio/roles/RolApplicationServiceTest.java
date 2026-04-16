package dev.marcosmoreira.consultorio.roles;

import dev.marcosmoreira.consultorio.roles.application.port.out.RolPersistencePort;
import dev.marcosmoreira.consultorio.roles.application.service.RolApplicationService;
import dev.marcosmoreira.consultorio.roles.domain.model.Rol;
import dev.marcosmoreira.consultorio.shared.error.ResourceNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias del servicio de aplicación del módulo de roles.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
class RolApplicationServiceTest {

    @Mock
    private RolPersistencePort rolPersistencePort;

    private RolApplicationService rolApplicationService;

    @BeforeEach
    void setUp() {
        rolApplicationService = new RolApplicationService(rolPersistencePort);
    }

    @Test
    void buscarPorId_deberiaRetornarRolSiExiste() {
        Rol rol = new Rol(1L, "ADMIN_CONSULTORIO", "Administrador consultorio");

        when(rolPersistencePort.buscarPorId(1L)).thenReturn(Optional.of(rol));

        Rol response = rolApplicationService.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.getRolId());
        assertEquals("ADMIN_CONSULTORIO", response.getCodigo());
    }

    @Test
    void buscarPorId_deberiaLanzarExcepcionSiNoExiste() {
        when(rolPersistencePort.buscarPorId(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> rolApplicationService.buscarPorId(99L)
        );

        assertEquals("No se encontró un rol con ID 99.", exception.getMessage());
    }

    @Test
    void listar_deberiaDelegarEnPersistencia() {
        Rol r1 = new Rol(1L, "ADMIN_CONSULTORIO", "Administrador consultorio");
        Rol r2 = new Rol(2L, "OPERADOR_CONSULTORIO", "Operador consultorio");

        when(rolPersistencePort.listar()).thenReturn(List.of(r1, r2));

        List<Rol> result = rolApplicationService.listar();

        assertEquals(2, result.size());
        verify(rolPersistencePort).listar();
    }
}
