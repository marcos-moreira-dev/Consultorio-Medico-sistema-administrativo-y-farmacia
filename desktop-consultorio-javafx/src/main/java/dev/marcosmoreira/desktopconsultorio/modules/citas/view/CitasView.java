package dev.marcosmoreira.desktopconsultorio.modules.citas.view;

import dev.marcosmoreira.desktopconsultorio.components.shared.buttons.PrimaryButton;
import dev.marcosmoreira.desktopconsultorio.components.shared.calendar.CalendarGrid;
import dev.marcosmoreira.desktopconsultorio.components.shared.feedback.EmptyStatePane;
import dev.marcosmoreira.desktopconsultorio.components.shared.layout.Card;
import dev.marcosmoreira.desktopconsultorio.http.dto.citas.CitaDto;
import dev.marcosmoreira.desktopconsultorio.components.shared.buttons.SecondaryButton;
import dev.marcosmoreira.desktopconsultorio.http.dto.pacientes.PacienteResumenDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.profesionales.ProfesionalResumenDto;
import dev.marcosmoreira.desktopconsultorio.modules.citas.viewmodel.CitasViewModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;

/**
 * Vista principal del módulo de Citas conectada al backend.
 *
 * <p>Layout de 2 paneles:
 * izquierda: timeline de franjas horarias del día;
 * derecha: calendario mensual interactivo.</p>
 */
public class CitasView extends VBox {

    private final CitasViewModel vm;
    private final VBox timelineContainer;
    private final CalendarGrid calendarGrid;

    public CitasView(CitasViewModel vm) {
        this.vm = vm;
        setSpacing(16);
        setPadding(new Insets(18, 20, 18, 20));

        // Toolbar
        HBox toolbar = new HBox(16);
        toolbar.getStyleClass().add("page-toolbar");
        toolbar.setPadding(new Insets(12, 16, 12, 16));

        Label fechaLabel = new Label();
        fechaLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        fechaLabel.setText(formatDate(vm.getFechaSeleccionada()));

        PrimaryButton btnNueva = new PrimaryButton("+ Nueva Cita");
        btnNueva.setOnAction(e -> mostrarFormularioNuevaCita());

        toolbar.getChildren().addAll(fechaLabel, new Region(), btnNueva);

        // 2-pane layout
        HBox mainLayout = new HBox(16);

        // Panel 1: Timeline (izquierda)
        ScrollPane timelineScroll = new ScrollPane();
        timelineContainer = new VBox(8);
        timelineContainer.setPadding(new Insets(12));
        timelineContainer.setMinWidth(340);
        timelineScroll.setContent(timelineContainer);
        timelineScroll.setFitToWidth(true);
        timelineScroll.setStyle("-fx-background-color: transparent;");

        Card timelineCard = new Card("Agenda del día");
        timelineCard.getChildren().add(timelineScroll);
        timelineCard.setPrefWidth(420);
        timelineCard.setMaxWidth(Double.MAX_VALUE);

        // Panel 2: Calendario (derecha, ocupa aprox. el doble)
        calendarGrid = new CalendarGrid(YearMonth.from(vm.getFechaSeleccionada()));
        calendarGrid.onDateSelected(date -> {
            vm.fechaSeleccionadaProperty().set(date);
            fechaLabel.setText(formatDate(date));
            vm.cargarCitasDelDia(date);
        });
        calendarGrid.selectDate(vm.getFechaSeleccionada());

        Card calendarCard = new Card("Calendario");
        calendarCard.getChildren().add(calendarGrid);
        HBox.setHgrow(calendarCard, Priority.ALWAYS);
        timelineCard.setMinWidth(360);
        calendarCard.setMinWidth(760);
        calendarCard.setPrefWidth(920);
        calendarCard.setMaxWidth(Double.MAX_VALUE);

        HBox.setHgrow(timelineCard, Priority.ALWAYS);
        HBox.setHgrow(calendarCard, Priority.ALWAYS);
        mainLayout.getChildren().addAll(timelineCard, calendarCard);

        // Binding reactivo
        vm.citasDelDiaProperty().addListener((javafx.collections.ListChangeListener<CitaDto>) change -> renderTimeline());
        vm.fechaSeleccionadaProperty().addListener((obs, old, newVal) -> {
            if (newVal != null) {
                fechaLabel.setText(formatDate(newVal));
                calendarGrid.selectDate(newVal);
            }
        });

        // Resaltar días con citas usando lo que devuelva backend/demo al ViewModel.
        calendarGrid.setDatesWithEvents(vm.getDiasConCitas());

        // Carga inicial desde backend
        vm.cargarCitasDelDia(vm.getFechaSeleccionada());
        timelineContainer.sceneProperty().addListener((obs, old, scene) -> calendarGrid.setDatesWithEvents(vm.getDiasConCitas()));

        getChildren().addAll(toolbar, mainLayout);
    }

