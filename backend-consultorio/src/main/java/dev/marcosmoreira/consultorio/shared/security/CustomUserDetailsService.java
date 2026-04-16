package dev.marcosmoreira.consultorio.shared.security;

import dev.marcosmoreira.consultorio.usuarios.infrastructure.persistence.entity.UsuarioEntity;
import dev.marcosmoreira.consultorio.usuarios.infrastructure.persistence.repository.UsuarioJpaRepository;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación de {@link UserDetailsService} para integrar Spring Security
 * con el módulo de usuarios del sistema.
 *
 * <p>Esta clase traduce un usuario persistido a la representación que Spring
 * Security necesita para autenticar y autorizar operaciones.</p>
 *
 * <p><strong>Suposición controlada para este proyecto:</strong> el repositorio
 * de usuarios expondrá una búsqueda por username sin distinción de mayúsculas
 * y la entidad de usuario ofrecerá getters básicos coherentes con el dominio.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioJpaRepository usuarioJpaRepository;

    /**
     * Construye el servicio de carga de usuarios para Spring Security.
     *
     * @param usuarioJpaRepository repositorio JPA del módulo de usuarios
     */
    public CustomUserDetailsService(UsuarioJpaRepository usuarioJpaRepository) {
        this.usuarioJpaRepository = usuarioJpaRepository;
    }

    /**
     * Carga un usuario por username para el proceso de autenticación de Spring.
     *
     * @param username nombre de usuario a buscar
     * @return principal de seguridad listo para autenticación/autorización
     * @throws UsernameNotFoundException si el usuario no existe
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String normalizedUsername = normalizeRequiredUsername(username);

        UsuarioEntity usuario = usuarioJpaRepository.findByUsernameIgnoreCase(normalizedUsername)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "No existe un usuario con username: " + normalizedUsername
                ));

        return toAuthenticatedUser(usuario);
    }

    /**
     * Traduce la entidad de usuario a la representación de Spring Security.
     *
     * @param usuario entidad persistida del usuario
     * @return usuario autenticado adaptado a Spring Security
     */
    private AuthenticatedUser toAuthenticatedUser(UsuarioEntity usuario) {
        String rolCodigo = usuario.getRolCodigo();
        String authority = buildAuthorityFromRoleCode(rolCodigo);

        return new AuthenticatedUser(
                usuario.getUsuarioId(),
                usuario.getUsername(),
                usuario.getPasswordHash(),
                usuario.getNombreCompleto(),
                rolCodigo,
                usuario.getRolNombre(),
                isUsuarioActivo(usuario),
                List.of(new SimpleGrantedAuthority(authority))
        );
    }

    /**
     * Determina si el usuario se encuentra activo a partir de su estado persistido.
     *
     * <p>Se usa {@code String.valueOf(...)} para mantener flexibilidad mientras
     * todavía no se implementa por completo el módulo de usuarios.</p>
     *
     * @param usuario entidad persistida del usuario
     * @return {@code true} si el estado es ACTIVO; {@code false} en otro caso
     */
    private boolean isUsuarioActivo(UsuarioEntity usuario) {
        return "ACTIVO".equalsIgnoreCase(String.valueOf(usuario.getEstado()));
    }

    /**
     * Construye una autoridad Spring Security a partir del código de rol.
     *
     * @param rolCodigo código del rol persistido
     * @return autoridad compatible con Spring Security
     */
    private String buildAuthorityFromRoleCode(String rolCodigo) {
        if (rolCodigo == null || rolCodigo.isBlank()) {
            return "ROLE_USER";
        }

        String normalized = rolCodigo.trim().toUpperCase();
        return normalized.startsWith("ROLE_") ? normalized : "ROLE_" + normalized;
    }

    /**
     * Normaliza y valida el username recibido.
     *
     * @param username username a normalizar
     * @return username ya normalizado
     */
    private String normalizeRequiredUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new UsernameNotFoundException("El username es obligatorio.");
        }

        return username.trim();
    }
}
