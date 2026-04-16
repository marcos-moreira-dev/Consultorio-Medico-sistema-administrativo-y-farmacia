package dev.marcosmoreira.consultorio.auth.application.port.out;

import dev.marcosmoreira.consultorio.auth.domain.model.AuthUser;
import java.util.Optional;

public interface LoadAuthUserPort {
    AuthUser authenticateAndLoad(String username, String rawPassword);
    Optional<AuthUser> getCurrentAuthenticatedUser();
}
