package dev.marcosmoreira.desktopconsultorio.http.service;

import dev.marcosmoreira.desktopconsultorio.app.ConsultorioDesktopApi;
import dev.marcosmoreira.desktopconsultorio.http.dto.common.PageResponseDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.profesionales.ProfesionalDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.profesionales.ProfesionalResumenDto;
import java.util.List;

/**
 * Servicio de profesionales contra el backend del consultorio.
 */
public class ProfesionalApiService {

    private final ConsultorioDesktopApi api;

    public ProfesionalApiService(ConsultorioDesktopApi api) {
        this.api = api;
    }

    /**
     * Lista profesionales con paginación.
     */
    public PageResponseDto<ProfesionalResumenDto> listar(int page, int size) {
        return api.fetchPage("/profesionales", page, size, ProfesionalResumenDto.class);
    }

    /**
     * Lista todos los profesionales activos (sin paginación).
     */
    public List<ProfesionalResumenDto> listarTodosActivos() {
        PageResponseDto<ProfesionalResumenDto> resp = listar(0, 200);
        if (resp == null || resp.getData() == null) return List.of();
        return resp.getData().stream()
                .filter(p -> "ACTIVO".equalsIgnoreCase(p.getEstadoProfesional()))
                .toList();
    }

    /**
     * Obtiene un profesional por su identificador.
     */
    public ProfesionalDto obtenerPorId(Long profesionalId) {
        return api.fetchOne("/profesionales/" + profesionalId, ProfesionalDto.class);
    }

    /**
     * Crea un nuevo profesional.
     */
    public ProfesionalDto crear(ProfesionalDto profesional) {
        return api.post("/profesionales", profesional, ProfesionalDto.class);
    }

    /**
     * Actualiza un profesional existente.
     */
    public ProfesionalDto actualizar(Long profesionalId, ProfesionalDto profesional) {
        return api.put("/profesionales/" + profesionalId, profesional, ProfesionalDto.class);
    }

    /**
     * Cambia el estado de un profesional (ACTIVO/INACTIVO).
     */
    public ProfesionalDto cambiarEstado(Long profesionalId, String nuevoEstado) {
        var body = new java.util.HashMap<String, Object>();
        body.put("nuevoEstado", nuevoEstado);
        return api.patch("/profesionales/" + profesionalId + "/estado", body, ProfesionalDto.class);
    }
}
