package dev.marcosmoreira.consultorio.usuarios;

import dev.marcosmoreira.consultorio.roles.infrastructure.persistence.entity.RolEntity;
import dev.marcosmoreira.consultorio.roles.infrastructure.persistence.repository.RolJpaRepository;
import dev.marcosmoreira.consultorio.usuarios.domain.enums.EstadoUsuario;
import dev.marcosmoreira.consultorio.usuarios.infrastructure.persistence.entity.UsuarioEntity;
import dev.marcosmoreira.consultorio.usuarios.infrastructure.persistence.repository.UsuarioJpaRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas JPA del repositorio de usuarios.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@DataJpaTest
class UsuarioJpaRepositoryTest {

    @Autowired
    private UsuarioJpaRepository usuarioJpaRepository;

    @Autowired
    private RolJpaRepository rolJpaRepository;

    @Test
    void findByUsernameIgnoreCase_deberiaRetornarUsuarioSinImportarMayusculas() {
        Long rolId = createRol("ADMIN_CONSULTORIO", "Administrador consultorio");

        UsuarioEntity entity = buildUsuario(
                rolId,
                "Admin",
                "HASH_123",
                "Administrador consultorio General",
                EstadoUsuario.ACTIVO
        );

        usuarioJpaRepository.save(entity);

        Optional<UsuarioEntity> result = usuarioJpaRepository.findByUsernameIgnoreCase("admin");

        assertTrue(result.isPresent());
        assertEquals("Admin", result.get().getUsername());
    }

    @Test
    void existsByUsernameIgnoreCase_deberiaRetornarTrueSiExiste() {
        Long rolId = createRol("OPERADOR_CONSULTORIO", "Operador consultorio");

        UsuarioEntity entity = buildUsuario(
                rolId,
                "recepcion1",
                "HASH_123",
                "Operador consultorio Principal",
                EstadoUsuario.ACTIVO
        );

        usuarioJpaRepository.save(entity);

        boolean exists = usuarioJpaRepository.existsByUsernameIgnoreCase("RECEPCION.ANA");

        assertTrue(exists);
    }

    @Test
    void buscarPorFiltros_deberiaRetornarCoincidencias() {
        Long adminRolId = createRol("ADMIN_CONSULTORIO", "Administrador consultorio");
        Long recepcionRolId = createRol("OPERADOR_CONSULTORIO", "Operador consultorio");

        usuarioJpaRepository.save(
                buildUsuario(
                        adminRolId,
                        "admin",
                        "HASH_1",
                        "Administrador consultorio General",
                        EstadoUsuario.ACTIVO
                )
        );

        usuarioJpaRepository.save(
                buildUsuario(
                        recepcionRolId,
                        "recepcion1",
                        "HASH_2",
                        "Operador consultorio Principal",
                        EstadoUsuario.INACTIVO
                )
        );

        Pageable pageable = PageRequest.of(0, 20);
        var result = usuarioJpaRepository.buscarPorFiltros(
                adminRolId,
                EstadoUsuario.ACTIVO,
                "admin",
                pageable
        );

        assertEquals(1, result.getContent().size());
        assertEquals("admin", result.getContent().get(0).getUsername());
    }

    private Long createRol(String codigo, String nombre) {
        RolEntity rol = new RolEntity();
        rol.setCodigo(codigo);
        rol.setNombre(nombre);
        return rolJpaRepository.save(rol).getRolId();
    }

    private UsuarioEntity buildUsuario(
            Long rolId,
            String username,
            String passwordHash,
            String nombreCompleto,
            EstadoUsuario estado
    ) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setRolId(rolId);
        entity.setUsername(username);
        entity.setPasswordHash(passwordHash);
        entity.setNombreCompleto(nombreCompleto);
        entity.setEstado(estado);
        return entity;
    }
}
