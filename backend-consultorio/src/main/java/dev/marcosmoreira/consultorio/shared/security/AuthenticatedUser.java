package dev.marcosmoreira.consultorio.shared.security;

import dev.marcosmoreira.consultorio.auth.domain.model.AuthUser;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Representación del usuario autenticado dentro del contexto de Spring Security.
 *
 * <p>Esta clase adapta la información relevante del usuario del sistema para que
 * pueda convivir con el contrato {@link UserDetails}, sin perder datos útiles
 * como identificador interno, nombre visible y rol resumido.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class AuthenticatedUser implements UserDetails {

    private final Long usuarioId;
    private final String username;
    private final String passwordHash;
    private final String nombreCompleto;
    private final String rolCodigo;
    private final String rolNombre;
    private final boolean activo;
    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * Construye un usuario autenticado completo.
     *
     * @param usuarioId identificador interno del usuario
     * @param username nombre de usuario
     * @param passwordHash hash de contraseña persistido
     * @param nombreCompleto nombre visible completo
     * @param rolCodigo código del rol principal
     * @param rolNombre nombre legible del rol principal
     * @param activo estado lógico del usuario
     * @param authorities autoridades de Spring Security
     */
    public AuthenticatedUser(
            Long usuarioId,
            String username,
            String passwordHash,
            String nombreCompleto,
            String rolCodigo,
            String rolNombre,
            boolean activo,
            Collection<? extends GrantedAuthority> authorities
    ) {
        this.usuarioId = usuarioId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.nombreCompleto = nombreCompleto;
        this.rolCodigo = rolCodigo;
        this.rolNombre = rolNombre;
        this.activo = activo;
        this.authorities = authorities == null ? List.of() : List.copyOf(authorities);
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getRolCodigo() {
        return rolCodigo;
    }

    public String getRolNombre() {
        return rolNombre;
    }

    /**
     * Convierte este principal de seguridad al modelo reducido de autenticación
     * usado por el módulo {@code auth}.
     *
     * @return modelo reducido del usuario autenticado
     */
    public AuthUser toAuthUser() {
        return new AuthUser(
                usuarioId,
                username,
                nombreCompleto,
                rolCodigo,
                rolNombre,
                activo
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return activo;
    }

    @Override
    public boolean isAccountNonLocked() {
        return activo;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return activo;
    }

    @Override
    public boolean isEnabled() {
        return activo;
    }
}
