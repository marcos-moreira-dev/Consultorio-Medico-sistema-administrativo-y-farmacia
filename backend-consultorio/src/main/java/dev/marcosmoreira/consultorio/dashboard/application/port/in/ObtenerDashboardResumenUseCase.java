package dev.marcosmoreira.consultorio.dashboard.application.port.in;

import dev.marcosmoreira.consultorio.dashboard.api.response.DashboardResumenResponse;

public interface ObtenerDashboardResumenUseCase {
    DashboardResumenResponse obtenerResumen();
}
