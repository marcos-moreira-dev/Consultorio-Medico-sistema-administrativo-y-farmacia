package dev.marcosmoreira.desktopconsultorio.http.service;

import dev.marcosmoreira.desktopconsultorio.app.ConsultorioDesktopApi;
import dev.marcosmoreira.desktopconsultorio.http.dto.cobros.CobroDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.common.PageResponseDto;
import dev.marcosmoreira.desktopconsultorio.http.util.UrlBuilder;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Servicio de cobros contra el backend del consultorio.
 */
public class CobroApiService {

    private final ConsultorioDesktopApi api;

    public CobroApiService(ConsultorioDesktopApi api) {
        this.api = api;
    }

    /**
     * Lista cobros con paginación y filtros.
     */
    public PageResponseDto<CobroDto> listar(int page, int size, String estado, String metodoPago) {
        String path = new UrlBuilder("/cobros")
                .param("page", page)
                .param("size", size)
                .param("estadoCobro", estado)
                .param("metodoPago", metodoPago)
                .toString();
        return api.fetchPage(path, page, size, CobroDto.class);
    }

    /**
     * Busca cobros en un rango de fechas.
     */
    public PageResponseDto<CobroDto> porRango(LocalDate desde, LocalDate hasta, int page, int size) {
        String path = new UrlBuilder("/cobros")
                .param("page", page)
                .param("size", size)
                .param("fechaDesde", desde != null ? desde.atStartOfDay() : null)
                .param("fechaHasta", hasta != null ? hasta.plusDays(1).atStartOfDay() : null)
                .toString();
        return api.fetchPage(path, page, size, CobroDto.class);
    }

    /**
     * Obtiene un cobro por su identificador.
     */
    public CobroDto obtenerPorId(Long cobroId) {
        return api.fetchOne("/cobros/" + cobroId, CobroDto.class);
    }

    /**
     * Registra un nuevo cobro.
     */
    public CobroDto crear(CobroDto cobro) {
        return api.post("/cobros", cobro, CobroDto.class);
    }

    /**
     * Calcula el total cobrado en un período.
     */
    public BigDecimal totalCobrado(LocalDate desde, LocalDate hasta) {
        // El backend puede exponer un endpoint de resumen; si no, se calcula en cliente
        PageResponseDto<CobroDto> resp = porRango(desde, hasta, 0, 1000);
        if (resp == null || resp.getData() == null) return BigDecimal.ZERO;
        return resp.getData().stream()
                .filter(c -> "PAGADO".equalsIgnoreCase(c.getEstadoCobro()))
                .map(CobroDto::getMonto)
                .filter(m -> m != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
