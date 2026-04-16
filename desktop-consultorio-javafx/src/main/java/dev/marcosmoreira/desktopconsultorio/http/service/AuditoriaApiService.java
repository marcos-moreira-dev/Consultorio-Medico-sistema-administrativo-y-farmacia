package dev.marcosmoreira.desktopconsultorio.http.service;

import dev.marcosmoreira.desktopconsultorio.app.ConsultorioDesktopApi;
import dev.marcosmoreira.desktopconsultorio.http.dto.auditoria.EventoAuditoriaDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.common.PageResponseDto;
import dev.marcosmoreira.desktopconsultorio.http.util.UrlBuilder;

/**
 * Servicio de auditoría contra el backend del consultorio.
 */
public class AuditoriaApiService {

    private final ConsultorioDesktopApi api;

    public AuditoriaApiService(ConsultorioDesktopApi api) {
        this.api = api;
    }

    /**
     * Lista eventos de auditoría con paginación y filtros.
     */
    public PageResponseDto<EventoAuditoriaDto> listar(int page, int size, String username, String modulo, String tipoEvento) {
        String path = new UrlBuilder("/auditoria/eventos")
                .param("page", page)
                .param("size", size)
                .param("modulo", modulo)
                .param("tipoEvento", tipoEvento)
                .toString();
        return api.fetchPage(path, page, size, EventoAuditoriaDto.class);
    }

    /**
     * Busca eventos por texto libre.
     */
    public PageResponseDto<EventoAuditoriaDto> buscar(String q, int page, int size) {
        String path = new UrlBuilder("/auditoria/eventos")
                .param("q", q)
                .param("page", page)
                .param("size", size)
                .toString();
        return api.fetchPage(path, page, size, EventoAuditoriaDto.class);
    }
}
