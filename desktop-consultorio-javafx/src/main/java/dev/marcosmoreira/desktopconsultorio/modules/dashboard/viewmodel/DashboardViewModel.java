package dev.marcosmoreira.desktopconsultorio.modules.dashboard.viewmodel;

import dev.marcosmoreira.desktopconsultorio.demo.DemoDataFactory;
import dev.marcosmoreira.desktopconsultorio.http.service.*;
import dev.marcosmoreira.desktopconsultorio.modules.dashboard.model.ActividadRecienteModel;
import dev.marcosmoreira.desktopconsultorio.modules.dashboard.model.DashboardMetricModel;
import dev.marcosmoreira.desktopconsultorio.shared.viewmodel.BaseViewModel;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel del Dashboard.
 */
public class DashboardViewModel extends BaseViewModel {

    private final ObjectProperty<LocalDate> fechaActual = new SimpleObjectProperty<>(LocalDate.now());
    private final StringProperty saludo = new SimpleStringProperty();

    private final ObservableList<DashboardMetricModel> metricas = FXCollections.observableArrayList();
    private final ObservableList<ActividadRecienteModel> actividadReciente = FXCollections.observableArrayList();
    private final ObservableList<ActividadRecienteModel> proximasCitas = FXCollections.observableArrayList();

    private final DashboardApiService dashboardApi;
    private final PacienteApiService pacienteApi;
    private final CitaApiService citaApi;
    private final CobroApiService cobroApi;
    private final ProfesionalApiService profesionalApi;

    public DashboardViewModel(DashboardApiService dashboardApi, PacienteApiService pacienteApi,
                              CitaApiService citaApi, CobroApiService cobroApi,
                              ProfesionalApiService profesionalApi) {
        this.dashboardApi = dashboardApi;
        this.pacienteApi = pacienteApi;
        this.citaApi = citaApi;
        this.cobroApi = cobroApi;
        this.profesionalApi = profesionalApi;
        actualizarSaludo();
    }

    // === Properties ===
    public ObjectProperty<LocalDate> fechaActualProperty() { return fechaActual; }
    public StringProperty saludoProperty() { return saludo; }
    public ObservableList<DashboardMetricModel> metricasProperty() { return metricas; }
    public ObservableList<ActividadRecienteModel> actividadRecienteProperty() { return actividadReciente; }
    public ObservableList<ActividadRecienteModel> proximasCitasProperty() { return proximasCitas; }

    /**
     * Carga las métricas del dashboard desde el backend.
     */
    public void cargarMetricas() {
        cargando.set(true);
        Thread.ofVirtual().start(() -> {
            dev.marcosmoreira.desktopconsultorio.http.dto.dashboard.DashboardResumenDto resumen = null;
            try {
                resumen = dashboardApi.obtenerResumen();
            } catch (Exception e) {
                // API no disponible
            }
            final dev.marcosmoreira.desktopconsultorio.http.dto.dashboard.DashboardResumenDto finalResumen = resumen;
            Platform.runLater(() -> {
                metricas.clear();
                if (finalResumen != null && (finalResumen.getTotalPacientes() != null && finalResumen.getTotalPacientes() > 0)) {
                    metricas.add(new DashboardMetricModel("Pacientes",
                            String.valueOf(finalResumen.getTotalPacientes()), "👥", "metric-pacientes"));
                    metricas.add(new DashboardMetricModel("Citas Hoy",
                            String.valueOf(finalResumen.getCitasHoy()), "📅", "metric-citas"));
                    metricas.add(new DashboardMetricModel("Cobros del Mes",
                            "$" + (finalResumen.getTotalCobradoMes() != null ? finalResumen.getTotalCobradoMes().toPlainString() : "0"),
                            "💰", "metric-cobros"));
                    metricas.add(new DashboardMetricModel("Profesionales",
                            String.valueOf(finalResumen.getProfesionalesActivos()), "🏥", "metric-profesionales"));
                } else {
                    cargarMetricasDemo();
                }
                cargando.set(false);
            });
        });
    }

    /**
     * Carga la actividad reciente.
     */
    public void cargarActividadReciente() {
        actividadReciente.setAll(DemoDataFactory.getActividadReciente());
    }

    /**
     * Carga las próximas citas del día.
     */
    public void cargarProximasCitas() {
        Thread.ofVirtual().start(() -> {
            List<dev.marcosmoreira.desktopconsultorio.http.dto.citas.AgendaItemDto> citas = new ArrayList<>();
            try {
                citas = citaApi.agendaDelDia(LocalDate.now(), null);
            } catch (Exception e) {
                // API no disponible
            }
            final List<dev.marcosmoreira.desktopconsultorio.http.dto.citas.AgendaItemDto> finalCitas = citas;
            Platform.runLater(() -> {
                proximasCitas.clear();
                if (finalCitas != null && !finalCitas.isEmpty()) {
                    var fmt = DateTimeFormatter.ofPattern("HH:mm");
                    for (var cita : finalCitas) {
                        String hora = cita.getFechaHoraInicio() != null
                                ? cita.getFechaHoraInicio().format(fmt) : "--:--";
                        String estado = cita.getEstadoCita() != null ? cita.getEstadoCita() : "PROGRAMADA";
                        String icono = switch (estado) {
                            case "ATENDIDA" -> "🟢";
                            case "CANCELADA" -> "🔴";
                            default -> "🟡";
                        };
                        proximasCitas.add(new ActividadRecienteModel(
                                hora,
                                cita.getNombreProfesional() != null ? cita.getNombreProfesional() : "Sin asignar",
                                estado,
                                (cita.getNombrePaciente() != null ? cita.getNombrePaciente() : "Paciente")
                                        + (cita.getMotivoBreve() != null ? " · " + cita.getMotivoBreve() : ""),
                                icono
                        ));
                    }
                } else {
                    cargarProximasCitasDemo();
                }
            });
        });
    }

    private void cargarMetricasDemo() {
        metricas.setAll(DemoDataFactory.getDashboardMetrics());
    }

    private void cargarProximasCitasDemo() {
        proximasCitas.setAll(
                new ActividadRecienteModel("08:00", "Dr. Paredes", "PROGRAMADA", "Carlos Mendoza · Control general", "🟢"),
                new ActividadRecienteModel("09:00", "Dra. Mora", "PROGRAMADA", "María Solís · Seguimiento", "🟡"),
                new ActividadRecienteModel("10:00", "Dr. Delgado", "EN ATENCION", "José Villacrés · Cardiología", "🔵"),
                new ActividadRecienteModel("11:00", "Dra. Vargas", "DISPONIBLE", "Franja libre", "⚪")
        );
    }

    private void actualizarSaludo() {
        int hora = java.time.LocalTime.now().getHour();
        String saludoText = hora < 12 ? "Buenos días" : hora < 18 ? "Buenas tardes" : "Buenas noches";
        saludo.set(saludoText);
    }
}
