package dev.marcosmoreira.consultorio.usuarios;

import dev.marcosmoreira.consultorio.roles.application.port.in.BuscarRolUseCase;
import dev.marcosmoreira.consultorio.roles.domain.model.Rol;
import dev.marcosmoreira.consultorio.shared.error.ResourceNotFoundException;
import dev.marcosmoreira.consultorio.usuarios.application.port.out.UsuarioPersistencePort;
import dev.marcosmoreira.consultorio.usuarios.application.service.UsuarioApplicationService;
import dev.marcosmoreira.consultorio.usuarios.domain.enums.EstadoUsuario;
import dev.marcosmoreira.consultorio.usuarios.domain.exception.UsuarioDuplicadoException;
import dev.marcosmoreira.consultorio.usuarios.domain.exception.UsuarioNoActivoException;
import dev.marcosmoreira.consultorio.usuarios.domain.model.Usuario;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioApplicationServiceTest {

    @Mock private UsuarioPersistencePort usuarioPersistencePort;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private BuscarRolUseCase buscarRolUseCase;

    private UsuarioApplicationService usuarioApplicationService;

    @BeforeEach
    void setUp() {
        usuarioApplicationService = new UsuarioApplicationService(
                usuarioPersistencePort,
                passwordEncoder,
                buscarRolUseCase
        );
    }

    @Test
    void crear_deberiaPersistirUsuarioConPasswordCodificadoYRolValido() {
        when(buscarRolUseCase.buscarPorId(10L)).thenReturn(new Rol(10L, "ADMIN_CONSULTORIO", "Administrador interno del consultorio"));
        when(usuarioPersistencePort.existsByUsernameIgnoreCase("admin.consultorio")).thenReturn(false);
        when(passwordEncoder.encode("Temporal123")).thenReturn("HASH_TEMPORAL");

        Usuario persisted = new Usuario(1L, 10L, "ADMIN_CONSULTORIO", "Administrador interno del consultorio", "admin.consultorio", "HASH_TEMPORAL", "admin.consultorio", EstadoUsuario.ACTIVO, null, null);
        when(usuarioPersistencePort.guardar(any(Usuario.class))).thenReturn(persisted);

        Usuario response = usuarioApplicationService.crear(10L, " admin.consultorio ", "Temporal123");

        assertNotNull(response);
        assertEquals(1L, response.getUsuarioId());
        assertEquals("admin.consultorio", response.getUsername());
        assertEquals(EstadoUsuario.ACTIVO, response.getEstado());

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioPersistencePort).guardar(captor.capture());
        verify(buscarRolUseCase).buscarPorId(10L);
        verify(passwordEncoder).encode("Temporal123");

        Usuario enviado = captor.getValue();
        assertEquals(10L, enviado.getRolId());
        assertEquals("admin.consultorio", enviado.getUsername());
        assertEquals("admin.consultorio", enviado.getNombreCompleto());
        assertEquals("HASH_TEMPORAL", enviado.getPasswordHash());
        assertEquals(EstadoUsuario.ACTIVO, enviado.getEstado());
    }

    @Test
    void crear_deberiaLanzarExcepcionSiUsernameYaExiste() {
        when(buscarRolUseCase.buscarPorId(10L)).thenReturn(new Rol(10L, "ADMIN_CONSULTORIO", "Administrador interno del consultorio"));
        when(usuarioPersistencePort.existsByUsernameIgnoreCase("admin.consultorio")).thenReturn(true);

        UsuarioDuplicadoException exception = assertThrows(
                UsuarioDuplicadoException.class,
                () -> usuarioApplicationService.crear(10L, "admin.consultorio", "Temporal123")
        );

        assertEquals("Ya existe un usuario con username 'admin.consultorio'.", exception.getMessage());
        verify(usuarioPersistencePort, never()).guardar(any());
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void crear_deberiaPropagarErrorSiRolNoExiste() {
        when(buscarRolUseCase.buscarPorId(99L)).thenThrow(new ResourceNotFoundException("No se encontró un rol con ID 99."));

        assertThrows(ResourceNotFoundException.class, () -> usuarioApplicationService.crear(99L, "admin.consultorio", "Temporal123"));
        verify(usuarioPersistencePort, never()).guardar(any());
    }

    @Test
    void buscarPorId_deberiaRetornarUsuarioCuandoExiste() {
        Usuario usuario = new Usuario(1L, 10L, "ADMIN_CONSULTORIO", "Administrador interno del consultorio", "admin.consultorio", "HASH", "admin.consultorio", EstadoUsuario.ACTIVO, null, null);
        when(usuarioPersistencePort.buscarPorId(1L)).thenReturn(Optional.of(usuario));

        Usuario result = usuarioApplicationService.buscarPorId(1L);

        assertEquals("admin.consultorio", result.getUsername());
    }

    @Test
    void listar_deberiaDelegarConFiltrosYPaginacion() {
        when(usuarioPersistencePort.listar(eq(10L), eq(EstadoUsuario.ACTIVO), eq("admin"), any())).thenReturn(new PageImpl<>(java.util.List.of(), PageRequest.of(0,20), 0));

        Page<Usuario> result = usuarioApplicationService.listar(10L, EstadoUsuario.ACTIVO, " admin ", PageRequest.of(0, 20));

        assertNotNull(result);
        verify(usuarioPersistencePort).listar(eq(10L), eq(EstadoUsuario.ACTIVO), eq("admin"), any());
    }

    @Test
    void cambiarEstado_deberiaActualizarUsuario() {
        Usuario usuario = new Usuario(1L, 10L, "ADMIN_CONSULTORIO", "Administrador interno del consultorio", "admin.consultorio", "HASH", "admin.consultorio", EstadoUsuario.ACTIVO, null, null);
        when(usuarioPersistencePort.buscarPorId(1L)).thenReturn(Optional.of(usuario));
        when(usuarioPersistencePort.guardar(any(Usuario.class))).thenAnswer(inv -> inv.getArgument(0));

        Usuario result = usuarioApplicationService.cambiarEstado(1L, EstadoUsuario.INACTIVO);

        assertEquals(EstadoUsuario.INACTIVO, result.getEstado());
    }

    @Test
    void resetPassword_deberiaFallarSiUsuarioNoActivo() {
        Usuario usuario = new Usuario(1L, 10L, "ADMIN_CONSULTORIO", "Administrador interno del consultorio", "admin.consultorio", "HASH", "admin.consultorio", EstadoUsuario.INACTIVO, null, null);
        when(usuarioPersistencePort.buscarPorId(1L)).thenReturn(Optional.of(usuario));

        assertThrows(UsuarioNoActivoException.class, () -> usuarioApplicationService.resetPassword(1L, "TemporalNueva123"));
    }
}
