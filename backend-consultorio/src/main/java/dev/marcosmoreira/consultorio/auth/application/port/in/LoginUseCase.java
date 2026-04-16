package dev.marcosmoreira.consultorio.auth.application.port.in;

import dev.marcosmoreira.consultorio.auth.api.response.LoginResponse;

/**
 * Caso de uso de inicio de sesión.
 *
 * <p>Define la operación de autenticación básica de la versión 1.0.
 * La implementación concreta será responsable de validar credenciales,
 * obtener la información del usuario autenticado y emitir el token
 * de acceso correspondiente.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public interface LoginUseCase {

    /**
     * Autentica a un usuario a partir de sus credenciales.
     *
     * @param username nombre de usuario
     * @param rawPassword contraseña en texto plano enviada por el cliente
     * @return respuesta de login con token y datos básicos del usuario
     */
    LoginResponse login(String username, String rawPassword);
}