    private void renderTimeline() {
        timelineContainer.getChildren().clear();
        calendarGrid.setDatesWithEvents(vm.getDiasConCitas());
        var citas = vm.citasDelDiaProperty();

        if (citas == null || citas.isEmpty()) {
            EmptyStatePane emptyState = new EmptyStatePane(
                    "📅",
                    "Sin citas para este día",
                    "No hay citas programadas. Use el botón 'Nueva Cita' para agendar."
            );
            timelineContainer.getChildren().add(emptyState);
            return;
        }

        // Slots de 7:00 a 17:00
        for (int h = 7; h <= 17; h++) {
            LocalTime hora = LocalTime.of(h, 0);
            CitaDto citaEncontrada = null;
            for (CitaDto c : citas) {
                if (c.getFechaHoraInicio() != null && c.getFechaHoraInicio().getHour() == h) {
                    citaEncontrada = c;
                    break;
                }
            }
            timelineContainer.getChildren().add(
                    citaEncontrada != null ? buildSlotCard(citaEncontrada) : buildEmptySlot(hora)
            );
        }
    }

    private Card buildSlotCard(CitaDto cita) {
        Card card = new Card();
        card.setPadding(new Insets(12));
        card.setSpacing(8);

        HBox topRow = new HBox(8);
        topRow.setAlignment(Pos.CENTER_LEFT);

        String hora = cita.getFechaHoraInicio() != null ?
                cita.getFechaHoraInicio().format(DateTimeFormatter.ofPattern("HH:mm")) : "--:--";
        Label timeLabel = new Label(hora);
        timeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label statusBadge = new Label(cita.getEstadoCita() != null ? cita.getEstadoCita() : "PROGRAMADA");
        statusBadge.setMinWidth(80);
        statusBadge.setAlignment(Pos.CENTER);
        statusBadge.setStyle(getBadgeStyle(cita.getEstadoCita()));

        topRow.getChildren().addAll(timeLabel, statusBadge);

        Label pacienteLabel = new Label(cita.getNombrePaciente() != null ? cita.getNombrePaciente() : "—");
        pacienteLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 600;");

        Label profLabel = new Label(cita.getNombreProfesional() != null ? cita.getNombreProfesional() : "—");
        profLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b7f8e;");

        Label motivoLabel = new Label(cita.getMotivoBreve() != null ? cita.getMotivoBreve() : "");
        motivoLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b7f8e;");
        motivoLabel.setWrapText(true);

        card.getChildren().addAll(topRow, pacienteLabel, profLabel, motivoLabel);
        card.setOnMouseClicked(e -> vm.setCitaSeleccionada(cita));

        return card;
    }

    private Card buildEmptySlot(LocalTime hora) {
        Card card = new Card();
        card.setPadding(new Insets(12));
        card.setOpacity(0.5);

        Label timeLabel = new Label(hora.format(DateTimeFormatter.ofPattern("HH:mm")));
        timeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #94a3b8;");
        Label dispLabel = new Label("Disponible");
        dispLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #94a3b8;");

        card.getChildren().addAll(timeLabel, dispLabel);
        return card;
    }

