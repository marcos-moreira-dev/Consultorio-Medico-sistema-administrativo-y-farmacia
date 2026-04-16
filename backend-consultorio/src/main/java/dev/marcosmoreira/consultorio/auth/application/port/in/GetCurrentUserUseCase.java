package dev.marcosmoreira.consultorio.auth.application.port.in;

import dev.marcosmoreira.consultorio.auth.api.response.MeResponse;

/**
 * Caso de uso para recuperar el usuario autenticado actual.
 *
 * <p>En la versión 1.0 este caso de uso devuelve directamente un modelo de
 * respuesta orientado a lectura, evitando introducir tipos intermedios
 * adicionales cuando el escenario todavía es pequeño y controlado.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public interface GetCurrentUserUseCase {

    /**
     * Recupera la información básica del usuario autenticado actual.
     *
     * @return respuesta con los datos del usuario autenticado
     */
    MeResponse getCurrentUser();
}
