package dev.marcosmoreira.desktopconsultorio.modules.dashboard.view;

import dev.marcosmoreira.desktopconsultorio.modules.dashboard.model.ActividadRecienteModel;
import dev.marcosmoreira.desktopconsultorio.modules.dashboard.model.DashboardMetricModel;
import dev.marcosmoreira.desktopconsultorio.modules.dashboard.viewmodel.DashboardViewModel;
import java.time.format.DateTimeFormatter;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;

/**
 * Vista principal del Dashboard.
 *
 * <p>Construye programáticamente la interfaz del panel de arranque con:
 * saludo contextual, métricas rápidas con colores por tipo, próximas citas
 * del día y resumen de cobros pendientes. Todo reactivo
 * vía propiedades del {@link DashboardViewModel}.</p>
 */
public class DashboardView extends VBox {

    private final DashboardViewModel vm;

    private final HBox metricasBox = new HBox(16);
    private final ListView<ActividadRecienteModel> listaCitas = new ListView<>();

    public DashboardView(DashboardViewModel vm) {
        this.vm = vm;
        setSpacing(16);
        setPadding(new Insets(18, 20, 18, 20));

        buildLayout();
        bindProperties();
        loadInitialData();
    }

    private void buildLayout() {
        // === HEADER: Saludo + Fecha ===
        HBox header = new HBox(16);
        header.setAlignment(Pos.CENTER_LEFT);
        Label saludoLabel = new Label();
        saludoLabel.getStyleClass().add("dashboard-greeting");
        saludoLabel.textProperty().bind(vm.saludoProperty());

        Label fechaLabel = new Label();
        fechaLabel.getStyleClass().add("dashboard-date");
        vm.fechaActualProperty().addListener((obs, old, newVal) -> {
            if (newVal != null) {
                fechaLabel.setText(newVal.format(DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM 'de' yyyy")));
            }
        });

        HBox.setHgrow(saludoLabel, Priority.ALWAYS);
        header.getChildren().addAll(saludoLabel, fechaLabel);

        // === MÉTRICAS ===
        metricasBox.setAlignment(Pos.CENTER_LEFT);
        VBox metricasCard = new VBox(12, metricasBox);
        metricasCard.getStyleClass().add("panel-card");
        metricasCard.setPadding(new Insets(16));

        // === CONTENIDO PRINCIPAL: 2 columnas ===
        HBox mainContent = new HBox(16);

        // Columna 1: Próximas citas
        VBox citasPanel = buildTimelinePanel("Próximas citas (hoy)", listaCitas);

        // Columna 2: Cobros pendientes
        VBox cobrosPanel = buildCobrosPendientesPanel();

        HBox.setHgrow(citasPanel, Priority.ALWAYS);
        HBox.setHgrow(cobrosPanel, Priority.ALWAYS);
        citasPanel.setMaxWidth(Double.MAX_VALUE);
        cobrosPanel.setMaxWidth(Double.MAX_VALUE);
        mainContent.getChildren().addAll(citasPanel, cobrosPanel);

        getChildren().addAll(header, metricasCard, mainContent);
    }

    // ============================================================
    // Paneles
    // ============================================================

    private VBox buildTimelinePanel(String titulo, ListView<ActividadRecienteModel> lista) {
        VBox panel = new VBox(10);
        panel.getStyleClass().add("panel-card");
        panel.setPadding(new Insets(16));

        Label titleLabel = new Label(titulo);
        titleLabel.getStyleClass().add("panel-title");

        lista.setCellFactory(lv -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(ActividadRecienteModel item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox row = new HBox(12);
                    row.setAlignment(Pos.CENTER_LEFT);
                    row.setPadding(new Insets(8, 0, 8, 0));

                    Label icono = new Label(item.getIcono());
                    icono.setMinWidth(28);
                    icono.setAlignment(Pos.TOP_CENTER);

                    VBox info = new VBox(3);
                    Label primary = new Label(item.getDescripcion());
                    primary.getStyleClass().add("timeline-primary");
                    primary.setWrapText(true);
                    Label secondary = new Label(item.getFechaHora() + " · " + item.getUsuario() + " · " + item.getTipo());
                    secondary.getStyleClass().add("timeline-secondary");
                    info.getChildren().addAll(primary, secondary);

                    row.getChildren().addAll(icono, info);
                    setGraphic(row);
                }
            }
        });

        lista.setPrefHeight(240);
        panel.getChildren().addAll(titleLabel, lista);
        return panel;
    }

