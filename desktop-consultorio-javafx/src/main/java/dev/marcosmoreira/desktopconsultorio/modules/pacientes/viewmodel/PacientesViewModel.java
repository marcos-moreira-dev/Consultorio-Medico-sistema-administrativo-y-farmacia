package dev.marcosmoreira.desktopconsultorio.modules.pacientes.viewmodel;

import dev.marcosmoreira.desktopconsultorio.demo.DemoDataFactory;
import dev.marcosmoreira.desktopconsultorio.http.dto.common.PageResponseDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.pacientes.PacienteDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.pacientes.PacienteResumenDto;
import dev.marcosmoreira.desktopconsultorio.http.service.PacienteApiService;
import dev.marcosmoreira.desktopconsultorio.modules.pacientes.model.PacienteFilterModel;
import dev.marcosmoreira.desktopconsultorio.modules.pacientes.model.PacienteItemModel;
import dev.marcosmoreira.desktopconsultorio.shared.viewmodel.BaseViewModel;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.time.LocalDate;
import java.util.function.Consumer;

public class PacientesViewModel extends BaseViewModel {

    private final PacienteApiService pacienteApi;

    private final ObservableList<PacienteItemModel> pacientes = FXCollections.observableArrayList();
    private final FilteredList<PacienteItemModel> filteredPacientes = new FilteredList<>(pacientes);
    private final ObjectProperty<PacienteItemModel> selectedItem = new SimpleObjectProperty<>();
    private final ObjectProperty<PacienteDto> pacienteDetalle = new SimpleObjectProperty<>();
    private final IntegerProperty totalPacientes = new SimpleIntegerProperty();

    private final PacienteFilterModel filtros = new PacienteFilterModel();

    public PacientesViewModel(PacienteApiService pacienteApi) {
        this.pacienteApi = pacienteApi;
    }

    public ObservableList<PacienteItemModel> pacientesProperty() { return filteredPacientes; }
    public ObservableList<PacienteItemModel> getAllPacientes() { return pacientes; }
    public ObjectProperty<PacienteItemModel> selectedItemProperty() { return selectedItem; }
    public ObjectProperty<PacienteDto> pacienteDetalleProperty() { return pacienteDetalle; }
    public IntegerProperty totalPacientesProperty() { return totalPacientes; }
    public PacienteFilterModel filtrosProperty() { return filtros; }
    public PacienteItemModel getSelectedItem() { return selectedItem.get(); }
    public void setSelectedItem(PacienteItemModel item) { selectedItem.set(item); }

    public void cargarPacientes() {
        cargarPacientes(0, 50);
    }

    public void cargarPacientes(int page, int size) {
        cargando.set(true);
        mensajeError.set("");
        Thread.ofVirtual().start(() -> {
            PageResponseDto<PacienteResumenDto> resp = null;
            try {
                resp = pacienteApi.listar(page, size);
            } catch (Exception e) {
                // API no disponible, se usará demo data
            }
            final PageResponseDto<PacienteResumenDto> finalResp = resp;
            Platform.runLater(() -> {
                pacientes.clear();
                if (finalResp != null && finalResp.getData() != null && !finalResp.getData().isEmpty()) {
                    for (PacienteResumenDto dto : finalResp.getData()) {
                        pacientes.add(toItemModel(dto));
                    }
                    if (finalResp.getMeta() != null) {
                        totalPacientes.set((int) finalResp.getMeta().getTotalElements());
                    }
                } else {
                    cargarPacientesDemo();
                }
                cargando.set(false);
            });
        });
    }

    public void buscarPacientes(String query) {
        if (query == null || query.trim().isEmpty()) {
            filteredPacientes.setPredicate(p -> true);
        } else {
            String q = query.toLowerCase().trim();
            filteredPacientes.setPredicate(p ->
                    p.getNombres().toLowerCase().contains(q) ||
                    p.getApellidos().toLowerCase().contains(q) ||
                    (p.getCedula() != null && p.getCedula().contains(q))
            );
        }
    }

    public void cargarDetalle(Long pacienteId) {
        Thread.ofVirtual().start(() -> {
            try {
                PacienteDto dto = pacienteApi.obtenerPorId(pacienteId);
                Platform.runLater(() -> pacienteDetalle.set(dto));
            } catch (Exception e) {
                Platform.runLater(() -> {
                    for (PacienteItemModel item : pacientes) {
                        if (item.getPacienteId() == pacienteId) {
                            PacienteDto dto = new PacienteDto();
                            dto.setPacienteId(item.getPacienteId());
                            dto.setNombres(item.getNombres());
                            dto.setApellidos(item.getApellidos());
                            dto.setCedula(item.getCedula());
                            dto.setTelefono(item.getTelefono());
                            pacienteDetalle.set(dto);
                            break;
                        }
                    }
                });
            }
        });
    }

    public void guardarPaciente(Long pacienteId, String nombres, String apellidos, String cedula,
                                 String telefono, LocalDate fechaNacimiento, String direccion,
                                 Runnable onSuccess, Consumer<String> onError) {
        if (pacienteId == null && cedula != null && !cedula.trim().isEmpty()) {
            for (PacienteItemModel existing : pacientes) {
                if (cedula.equals(existing.getCedula())) {
                    onError.accept("Ya existe un paciente con cédula " + cedula + ": " + existing.getNombreCompleto());
                    return;
                }
            }
        }

        executeAsync("guardar paciente", () -> {
            PacienteDto dto = new PacienteDto();
            dto.setNombres(nombres);
            dto.setApellidos(apellidos);
            dto.setCedula(cedula);
            dto.setTelefono(telefono);
            dto.setFechaNacimiento(fechaNacimiento);
            dto.setDireccionBasica(direccion);

            if (pacienteId == null) {
                pacienteApi.crear(dto);
            } else {
                dto.setPacienteId(pacienteId);
                pacienteApi.actualizar(pacienteId, dto);
            }
            Platform.runLater(() -> {
                if (onSuccess != null) onSuccess.run();
            });
        }, null);
    }

    private void cargarPacientesDemo() {
        pacientes.clear();
        for (var dto : DemoDataFactory.getPacientes()) {
            pacientes.add(toItemModel(dto));
        }
        totalPacientes.set(pacientes.size());
    }

    private PacienteItemModel toItemModel(PacienteResumenDto dto) {
        PacienteItemModel item = new PacienteItemModel();
        item.setPacienteId(dto.getPacienteId());
        item.setNombres(dto.getNombres());
        item.setApellidos(dto.getApellidos());
        item.setCedula(dto.getCedula());
        item.setTelefono(dto.getTelefono());
        return item;
    }
}
