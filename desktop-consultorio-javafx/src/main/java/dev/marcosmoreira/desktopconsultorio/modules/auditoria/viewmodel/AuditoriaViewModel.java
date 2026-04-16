package dev.marcosmoreira.desktopconsultorio.modules.auditoria.viewmodel;

import dev.marcosmoreira.desktopconsultorio.demo.DemoDataFactory;
import dev.marcosmoreira.desktopconsultorio.http.dto.auditoria.EventoAuditoriaDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.common.PageResponseDto;
import dev.marcosmoreira.desktopconsultorio.http.service.AuditoriaApiService;
import dev.marcosmoreira.desktopconsultorio.shared.viewmodel.BaseViewModel;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * ViewModel del módulo de Auditoría.
 */
public class AuditoriaViewModel extends BaseViewModel {

    private final AuditoriaApiService auditoriaApi;

    private final ObservableList<EventoAuditoriaDto> eventos = FXCollections.observableArrayList();
    private final StringProperty filtroModulo = new SimpleStringProperty("");
    private final StringProperty filtroUsuario = new SimpleStringProperty("");
    private final StringProperty filtroBusqueda = new SimpleStringProperty("");

    public AuditoriaViewModel(AuditoriaApiService auditoriaApi) {
        this.auditoriaApi = auditoriaApi;
    }

    // === Properties ===
    public ObservableList<EventoAuditoriaDto> eventosProperty() { return eventos; }
    public StringProperty filtroModuloProperty() { return filtroModulo; }
    public StringProperty filtroUsuarioProperty() { return filtroUsuario; }
    public StringProperty filtroBusquedaProperty() { return filtroBusqueda; }

    /**
     * Carga eventos de auditoría.
     */
    public void cargarEventos() {
        executeAsync("cargar auditoría", () -> {
            PageResponseDto<EventoAuditoriaDto> resp = auditoriaApi.listar(0, 100, null, null, null);
            Platform.runLater(() -> {
                eventos.clear();
                if (resp != null && resp.getData() != null && !resp.getData().isEmpty()) {
                    eventos.addAll(resp.getData());
                } else {
                    cargarEventosDemo();
                }
            });
        }, null);
    }

    /**
     * Aplica filtros en cliente sobre la lista ya cargada.
     */
    public ObservableList<EventoAuditoriaDto> getEventosFiltrados() {
        String modulo = filtroModulo.get();
        String usuario = filtroUsuario.get();
        String busqueda = filtroBusqueda.get();

        if ((modulo == null || modulo.isEmpty() || "Todos".equals(modulo))
                && (usuario == null || usuario.isEmpty())
                && (busqueda == null || busqueda.isEmpty())) {
            return eventos;
        }

        ObservableList<EventoAuditoriaDto> filtrados = FXCollections.observableArrayList();
        for (EventoAuditoriaDto e : eventos) {
            boolean match = true;
            if (modulo != null && !modulo.isEmpty() && !"Todos".equals(modulo)) {
                match = match && modulo.equalsIgnoreCase(e.getModulo());
            }
            if (usuario != null && !usuario.isEmpty()) {
                match = match && (e.getUsername() != null && e.getUsername().toLowerCase().contains(usuario.toLowerCase()));
            }
            if (busqueda != null && !busqueda.isEmpty()) {
                String texto = (e.getDescripcion() + " " + e.getUsername() + " " + e.getModulo()).toLowerCase();
                match = match && texto.contains(busqueda.toLowerCase());
            }
            if (match) filtrados.add(e);
        }
        return filtrados;
    }

    private void cargarEventosDemo() {
        eventos.clear();
        eventos.addAll(DemoDataFactory.getAuditEvents());
    }
}