    private String getBadgeStyle(String estado) {
        if (estado == null) estado = "PROGRAMADA";
        return switch (estado.toUpperCase()) {
            case "PROGRAMADA" -> "-fx-background-color: #e6f5ef; -fx-text-fill: #1c6b4d; -fx-font-size: 11px; -fx-font-weight: 600; -fx-padding: 4 10 4 10; -fx-background-radius: 999;";
            case "ATENDIDA" -> "-fx-background-color: #eff6ff; -fx-text-fill: #1d4ed8; -fx-font-size: 11px; -fx-font-weight: 600; -fx-padding: 4 10 4 10; -fx-background-radius: 999;";
            case "CANCELADA" -> "-fx-background-color: #fef2f2; -fx-text-fill: #b34a48; -fx-font-size: 11px; -fx-font-weight: 600; -fx-padding: 4 10 4 10; -fx-background-radius: 999; -fx-opacity: 0.7;";
            default -> "-fx-background-color: #f0f3f6; -fx-text-fill: #6b7f8e; -fx-font-size: 11px; -fx-font-weight: 600; -fx-padding: 4 10 4 10; -fx-background-radius: 999;";
        };
    }

    private String formatDate(LocalDate date) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        return date.format(fmt);
    }

    private void mostrarFormularioNuevaCita() {
        Stage stage = new Stage();
        stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        stage.initStyle(javafx.stage.StageStyle.UNDECORATED);
        stage.setTitle("Nueva Cita");

        VBox form = new VBox(0);
        form.setMinWidth(460);
        form.setMaxWidth(460);
        form.getStyleClass().add("dialog-box");

        // Header
        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(20, 20, 16, 20));
        header.setStyle("-fx-border-color: transparent transparent #e2e8f0 transparent; -fx-border-width: 0 0 1 0;");
        Label iconHeader = new Label("📅");
        iconHeader.setStyle("-fx-font-size: 22px;");
        Label titulo = new Label("Nueva Cita");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        header.getChildren().addAll(iconHeader, titulo);

        // Body
        VBox body = new VBox(12);
        body.setPadding(new Insets(16, 20, 16, 20));

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);

        ComboBox<PacienteResumenDto> cbPaciente = new ComboBox<>();
        cbPaciente.setPromptText("Seleccionar paciente");
        cbPaciente.setMaxWidth(Double.MAX_VALUE);
        cbPaciente.setCellFactory(lv -> new javafx.scene.control.ListCell<>() {
            protected void updateItem(PacienteResumenDto item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNombres() + " " + item.getApellidos());
            }
        });
        cbPaciente.setButtonCell(new javafx.scene.control.ListCell<>() {
            protected void updateItem(PacienteResumenDto item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNombres() + " " + item.getApellidos());
            }
        });

        ComboBox<ProfesionalResumenDto> cbProfesional = new ComboBox<>();
        cbProfesional.setPromptText("Seleccionar profesional");
        cbProfesional.setMaxWidth(Double.MAX_VALUE);
        cbProfesional.setCellFactory(lv -> new javafx.scene.control.ListCell<>() {
            protected void updateItem(ProfesionalResumenDto item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNombres() + " " + item.getApellidos() + " - " + item.getEspecialidadBreve());
            }
        });
        cbProfesional.setButtonCell(new javafx.scene.control.ListCell<>() {
            protected void updateItem(ProfesionalResumenDto item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNombres() + " " + item.getApellidos());
            }
        });

        // Cargar datos
        Thread.ofVirtual().start(() -> {
            try {
                var pacientesResp = vm.getPacienteApi().listar(0, 200);
                var profResp = vm.getProfesionalApi().listar(0, 200);
                javafx.application.Platform.runLater(() -> {
                    if (pacientesResp != null && pacientesResp.getData() != null) {
                        cbPaciente.getItems().addAll(pacientesResp.getData());
                    }
                    if (profResp != null && profResp.getData() != null) {
                        cbProfesional.getItems().addAll(profResp.getData());
                    }
                });
            } catch (Exception e) {
                // Silencioso
            }
        });

        DatePicker dpFecha = new DatePicker(vm.getFechaSeleccionada());
        dpFecha.setMaxWidth(Double.MAX_VALUE);

        ComboBox<String> cbHora = new ComboBox<>();
        for (int h = 7; h <= 17; h++) {
            cbHora.getItems().add(String.format("%02d:00", h));
            cbHora.getItems().add(String.format("%02d:30", h));
        }
        cbHora.setPromptText("Seleccionar hora");
        cbHora.setMaxWidth(Double.MAX_VALUE);

        Label lblPaciente = new Label("Paciente");
        lblPaciente.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #475569;");
        Label lblProfesional = new Label("Profesional");
        lblProfesional.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #475569;");
        Label lblFecha = new Label("Fecha");
        lblFecha.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #475569;");
        Label lblHora = new Label("Hora");
        lblHora.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #475569;");

        grid.add(lblPaciente, 0, 0);
        grid.add(cbPaciente, 0, 1);
        GridPane.setColumnSpan(cbPaciente, 2);
        grid.add(lblProfesional, 0, 2);
        grid.add(cbProfesional, 0, 3);
        GridPane.setColumnSpan(cbProfesional, 2);
        grid.add(lblFecha, 0, 4);
        grid.add(lblHora, 1, 4);
        grid.add(dpFecha, 0, 5);
        grid.add(cbHora, 1, 5);
        GridPane.setHgrow(dpFecha, Priority.ALWAYS);
        GridPane.setHgrow(cbHora, Priority.ALWAYS);

        // Separador
        javafx.scene.control.Separator sep = new javafx.scene.control.Separator();
        sep.setPadding(new Insets(4, 0, 4, 0));

        // Motivo y observación
        Label lblMotivo = new Label("Motivo");
        lblMotivo.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #475569;");
        TextField txtMotivo = new TextField();
        txtMotivo.setPromptText("Motivo breve de la consulta");
        txtMotivo.setMaxWidth(Double.MAX_VALUE);

        Label lblObs = new Label("Observación (opcional)");
        lblObs.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #475569;");
        TextArea txtObs = new TextArea();
        txtObs.setPromptText("Notas adicionales...");
        txtObs.setMaxWidth(Double.MAX_VALUE);
        txtObs.setPrefRowCount(2);

        body.getChildren().addAll(grid, sep, lblMotivo, txtMotivo, lblObs, txtObs);

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
        PrimaryButton btnGuardar = new PrimaryButton("Crear Cita");
        footer.getChildren().addAll(lblError, btnCancelar, btnGuardar);

        btnGuardar.setOnAction(e -> {
            PacienteResumenDto paciente = cbPaciente.getValue();
            ProfesionalResumenDto profesional = cbProfesional.getValue();
            LocalDate fecha = dpFecha.getValue();
            String hora = cbHora.getValue();
            String motivo = txtMotivo.getText().trim();
            if (paciente == null || profesional == null || fecha == null || hora == null) {
                lblError.setText("Paciente, profesional, fecha y hora son obligatorios.");
                lblError.setVisible(true);
                return;
            }
            lblError.setVisible(false);
            LocalDateTime fechaHora = fecha.atTime(LocalTime.parse(hora));
            vm.crearCita(paciente.getPacienteId(), profesional.getProfesionalId(), fechaHora, motivo, () -> {
                stage.close();
                vm.cargarCitasDelDia(fecha);
            });
        });

        form.getChildren().addAll(header, body, footer);

        stage.setScene(new javafx.scene.Scene(form));
        stage.showAndWait();
    }

    private static class Region extends javafx.scene.layout.Region {
        public Region() { HBox.setHgrow(this, Priority.ALWAYS); }
    }
}
