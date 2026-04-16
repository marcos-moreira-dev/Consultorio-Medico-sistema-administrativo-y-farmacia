package dev.marcosmoreira.desktopconsultorio.http.service;

import dev.marcosmoreira.desktopconsultorio.app.ConsultorioDesktopApi;
import dev.marcosmoreira.desktopconsultorio.http.dto.common.PageResponseDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.pacientes.PacienteDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.pacientes.PacienteResumenDto;
import dev.marcosmoreira.desktopconsultorio.http.util.UrlBuilder;

/**
 * Servicio de pacientes contra el backend del consultorio.
 */
public class PacienteApiService {

    private final ConsultorioDesktopApi api;

    public PacienteApiService(ConsultorioDesktopApi api) {
        this.api = api;
    }

    /**
     * Lista pacientes con paginación.
     *
     * @param page página (0-based)
     * @param size tamaño de página
     * @return respuesta paginada con resumen de pacientes
     */
    public PageResponseDto<PacienteResumenDto> listar(int page, int size) {
        return api.fetchPage("/pacientes", page, size, PacienteResumenDto.class);
    }

    /**
     * Busca pacientes por texto (nombre, apellido, cédula).
     */
    public PageResponseDto<PacienteResumenDto> buscar(String q, int page, int size) {
        String path = new UrlBuilder("/pacientes")
                .param("q", q)
                .param("page", page)
                .param("size", size)
                .toString();
        return api.fetchPage(path, page, size, PacienteResumenDto.class);
    }

    /**
     * Obtiene un paciente por su identificador.
     */
    public PacienteDto obtenerPorId(Long pacienteId) {
        return api.fetchOne("/pacientes/" + pacienteId, PacienteDto.class);
    }

    /**
     * Crea un nuevo paciente.
     */
    public PacienteDto crear(PacienteDto paciente) {
        return api.post("/pacientes", paciente, PacienteDto.class);
    }

    /**
     * Actualiza un paciente existente.
     */
    public PacienteDto actualizar(Long pacienteId, PacienteDto paciente) {
        return api.put("/pacientes/" + pacienteId, paciente, PacienteDto.class);
    }
}
