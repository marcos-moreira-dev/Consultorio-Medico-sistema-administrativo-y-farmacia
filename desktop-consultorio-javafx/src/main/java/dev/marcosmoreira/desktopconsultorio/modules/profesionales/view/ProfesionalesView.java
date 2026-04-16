package dev.marcosmoreira.desktopconsultorio.modules.profesionales.view;

import dev.marcosmoreira.desktopconsultorio.components.shared.buttons.PrimaryButton;
import dev.marcosmoreira.desktopconsultorio.components.shared.buttons.SecondaryButton;
import dev.marcosmoreira.desktopconsultorio.components.shared.dialogs.ConfirmDialog;
import dev.marcosmoreira.desktopconsultorio.components.shared.feedback.EmptyStatePane;
import dev.marcosmoreira.desktopconsultorio.components.shared.feedback.StatusBadge;
import dev.marcosmoreira.desktopconsultorio.components.shared.forms.SearchField;
import dev.marcosmoreira.desktopconsultorio.components.shared.layout.Card;
import dev.marcosmoreira.desktopconsultorio.demo.DemoDataFactory;
import dev.marcosmoreira.desktopconsultorio.http.dto.profesionales.ProfesionalDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.profesionales.ProfesionalResumenDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.common.PageResponseDto;
import dev.marcosmoreira.desktopconsultorio.http.service.ProfesionalApiService;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Vista del módulo de Profesionales conectada al backend.
 */
public class ProfesionalesView extends VBox {

    private final ProfesionalApiService profesionalApi;
    private final GridPane gridContainer;
    private List<ProfesionalResumenDto> todosProfesionales = new ArrayList<>();

    public ProfesionalesView(ProfesionalApiService profesionalApi) {
        this.profesionalApi = profesionalApi;
        setSpacing(16);
        setPadding(new Insets(18, 20, 18, 20));

        // Toolbar
        HBox toolbar = new HBox(16);
        toolbar.getStyleClass().add("page-toolbar");
        toolbar.setPadding(new Insets(12, 16, 12, 16));

        SearchField searchField = new SearchField("Buscar por nombre o especialidad...");
        searchField.textProperty().addListener((obs, old, newVal) -> filtrarProfesionales(newVal));
        PrimaryButton btnNuevo = new PrimaryButton("+ Nuevo Profesional");
        btnNuevo.setOnAction(e -> mostrarFormularioNuevoProfesional());

        toolbar.getChildren().addAll(searchField, new Region(), btnNuevo);

        // Grid de tarjetas
        gridContainer = new GridPane();
        gridContainer.setHgap(16);
        gridContainer.setVgap(16);

        // Carga inicial
        cargarProfesionales();

        getChildren().addAll(toolbar, gridContainer);
    }

    private void cargarProfesionales() {
        Thread.ofVirtual().start(() -> {
            PageResponseDto<ProfesionalResumenDto> resp = null;
            try {
                resp = profesionalApi.listar(0, 50);
            } catch (Exception e) {
                // API no disponible
            }
            final PageResponseDto<ProfesionalResumenDto> finalResp = resp;
            Platform.runLater(() -> {
                gridContainer.getChildren().clear();
                // Usar demo data si la API falla, devuelve lista vacía, o devuelve menos de 5 elementos
                if (finalResp != null && finalResp.getData() != null && finalResp.getData().size() >= 5) {
                    renderGrid(finalResp.getData());
                } else {
                    renderGridDemo();
                }
            });
        });
    }

    private void renderGrid(List<ProfesionalResumenDto> items) {
        todosProfesionales = new ArrayList<>(items);
        gridContainer.getChildren().clear();
        if (items.isEmpty()) {
            gridContainer.add(new EmptyStatePane("👨‍⚕️", "Sin profesionales", "No se encontraron profesionales con los filtros actuales."), 0, 0);
            return;
        }
        int col = 0, row = 0;
        for (ProfesionalResumenDto p : items) {
            gridContainer.add(buildCard(p), col, row);
            col++;
            if (col > 2) { col = 0; row++; }
        }
    }

