package dev.marcosmoreira.consultorio.usuarios.infrastructure.persistence.adapter;

import dev.marcosmoreira.consultorio.usuarios.application.port.out.UsuarioPersistencePort;
import dev.marcosmoreira.consultorio.usuarios.domain.enums.EstadoUsuario;
import dev.marcosmoreira.consultorio.usuarios.domain.model.Usuario;
import dev.marcosmoreira.consultorio.usuarios.infrastructure.persistence.entity.UsuarioEntity;
import dev.marcosmoreira.consultorio.usuarios.infrastructure.persistence.mapper.UsuarioPersistenceMapper;
import dev.marcosmoreira.consultorio.usuarios.infrastructure.persistence.repository.UsuarioJpaRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioPersistenceAdapter implements UsuarioPersistencePort {
    private final UsuarioJpaRepository usuarioJpaRepository;
    private final UsuarioPersistenceMapper usuarioPersistenceMapper;
    public UsuarioPersistenceAdapter(UsuarioJpaRepository usuarioJpaRepository, UsuarioPersistenceMapper usuarioPersistenceMapper) {
        this.usuarioJpaRepository = usuarioJpaRepository;
        this.usuarioPersistenceMapper = usuarioPersistenceMapper;
    }
    @Override public Usuario guardar(Usuario usuario) {
        UsuarioEntity entity = usuarioPersistenceMapper.toEntity(usuario);
        return usuarioPersistenceMapper.toDomain(usuarioJpaRepository.save(entity));
    }
    @Override public Optional<Usuario> buscarPorId(Long usuarioId) {
        return usuarioJpaRepository.findById(usuarioId).map(usuarioPersistenceMapper::toDomain);
    }
    @Override public Page<Usuario> listar(Long rolId, EstadoUsuario estado, String q, Pageable pageable) {
        return usuarioJpaRepository.buscarPorFiltros(rolId, estado, q, pageable).map(usuarioPersistenceMapper::toDomain);
    }
    @Override public boolean existsByUsernameIgnoreCase(String username) {
        return usuarioJpaRepository.existsByUsernameIgnoreCase(username);
    }
}
