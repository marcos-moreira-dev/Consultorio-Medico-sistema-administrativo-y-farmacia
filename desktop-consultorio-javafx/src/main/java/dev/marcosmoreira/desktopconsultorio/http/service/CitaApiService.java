package dev.marcosmoreira.desktopconsultorio.http.service;

import dev.marcosmoreira.desktopconsultorio.app.ConsultorioDesktopApi;
import dev.marcosmoreira.desktopconsultorio.http.dto.citas.AgendaItemDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.citas.CitaDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.common.PageResponseDto;
import dev.marcosmoreira.desktopconsultorio.http.util.UrlBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio de citas contra el backend del consultorio.
 */
public class CitaApiService {

    private final ConsultorioDesktopApi api;

    public CitaApiService(ConsultorioDesktopApi api) {
        this.api = api;
    }

    /**
     * Lista citas con paginación y filtros opcionales.
     */
    public PageResponseDto<CitaDto> listar(int page, int size, String estado, Long profesionalId, Long pacienteId) {
        String path = new UrlBuilder("/citas")
                .param("page", page)
                .param("size", size)
                .param("estadoCita", estado)
                .param("profesionalId", profesionalId)
                .param("pacienteId", pacienteId)
                .toString();
        return api.fetchPage(path, page, size, CitaDto.class);
    }

    /**
     * Obtiene la agenda del día para un profesional específico.
     */
    public List<AgendaItemDto> agendaDelDia(LocalDate fecha, Long profesionalId) {
        LocalDateTime from = fecha.atStartOfDay();
        LocalDateTime to = fecha.plusDays(1).atStartOfDay();
        String path = new UrlBuilder("/citas")
                .param("fechaDesde", from)
                .param("fechaHasta", to)
                .param("profesionalId", profesionalId)
                .toString();
        PageResponseDto<AgendaItemDto> resp = api.fetchPage(path, 0, 100, AgendaItemDto.class);
        return resp != null && resp.getData() != null ? resp.getData() : List.of();
    }

    /**
     * Obtiene una cita por su identificador.
     */
    public CitaDto obtenerPorId(Long citaId) {
        return api.fetchOne("/citas/" + citaId, CitaDto.class);
    }

    /**
     * Crea una nueva cita.
     */
    public CitaDto crear(CitaDto cita) {
        return api.post("/citas", cita, CitaDto.class);
    }

    /**
     * Cancela una cita existente.
     */
    public CitaDto cancelar(Long citaId, String observacion) {
        var body = new java.util.HashMap<String, Object>();
        body.put("observacionOperativa", observacion);
        return api.patch("/citas/" + citaId + "/cancelar", body, CitaDto.class);
    }

    /**
     * Reprograma una cita a nueva fecha/hora.
     */
    public CitaDto reprogramar(Long citaId, java.time.LocalDateTime nuevaFechaHora, String observacion) {
        var body = new java.util.HashMap<String, Object>();
        body.put("nuevaFechaHoraInicio", nuevaFechaHora.toString());
        body.put("observacionOperativa", observacion);
        return api.patch("/citas/" + citaId + "/reprogramar", body, CitaDto.class);
    }
}
