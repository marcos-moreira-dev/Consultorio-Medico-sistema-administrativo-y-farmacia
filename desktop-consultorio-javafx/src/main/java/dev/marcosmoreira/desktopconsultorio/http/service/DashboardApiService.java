package dev.marcosmoreira.desktopconsultorio.http.service;

import dev.marcosmoreira.desktopconsultorio.app.ConsultorioDesktopApi;
import dev.marcosmoreira.desktopconsultorio.http.dto.dashboard.DashboardResumenDto;

public class DashboardApiService {

    private final ConsultorioDesktopApi api;

    public DashboardApiService(ConsultorioDesktopApi api) {
        this.api = api;
    }

    public DashboardResumenDto obtenerResumen() {
        return api.fetchOne("/dashboard/resumen", DashboardResumenDto.class);
    }
}