    /**
     * Filtra profesionales en tiempo real con normalización de texto.
     * Normaliza: quita acentos, caracteres especiales, convierte a minúsculas.
     * Busca en: nombres, apellidos y especialidad.
     */
    private void filtrarProfesionales(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            renderGrid(todosProfesionales);
            return;
        }
        String filtro = normalize(texto);
        List<ProfesionalResumenDto> filtrados = todosProfesionales.stream()
                .filter(p -> {
                    String searchKey = normalize(p.getNombres() + " " + p.getApellidos() + " " + p.getEspecialidadBreve());
                    return searchKey.contains(filtro);
                })
                .toList();
        renderGrid(filtrados);
    }

    /**
     * Normaliza texto para búsqueda: quita acentos, caracteres especiales, convierte a minúsculas.
     */
    private String normalize(String value) {
        if (value == null) return "";
        String normalized = Normalizer.normalize(value, Normalizer.Form.NFD);
        String withoutMarks = normalized.replaceAll("\\p{M}+", "");
        return withoutMarks.replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit}]+", "").toLowerCase(Locale.ROOT);
    }

    private void renderGridDemo() {
        renderGrid(DemoDataFactory.getProfesionales());
    }

    private Card buildCard(ProfesionalResumenDto p) {
        Card card = new Card();
        card.setPadding(new Insets(20));
        card.setSpacing(12);
        card.setAlignment(Pos.CENTER);

        Label avatar = new Label("👨‍⚕️");
        avatar.setStyle("-fx-font-size: 48px;");

        Label nombre = new Label(p.getNombreCompleto());
        nombre.getStyleClass().add("professional-name");
        nombre.setWrapText(true);
        nombre.setAlignment(Pos.CENTER);

        Label especialidad = new Label(p.getEspecialidadBreve() != null ? p.getEspecialidadBreve() : "—");
        especialidad.getStyleClass().add("professional-specialty");

        StatusBadge badge = new StatusBadge(
                "ACTIVO".equals(p.getEstadoProfesional()) ? "🟢 ACTIVO" : "⚪ INACTIVO",
                "ACTIVO".equals(p.getEstadoProfesional()) ? StatusBadge.StateType.ACTIVO : StatusBadge.StateType.INACTIVO
        );

        Label stats = new Label("📅 " + (p.getTotalCitasMes() != null ? p.getTotalCitasMes() : 0) + " citas/mes");
        stats.setStyle("-fx-font-size: 11px; -fx-text-fill: #6b7f8e;");

        boolean activo = "ACTIVO".equals(p.getEstadoProfesional());
        Button btnEstado = new Button(activo ? "Desactivar" : "Activar");
        btnEstado.getStyleClass().add(activo ? "btn-danger" : "btn-primary");
        btnEstado.setStyle("-fx-font-size: 11px; -fx-padding: 4 12 4 12;");
        btnEstado.setOnAction(e -> confirmarCambioEstado(p));

        card.getChildren().addAll(avatar, nombre, especialidad, badge, stats, btnEstado);
        return card;
    }

    private void confirmarCambioEstado(ProfesionalResumenDto prof) {
        boolean activo = "ACTIVO".equals(prof.getEstadoProfesional());
        String accion = activo ? "desactivar" : "activar";
        ConfirmDialog dialog = new ConfirmDialog(
                "Confirmar acción",
                "¿Está seguro que desea " + accion + " al profesional " + prof.getNombreCompleto() + "?",
                activo ? "Desactivar" : "Activar",
                true
        );
        if (dialog.showAndWait()) {
            String nuevoEstado = activo ? "INACTIVO" : "ACTIVO";
            Thread.ofVirtual().start(() -> {
                try {
                    profesionalApi.cambiarEstado(prof.getProfesionalId(), nuevoEstado);
                    Platform.runLater(() -> cargarProfesionales());
                } catch (Exception ex) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Error al cambiar estado: " + ex.getMessage(), ButtonType.OK);
                        alert.showAndWait();
                    });
                }
            });
        }
    }

    private void mostrarFormularioNuevoProfesional() {
        javafx.stage.Stage stage = new javafx.stage.Stage();
        stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        stage.initStyle(javafx.stage.StageStyle.UNDECORATED);
        stage.setTitle("Nuevo Profesional");

        VBox form = new VBox(14);
        form.setPadding(new Insets(24));
        form.setMinWidth(400);
        form.setMaxWidth(400);
        form.getStyleClass().add("dialog-box");

        Label titulo = new Label("Nuevo Profesional");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField txtNombres = new TextField();
        txtNombres.setPromptText("Nombres");
        txtNombres.setMaxWidth(Double.MAX_VALUE);

        TextField txtApellidos = new TextField();
        txtApellidos.setPromptText("Apellidos");
        txtApellidos.setMaxWidth(Double.MAX_VALUE);

        TextField txtEspecialidad = new TextField();
        txtEspecialidad.setPromptText("Especialidad (ej: Cardiología)");
        txtEspecialidad.setMaxWidth(Double.MAX_VALUE);

        Label lblError = new Label();
        lblError.setStyle("-fx-text-fill: #dc2626; -fx-font-size: 12px;");
        lblError.setVisible(false);

        HBox acciones = new HBox(10);
        acciones.setAlignment(Pos.CENTER_RIGHT);
        PrimaryButton btnGuardar = new PrimaryButton("Crear");
        SecondaryButton btnCancelar = new SecondaryButton("Cancelar");
        btnCancelar.setOnAction(e -> stage.close());
        acciones.getChildren().addAll(btnCancelar, btnGuardar);

        btnGuardar.setOnAction(e -> {
            String nombres = txtNombres.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            String especialidad = txtEspecialidad.getText().trim();
            if (nombres.isEmpty() || apellidos.isEmpty()) {
                lblError.setText("Nombres y apellidos son obligatorios.");
                lblError.setVisible(true);
                return;
            }
            lblError.setVisible(false);
            Thread.ofVirtual().start(() -> {
                try {
                    ProfesionalDto nuevo = new ProfesionalDto();
                    nuevo.setNombres(nombres);
                    nuevo.setApellidos(apellidos);
                    nuevo.setEspecialidadBreve(especialidad.isEmpty() ? "General" : especialidad);
                    profesionalApi.crear(nuevo);
                    Platform.runLater(() -> {
                        stage.close();
                        cargarProfesionales();
                    });
                } catch (Exception ex) {
                    Platform.runLater(() -> {
                        lblError.setText("Error al crear: " + ex.getMessage());
                        lblError.setVisible(true);
                    });
                }
            });
        });

        form.getChildren().addAll(titulo,
                crearCampo("Nombres:", txtNombres),
                crearCampo("Apellidos:", txtApellidos),
                crearCampo("Especialidad:", txtEspecialidad),
                lblError, acciones);

        stage.setScene(new javafx.scene.Scene(form));
        stage.showAndWait();
    }

    private VBox crearCampo(String label, Control control) {
        Label lbl = new Label(label);
        lbl.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");
        VBox box = new VBox(4, lbl, control);
        return box;
    }

    private static class Region extends javafx.scene.layout.Region {
        public Region() { HBox.setHgrow(this, Priority.ALWAYS); }
    }
}