    private VBox buildCobrosPendientesPanel() {
        VBox panel = new VBox(10);
        panel.getStyleClass().add("panel-card");
        panel.setPadding(new Insets(16));
        panel.setPrefWidth(420);
        panel.setMaxWidth(Double.MAX_VALUE);

        Label title = new Label("Cobros pendientes");
        title.getStyleClass().add("panel-title");

        // Datos demo
        VBox items = new VBox(8);
        items.getChildren().addAll(
                buildCobroItem("José Villacrés", "$50.00", "Tarjeta", "11/04"),
                buildCobroItem("Ana Peñafiel", "$35.00", "Transfer.", "10/04"),
                buildCobroItem("Miguel Cevallos", "$25.00", "Efectivo", "09/04")
        );

        Separator sep = new Separator();
        HBox totalRow = new HBox(8);
        totalRow.setAlignment(Pos.CENTER_LEFT);
        Label totalLabel = new Label("Total pendiente:");
        totalLabel.getStyleClass().add("cobro-total-label");
        Label totalValue = new Label("$110.00");
        totalValue.getStyleClass().add("cobro-total-value");
        totalRow.getChildren().addAll(totalLabel, totalValue);

        panel.getChildren().addAll(title, items, sep, totalRow);
        return panel;
    }

    private HBox buildCobroItem(String paciente, String monto, String metodo, String fecha) {
        HBox row = new HBox(8);
        row.setAlignment(Pos.CENTER_LEFT);

        Label fechaLabel = new Label("📅 " + fecha);
        fechaLabel.getStyleClass().add("cobro-item-date");

        VBox info = new VBox(2);
        Label nombre = new Label(paciente);
        nombre.getStyleClass().add("cobro-item-name");
        Label detalles = new Label(metodo + " · " + fecha);
        detalles.getStyleClass().add("cobro-item-details");
        info.getChildren().addAll(nombre, detalles);

        Label montoLabel = new Label(monto);
        montoLabel.getStyleClass().add("cobro-item-amount");

        row.getChildren().addAll(fechaLabel, info, montoLabel);
        return row;
    }

    // ============================================================
    // Bindings y carga
    // ============================================================

    private void bindProperties() {
        vm.metricasProperty().addListener((ListChangeListener<DashboardMetricModel>) c -> {
            metricasBox.getChildren().clear();
            while (c.next()) {
                if (c.wasAdded()) {
                    for (DashboardMetricModel m : c.getAddedSubList()) {
                        metricasBox.getChildren().add(buildMetricCard(m));
                    }
                }
            }
        });
    }

    private VBox buildMetricCard(DashboardMetricModel metric) {
        VBox card = new VBox(8);
        card.getStyleClass().addAll("metric-card", metric.getColorClase());
        card.setPadding(new Insets(16));
        card.setPrefWidth(220);
        card.setMinWidth(180);

        HBox topRow = new HBox(8);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label icono = new Label(metric.getIcono());
        icono.getStyleClass().add("metric-icon");

        Label titulo = new Label(metric.getTitulo());
        titulo.getStyleClass().add("metric-title");

        topRow.getChildren().addAll(icono, titulo);

        Label valor = new Label(metric.getValor());
        valor.getStyleClass().add("metric-value");
        valor.textProperty().bind(metric.valorProperty());

        card.getChildren().addAll(topRow, valor);
        return card;
    }

    private void loadInitialData() {
        vm.cargarMetricas();
        vm.cargarProximasCitas();
    }
}
