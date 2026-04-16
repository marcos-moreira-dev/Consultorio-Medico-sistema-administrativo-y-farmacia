package dev.marcosmoreira.consultorio.roles;

import dev.marcosmoreira.consultorio.roles.infrastructure.persistence.entity.RolEntity;
import dev.marcosmoreira.consultorio.roles.infrastructure.persistence.repository.RolJpaRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas JPA del repositorio de roles.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@DataJpaTest
class RolJpaRepositoryTest {

    @Autowired
    private RolJpaRepository rolJpaRepository;

    @Test
    void findAllByOrderByNombreAscRolIdAsc_deberiaRetornarRolesOrdenados() {
        RolEntity r1 = new RolEntity();
        r1.setCodigo("OPERADOR_CONSULTORIO");
        r1.setNombre("Operador consultorio");

        RolEntity r2 = new RolEntity();
        r2.setCodigo("ADMIN_CONSULTORIO");
        r2.setNombre("Administrador consultorio");

        rolJpaRepository.save(r1);
        rolJpaRepository.save(r2);

        List<RolEntity> result = rolJpaRepository.findAllByOrderByNombreAscRolIdAsc();

        assertEquals(2, result.size());
        assertEquals("Administrador consultorio", result.get(0).getNombre());
        assertEquals("Operador consultorio", result.get(1).getNombre());
    }
}
