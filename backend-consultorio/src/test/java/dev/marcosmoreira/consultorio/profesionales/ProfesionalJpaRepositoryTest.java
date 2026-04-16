package dev.marcosmoreira.consultorio.profesionales;

import dev.marcosmoreira.consultorio.profesionales.domain.enums.EstadoProfesional;
import dev.marcosmoreira.consultorio.profesionales.infrastructure.persistence.entity.ProfesionalEntity;
import dev.marcosmoreira.consultorio.profesionales.infrastructure.persistence.repository.ProfesionalJpaRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas JPA del repositorio de profesionales.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@DataJpaTest
class ProfesionalJpaRepositoryTest {

    @Autowired
    private ProfesionalJpaRepository profesionalJpaRepository;

    @Test
    void existsByUsuarioId_deberiaRetornarTrueSiExisteAsociacion() {
        ProfesionalEntity entity = buildProfesional(
                10L,
                "Carlos",
                "Mendoza",
                "Medicina general",
                "REG-001",
                EstadoProfesional.ACTIVO
        );

        profesionalJpaRepository.save(entity);

        boolean exists = profesionalJpaRepository.existsByUsuarioId(10L);

        assertTrue(exists);
    }

    @Test
    void existsByUsuarioIdAndProfesionalIdNot_deberiaRetornarTrueSiExisteOtroProfesionalAsociado() {
        ProfesionalEntity saved = profesionalJpaRepository.save(
                buildProfesional(
                        10L,
                        "Carlos",
                        "Mendoza",
                        "Medicina general",
                        "REG-001",
                        EstadoProfesional.ACTIVO
                )
        );

        boolean exists = profesionalJpaRepository.existsByUsuarioIdAndProfesionalIdNot(
                10L,
                saved.getProfesionalId() + 100L
        );

        assertTrue(exists);
    }

    @Test
    void buscarPorFiltros_deberiaRetornarCoincidencias() {
        profesionalJpaRepository.save(
                buildProfesional(
                        10L,
                        "Ana",
                        "Vera",
                        "Pediatría",
                        "PED-1",
                        EstadoProfesional.ACTIVO
                )
        );
        profesionalJpaRepository.save(
                buildProfesional(
                        11L,
                        "Luis",
                        "Mora",
                        "Dermatología",
                        "DER-1",
                        EstadoProfesional.INACTIVO
                )
        );

        Pageable pageable = PageRequest.of(0, 20);
        var result = profesionalJpaRepository.buscarPorFiltros(
                10L,
                EstadoProfesional.ACTIVO,
                "ana",
                pageable
        );

        assertEquals(1, result.getContent().size());
        assertEquals("Ana", result.getContent().get(0).getNombres());
    }

    private ProfesionalEntity buildProfesional(
            Long usuarioId,
            String nombres,
            String apellidos,
            String especialidadBreve,
            String registroProfesional,
            EstadoProfesional estadoProfesional
    ) {
        ProfesionalEntity entity = new ProfesionalEntity();
        entity.setUsuarioId(usuarioId);
        entity.setNombres(nombres);
        entity.setApellidos(apellidos);
        entity.setEspecialidadBreve(especialidadBreve);
        entity.setRegistroProfesional(registroProfesional);
        entity.setEstadoProfesional(estadoProfesional);
        return entity;
    }
}
