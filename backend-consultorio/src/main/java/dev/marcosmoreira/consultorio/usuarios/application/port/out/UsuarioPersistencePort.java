package dev.marcosmoreira.consultorio.usuarios.application.port.out;

import dev.marcosmoreira.consultorio.usuarios.domain.enums.EstadoUsuario;
import dev.marcosmoreira.consultorio.usuarios.domain.model.Usuario;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioPersistencePort {
    Usuario guardar(Usuario usuario);
    Optional<Usuario> buscarPorId(Long usuarioId);
    Page<Usuario> listar(Long rolId, EstadoUsuario estado, String q, Pageable pageable);
    boolean existsByUsernameIgnoreCase(String username);
}
