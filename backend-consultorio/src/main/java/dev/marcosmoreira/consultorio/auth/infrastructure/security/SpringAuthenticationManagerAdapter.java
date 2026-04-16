package dev.marcosmoreira.consultorio.auth.infrastructure.security;

import dev.marcosmoreira.consultorio.auth.application.port.out.LoadAuthUserPort;
import dev.marcosmoreira.consultorio.auth.domain.exception.InvalidCredentialsException;
import dev.marcosmoreira.consultorio.auth.domain.model.AuthUser;
import java.util.Collection;
import java.util.Optional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Adaptador de infraestructura que integra el módulo de autenticación con Spring Security.
 *
 * <p>Su responsabilidad es delegar la autenticación al {@link AuthenticationManager}
 * configurado por la aplicación y traducir el principal autenticado a un
 * {@link AuthUser} entendible por la capa de aplicación.</p>
 *
 * <p>También ofrece acceso al usuario autenticado actual a partir del
 * {@link SecurityContextHolder}.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Component
public class SpringAuthenticationManagerAdapter implements LoadAuthUserPort {

    private final AuthenticationManager authenticationManager;

    /**
     * Construye el adaptador con el authentication manager configurado por Spring.
     *
     * @param authenticationManager manager encargado de validar credenciales
     */
    public SpringAuthenticationManagerAdapter(@Lazy AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Autentica credenciales y devuelve el usuario autenticado resultante.
     *
     * @param username nombre de usuario
     * @param rawPassword contraseña en texto plano
     * @return usuario autenticado
     * @throws InvalidCredentialsException si las credenciales no son válidas
     */
    @Override
    public AuthUser authenticateAndLoad(String username, String rawPassword) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, rawPassword)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return mapAuthenticationToAuthUser(authentication);
        } catch (BadCredentialsException ex) {
            throw new InvalidCredentialsException("Credenciales inválidas.", ex);
        } catch (DisabledException ex) {
            throw new InvalidCredentialsException("El usuario se encuentra deshabilitado.", ex);
        }
    }

    /**
     * Recupera el usuario autenticado actual desde el contexto de seguridad.
     *
     * @return usuario autenticado actual, o vacío si no existe autenticación válida
     */
    @Override
    public Optional<AuthUser> getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();

        if (principal == null || "anonymousUser".equals(principal)) {
            return Optional.empty();
        }

        return Optional.of(mapAuthenticationToAuthUser(authentication));
    }

    /**
     * Traduce un objeto {@link Authentication} de Spring Security al modelo
     * reducido {@link AuthUser} usado por el módulo.
     *
     * <p>Este método intenta aprovechar la mayor cantidad de información útil
     * disponible en el principal autenticado. Si el principal no expone todos
     * los datos, se construye una representación mínima pero válida.</p>
     *
     * @param authentication autenticación exitosa o vigente
     * @return modelo reducido del usuario autenticado
     */
    private AuthUser mapAuthenticationToAuthUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof AuthUser authUser) {
            return authUser;
        }

        if (principal instanceof UserDetails userDetails) {
            return new AuthUser(
                    null,
                    userDetails.getUsername(),
                    userDetails.getUsername(),
                    extractFirstAuthority(authentication.getAuthorities()),
                    extractFirstAuthority(authentication.getAuthorities()),
                    userDetails.isEnabled()
            );
        }

        if (principal instanceof String username) {
            return new AuthUser(
                    null,
                    username,
                    username,
                    extractFirstAuthority(authentication.getAuthorities()),
                    extractFirstAuthority(authentication.getAuthorities()),
                    true
            );
        }

        throw new InvalidCredentialsException(
                "No fue posible traducir el principal autenticado al modelo AuthUser."
        );
    }

    /**
     * Extrae la primera autoridad disponible para usarla como representación básica
     * del rol principal cuando aún no se dispone de una proyección más rica.
     *
     * @param authorities colección de autoridades de Spring Security
     * @return nombre de la primera autoridad o {@code null} si no existe ninguna
     */
    private String extractFirstAuthority(Collection<? extends GrantedAuthority> authorities) {
        if (authorities == null || authorities.isEmpty()) {
            return null;
        }

        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse(null);
    }
}
