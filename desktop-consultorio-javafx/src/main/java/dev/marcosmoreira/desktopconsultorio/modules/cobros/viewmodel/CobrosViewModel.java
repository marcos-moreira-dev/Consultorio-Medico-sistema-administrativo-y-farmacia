package dev.marcosmoreira.desktopconsultorio.modules.cobros.viewmodel;

import dev.marcosmoreira.desktopconsultorio.demo.DemoDataFactory;
import dev.marcosmoreira.desktopconsultorio.http.dto.atenciones.AtencionDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.cobros.CobroDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.common.PageResponseDto;
import dev.marcosmoreira.desktopconsultorio.http.service.AtencionApiService;
import dev.marcosmoreira.desktopconsultorio.http.service.CobroApiService;
import dev.marcosmoreira.desktopconsultorio.shared.viewmodel.BaseViewModel;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class CobrosViewModel extends BaseViewModel {

    private final CobroApiService cobroApi;
    private final AtencionApiService atencionApi;

    private final ObservableList<CobroDto> cobros = FXCollections.observableArrayList();
    private final ObservableList<AtencionDto> atencionesDisponibles = FXCollections.observableArrayList();
    private final DoubleProperty totalPagado = new SimpleDoubleProperty();
    private final DoubleProperty totalPendiente = new SimpleDoubleProperty();
    private final DoubleProperty promedioCobro = new SimpleDoubleProperty();

    public CobrosViewModel(CobroApiService cobroApi, AtencionApiService atencionApi) {
        this.cobroApi = cobroApi;
        this.atencionApi = atencionApi;
    }

    public ObservableList<CobroDto> cobrosProperty() { return cobros; }
    public ObservableList<AtencionDto> atencionesDisponiblesProperty() { return atencionesDisponibles; }
    public DoubleProperty totalPagadoProperty() { return totalPagado; }
    public DoubleProperty totalPendienteProperty() { return totalPendiente; }
    public DoubleProperty promedioCobroProperty() { return promedioCobro; }
    public double getTotalPagado() { return totalPagado.get(); }
    public double getTotalPendiente() { return totalPendiente.get(); }
    public double getPromedioCobro() { return promedioCobro.get(); }

    public void cargarCobros() {
        cargando.set(true);
        Thread.ofVirtual().start(() -> {
            PageResponseDto<CobroDto> resp = null;
            try {
                resp = cobroApi.listar(0, 200, null, null);
            } catch (Exception e) {
                // API no disponible
            }
            final PageResponseDto<CobroDto> finalResp = resp;
            Platform.runLater(() -> {
                cobros.clear();
                double pagadoHoy = 0, pendiente = 0;
                LocalDate hoy = LocalDate.now();
                if (finalResp != null && finalResp.getData() != null && !finalResp.getData().isEmpty()) {
                    for (CobroDto c : finalResp.getData()) {
                        cobros.add(c);
                        double monto = c.getMonto() != null ? c.getMonto().doubleValue() : 0;
                        if ("PAGADO".equals(c.getEstadoCobro())) {
                            if (c.getFechaHoraRegistro() != null && c.getFechaHoraRegistro().toLocalDate().equals(hoy)) {
                                pagadoHoy += monto;
                            }
                        } else {
                            pendiente += monto;
                        }
                    }
                } else {
                    cargarCobrosDemo();
                    pagadoHoy = totalPagado.get();
                    pendiente = totalPendiente.get();
                }
                totalPagado.set(pagadoHoy);
                totalPendiente.set(pendiente);
                int count = cobros.size();
                promedioCobro.set(count > 0 ? (pagadoHoy + pendiente) / count : 0);
                cargando.set(false);
            });
        });
    }

    public void crearCobro(Long atencionId, java.math.BigDecimal monto, String metodoPago, String estadoCobro, String observacion, Runnable onSuccess) {
        executeAsync("crear cobro", () -> {
            CobroDto nuevo = new CobroDto();
            nuevo.setAtencionId(atencionId);
            nuevo.setMonto(monto);
            nuevo.setMetodoPago(metodoPago);
            nuevo.setEstadoCobro(estadoCobro);
            nuevo.setObservacionAdministrativa(observacion);
            cobroApi.crear(nuevo);
            Platform.runLater(() -> {
                if (onSuccess != null) onSuccess.run();
            });
        }, null);
    }

    public void cargarAtencionesDisponibles() {
        Thread.ofVirtual().start(() -> {
            PageResponseDto<AtencionDto> resp = null;
            try {
                resp = atencionApi.listar(0, 200, null, null);
            } catch (Exception e) {
                // fallback demo
            }
            final PageResponseDto<AtencionDto> finalResp = resp;
            Platform.runLater(() -> {
                atencionesDisponibles.clear();
                if (finalResp != null && finalResp.getData() != null && !finalResp.getData().isEmpty()) {
                    finalResp.getData().stream()
                            .filter(a -> a.getTieneCobro() == null || !a.getTieneCobro())
                            .forEach(atencionesDisponibles::add);
                } else {
                    DemoDataFactory.getAtenciones().stream()
                            .filter(a -> a.getAtencionId() != null)
                            .forEach(atencionesDisponibles::add);
                }
            });
        });
    }

    private void cargarCobrosDemo() {
        cobros.clear();
        cobros.addAll(DemoDataFactory.getCobros());
        LocalDate hoy = LocalDate.now();
        double pagado = cobros.stream()
                .filter(c -> "PAGADO".equals(c.getEstadoCobro()))
                .filter(c -> c.getFechaHoraRegistro() != null && c.getFechaHoraRegistro().toLocalDate().equals(hoy))
                .mapToDouble(c -> c.getMonto() != null ? c.getMonto().doubleValue() : 0)
                .sum();
        double pendiente = cobros.stream().filter(c -> !"PAGADO".equals(c.getEstadoCobro()))
                .mapToDouble(c -> c.getMonto() != null ? c.getMonto().doubleValue() : 0).sum();
        totalPagado.set(pagado);
        totalPendiente.set(pendiente);
        promedioCobro.set(cobros.size() > 0 ? (pagado + pendiente) / cobros.size() : 0);
    }
}
