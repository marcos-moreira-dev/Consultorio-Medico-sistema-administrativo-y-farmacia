package dev.marcosmoreira.desktopconsultorio.modules.pacientes.view;

import dev.marcosmoreira.desktopconsultorio.components.shared.buttons.PrimaryButton;
import dev.marcosmoreira.desktopconsultorio.components.shared.buttons.SecondaryButton;
import dev.marcosmoreira.desktopconsultorio.components.shared.feedback.EmptyStatePane;
import dev.marcosmoreira.desktopconsultorio.components.shared.forms.SearchField;
import dev.marcosmoreira.desktopconsultorio.components.shared.layout.Card;
import dev.marcosmoreira.desktopconsultorio.components.shared.pagination.PaginationBar;
import dev.marcosmoreira.desktopconsultorio.components.shared.layout.PageToolbar;
import dev.marcosmoreira.desktopconsultorio.modules.pacientes.model.PacienteItemModel;
import dev.marcosmoreira.desktopconsultorio.modules.pacientes.viewmodel.PacientesViewModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

public class PacientesView extends VBox {

    private final PacientesViewModel vm;
    private final TableView<PacienteItemModel> tabla;
    private final SearchField searchField;
    private final Label lblTotal;
    private final PaginationBar paginationBar;
    private final VBox detallePanel;
    private final Label lblDetalleNombre;
    private final Label lblDetalleCedula;
    private final Label lblDetalleTelefono;

    public PacientesView(PacientesViewModel vm) {
        this.vm = vm;
        setSpacing(16);
        setPadding(new Insets(18, 20, 18, 20));

        searchField = new SearchField("Buscar por nombre, cédula o teléfono...");
        lblTotal = new Label();
        lblTotal.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b7f8e;");
        paginationBar = new PaginationBar(10, page -> vm.cargarPacientes());

        tabla = new TableView<>();
        configurarTabla();

        Card detalleCard = new Card("Detalle del paciente");
        detalleCard.setSpacing(12);
        lblDetalleNombre = new Label("Seleccione un paciente");
        lblDetalleNombre.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        lblDetalleCedula = new Label();
        lblDetalleTelefono = new Label();
        detallePanel = new VBox(12, lblDetalleNombre, lblDetalleCedula, lblDetalleTelefono);
        detallePanel.setAlignment(Pos.CENTER_LEFT);
        detalleCard.getChildren().add(detallePanel);

        PageToolbar toolbar = new PageToolbar();
        toolbar.getChildren().add(searchField);

        PrimaryButton btnNuevo = new PrimaryButton("+ Nuevo Paciente");
        btnNuevo.setOnAction(e -> mostrarFormularioPaciente(null));
        toolbar.getChildren().add(btnNuevo);

        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPositions(0.55);

        VBox leftPanel = new VBox(12, toolbar, lblTotal, tabla, paginationBar);
        leftPanel.setPadding(new Insets(0, 12, 0, 0));

        VBox rightPanel = new VBox(12, detalleCard);
        SecondaryButton btnEditar = new SecondaryButton("✏️ Editar");
        btnEditar.setOnAction(e -> {
            if (vm.getSelectedItem() != null) {
                mostrarFormularioPaciente(vm.getSelectedItem());
            }
        });
        rightPanel.getChildren().add(btnEditar);

        splitPane.getItems().addAll(leftPanel, rightPanel);

        bindProperties();
        getChildren().add(splitPane);
    }

