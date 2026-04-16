package dev.marcosmoreira.desktopconsultorio.modules.usuarios.view;

import dev.marcosmoreira.desktopconsultorio.components.shared.buttons.DangerButton;
import dev.marcosmoreira.desktopconsultorio.components.shared.buttons.PrimaryButton;
import dev.marcosmoreira.desktopconsultorio.components.shared.buttons.SecondaryButton;
import dev.marcosmoreira.desktopconsultorio.components.shared.dialogs.ConfirmDialog;
import dev.marcosmoreira.desktopconsultorio.components.shared.feedback.EmptyStatePane;
import dev.marcosmoreira.desktopconsultorio.components.shared.feedback.LoadingIndicator;
import dev.marcosmoreira.desktopconsultorio.components.shared.feedback.StatusBadge;
import dev.marcosmoreira.desktopconsultorio.components.shared.forms.SearchField;
import dev.marcosmoreira.desktopconsultorio.components.shared.pagination.PaginationBar;
import dev.marcosmoreira.desktopconsultorio.http.dto.usuarios.UsuarioResumenDto;
import dev.marcosmoreira.desktopconsultorio.modules.usuarios.viewmodel.UsuariosViewModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class UsuariosView extends VBox {

    private final UsuariosViewModel vm;
    private final TableView<UsuarioResumenDto> tabla;
    private final PaginationBar paginationBar;
    private final SearchField searchField;

    public UsuariosView(UsuariosViewModel vm) {
        this.vm = vm;
        this.paginationBar = new PaginationBar(vm.getSize(), page -> vm.cargarUsuarios());
        this.searchField = new SearchField("Buscar por username...");

        setSpacing(16);
        setPadding(new Insets(18, 20, 18, 20));

        HBox toolbar = new HBox(12);
        toolbar.setPadding(new Insets(8, 0, 8, 0));
        PrimaryButton btnNuevo = new PrimaryButton("Nuevo Usuario");
        btnNuevo.setOnAction(e -> mostrarFormularioCrear());
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        toolbar.getChildren().addAll(btnNuevo, spacer, searchField);

        tabla = new TableView<>();
        configurarTabla();

        VBox container = new VBox(8);
        container.getChildren().addAll(toolbar, tabla);
        VBox.setVgrow(tabla, Priority.ALWAYS);

        searchField.textProperty().addListener((obs, old, val) -> {
            if (val.length() > 2 || val.isEmpty()) {
                vm.buscar(val);
            }
        });

        vm.getUsuarios().addListener((javafx.collections.ListChangeListener<UsuarioResumenDto>) change -> {
            tabla.setItems(vm.getUsuarios());
            paginationBar.update((int) vm.getTotalElements());
        });

        vm.cargandoProperty().addListener((obs, old, val) -> {
            if (val) {
                tabla.setPlaceholder(new LoadingIndicator("Cargando usuarios..."));
            } else if (vm.getUsuarios().isEmpty()) {
                tabla.setPlaceholder(new EmptyStatePane("📋", "Sin usuarios", "No se encontraron usuarios con los filtros actuales."));
            }
        });

        vm.cargarUsuarios();

        getChildren().addAll(container, paginationBar);
    }

    private void configurarTabla() {
        TableColumn<UsuarioResumenDto, String> colUsername = new TableColumn<>("Username");
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colUsername.setPrefWidth(160);

        TableColumn<UsuarioResumenDto, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(cellData -> {
            String nombre = cellData.getValue().getNombreCompleto();
            return new javafx.beans.property.SimpleStringProperty(nombre != null ? nombre : cellData.getValue().getUsername());
        });
        colNombre.setPrefWidth(180);

        TableColumn<UsuarioResumenDto, String> colRol = new TableColumn<>("Rol");
        colRol.setCellValueFactory(new PropertyValueFactory<>("rolNombre"));
        colRol.setPrefWidth(180);

        TableColumn<UsuarioResumenDto, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colEstado.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setGraphic(new StatusBadge(item));
                }
            }
        });
        colEstado.setPrefWidth(110);

        TableColumn<UsuarioResumenDto, Void> colAcciones = new TableColumn<>("Acciones");
        colAcciones.setCellFactory(col -> new TableCell<>() {
            private final Button btnEstado = new Button();
            private final Button btnReset = new Button("Reset PW");
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }
                UsuarioResumenDto u = getTableView().getItems().get(getIndex());
                boolean activo = "ACTIVO".equalsIgnoreCase(u.getEstado());
                btnEstado.setText(activo ? "Desactivar" : "Activar");
                btnEstado.getStyleClass().add(activo ? "btn-danger" : "btn-primary");
                btnEstado.setStyle("-fx-font-size: 11px; -fx-padding: 4 10 4 10;");
                btnEstado.setOnAction(e -> confirmarCambioEstado(u));
                btnReset.setStyle("-fx-font-size: 11px; -fx-padding: 4 10 4 10;");
                btnReset.setOnAction(e -> mostrarResetPassword(u));
                HBox box = new HBox(6, btnEstado, btnReset);
                box.setAlignment(Pos.CENTER);
                setGraphic(box);
            }
        });
        colAcciones.setPrefWidth(200);

        tabla.getColumns().addAll(colUsername, colNombre, colRol, colEstado, colAcciones);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void confirmarCambioEstado(UsuarioResumenDto usuario) {
        boolean activo = "ACTIVO".equalsIgnoreCase(usuario.getEstado());
        String accion = activo ? "desactivar" : "activar";
        ConfirmDialog dialog = new ConfirmDialog(
                "Confirmar acción",
                "¿Está seguro que desea " + accion + " al usuario " + usuario.getUsername() + "?",
                activo ? "Desactivar" : "Activar",
                true
        );
        if (dialog.showAndWait()) {
            String nuevoEstado = activo ? "INACTIVO" : "ACTIVO";
            vm.cambiarEstado(usuario.getUsuarioId(), nuevoEstado);
        }
    }

    private void mostrarResetPassword(UsuarioResumenDto usuario) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Resetear contraseña");
        dialog.setHeaderText("Resetear contraseña de: " + usuario.getUsername());
        dialog.setContentText("Nueva contraseña temporal:");
        dialog.showAndWait().ifPresent(nuevoPassword -> {
            if (!nuevoPassword.trim().isEmpty()) {
                vm.resetPassword(usuario.getUsuarioId(), nuevoPassword.trim());
            }
        });
    }

    private void mostrarFormularioCrear() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Nuevo Usuario");

        VBox form = new VBox(14);
        form.setPadding(new Insets(24));
        form.setMinWidth(400);
        form.setMaxWidth(400);
        form.getStyleClass().add("dialog-box");

        Label titulo = new Label("Crear Usuario");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField txtUsername = new TextField();
        txtUsername.setPromptText("Username");
        txtUsername.setMaxWidth(Double.MAX_VALUE);

        TextField txtPassword = new TextField();
        txtPassword.setPromptText("Contraseña temporal");
        txtPassword.setMaxWidth(Double.MAX_VALUE);

        ComboBox<String> cbRol = new ComboBox<>();
        cbRol.getItems().addAll("ADMIN_CONSULTORIO", "OPERADOR_CONSULTORIO", "PROFESIONAL_CONSULTORIO");
        cbRol.setPromptText("Seleccionar rol");
        cbRol.setMaxWidth(Double.MAX_VALUE);

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
            String username = txtUsername.getText().trim();
            String password = txtPassword.getText().trim();
            String rol = cbRol.getValue();
            if (username.isEmpty()) {
                lblError.setText("El username es obligatorio.");
                lblError.setVisible(true);
                return;
            }
            if (password.isEmpty()) {
                lblError.setText("La contraseña temporal es obligatoria.");
                lblError.setVisible(true);
                return;
            }
            if (rol == null) {
                lblError.setText("Debe seleccionar un rol.");
                lblError.setVisible(true);
                return;
            }
            lblError.setVisible(false);
            Long rolId = switch (rol) {
                case "ADMIN_CONSULTORIO" -> 1L;
                case "OPERADOR_CONSULTORIO" -> 2L;
                default -> 3L;
            };
            vm.crearUsuario(rolId, username, password);
            stage.close();
        });

        form.getChildren().addAll(titulo, crearCampo("Username:", txtUsername), crearCampo("Contraseña temporal:", txtPassword), crearCampo("Rol:", cbRol), lblError, acciones);

        stage.setScene(new javafx.scene.Scene(form));
        stage.showAndWait();
        vm.cargarUsuarios();
    }

    private VBox crearCampo(String label, Control control) {
        Label lbl = new Label(label);
        lbl.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");
        VBox.setVgrow(control, Priority.ALWAYS);
        VBox box = new VBox(4, lbl, control);
        return box;
    }
}
