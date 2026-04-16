package dev.marcosmoreira.desktopconsultorio.http.service;

import dev.marcosmoreira.desktopconsultorio.app.ConsultorioDesktopApi;
import dev.marcosmoreira.desktopconsultorio.http.dto.atenciones.AtencionDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.common.PageResponseDto;
import dev.marcosmoreira.desktopconsultorio.http.util.UrlBuilder;
import java.util.List;

/**
 * Servicio de atenciones contra el backend del consultorio.
 */
public class AtencionApiService {

    private final ConsultorioDesktopApi api;

    public AtencionApiService(ConsultorioDesktopApi api) {
        this.api = api;
    }

    /**
     * Lista atenciones con paginación y filtros.
     */
    public PageResponseDto<AtencionDto> listar(int page, int size, Long pacienteId, Long profesionalId) {
        String path = new UrlBuilder("/atenciones")
                .param("page", page)
                .param("size", size)
                .param("pacienteId", pacienteId)
                .param("profesionalId", profesionalId)
                .toString();
        return api.fetchPage(path, page, size, AtencionDto.class);
    }

    /**
     * Obtiene una atención por su identificador.
     */
    public AtencionDto obtenerPorId(Long atencionId) {
        return api.fetchOne("/atenciones/" + atencionId, AtencionDto.class);
    }

    /**
     * Busca atenciones de un paciente específico.
     */
    public List<AtencionDto> porPaciente(Long pacienteId) {
        PageResponseDto<AtencionDto> resp = listar(0, 100, pacienteId, null);
        return resp != null && resp.getData() != null ? resp.getData() : List.of();
    }

    /**
     * Registra una nueva atención.
     */
    public AtencionDto crear(AtencionDto atencion) {
        return api.post("/atenciones", atencion, AtencionDto.class);
    }
}