    private void configurarTabla() {
        TableColumn<PacienteItemModel, String> colNombre = new TableColumn<>("Nombre completo");
        colNombre.setCellValueFactory(cellData ->
                cellData.getValue().nombresProperty().concat(" ").concat(cellData.getValue().apellidosProperty()));
        colNombre.setPrefWidth(220);

        TableColumn<PacienteItemModel, String> colCedula = new TableColumn<>("Cédula");
        colCedula.setCellValueFactory(cellData -> cellData.getValue().cedulaProperty());
        colCedula.setPrefWidth(120);

        TableColumn<PacienteItemModel, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(cellData -> cellData.getValue().telefonoProperty());
        colTelefono.setPrefWidth(120);

        TableColumn<PacienteItemModel, Number> colCitas = new TableColumn<>("Citas");
        colCitas.setCellValueFactory(cellData -> cellData.getValue().totalCitasProperty());
        colCitas.setPrefWidth(60);
        colCitas.setStyle("-fx-alignment: center;");

        tabla.getColumns().addAll(colNombre, colCedula, colTelefono, colCitas);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabla.setPlaceholder(new EmptyStatePane("👥", "No se encontraron pacientes", "Intente ajustar los filtros de búsqueda.", "+ Nuevo Paciente", () -> mostrarFormularioPaciente(null)));

        tabla.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            if (newVal != null) {
                vm.setSelectedItem(newVal);
                vm.cargarDetalle(newVal.getPacienteId());
            }
        });
    }

    private void mostrarFormularioPaciente(PacienteItemModel paciente) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle(paciente == null ? "Nuevo Paciente" : "Editar Paciente");

        VBox form = new VBox(0);
        form.setMinWidth(460);
        form.setMaxWidth(460);
        form.getStyleClass().add("dialog-box");

        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(20, 20, 16, 20));
        header.setStyle("-fx-border-color: transparent transparent #e2e8f0 transparent; -fx-border-width: 0 0 1 0;");
        Label iconHeader = new Label("👤");
        iconHeader.setStyle("-fx-font-size: 22px;");
        Label titulo = new Label(paciente == null ? "Nuevo Paciente" : "Editar Paciente");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        header.getChildren().addAll(iconHeader, titulo);

        VBox body = new VBox(12);
        body.setPadding(new Insets(16, 20, 16, 20));

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);

        TextField txtNombres = new TextField();
        txtNombres.setPromptText("Nombres");
        txtNombres.setMaxWidth(Double.MAX_VALUE);

        TextField txtApellidos = new TextField();
        txtApellidos.setPromptText("Apellidos");
        txtApellidos.setMaxWidth(Double.MAX_VALUE);

        TextField txtCedula = new TextField();
        txtCedula.setPromptText("Cédula (opcional)");
        txtCedula.setMaxWidth(Double.MAX_VALUE);

        TextField txtTelefono = new TextField();
        txtTelefono.setPromptText("Teléfono (opcional)");
        txtTelefono.setMaxWidth(Double.MAX_VALUE);

        DatePicker dpFecha = new DatePicker();
        dpFecha.setPromptText("Fecha de nacimiento");
        dpFecha.setMaxWidth(Double.MAX_VALUE);

        TextField txtDireccion = new TextField();
        txtDireccion.setPromptText("Dirección básica (opcional)");
        txtDireccion.setMaxWidth(Double.MAX_VALUE);

        Label lblNombres = new Label("Nombres");
        lblNombres.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #475569;");
        Label lblApellidos = new Label("Apellidos");
        lblApellidos.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #475569;");
        Label lblCedula = new Label("Cédula");
        lblCedula.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #475569;");
        Label lblTelefono = new Label("Teléfono");
        lblTelefono.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #475569;");
        Label lblFecha = new Label("Fecha nacimiento");
        lblFecha.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #475569;");
        Label lblDireccion = new Label("Dirección");
        lblDireccion.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #475569;");

        grid.add(lblNombres, 0, 0);
        grid.add(lblApellidos, 1, 0);
        grid.add(txtNombres, 0, 1);
        grid.add(txtApellidos, 1, 1);
        GridPane.setHgrow(txtNombres, Priority.ALWAYS);
        GridPane.setHgrow(txtApellidos, Priority.ALWAYS);
        grid.add(lblCedula, 0, 2);
        grid.add(lblTelefono, 1, 2);
        grid.add(txtCedula, 0, 3);
        grid.add(txtTelefono, 1, 3);
        GridPane.setHgrow(txtCedula, Priority.ALWAYS);
        GridPane.setHgrow(txtTelefono, Priority.ALWAYS);
        grid.add(lblFecha, 0, 4);
        grid.add(lblDireccion, 1, 4);
        grid.add(dpFecha, 0, 5);
        grid.add(txtDireccion, 1, 5);
        GridPane.setHgrow(dpFecha, Priority.ALWAYS);
        GridPane.setHgrow(txtDireccion, Priority.ALWAYS);

        if (paciente != null) {
            txtNombres.setText(paciente.getNombres());
            txtApellidos.setText(paciente.getApellidos());
            txtCedula.setText(paciente.getCedula());
            txtTelefono.setText(paciente.getTelefono());
        }

        body.getChildren().add(grid);

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
        PrimaryButton btnGuardar = new PrimaryButton(paciente == null ? "Crear" : "Guardar");
        footer.getChildren().addAll(lblError, btnCancelar, btnGuardar);

        btnGuardar.setOnAction(e -> {
            String nombres = txtNombres.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            if (nombres.isEmpty() || apellidos.isEmpty()) {
                lblError.setText("Nombres y apellidos son obligatorios.");
                lblError.setVisible(true);
                return;
            }
            lblError.setVisible(false);
            vm.guardarPaciente(
                    paciente != null ? paciente.getPacienteId() : null,
                    nombres,
                    apellidos,
                    txtCedula.getText().trim(),
                    txtTelefono.getText().trim(),
                    dpFecha.getValue(),
                    txtDireccion.getText().trim(),
                    () -> {
                        stage.close();
                        vm.cargarPacientes();
                    },
                    (msg) -> {
                        lblError.setText(msg);
                        lblError.setVisible(true);
                    }
            );
        });

        form.getChildren().addAll(header, body, footer);
        stage.setScene(new javafx.scene.Scene(form));
        stage.showAndWait();
    }

    private void bindProperties() {
        searchField.onSearch(q -> vm.buscarPacientes(q));
        searchField.textProperty().addListener((obs, old, newVal) -> vm.buscarPacientes(newVal));

        vm.totalPacientesProperty().addListener((obs, old, val) ->
                lblTotal.setText("Total: " + val + " pacientes"));

        vm.pacienteDetalleProperty().addListener((obs, old, dto) -> {
            if (dto != null) {
                lblDetalleNombre.setText(dto.getNombreCompleto());
                lblDetalleCedula.setText("Cédula: " + (dto.getCedula() != null ? dto.getCedula() : "—"));
                lblDetalleTelefono.setText("Teléfono: " + (dto.getTelefono() != null ? dto.getTelefono() : "—"));
            }
        });

        vm.cargarPacientes();
    }
}
