package dev.marcosmoreira.consultorio.profesionales.application.port.out;

import dev.marcosmoreira.consultorio.profesionales.domain.enums.EstadoProfesional;
import dev.marcosmoreira.consultorio.profesionales.domain.model.Profesional;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProfesionalPersistencePort {
    Profesional guardar(Profesional profesional);
    Optional<Profesional> buscarPorId(Long profesionalId);
    Page<Profesional> listar(Long usuarioId, EstadoProfesional estadoProfesional, String q, Pageable pageable);
    boolean existsByUsuarioId(Long usuarioId);
    boolean existsByUsuarioIdAndProfesionalIdNot(Long usuarioId, Long profesionalId);
}
