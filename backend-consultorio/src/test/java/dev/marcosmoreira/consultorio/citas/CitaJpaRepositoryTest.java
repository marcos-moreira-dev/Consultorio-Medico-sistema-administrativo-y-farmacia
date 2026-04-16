package dev.marcosmoreira.consultorio.citas;

import dev.marcosmoreira.consultorio.citas.domain.enums.EstadoCita;
import dev.marcosmoreira.consultorio.citas.infrastructure.persistence.entity.CitaEntity;
import dev.marcosmoreira.consultorio.citas.infrastructure.persistence.repository.CitaJpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas JPA del repositorio de citas.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@DataJpaTest
class CitaJpaRepositoryTest {

    @Autowired
    private CitaJpaRepository citaJpaRepository;

    @Test
    void existsByProfesionalIdAndFechaHoraInicio_deberiaRetornarTrueSiExisteSlotOcupado() {
        LocalDateTime inicio = LocalDateTime.of(2026, 4, 10, 9, 0);

        CitaEntity entity = buildCita(1L, 2L, inicio, EstadoCita.PROGRAMADA, "Control", null);
        citaJpaRepository.save(entity);

        boolean exists = citaJpaRepository.existsByProfesionalIdAndFechaHoraInicio(2L, inicio);

        assertTrue(exists);
    }

    @Test
    void existsByProfesionalIdAndFechaHoraInicioAndCitaIdNot_deberiaRetornarTrueSiExisteOtraCita() {
        LocalDateTime inicio = LocalDateTime.of(2026, 4, 10, 10, 0);

        CitaEntity entity = buildCita(1L, 2L, inicio, EstadoCita.PROGRAMADA, "Chequeo", null);
        CitaEntity saved = citaJpaRepository.save(entity);

        boolean exists = citaJpaRepository.existsByProfesionalIdAndFechaHoraInicioAndCitaIdNot(
                2L,
                inicio,
                saved.getCitaId() + 100L
        );

        assertTrue(exists);
    }

    @Test
    void buscarPorFiltros_deberiaRetornarCoincidencias() {
        CitaEntity c1 = buildCita(
                1L,
                2L,
                LocalDateTime.of(2026, 4, 10, 9, 0),
                EstadoCita.PROGRAMADA,
                "Control",
                null
        );
        CitaEntity c2 = buildCita(
                3L,
                4L,
                LocalDateTime.of(2026, 4, 11, 9, 0),
                EstadoCita.CANCELADA,
                "Chequeo",
                null
        );

        citaJpaRepository.save(c1);
        citaJpaRepository.save(c2);

        Pageable pageable = PageRequest.of(0, 20);
        var result = citaJpaRepository.buscarPorFiltros(
                1L,
                2L,
                EstadoCita.PROGRAMADA,
                LocalDateTime.of(2026, 4, 1, 0, 0),
                LocalDateTime.of(2026, 4, 30, 23, 59),
                pageable
        );

        assertEquals(1, result.getContent().size());
        assertEquals("Control", result.getContent().get(0).getMotivoBreve());
    }

    private CitaEntity buildCita(
            Long pacienteId,
            Long profesionalId,
            LocalDateTime fechaHoraInicio,
            EstadoCita estadoCita,
            String motivoBreve,
            String observacionOperativa
    ) {
        CitaEntity entity = new CitaEntity();
        entity.setPacienteId(pacienteId);
        entity.setProfesionalId(profesionalId);
        entity.setFechaHoraInicio(fechaHoraInicio);
        entity.setEstadoCita(estadoCita);
        entity.setMotivoBreve(motivoBreve);
        entity.setObservacionOperativa(observacionOperativa);
        return entity;
    }
}
