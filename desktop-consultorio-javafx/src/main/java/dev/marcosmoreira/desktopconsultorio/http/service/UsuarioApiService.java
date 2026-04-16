package dev.marcosmoreira.desktopconsultorio.http.service;

import dev.marcosmoreira.desktopconsultorio.app.ConsultorioDesktopApi;
import dev.marcosmoreira.desktopconsultorio.http.dto.common.PageResponseDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.usuarios.UsuarioDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.usuarios.UsuarioResumenDto;
import dev.marcosmoreira.desktopconsultorio.http.util.UrlBuilder;

import java.util.HashMap;
import java.util.Map;

public class UsuarioApiService {

    private final ConsultorioDesktopApi api;

    public UsuarioApiService(ConsultorioDesktopApi api) {
        this.api = api;
    }

    public PageResponseDto<UsuarioResumenDto> listar(int page, int size, Long rolId, String estado, String q) {
        String path = new UrlBuilder("/usuarios")
                .param("page", page)
                .param("size", size)
                .param("rolId", rolId)
                .param("estado", estado)
                .param("q", q)
                .toString();
        return api.fetchPage(path, page, size, UsuarioResumenDto.class);
    }

    public UsuarioDto obtenerPorId(Long usuarioId) {
        return api.fetchOne("/usuarios/" + usuarioId, UsuarioDto.class);
    }

    public UsuarioDto crear(Long rolId, String username, String passwordTemporal) {
        Map<String, Object> body = new HashMap<>();
        body.put("rolId", rolId);
        body.put("username", username);
        body.put("passwordTemporal", passwordTemporal);
        return api.post("/usuarios", body, UsuarioDto.class);
    }

    public UsuarioDto cambiarEstado(Long usuarioId, String nuevoEstado) {
        Map<String, Object> body = new HashMap<>();
        body.put("nuevoEstado", nuevoEstado);
        return api.patch("/usuarios/" + usuarioId + "/estado", body, UsuarioDto.class);
    }

    public UsuarioDto resetPassword(Long usuarioId, String nuevoPassword) {
        Map<String, Object> body = new HashMap<>();
        body.put("nuevoPassword", nuevoPassword);
        return api.patch("/usuarios/" + usuarioId + "/reset-password", body, UsuarioDto.class);
    }
}
