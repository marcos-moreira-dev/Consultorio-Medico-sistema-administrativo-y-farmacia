package dev.marcosmoreira.consultorio.pacientes;

import dev.marcosmoreira.consultorio.pacientes.infrastructure.persistence.entity.PacienteEntity;
import dev.marcosmoreira.consultorio.pacientes.infrastructure.persistence.repository.PacienteJpaRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas JPA del repositorio de pacientes.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@DataJpaTest
class PacienteJpaRepositoryTest {

    @Autowired
    private PacienteJpaRepository pacienteJpaRepository;

    @Test
    void existsByCedulaIgnoreCase_deberiaRetornarTrueSiExiste() {
        PacienteEntity entity = buildPaciente(
                "Ana",
                "Pérez",
                "0991112233",
                "0922334455",
                LocalDate.of(1998, 5, 10),
                "Centro"
        );

        pacienteJpaRepository.save(entity);

        boolean exists = pacienteJpaRepository.existsByCedulaIgnoreCase("0922334455");

        assertTrue(exists);
    }

    @Test
    void existsByCedulaIgnoreCaseAndPacienteIdNot_deberiaRetornarTrueSiExisteOtroPaciente() {
        PacienteEntity saved = pacienteJpaRepository.save(
                buildPaciente(
                        "Ana",
                        "Pérez",
                        "0991112233",
                        "0922334455",
                        LocalDate.of(1998, 5, 10),
                        "Centro"
                )
        );

        boolean exists = pacienteJpaRepository.existsByCedulaIgnoreCaseAndPacienteIdNot(
                "0922334455",
                saved.getPacienteId() + 100L
        );

        assertTrue(exists);
    }

    @Test
    void buscarPorFiltros_deberiaRetornarCoincidencias() {
        pacienteJpaRepository.save(
                buildPaciente(
                        "Ana",
                        "Pérez",
                        "0991112233",
                        "0922334455",
                        LocalDate.of(1998, 5, 10),
                        "Centro"
                )
        );
        pacienteJpaRepository.save(
                buildPaciente(
                        "Luis",
                        "Mora",
                        "0992223344",
                        "0911223344",
                        LocalDate.of(1990, 1, 15),
                        "Norte"
                )
        );

        Pageable pageable = PageRequest.of(0, 20);
        var result = pacienteJpaRepository.buscarPorFiltros(
                "0922334455",
                LocalDate.of(1998, 5, 10),
                "ana",
                pageable
        );

        assertEquals(1, result.getContent().size());
        assertEquals("Ana", result.getContent().get(0).getNombres());
    }

    private PacienteEntity buildPaciente(
            String nombres,
            String apellidos,
            String telefono,
            String cedula,
            LocalDate fechaNacimiento,
            String direccionBasica
    ) {
        PacienteEntity entity = new PacienteEntity();
        entity.setNombres(nombres);
        entity.setApellidos(apellidos);
        entity.setTelefono(telefono);
        entity.setCedula(cedula);
        entity.setFechaNacimiento(fechaNacimiento);
        entity.setDireccionBasica(direccionBasica);
        return entity;
    }
}
