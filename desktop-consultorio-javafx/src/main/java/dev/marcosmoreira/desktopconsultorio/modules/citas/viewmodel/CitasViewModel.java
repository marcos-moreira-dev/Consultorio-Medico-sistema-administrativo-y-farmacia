package dev.marcosmoreira.desktopconsultorio.modules.citas.viewmodel;

import dev.marcosmoreira.desktopconsultorio.demo.DemoDataFactory;
import dev.marcosmoreira.desktopconsultorio.http.dto.citas.CitaDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.common.PageResponseDto;
import dev.marcosmoreira.desktopconsultorio.http.service.CitaApiService;
import dev.marcosmoreira.desktopconsultorio.http.service.PacienteApiService;
import dev.marcosmoreira.desktopconsultorio.http.service.ProfesionalApiService;
import dev.marcosmoreira.desktopconsultorio.shared.viewmodel.BaseViewModel;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class CitasViewModel extends BaseViewModel {

    private final CitaApiService citaApi;
    private final PacienteApiService pacienteApi;
    private final ProfesionalApiService profesionalApi;

    private final ObjectProperty<LocalDate> fechaSeleccionada = new SimpleObjectProperty<>(LocalDate.now());
    private final ObservableList<CitaDto> citasDelDia = FXCollections.observableArrayList();
    private final ObjectProperty<CitaDto> citaSeleccionada = new SimpleObjectProperty<>();
    private final Set<LocalDate> diasConCitas = new HashSet<>();

    public CitasViewModel(CitaApiService citaApi, PacienteApiService pacienteApi, ProfesionalApiService profesionalApi) {
        this.citaApi = citaApi;
        this.pacienteApi = pacienteApi;
        this.profesionalApi = profesionalApi;
    }

    public ObjectProperty<LocalDate> fechaSeleccionadaProperty() { return fechaSeleccionada; }
    public ObservableList<CitaDto> citasDelDiaProperty() { return citasDelDia; }
    public ObjectProperty<CitaDto> citaSeleccionadaProperty() { return citaSeleccionada; }
    public Set<LocalDate> getDiasConCitas() { return diasConCitas; }
    public LocalDate getFechaSeleccionada() { return fechaSeleccionada.get(); }
    public CitaDto getCitaSeleccionada() { return citaSeleccionada.get(); }
    public void setCitaSeleccionada(CitaDto cita) { citaSeleccionada.set(cita); }
    public ObjectProperty<CitaDto> getCitaSeleccionadaProperty() { return citaSeleccionada; }
    public PacienteApiService getPacienteApi() { return pacienteApi; }
    public ProfesionalApiService getProfesionalApi() { return profesionalApi; }

    public void cargarCitasDelDia(LocalDate fecha) {
        final LocalDate targetFecha = fecha != null ? fecha : LocalDate.now();
        fechaSeleccionada.set(targetFecha);
        cargando.set(true);
        Thread.ofVirtual().start(() -> {
            List<CitaDto> citas = new ArrayList<>();
            try {
                cargarCitasDelMes();
                PageResponseDto<CitaDto> resp = citaApi.listar(0, 100, null, null, null);
                if (resp != null && resp.getData() != null) {
                    for (CitaDto c : resp.getData()) {
                        if (c.getFechaHoraInicio() != null && c.getFechaHoraInicio().toLocalDate().equals(targetFecha)) {
                            citas.add(c);
                        }
                    }
                }
            } catch (Exception e) {
                // API no disponible
            }
            final List<CitaDto> finalCitas = citas;
            Platform.runLater(() -> {
                citasDelDia.clear();
                if (!finalCitas.isEmpty()) {
                    finalCitas.sort(Comparator.comparing(c -> c.getFechaHoraInicio() != null ? c.getFechaHoraInicio() : LocalDateTime.MAX));
                    citasDelDia.addAll(finalCitas);
                } else {
                    cargarCitasDemo(targetFecha);
                }
                diasConCitas.add(targetFecha);
                cargando.set(false);
            });
        });
    }

    private void cargarCitasDelMes() {
        try {
            PageResponseDto<CitaDto> resp = citaApi.listar(0, 200, null, null, null);
            if (resp != null && resp.getData() != null) {
                for (CitaDto cita : resp.getData()) {
                    if (cita.getFechaHoraInicio() != null) {
                        diasConCitas.add(cita.getFechaHoraInicio().toLocalDate());
                    }
                }
            }
        } catch (Exception e) {
            // Silencioso
        }
    }

    public void crearCita(Long pacienteId, Long profesionalId, LocalDateTime fechaHora, String motivo, Runnable onSuccess) {
        executeAsync("crear cita", () -> {
            CitaDto nueva = new CitaDto();
            nueva.setPacienteId(pacienteId);
            nueva.setProfesionalId(profesionalId);
            nueva.setFechaHoraInicio(fechaHora);
            nueva.setMotivoBreve(motivo);
            nueva.setEstadoCita("PROGRAMADA");
            citaApi.crear(nueva);
            Platform.runLater(() -> {
                cargarCitasDelDia(fechaSeleccionada.get());
                if (onSuccess != null) onSuccess.run();
            });
        }, null);
    }

    public void cancelarCita(Long citaId, String observacion, Runnable onSuccess) {
        executeAsync("cancelar cita", () -> {
            citaApi.cancelar(citaId, observacion);
            Platform.runLater(() -> {
                cargarCitasDelDia(fechaSeleccionada.get());
                if (onSuccess != null) onSuccess.run();
            });
        }, null);
    }

    public void reprogramarCita(Long citaId, LocalDateTime nuevaFechaHora, String observacion, Runnable onSuccess) {
        executeAsync("reprogramar cita", () -> {
            citaApi.reprogramar(citaId, nuevaFechaHora, observacion);
            Platform.runLater(() -> {
                cargarCitasDelDia(fechaSeleccionada.get());
                if (onSuccess != null) onSuccess.run();
            });
        }, null);
    }

    private void cargarCitasDemo(LocalDate fecha) {
        citasDelDia.clear();
        citasDelDia.addAll(DemoDataFactory.getCitasForDate(fecha));
    }
}
