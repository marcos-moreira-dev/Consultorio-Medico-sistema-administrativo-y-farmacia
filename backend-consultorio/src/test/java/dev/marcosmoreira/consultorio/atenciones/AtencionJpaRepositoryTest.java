package dev.marcosmoreira.consultorio.atenciones;

import dev.marcosmoreira.consultorio.atenciones.infrastructure.persistence.entity.AtencionEntity;
import dev.marcosmoreira.consultorio.atenciones.infrastructure.persistence.repository.AtencionJpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas JPA del repositorio de atenciones.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@DataJpaTest
class AtencionJpaRepositoryTest {

    @Autowired
    private AtencionJpaRepository atencionJpaRepository;

    @Test
    void saveYFindById_deberiaPersistirAtencion() {
        AtencionEntity entity = buildAtencion(
                1L,
                2L,
                3L,
                LocalDateTime.of(2026, 4, 8, 9, 0),
                "Control general",
                "Reposo"
        );

        AtencionEntity saved = atencionJpaRepository.save(entity);

        assertNotNull(saved.getAtencionId());
        assertTrue(atencionJpaRepository.findById(saved.getAtencionId()).isPresent());
    }

    @Test
    void buscarPorFiltros_deberiaRetornarCoincidencias() {
        AtencionEntity a1 = buildAtencion(
                1L,
                2L,
                null,
                LocalDateTime.of(2026, 4, 8, 10, 0),
                "Chequeo",
                null
        );
        AtencionEntity a2 = buildAtencion(
                3L,
                4L,
                null,
                LocalDateTime.of(2026, 4, 9, 11, 0),
                "Control",
                null
        );

        atencionJpaRepository.save(a1);
        atencionJpaRepository.save(a2);

        Pageable pageable = PageRequest.of(0, 20);
        var result = atencionJpaRepository.buscarPorFiltros(
                1L,
                2L,
                LocalDateTime.of(2026, 4, 1, 0, 0),
                LocalDateTime.of(2026, 4, 30, 23, 59),
                pageable
        );

        assertEquals(1, result.getContent().size());
        assertEquals("Chequeo", result.getContent().get(0).getNotaBreve());
    }

    private AtencionEntity buildAtencion(
            Long pacienteId,
            Long profesionalId,
            Long citaId,
            LocalDateTime fechaHoraAtencion,
            String notaBreve,
            String indicacionesBreves
    ) {
        AtencionEntity entity = new AtencionEntity();
        entity.setPacienteId(pacienteId);
        entity.setProfesionalId(profesionalId);
        entity.setCitaId(citaId);
        entity.setFechaHoraAtencion(fechaHoraAtencion);
        entity.setNotaBreve(notaBreve);
        entity.setIndicacionesBreves(indicacionesBreves);
        return entity;
    }
}
