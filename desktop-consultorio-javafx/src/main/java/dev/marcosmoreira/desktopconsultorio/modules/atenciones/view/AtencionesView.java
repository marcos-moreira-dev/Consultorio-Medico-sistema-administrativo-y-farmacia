package dev.marcosmoreira.desktopconsultorio.modules.atenciones.view;

import dev.marcosmoreira.desktopconsultorio.components.shared.buttons.PrimaryButton;
import dev.marcosmoreira.desktopconsultorio.components.shared.buttons.SecondaryButton;
import dev.marcosmoreira.desktopconsultorio.components.shared.feedback.EmptyStatePane;
import dev.marcosmoreira.desktopconsultorio.components.shared.forms.SearchField;
import dev.marcosmoreira.desktopconsultorio.components.shared.layout.PageToolbar;
import dev.marcosmoreira.desktopconsultorio.http.dto.atenciones.AtencionDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.pacientes.PacienteResumenDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.profesionales.ProfesionalResumenDto;
import dev.marcosmoreira.desktopconsultorio.modules.atenciones.viewmodel.AtencionesViewModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AtencionesView extends VBox {

    private final AtencionesViewModel vm;
    private final TableView<AtencionDto> tabla;
    private final SearchField searchField;
    private final Label lblTotal;
    private List<AtencionDto> todasAtenciones = new ArrayList<>();

    public AtencionesView(AtencionesViewModel vm) {
        this.vm = vm;
        setSpacing(16);
        setPadding(new Insets(18, 20, 18, 20));

        searchField = new SearchField("Buscar por paciente o profesional...");
        lblTotal = new Label();
        lblTotal.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b7f8e;");

        tabla = new TableView<>();
        configurarTabla();

        PageToolbar toolbar = new PageToolbar();
        toolbar.getChildren().add(searchField);

        PrimaryButton btnNueva = new PrimaryButton("+ Registrar Atención");
        btnNueva.setOnAction(e -> mostrarFormularioAtencion());
        toolbar.getChildren().add(btnNueva);

        searchField.textProperty().addListener((obs, old, newVal) -> filtrarAtenciones(newVal));

        vm.totalAtencionesProperty().addListener((obs, old, val) ->
                lblTotal.setText("Total: " + val + " atenciones registradas"));

        vm.atencionesProperty().addListener((javafx.collections.ListChangeListener<AtencionDto>) change -> {
            todasAtenciones = new ArrayList<>(vm.atencionesProperty());
            tabla.setItems(vm.atencionesProperty());
        });

        vm.cargarAtenciones();
        vm.cargarCatalogos();

        getChildren().addAll(toolbar, lblTotal, tabla);
    }

    private void configurarTabla() {
        TableColumn<AtencionDto, String> colPaciente = new TableColumn<>("Paciente");
        colPaciente.setCellValueFactory(new PropertyValueFactory<>("nombrePaciente"));
        colPaciente.setPrefWidth(200);

        TableColumn<AtencionDto, String> colProfesional = new TableColumn<>("Profesional");
        colProfesional.setCellValueFactory(new PropertyValueFactory<>("nombreProfesional"));
        colProfesional.setPrefWidth(180);

        TableColumn<AtencionDto, LocalDateTime> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaHoraAtencion"));
        colFecha.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.format(DateTimeFormatter.ofPattern("dd/MM HH:mm")));
            }
        });
        colFecha.setPrefWidth(140);

        TableColumn<AtencionDto, String> colNota = new TableColumn<>("Nota breve");
        colNota.setCellValueFactory(new PropertyValueFactory<>("notaBreve"));
        colNota.setPrefWidth(300);

        tabla.getColumns().addAll(colPaciente, colProfesional, colFecha, colNota);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabla.setPlaceholder(new EmptyStatePane("📋", "Sin atenciones", "No hay atenciones registradas aún."));
    }

    private void mostrarFormularioAtencion() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Registrar Atención");

        VBox form = new VBox(0);
        form.setMinWidth(500);
        form.setMaxWidth(500);
        form.getStyleClass().add("dialog-box");

        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(20, 20, 16, 20));
        header.setStyle("-fx-border-color: transparent transparent #e2e8f0 transparent; -fx-border-width: 0 0 1 0;");
        Label iconHeader = new Label("🩺");
        iconHeader.setStyle("-fx-font-size: 22px;");
        Label titulo = new Label("Registrar Atención");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        header.getChildren().addAll(iconHeader, titulo);

        VBox body = new VBox(12);
        body.setPadding(new Insets(16, 20, 16, 20));

        ComboBox<PacienteResumenDto> cbPaciente = new ComboBox<>();
        cbPaciente.setPromptText("Seleccione paciente");
        cbPaciente.setMaxWidth(Double.MAX_VALUE);
        cbPaciente.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(PacienteResumenDto item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : item.getNombreCompleto());
            }
        });
        cbPaciente.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(PacienteResumenDto item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : item.getNombreCompleto());
            }
        });

        ComboBox<ProfesionalResumenDto> cbProfesional = new ComboBox<>();
        cbProfesional.setPromptText("Seleccione profesional");
        cbProfesional.setMaxWidth(Double.MAX_VALUE);
        cbProfesional.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(ProfesionalResumenDto item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : item.getNombreCompleto() + " - " + item.getEspecialidadBreve());
            }
        });
        cbProfesional.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(ProfesionalResumenDto item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : item.getNombreCompleto());
            }
        });

        cbPaciente.setItems(vm.pacientesProperty());
        cbProfesional.setItems(vm.profesionalesProperty());

        Label lblPaciente = new Label("Paciente");
        lblPaciente.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #475569;");
        Label lblProfesional = new Label("Profesional");
        lblProfesional.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #475569;");
        Label lblNota = new Label("Nota breve");
        lblNota.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #475569;");
        Label lblIndicaciones = new Label("Indicaciones (opcional)");
        lblIndicaciones.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #475569;");

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);
        grid.add(lblPaciente, 0, 0);
        grid.add(cbPaciente, 0, 1);
        GridPane.setColumnSpan(cbPaciente, 2);
        GridPane.setHgrow(cbPaciente, Priority.ALWAYS);
        grid.add(lblProfesional, 0, 2);
        grid.add(cbProfesional, 0, 3);
        GridPane.setColumnSpan(cbProfesional, 2);
        GridPane.setHgrow(cbProfesional, Priority.ALWAYS);

        TextArea txtNota = new TextArea();
        txtNota.setPromptText("Nota breve de la atención (obligatoria)");
        txtNota.setMaxWidth(Double.MAX_VALUE);
        txtNota.setPrefRowCount(3);

        TextArea txtIndicaciones = new TextArea();
        txtIndicaciones.setPromptText("Indicaciones breves (opcional)");
        txtIndicaciones.setMaxWidth(Double.MAX_VALUE);
        txtIndicaciones.setPrefRowCount(2);

        body.getChildren().addAll(grid, lblNota, txtNota, lblIndicaciones, txtIndicaciones);

        HBox footer = new HBox(10);
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setPadding(new Insets(12, 20, 16, 20));
        footer.setStyle("-fx-border-color: #e2e8f0 transparent transparent transparent; -fx-border-width: 1 0 0 0;");
        Label lblError = new Label();
        lblError.setStyle("-fx-text-fill: #dc2626; -fx-font-size: 12px;");
        lblError.setVisible(false);
        HBox.setHgrow(lblError, Priority.ALWAYS);
        SecondaryButton btnCancelar = new SecondaryButton("Cancelar");
        btnCancelar.setOnAction(e -> stage.close());
        PrimaryButton btnGuardar = new PrimaryButton("Guardar");
        footer.getChildren().addAll(lblError, btnCancelar, btnGuardar);

        btnGuardar.setOnAction(e -> {
            PacienteResumenDto paciente = cbPaciente.getValue();
            ProfesionalResumenDto profesional = cbProfesional.getValue();
            String nota = txtNota.getText().trim();
            if (paciente == null || profesional == null || nota.isEmpty()) {
                lblError.setText("Paciente, profesional y nota son obligatorios.");
                lblError.setVisible(true);
                return;
            }
            lblError.setVisible(false);
            vm.setPacienteSeleccionadoId(paciente.getPacienteId());
            vm.setProfesionalSeleccionadoId(profesional.getProfesionalId());
            vm.notaBreveProperty().set(nota);
            vm.indicacionesProperty().set(txtIndicaciones.getText().trim());
            vm.crearAtencion(() -> {
                stage.close();
                showCobroSuggestion();
            });
        });

        form.getChildren().addAll(header, body, footer);
        stage.setScene(new javafx.scene.Scene(form));
        stage.showAndWait();
    }

    private void showCobroSuggestion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Atención registrada");
        alert.setHeaderText("¿Desea registrar el cobro ahora?");
        alert.setContentText("La atención fue registrada correctamente. Puede registrar el cobro correspondiente.");
        ButtonType btnCobro = new ButtonType("💰 Ir a Cobros");
        ButtonType btnCancelar = new ButtonType("No, gracias", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(btnCobro, btnCancelar);
        alert.showAndWait().ifPresent(response -> {
            if (response == btnCobro) {
                dev.marcosmoreira.desktopconsultorio.shell.controller.MainShellController.navigateToCobros();
            }
        });
    }

    private void filtrarAtenciones(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            tabla.setItems(vm.atencionesProperty());
            return;
        }
        String filtro = normalize(texto);
        List<AtencionDto> filtradas = todasAtenciones.stream()
                .filter(a -> {
                    String paciente = normalize(a.getNombrePaciente() != null ? a.getNombrePaciente() : "");
                    String profesional = normalize(a.getNombreProfesional() != null ? a.getNombreProfesional() : "");
                    return paciente.contains(filtro) || profesional.contains(filtro);
                })
                .toList();
        tabla.getItems().setAll(filtradas);
    }

    private String normalize(String value) {
        String safeValue = value == null ? "" : value;
        String normalized = java.text.Normalizer.normalize(safeValue, java.text.Normalizer.Form.NFD);
        String withoutMarks = normalized.replaceAll("\\p{M}+", "");
        return withoutMarks.replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit}]+", "").toLowerCase(java.util.Locale.ROOT);
    }
}
