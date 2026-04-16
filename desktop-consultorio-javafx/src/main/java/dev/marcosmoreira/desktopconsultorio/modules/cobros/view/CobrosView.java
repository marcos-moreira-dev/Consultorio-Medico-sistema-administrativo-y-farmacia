package dev.marcosmoreira.desktopconsultorio.modules.cobros.view;

import dev.marcosmoreira.desktopconsultorio.components.shared.buttons.PrimaryButton;
import dev.marcosmoreira.desktopconsultorio.components.shared.buttons.SecondaryButton;
import dev.marcosmoreira.desktopconsultorio.components.shared.layout.Card;
import dev.marcosmoreira.desktopconsultorio.components.shared.layout.MetricCard;
import dev.marcosmoreira.desktopconsultorio.http.dto.atenciones.AtencionDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.cobros.CobroDto;
import dev.marcosmoreira.desktopconsultorio.components.shared.pagination.PaginationBar;
import dev.marcosmoreira.desktopconsultorio.modules.cobros.viewmodel.CobrosViewModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.time.format.DateTimeFormatter;

/**
 * Vista del módulo de Cobros conectada al backend.
 */
public class CobrosView extends VBox {

    private final CobrosViewModel vm;
    private final TableView<CobroDto> tabla;
    private final PaginationBar paginationBar;

    public CobrosView(CobrosViewModel vm) {
        this.vm = vm;
        this.paginationBar = new PaginationBar(15, page -> vm.cargarCobros());
        setSpacing(16);
        setPadding(new Insets(18, 20, 18, 20));

        // Métricas
        HBox metricsRow = new HBox(16);
        MetricCard mcPagado = new MetricCard("💰", "Total pagado hoy", "$0.00", "cobros");
        MetricCard mcPendiente = new MetricCard("⏳", "Pendiente", "$0.00", "citas");
        MetricCard mcPromedio = new MetricCard("📊", "Promedio/cita", "$0.00", "pacientes");
        metricsRow.getChildren().addAll(mcPagado, mcPendiente, mcPromedio);

        // Toolbar
        HBox toolbar = new HBox(16);
        toolbar.getStyleClass().add("page-toolbar");
        toolbar.setPadding(new Insets(12, 16, 12, 16));

        Label lblInfo = new Label("Todos los cobros");
        lblInfo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        PrimaryButton btnNuevo = new PrimaryButton("+ Nuevo Cobro");
        btnNuevo.setOnAction(e -> mostrarFormularioNuevoCobro());

        toolbar.getChildren().addAll(lblInfo, new Region(), btnNuevo);

        // Tabla
        tabla = new TableView<>();
        configurarTabla();

        // Bindings
        vm.cobrosProperty().addListener((javafx.collections.ListChangeListener<CobroDto>) change -> {
            tabla.setItems(vm.cobrosProperty());
        });

        vm.totalPagadoProperty().addListener((obs, old, val) -> updateMetricDisplay(mcPagado, val.doubleValue()));
        vm.totalPendienteProperty().addListener((obs, old, val) -> updateMetricDisplay(mcPendiente, val.doubleValue()));
        vm.promedioCobroProperty().addListener((obs, old, val) -> updateMetricDisplay(mcPromedio, val.doubleValue()));

        // Color rows by status
        tabla.setRowFactory(tv -> new TableRow<CobroDto>() {
            @Override
            protected void updateItem(CobroDto item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else if ("PENDIENTE".equals(item.getEstadoCobro())) {
                    setStyle("-fx-background-color: #fffbeb;");
                } else {
                    setStyle("");
                }
            }
        });

        // Carga inicial
        vm.cargarCobros();

        getChildren().addAll(metricsRow, toolbar, tabla, paginationBar);
    }

    private void updateMetricDisplay(MetricCard card, double value) {
        for (javafx.scene.Node n : card.getChildren()) {
            if (n instanceof Label label && label.getStyleClass().contains("metric-value")) {
                label.setText(String.format("$%.2f", value));
                break;
            }
        }
    }

