package dev.marcosmoreira.consultorio.usuarios.application.port.in;

import dev.marcosmoreira.consultorio.usuarios.domain.enums.EstadoUsuario;
import dev.marcosmoreira.consultorio.usuarios.domain.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarUsuariosUseCase {
    Page<Usuario> listar(Long rolId, EstadoUsuario estado, String q, Pageable pageable);
}
