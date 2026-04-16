package dev.marcosmoreira.desktopconsultorio.modules.atenciones.viewmodel;

import dev.marcosmoreira.desktopconsultorio.demo.DemoDataFactory;
import dev.marcosmoreira.desktopconsultorio.http.dto.atenciones.AtencionDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.common.PageResponseDto;
import dev.marcosmoreira.desktopconsultorio.http.service.AtencionApiService;
import dev.marcosmoreira.desktopconsultorio.http.dto.pacientes.PacienteResumenDto;
import dev.marcosmoreira.desktopconsultorio.http.service.PacienteApiService;
import dev.marcosmoreira.desktopconsultorio.http.dto.profesionales.ProfesionalResumenDto;
import dev.marcosmoreira.desktopconsultorio.http.service.ProfesionalApiService;
import dev.marcosmoreira.desktopconsultorio.shared.viewmodel.BaseViewModel;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AtencionesViewModel extends BaseViewModel {

    private final AtencionApiService atencionApi;
    private final PacienteApiService pacienteApi;
    private final ProfesionalApiService profesionalApi;

    private final ObservableList<AtencionDto> atenciones = FXCollections.observableArrayList();
    private final IntegerProperty totalAtenciones = new SimpleIntegerProperty();

    private final ObservableList<PacienteResumenDto> pacientes = FXCollections.observableArrayList();
    private final ObservableList<ProfesionalResumenDto> profesionales = FXCollections.observableArrayList();
    private Long pacienteSeleccionadoId;
    private Long profesionalSeleccionadoId;
    private final StringProperty notaBreve = new SimpleStringProperty();
    private final StringProperty indicaciones = new SimpleStringProperty();

    public AtencionesViewModel(AtencionApiService atencionApi, PacienteApiService pacienteApi, ProfesionalApiService profesionalApi) {
        this.atencionApi = atencionApi;
        this.pacienteApi = pacienteApi;
        this.profesionalApi = profesionalApi;
    }

    public ObservableList<AtencionDto> atencionesProperty() { return atenciones; }
    public IntegerProperty totalAtencionesProperty() { return totalAtenciones; }
    public ObservableList<PacienteResumenDto> pacientesProperty() { return pacientes; }
    public ObservableList<ProfesionalResumenDto> profesionalesProperty() { return profesionales; }
    public StringProperty notaBreveProperty() { return notaBreve; }
    public StringProperty indicacionesProperty() { return indicaciones; }
    public Long getPacienteSeleccionadoId() { return pacienteSeleccionadoId; }
    public void setPacienteSeleccionadoId(Long id) { this.pacienteSeleccionadoId = id; }
    public Long getProfesionalSeleccionadoId() { return profesionalSeleccionadoId; }
    public void setProfesionalSeleccionadoId(Long id) { this.profesionalSeleccionadoId = id; }

    public void cargarAtenciones() {
        cargando.set(true);
        Thread.ofVirtual().start(() -> {
            PageResponseDto<AtencionDto> resp = null;
            try {
                resp = atencionApi.listar(0, 100, null, null);
            } catch (Exception e) {
                // API no disponible
            }
            final PageResponseDto<AtencionDto> finalResp = resp;
            Platform.runLater(() -> {
                atenciones.clear();
                if (finalResp != null && finalResp.getData() != null && !finalResp.getData().isEmpty()) {
                    atenciones.addAll(finalResp.getData());
                    if (finalResp.getMeta() != null) {
                        totalAtenciones.set((int) finalResp.getMeta().getTotalElements());
                    }
                } else {
                    cargarAtencionesDemo();
                }
                cargando.set(false);
            });
        });
    }

    public void cargarCatalogos() {
        Thread.ofVirtual().start(() -> {
            try {
                PageResponseDto<PacienteResumenDto> respP = pacienteApi.listar(0, 200);
                if (respP != null && respP.getData() != null) {
                    Platform.runLater(() -> pacientes.addAll(respP.getData()));
                }
            } catch (Exception e) { /* silencioso */ }
            try {
                PageResponseDto<ProfesionalResumenDto> respPr = profesionalApi.listar(0, 50);
                if (respPr != null && respPr.getData() != null) {
                    Platform.runLater(() -> profesionales.addAll(respPr.getData()));
                }
            } catch (Exception e) { /* silencioso */ }
        });
    }

    public void crearAtencion(Runnable onSuccess) {
        if (pacienteSeleccionadoId == null || notaBreve.get() == null || notaBreve.get().trim().isEmpty()) {
            mensajeError.set("Seleccione un paciente y escriba una nota breve.");
            return;
        }
        executeAsync("crear atención", () -> {
            AtencionDto nueva = new AtencionDto();
            nueva.setPacienteId(pacienteSeleccionadoId);
            nueva.setProfesionalId(profesionalSeleccionadoId);
            nueva.setNotaBreve(notaBreve.get());
            nueva.setIndicacionesBreves(indicaciones.get());
            atencionApi.crear(nueva);
            Platform.runLater(() -> {
                notaBreve.set("");
                indicaciones.set("");
                pacienteSeleccionadoId = null;
                profesionalSeleccionadoId = null;
                cargarAtenciones();
                if (onSuccess != null) onSuccess.run();
            });
        }, null);
    }

    private void cargarAtencionesDemo() {
        atenciones.clear();
        atenciones.addAll(DemoDataFactory.getAtenciones());
        totalAtenciones.set(atenciones.size());
    }
}