    private void configurarTabla() {
        TableColumn<CobroDto, java.time.LocalDateTime> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaHoraRegistro"));
        colFecha.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(java.time.LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.format(DateTimeFormatter.ofPattern("dd/MM HH:mm")));
            }
        });
        colFecha.setPrefWidth(120);

        TableColumn<CobroDto, String> colPaciente = new TableColumn<>("Paciente");
        colPaciente.setCellValueFactory(new PropertyValueFactory<>("nombrePaciente"));
        colPaciente.setPrefWidth(180);

        TableColumn<CobroDto, Number> colMonto = new TableColumn<>("Monto");
        colMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));
        colMonto.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : String.format("$%.2f", item.doubleValue()));
            }
        });
        colMonto.setPrefWidth(90);

        TableColumn<CobroDto, String> colMetodo = new TableColumn<>("Método");
        colMetodo.setCellValueFactory(new PropertyValueFactory<>("metodoPago"));
        colMetodo.setPrefWidth(120);

        TableColumn<CobroDto, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estadoCobro"));
        colEstado.setPrefWidth(110);

        tabla.getColumns().addAll(colFecha, colPaciente, colMonto, colMetodo, colEstado);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private static class Region extends javafx.scene.layout.Region {
        public Region() { HBox.setHgrow(this, Priority.ALWAYS); }
    }

    private void mostrarFormularioNuevoCobro() {
        javafx.stage.Stage stage = new javafx.stage.Stage();
        stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        stage.initStyle(javafx.stage.StageStyle.UNDECORATED);
        stage.setTitle("Nuevo Cobro");

        VBox form = new VBox(0);
        form.setMinWidth(440);
        form.setMaxWidth(440);
        form.getStyleClass().add("dialog-box");

        // Header
        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(20, 20, 16, 20));
        header.setStyle("-fx-border-color: transparent transparent #e2e8f0 transparent; -fx-border-width: 0 0 1 0;");
        Label iconHeader = new Label("💰");
        iconHeader.setStyle("-fx-font-size: 22px;");
        Label titulo = new Label("Registrar Cobro");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        header.getChildren().addAll(iconHeader, titulo);

        // Body
        VBox body = new VBox(12);
        body.setPadding(new Insets(16, 20, 16, 20));

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);

        ComboBox<AtencionDto> cbAtencion = new ComboBox<>();
        cbAtencion.setPromptText("Seleccione la atención pendiente de cobro");
        cbAtencion.setMaxWidth(Double.MAX_VALUE);
        cbAtencion.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(AtencionDto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    String fecha = item.getFechaHoraAtencion() != null
                            ? item.getFechaHoraAtencion().format(DateTimeFormatter.ofPattern("dd/MM HH:mm"))
                            : "Sin fecha";
                    setText(item.getNombrePaciente() + " · " + item.getNombreProfesional() + " · " + fecha);
                }
            }
        });
        cbAtencion.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(AtencionDto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    setText(item.getNombrePaciente() + " · " + item.getNombreProfesional());
                }
            }
        });
        cbAtencion.setItems(vm.atencionesDisponiblesProperty());
        vm.cargarAtencionesDisponibles();

        TextField txtMonto = new TextField();
        txtMonto.setPromptText("Ej: 35.00");
        txtMonto.setMaxWidth(Double.MAX_VALUE);

        ComboBox<String> cbMetodo = new ComboBox<>();
        cbMetodo.getItems().addAll("EFECTIVO", "TARJETA", "TRANSFERENCIA");
        cbMetodo.setPromptText("Seleccionar método");
        cbMetodo.setMaxWidth(Double.MAX_VALUE);

        ComboBox<String> cbEstado = new ComboBox<>();
        cbEstado.getItems().addAll("PAGADO", "PENDIENTE");
        cbEstado.setValue("PAGADO");
        cbEstado.setMaxWidth(Double.MAX_VALUE);

        Label lblAtencion = new Label("Atención");
        lblAtencion.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #475569;");
        Label lblMonto = new Label("Monto ($)");
        lblMonto.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #475569;");
        Label lblMetodo = new Label("Método de pago");
        lblMetodo.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #475569;");
        Label lblEstado = new Label("Estado");
        lblEstado.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #475569;");

        grid.add(lblAtencion, 0, 0);
        grid.add(lblMonto, 1, 0);
        grid.add(cbAtencion, 0, 1);
        grid.add(txtMonto, 1, 1);
        GridPane.setHgrow(cbAtencion, Priority.ALWAYS);
        GridPane.setHgrow(txtMonto, Priority.ALWAYS);
        grid.add(lblMetodo, 0, 2);
        grid.add(lblEstado, 1, 2);
        grid.add(cbMetodo, 0, 3);
        grid.add(cbEstado, 1, 3);
        GridPane.setHgrow(cbMetodo, Priority.ALWAYS);
        GridPane.setHgrow(cbEstado, Priority.ALWAYS);

        // Separador
        javafx.scene.control.Separator sep = new javafx.scene.control.Separator();
        sep.setPadding(new Insets(4, 0, 4, 0));

        Label lblObs = new Label("Observación (opcional)");
        lblObs.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #475569;");
        TextArea txtObs = new TextArea();
        txtObs.setPromptText("Notas administrativas...");
        txtObs.setMaxWidth(Double.MAX_VALUE);
        txtObs.setPrefRowCount(2);

        body.getChildren().addAll(grid, sep, lblObs, txtObs);

        // Footer
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
        PrimaryButton btnGuardar = new PrimaryButton("Registrar");
        footer.getChildren().addAll(lblError, btnCancelar, btnGuardar);

        btnGuardar.setOnAction(e -> {
            AtencionDto atencion = cbAtencion.getValue();
            String montoStr = txtMonto.getText().trim();
            String metodo = cbMetodo.getValue();
            String estado = cbEstado.getValue();
            if (atencion == null || montoStr.isEmpty() || metodo == null) {
                lblError.setText("Atención, monto y método son obligatorios.");
                lblError.setVisible(true);
                return;
            }
            lblError.setVisible(false);
            try {
                java.math.BigDecimal monto = new java.math.BigDecimal(montoStr);
                vm.crearCobro(atencion.getAtencionId(), monto, metodo, estado, txtObs.getText().trim(), () -> {
                    stage.close();
                    vm.cargarCobros();
                });
            } catch (NumberFormatException ex) {
                lblError.setText("El monto debe ser un número válido.");
                lblError.setVisible(true);
            }
        });

        form.getChildren().addAll(header, body, footer);

        stage.setScene(new javafx.scene.Scene(form));
        stage.showAndWait();
    }

    private VBox crearCampo(String label, Control control) {
        Label lbl = new Label(label);
        lbl.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");
        VBox box = new VBox(4, lbl, control);
        return box;
    }
}
