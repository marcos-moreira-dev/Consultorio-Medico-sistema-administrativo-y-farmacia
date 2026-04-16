package dev.marcosmoreira.consultorio.usuarios.application.service;

import dev.marcosmoreira.consultorio.roles.application.port.in.BuscarRolUseCase;
import dev.marcosmoreira.consultorio.shared.error.ResourceNotFoundException;
import dev.marcosmoreira.consultorio.shared.util.PageableUtils;
import dev.marcosmoreira.consultorio.usuarios.application.port.in.BuscarUsuarioUseCase;
import dev.marcosmoreira.consultorio.usuarios.application.port.in.CambiarEstadoUsuarioUseCase;
import dev.marcosmoreira.consultorio.usuarios.application.port.in.CrearUsuarioUseCase;
import dev.marcosmoreira.consultorio.usuarios.application.port.in.ListarUsuariosUseCase;
import dev.marcosmoreira.consultorio.usuarios.application.port.in.ResetPasswordUsuarioUseCase;
import dev.marcosmoreira.consultorio.usuarios.application.port.out.UsuarioPersistencePort;
import dev.marcosmoreira.consultorio.usuarios.domain.enums.EstadoUsuario;
import dev.marcosmoreira.consultorio.usuarios.domain.exception.UsuarioDuplicadoException;
import dev.marcosmoreira.consultorio.usuarios.domain.exception.UsuarioNoActivoException;
import dev.marcosmoreira.consultorio.usuarios.domain.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio de aplicación del módulo de usuarios.
 *
 * <p>Orquesta los casos de uso administrativos de usuarios del sistema:
 * creación, consulta, listado, cambio de estado y reseteo de contraseña.</p>
 */
@Service
@Transactional(readOnly = true)
public class UsuarioApplicationService implements
        CrearUsuarioUseCase,
        BuscarUsuarioUseCase,
        ListarUsuariosUseCase,
        CambiarEstadoUsuarioUseCase,
        ResetPasswordUsuarioUseCase {

    private final UsuarioPersistencePort usuarioPersistencePort;
    private final PasswordEncoder passwordEncoder;
    private final BuscarRolUseCase buscarRolUseCase;

    public UsuarioApplicationService(
            UsuarioPersistencePort usuarioPersistencePort,
            @Lazy PasswordEncoder passwordEncoder,
            BuscarRolUseCase buscarRolUseCase
    ) {
        this.usuarioPersistencePort = usuarioPersistencePort;
        this.passwordEncoder = passwordEncoder;
        this.buscarRolUseCase = buscarRolUseCase;
    }

    /**
     * Registra un nuevo usuario del sistema.
     *
     * <p>En la V1 no se persiste un nombre visible propio en {@code usuario}. El nombre
     * visible se resuelve después desde el vínculo con {@code profesional} o, si todavía
     * no existe, desde el propio {@code username}. Esta decisión reduce inconsistencia
     * entre contrato HTTP, JPA y base de datos.</p>
     */
    @Override
    @Transactional
    public Usuario crear(Long rolId, String username, String rawPassword) {
        validatePositiveId(rolId, "El rol es obligatorio y debe ser válido.");

        buscarRolUseCase.buscarPorId(rolId);

        String normalizedUsername = normalizeRequiredText(username, "El username es obligatorio.");
        validateRequiredText(rawPassword, "La contraseña temporal es obligatoria.");

        if (usuarioPersistencePort.existsByUsernameIgnoreCase(normalizedUsername)) {
            throw new UsuarioDuplicadoException(
                    "Ya existe un usuario con username '" + normalizedUsername + "'."
            );
        }

        Usuario usuario = new Usuario();
        usuario.setRolId(rolId);
        usuario.setUsername(normalizedUsername);
        usuario.setNombreCompleto(normalizedUsername);
        usuario.setPasswordHash(passwordEncoder.encode(rawPassword));
        usuario.setEstado(EstadoUsuario.ACTIVO);

        return usuarioPersistencePort.guardar(usuario);
    }

    @Override
    public Usuario buscarPorId(Long usuarioId) {
        validatePositiveId(usuarioId, "El identificador del usuario debe ser mayor que cero.");

        return usuarioPersistencePort.buscarPorId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró un usuario con ID " + usuarioId + "."
                ));
    }

    @Override
    public Page<Usuario> listar(Long rolId, EstadoUsuario estado, String q, Pageable pageable) {
        validateOptionalPositiveId(rolId, "Si se envía rolId, debe ser mayor que cero.");
        String normalizedQ = normalizeNullableText(q);
        Pageable sanitizedPageable = PageableUtils.sanitize(
                pageable,
                Sort.by(Sort.Order.asc("username"), Sort.Order.asc("usuarioId")),
                "username", "usuarioId", "fechaCreacion"
        );
        return usuarioPersistencePort.listar(rolId, estado, normalizedQ, sanitizedPageable);
    }

    @Override
    @Transactional
    public Usuario cambiarEstado(Long usuarioId, EstadoUsuario nuevoEstado) {
        validatePositiveId(usuarioId, "El identificador del usuario debe ser mayor que cero.");

        if (nuevoEstado == null) {
            throw new IllegalArgumentException("El nuevo estado es obligatorio.");
        }

        Usuario usuario = buscarPorId(usuarioId);
        usuario.setEstado(nuevoEstado);

        return usuarioPersistencePort.guardar(usuario);
    }

    @Override
    @Transactional
    public Usuario resetPassword(Long usuarioId, String nuevoPassword) {
        validatePositiveId(usuarioId, "El identificador del usuario debe ser mayor que cero.");
        validateRequiredText(nuevoPassword, "La nueva contraseña es obligatoria.");

        Usuario usuario = buscarPorId(usuarioId);

        if (!usuario.isActivo()) {
            throw new UsuarioNoActivoException(
                    "No se puede resetear la contraseña de un usuario inactivo."
            );
        }

        usuario.setPasswordHash(passwordEncoder.encode(nuevoPassword));
        return usuarioPersistencePort.guardar(usuario);
    }

    private void validatePositiveId(Long value, String message) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    private void validateOptionalPositiveId(Long value, String message) {
        if (value != null && value <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    private void validateRequiredText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }

    private String normalizeRequiredText(String value, String message) {
        validateRequiredText(value, message);
        return value.trim();
    }

    private String normalizeNullableText(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }
}
