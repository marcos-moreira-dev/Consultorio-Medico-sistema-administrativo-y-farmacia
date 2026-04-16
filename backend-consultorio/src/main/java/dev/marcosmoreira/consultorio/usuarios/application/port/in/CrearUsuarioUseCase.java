package dev.marcosmoreira.consultorio.usuarios.application.port.in;

import dev.marcosmoreira.consultorio.usuarios.domain.model.Usuario;

/**
 * Caso de uso para registrar usuarios internos del sistema.
 *
 * <p>En la V1 el backend no persiste un nombre visible propio en {@code usuario};
 * ese dato se deriva luego desde {@code profesional} o, en su defecto, desde
 * {@code username}. Por eso este caso de uso solo exige los datos realmente
 * persistibles en el modelo actual.</p>
 */
public interface CrearUsuarioUseCase {

    /**
     * Registra un usuario interno.
     *
     * @param rolId identificador del rol asignado
     * @param username nombre de usuario
     * @param rawPassword contraseña temporal en texto plano
     * @return usuario creado
     */
    Usuario crear(Long rolId, String username, String rawPassword);
}
