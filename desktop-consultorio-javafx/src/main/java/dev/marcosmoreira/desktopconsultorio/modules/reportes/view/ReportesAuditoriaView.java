package dev.marcosmoreira.desktopconsultorio.modules.reportes.view;

import dev.marcosmoreira.desktopconsultorio.components.shared.buttons.PrimaryButton;
import dev.marcosmoreira.desktopconsultorio.components.shared.buttons.SecondaryButton;
import dev.marcosmoreira.desktopconsultorio.components.shared.feedback.StatusBadge;
import dev.marcosmoreira.desktopconsultorio.components.shared.forms.SearchField;
import dev.marcosmoreira.desktopconsultorio.components.shared.layout.Card;
import dev.marcosmoreira.desktopconsultorio.http.dto.auditoria.EventoAuditoriaDto;
import dev.marcosmoreira.desktopconsultorio.http.service.AuditoriaApiService;
import dev.marcosmoreira.desktopconsultorio.components.shared.pagination.PaginationBar;
import dev.marcosmoreira.desktopconsultorio.modules.auditoria.viewmodel.AuditoriaViewModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;

/**
 * Vista del módulo de Reportes y Auditoría conectada al backend.
 */
public class ReportesAuditoriaView extends VBox {

    private final AuditoriaViewModel auditoriaVM;
    private TableView<EventoAuditoriaDto> tablaAudit;
    private final PaginationBar paginationBar;

    public ReportesAuditoriaView(AuditoriaApiService auditoriaApi) {
        setSpacing(16);
        setPadding(new Insets(18, 20, 18, 20));

        // Sección Auditoría (inicializar primero)
        auditoriaVM = new AuditoriaViewModel(auditoriaApi);
        this.paginationBar = new PaginationBar(25, page -> auditoriaVM.cargarEventos());

        // Sección Reportes
        Card reportesCard = new Card("📊 Generar Reporte");
        reportesCard.setSpacing(16);

        HBox tipoRow = new HBox(12);
        tipoRow.setAlignment(Pos.CENTER_LEFT);
        Label lblTipo = new Label("Tipo:");
        lblTipo.setMinWidth(100);
        ComboBox<String> cbTipo = new ComboBox<>();
        cbTipo.getItems().addAll("Cobros del mes", "Atenciones por profesional", "Citas atendidas", "Pacientes nuevos");
        cbTipo.setValue("Cobros del mes");
        tipoRow.getChildren().addAll(lblTipo, cbTipo);

        HBox periodoRow = new HBox(12);
        periodoRow.setAlignment(Pos.CENTER_LEFT);
        Label lblPeriodo = new Label("Período:");
        lblPeriodo.setMinWidth(100);
        DatePicker dpDesde = new DatePicker(java.time.LocalDate.now().withDayOfMonth(1));
        DatePicker dpHasta = new DatePicker(java.time.LocalDate.now());
        periodoRow.getChildren().addAll(lblPeriodo, new Label("Desde:"), dpDesde, new Label("Hasta:"), dpHasta);

        HBox formatoRow = new HBox(12);
        formatoRow.setAlignment(Pos.CENTER_LEFT);
        Label lblFormato = new Label("Formato:");
        lblFormato.setMinWidth(100);
        ToggleGroup tgFormato = new ToggleGroup();
        RadioButton rbPdf = new RadioButton("PDF");
        RadioButton rbXlsx = new RadioButton("XLSX");
        RadioButton rbDocx = new RadioButton("DOCX");
        rbPdf.setToggleGroup(tgFormato);
        rbXlsx.setToggleGroup(tgFormato);
        rbDocx.setToggleGroup(tgFormato);
        rbPdf.setSelected(true);
        formatoRow.getChildren().addAll(lblFormato, rbPdf, rbXlsx, rbDocx);

        HBox accionesReportes = new HBox(12);
        accionesReportes.setAlignment(Pos.CENTER_RIGHT);
        PrimaryButton btnGenerar = new PrimaryButton("📥 Generar y Descargar");
        SecondaryButton btnPreview = new SecondaryButton("👁️ Previsualizar");
        accionesReportes.getChildren().addAll(btnPreview, btnGenerar);

        reportesCard.getChildren().addAll(tipoRow, periodoRow, formatoRow, accionesReportes);

        // Sección Auditoría
        Card auditoriaCard = new Card("📋 Registro de Auditoría");
        auditoriaCard.setSpacing(12);

        // Filtros
        HBox filtrosAudit = new HBox(12);
        filtrosAudit.getStyleClass().add("page-toolbar");
        filtrosAudit.setPadding(new Insets(8));
        SearchField searchAudit = new SearchField("Buscar evento...");
        searchAudit.textProperty().addListener((obs, old, newVal) -> auditoriaVM.filtroBusquedaProperty().set(newVal));
        ComboBox<String> cbModulo = new ComboBox<>();
        cbModulo.setPromptText("Módulo");
        cbModulo.getItems().addAll("Todos", "AUTH", "PACIENTES", "CITAS", "COBROS");
        cbModulo.setValue("Todos");
        cbModulo.setOnAction(e -> auditoriaVM.filtroModuloProperty().set(cbModulo.getValue()));
        filtrosAudit.getChildren().addAll(searchAudit, cbModulo);

        // Tabla
        tablaAudit = new TableView<>();

        TableColumn<EventoAuditoriaDto, java.time.LocalDateTime> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaHora"));
        colFecha.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(java.time.LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.format(DateTimeFormatter.ofPattern("dd/MM HH:mm")));
            }
        });
        colFecha.setPrefWidth(140);

        TableColumn<EventoAuditoriaDto, String> colUsuario = new TableColumn<>("Usuario");
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("username"));
        colUsuario.setPrefWidth(150);

        TableColumn<EventoAuditoriaDto, String> colModulo = new TableColumn<>("Módulo");
        colModulo.setCellValueFactory(new PropertyValueFactory<>("modulo"));
        colModulo.setPrefWidth(120);

        TableColumn<EventoAuditoriaDto, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoEvento"));
        colTipo.setPrefWidth(140);

        TableColumn<EventoAuditoriaDto, String> colDesc = new TableColumn<>("Descripción");
        colDesc.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colDesc.setPrefWidth(350);

        tablaAudit.getColumns().addAll(colFecha, colUsuario, colModulo, colTipo, colDesc);
        tablaAudit.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        auditoriaVM.eventosProperty().addListener((javafx.collections.ListChangeListener<EventoAuditoriaDto>) change ->
                tablaAudit.setItems(auditoriaVM.getEventosFiltrados()));

        auditoriaVM.filtroBusquedaProperty().addListener((obs, old, newVal) ->
                tablaAudit.setItems(auditoriaVM.getEventosFiltrados()));

        auditoriaVM.filtroModuloProperty().addListener((obs, old, newVal) ->
                tablaAudit.setItems(auditoriaVM.getEventosFiltrados()));

        // Carga inicial
        auditoriaVM.cargarEventos();

        auditoriaCard.getChildren().addAll(filtrosAudit, tablaAudit, paginationBar);

        getChildren().addAll(reportesCard, auditoriaCard);
    }

    private static class Region extends javafx.scene.layout.Region {
        public Region() { HBox.setHgrow(this, Priority.ALWAYS); }
    }
}
