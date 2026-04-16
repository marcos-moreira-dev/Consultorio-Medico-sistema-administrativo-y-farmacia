package dev.marcosmoreira.desktopconsultorio.modules.usuarios.viewmodel;

import dev.marcosmoreira.desktopconsultorio.demo.DemoDataFactory;
import dev.marcosmoreira.desktopconsultorio.http.dto.usuarios.UsuarioResumenDto;
import dev.marcosmoreira.desktopconsultorio.http.service.UsuarioApiService;
import dev.marcosmoreira.desktopconsultorio.shared.viewmodel.BaseViewModel;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class UsuariosViewModel extends BaseViewModel {

    private final UsuarioApiService usuarioApi;

    private final ObservableList<UsuarioResumenDto> usuarios = FXCollections.observableArrayList();
    private final StringProperty filtroTexto = new SimpleStringProperty("");
    private final ObjectProperty<UsuarioResumenDto> seleccionado = new SimpleObjectProperty<>();

    private int paginaActual = 0;
    private int size = 20;
    private long totalElements = 0;
    private int totalPages = 0;

    public UsuariosViewModel(UsuarioApiService usuarioApi) {
        this.usuarioApi = usuarioApi;
    }

    public void cargarUsuarios() {
        executeAsync("cargar usuarios", () -> {
            String q = filtroTexto.get().trim().isEmpty() ? null : filtroTexto.get().trim();
            var resp = usuarioApi.listar(paginaActual, size, null, null, q);
            List<UsuarioResumenDto> lista = new ArrayList<>();
            long total = 0;
            int totalPaginas = 0;
            if (resp != null) {
                if (resp.getData() != null) lista = resp.getData();
                if (resp.getMeta() != null) {
                    total = resp.getMeta().getTotalElements();
                    totalPaginas = resp.getMeta().getTotalPages();
                }
            }
            final List<UsuarioResumenDto> datos = lista;
            final long t = total;
            final int tp = totalPaginas;
            Platform.runLater(() -> {
                usuarios.setAll(datos);
                totalElements = t;
                totalPages = tp;
            });
        }, null);
    }

    public void buscar(String texto) {
        filtroTexto.set(texto);
        paginaActual = 0;
        cargarUsuarios();
    }

    public void paginaSiguiente() {
        if (paginaActual < totalPages - 1) {
            paginaActual++;
            cargarUsuarios();
        }
    }

    public void paginaAnterior() {
        if (paginaActual > 0) {
            paginaActual--;
            cargarUsuarios();
        }
    }

    public ObservableList<UsuarioResumenDto> getUsuarios() { return usuarios; }
    public StringProperty filtroTextoProperty() { return filtroTexto; }
    public ObjectProperty<UsuarioResumenDto> seleccionadoProperty() { return seleccionado; }
    public int getPaginaActual() { return paginaActual; }
    public long getTotalElements() { return totalElements; }
    public int getTotalPages() { return totalPages; }
    public int getSize() { return size; }

    public List<UsuarioResumenDto> generarDemo() {
        return DemoDataFactory.getUsuarios();
    }

    public void crearUsuario(Long rolId, String username, String passwordTemporal) {
        executeAsync("crear usuario", () -> {
            usuarioApi.crear(rolId, username, passwordTemporal);
            Platform.runLater(this::cargarUsuarios);
        }, null);
    }

    public void cambiarEstado(Long usuarioId, String nuevoEstado) {
        executeAsync("cambiar estado", () -> {
            usuarioApi.cambiarEstado(usuarioId, nuevoEstado);
            Platform.runLater(this::cargarUsuarios);
        }, null);
    }

    public void resetPassword(Long usuarioId, String nuevoPassword) {
        executeAsync("resetear password", () -> {
            usuarioApi.resetPassword(usuarioId, nuevoPassword);
            Platform.runLater(this::cargarUsuarios);
        }, null);
    }
}
