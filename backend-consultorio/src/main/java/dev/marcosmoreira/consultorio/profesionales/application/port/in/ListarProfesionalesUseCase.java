package dev.marcosmoreira.consultorio.profesionales.application.port.in;

import dev.marcosmoreira.consultorio.profesionales.domain.enums.EstadoProfesional;
import dev.marcosmoreira.consultorio.profesionales.domain.model.Profesional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarProfesionalesUseCase {
    Page<Profesional> listar(Long usuarioId, EstadoProfesional estadoProfesional, String q, Pageable pageable);
}
