package dev.marcosmoreira.desktopconsultorio.shell.controller;

import dev.marcosmoreira.desktopconsultorio.app.ConsultorioDesktopApi;
import dev.marcosmoreira.desktopconsultorio.app.DesktopConsultorioApplication;
import dev.marcosmoreira.desktopconsultorio.config.AppConfig;
import dev.marcosmoreira.desktopconsultorio.http.service.*;
import dev.marcosmoreira.desktopconsultorio.modules.dashboard.view.DashboardView;
import dev.marcosmoreira.desktopconsultorio.modules.dashboard.viewmodel.DashboardViewModel;
import dev.marcosmoreira.desktopconsultorio.modules.pacientes.service.PacienteCoordinator;
import dev.marcosmoreira.desktopconsultorio.modules.pacientes.view.PacientesView;
import dev.marcosmoreira.desktopconsultorio.modules.pacientes.viewmodel.PacientesViewModel;
import dev.marcosmoreira.desktopconsultorio.modules.citas.view.CitasView;
import dev.marcosmoreira.desktopconsultorio.modules.citas.viewmodel.CitasViewModel;
import dev.marcosmoreira.desktopconsultorio.modules.atenciones.view.AtencionesView;
import dev.marcosmoreira.desktopconsultorio.modules.atenciones.viewmodel.AtencionesViewModel;
import dev.marcosmoreira.desktopconsultorio.modules.cobros.view.CobrosView;
import dev.marcosmoreira.desktopconsultorio.modules.profesionales.view.ProfesionalesView;
import dev.marcosmoreira.desktopconsultorio.modules.reportes.view.ReportesAuditoriaView;
import dev.marcosmoreira.desktopconsultorio.modules.usuarios.view.UsuariosView;
import dev.marcosmoreira.desktopconsultorio.modules.usuarios.viewmodel.UsuariosViewModel;
import dev.marcosmoreira.desktopconsultorio.session.SessionSnapshot;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Shell operativo mínimo y real del desktop del consultorio.
 */
public class MainShellController {

    @FXML private Label appTitleLabel;
    @FXML private ImageView sidebarLogoImageView;
    @FXML private ImageView sidebarBrandLargeImageView;
    @FXML private Label sessionLabel;
    @FXML private Label roleLabel;
    @FXML private Label contentTitleLabel;
    @FXML private Label contentSubtitleLabel;
    @FXML private VBox contentBox;

    @FXML private Button dashboardButton;
    @FXML private Button usuariosButton;
    @FXML private Button pacientesButton;
    @FXML private Button citasButton;
    @FXML private Button cobrosButton;
    @FXML private Button profesionalesButton;
    @FXML private Button atencionesButton;
    @FXML private Button reportesButton;
    @FXML private Button auditoriaButton;
    @FXML private Button logoutButton;
    @FXML private MenuBar menuBar;

    private AppConfig config;
    private ConsultorioDesktopApi api;
    private SessionSnapshot session;

    // Servicios API para módulos
    private DashboardApiService dashboardApi;
    private PacienteApiService pacienteApi;
    private CitaApiService citaApi;
    private CobroApiService cobroApi;
    private ProfesionalApiService profesionalApi;
    private AtencionApiService atencionApi;
    private ReporteApiService reporteApi;
    private AuditoriaApiService auditoriaApi;
    private UsuarioApiService usuarioApi;

    // Coordinadores de módulos
    private PacienteCoordinator pacienteCoordinator;
    private PacientesViewModel pacientesViewModel;

    private static MainShellController instance;

    @FXML
    public void initialize() {
        instance = this;
        this.config = DesktopConsultorioApplication.getConfig();
        this.api = DesktopConsultorioApplication.getApi();
        this.session = DesktopConsultorioApplication.getSessionStore().getCurrentSession().orElse(SessionSnapshot.uiExploration());

        // Inicializar servicios API
        this.dashboardApi = new DashboardApiService(api);
        this.pacienteApi = new PacienteApiService(api);
        this.citaApi = new CitaApiService(api);
        this.cobroApi = new CobroApiService(api);
        this.profesionalApi = new ProfesionalApiService(api);
        this.atencionApi = new AtencionApiService(api);
        this.reporteApi = new ReporteApiService(api);
        this.auditoriaApi = new AuditoriaApiService(api);
        this.usuarioApi = new UsuarioApiService(api);

        // Inicializar coordinadores
        this.pacienteCoordinator = new PacienteCoordinator(pacienteApi);

        appTitleLabel.setText("Consultorio Médico");
        var iconResource = getClass().getResource("/images/branding/logo-consultorio-icon.png");
        var brandResource = getClass().getResource("/images/branding/logo-consultorio.png");
        if (sidebarLogoImageView != null) {
            if (iconResource != null) {
                sidebarLogoImageView.setImage(new Image(iconResource.toExternalForm()));
            } else if (brandResource != null) {
                sidebarLogoImageView.setImage(new Image(brandResource.toExternalForm()));
            }
        }
        if (sidebarBrandLargeImageView != null) {
            if (brandResource != null) {
                sidebarBrandLargeImageView.setImage(new Image(brandResource.toExternalForm()));
            } else if (iconResource != null) {
                sidebarBrandLargeImageView.setImage(new Image(iconResource.toExternalForm()));
            }
        }
        sessionLabel.setText(session.getDisplayName());
        roleLabel.setText(resolveRoleLabel());

        // Status indicator
        boolean online = api.pingBackend();
        if (sessionLabel != null) {
            String statusPrefix = online ? "🟢 " : "⚪ ";
            sessionLabel.setText(statusPrefix + session.getDisplayName());
        }

        // Filtrar sidebar según rol
        aplicarVisibilidadPorRol();

        showDashboard();
    }

    /**
     * Navegación estática para módulos que necesitan redirigir entre sí.
     */
    public static void navigateToCobros() {
        if (instance != null) {
            javafx.application.Platform.runLater(() -> instance.showCobros());
        }
    }

    @FXML private void onDashboard() { showDashboard(); }
    @FXML private void onUsuarios() { showUsuarios(); }
    @FXML private void onPacientes() { showPacientes(); }
    @FXML private void onCitas() { showCitas(); }
    @FXML private void onAtenciones() { showAtenciones(); }
    @FXML private void onCobros() { showCobros(); }
    @FXML private void onProfesionales() { showProfesionales(); }
    @FXML private void onReportes() { showReportesAuditoria(); }
    @FXML private void onAuditoria() { showReportesAuditoria(); }

    @FXML
    private void onLogout() {
        DesktopConsultorioApplication.showLoginView();
    }

    @FXML
    private void onSalir() {
        Platform.exit();
    }

    @FXML
    private void onPantallaCompleta() {
        var stage = (javafx.stage.Stage) contentBox.getScene().getWindow();
        stage.setFullScreen(!stage.isFullScreen());
    }

    @FXML
    private void onRecargar() {
        Button activeBtn = null;
        for (Button b : new Button[]{dashboardButton, usuariosButton, pacientesButton, citasButton, cobrosButton, profesionalesButton, atencionesButton, reportesButton, auditoriaButton}) {
            if (b.getStyleClass().contains("sidebar-button-active")) {
                activeBtn = b;
                break;
            }
        }
        if (activeBtn == dashboardButton) showDashboard();
        else if (activeBtn == usuariosButton) showUsuarios();
        else if (activeBtn == pacientesButton) showPacientes();
        else if (activeBtn == citasButton) showCitas();
        else if (activeBtn == cobrosButton) showCobros();
        else if (activeBtn == profesionalesButton) showProfesionales();
        else if (activeBtn == atencionesButton) showAtenciones();
        else if (activeBtn == reportesButton || activeBtn == auditoriaButton) showReportesAuditoria();
    }

    @FXML
    private void onAcercaDe() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Acerca de");
        alert.setHeaderText("Santa Emilia Desktop");
        alert.setContentText("Consultorio Médico - Versión 0.1.0\nSoftware de escritorio para operación administrativa.\n\nDesarrollado con JavaFX 21 + Spring Boot 3.4");
        alert.showAndWait();
    }

    private void showDashboard() {
        setActive(dashboardButton);
        contentTitleLabel.setText("Panel operativo inicial");
        contentSubtitleLabel.setText(session.hasBearerToken()
                ? "Resumen operativo del consultorio en tiempo real."
                : "Vista de referencia con datos de demostración.");

        DashboardViewModel vm = new DashboardViewModel(
                dashboardApi, pacienteApi, citaApi, cobroApi, profesionalApi);
        DashboardView dashboardView = new DashboardView(vm);

        contentBox.getChildren().setAll(dashboardView);
    }

    private void showUsuarios() {
        setActive(usuariosButton);
        contentTitleLabel.setText("Usuarios");
        contentSubtitleLabel.setText("Gestión de cuentas de acceso al sistema.");
        UsuariosViewModel vm = new UsuariosViewModel(usuarioApi);
        contentBox.getChildren().setAll(new UsuariosView(vm));
    }

    /**
     * Muestra el módulo de citas con timeline + calendario 3-pane.
     */
    private void showCitas() {
        setActive(citasButton);
        contentTitleLabel.setText("Citas");
        contentSubtitleLabel.setText("Agenda diaria y programación de consultas.");

        CitasViewModel citasViewModel = new CitasViewModel(citaApi, pacienteApi, profesionalApi);
        CitasView citasView = new CitasView(citasViewModel);
        contentBox.getChildren().setAll(citasView);
    }

    /**
     * Muestra el módulo de pacientes con vista Master-Detail.
     */
    private void showPacientes() {
        setActive(pacientesButton);
        contentTitleLabel.setText("Pacientes");
        contentSubtitleLabel.setText("Gestión operativa de pacientes y búsqueda rápida.");

        if (pacientesViewModel == null) {
            pacientesViewModel = pacienteCoordinator.createViewModel();
        }
        PacientesView pacientesView = new PacientesView(pacientesViewModel);
        contentBox.getChildren().setAll(pacientesView);
    }

    /**
     * Muestra el módulo de atenciones con tabla y formulario slide-down.
     */
    private void showAtenciones() {
        setActive(atencionesButton);
        contentTitleLabel.setText("Atenciones");
        contentSubtitleLabel.setText("Registro de consultas y notas clínicas.");
        AtencionesViewModel vm = new AtencionesViewModel(atencionApi, pacienteApi, profesionalApi);
        contentBox.getChildren().setAll(new AtencionesView(vm));
    }

    /**
     * Muestra el módulo de cobros con dashboard financiero.
     */
    private void showCobros() {
        setActive(cobrosButton);
        contentTitleLabel.setText("Cobros");
        contentSubtitleLabel.setText("Registro de pagos y estado financiero del consultorio.");
        dev.marcosmoreira.desktopconsultorio.modules.cobros.viewmodel.CobrosViewModel cobrosVM =
                new dev.marcosmoreira.desktopconsultorio.modules.cobros.viewmodel.CobrosViewModel(cobroApi, atencionApi);
        contentBox.getChildren().setAll(new CobrosView(cobrosVM));
    }

    /**
     * Muestra el módulo de profesionales con grid de tarjetas.
     */
    private void showProfesionales() {
        setActive(profesionalesButton);
        contentTitleLabel.setText("Profesionales");
        contentSubtitleLabel.setText("Plantilla clínica activa y especialidades del consultorio.");
        contentBox.getChildren().setAll(new ProfesionalesView(profesionalApi));
    }

    /**
     * Muestra el módulo combinado de Reportes y Auditoría.
     */
    private void showReportesAuditoria() {
        setActive(reportesButton);
        contentTitleLabel.setText("Reportes y Auditoría");
        contentSubtitleLabel.setText("Exportaciones y trazabilidad del sistema.");
        contentBox.getChildren().setAll(new ReportesAuditoriaView(auditoriaApi));
    }

    private Node buildInfoBanner() {
        VBox box = new VBox(8);
        box.getStyleClass().add("panel-card");
        box.setPadding(new Insets(16));
        Label title = new Label("Sesión actual");
        title.getStyleClass().add("panel-title");
        Label description = new Label(session.hasBearerToken()
                ? "Usuario autenticado: " + api.fetchUserSummary(session)
                : (session.isAuthenticated()
                    ? "Sesión local activa. Ya puedes recorrer módulos, revisar indicadores y observar la operación del consultorio."
                    : "Pantalla inicial del consultorio para revisar navegación, jerarquía y paneles principales."));
        description.setWrapText(true);
        box.getChildren().addAll(title, description);
        return box;
    }

    private Node buildDashboardCards() {
        boolean demoLocal = session.isAuthenticated() && !session.hasBearerToken();
        if (demoLocal) {
            HBox layout = new HBox(16);
            VBox spotlight = new VBox(16);
            spotlight.getStyleClass().addAll("panel-card", "dashboard-spotlight");
            spotlight.setPadding(new Insets(20));
            Label chip = new Label("Radar operativo");
            chip.getStyleClass().add("dashboard-chip");
            Label metric = new Label("148");
            metric.getStyleClass().add("dashboard-spotlight-metric");
            Label headline = new Label("Pacientes activos en la operación demo");
            headline.getStyleClass().add("dashboard-spotlight-title");
            Label body = new Label("La vista local ya presenta el tablero principal, indicadores rápidos y una lectura administrativa del consultorio.");
            body.setWrapText(true);
            FlowPane micro = new FlowPane();
            micro.setHgap(12);
            micro.setVgap(12);
            micro.getChildren().addAll(
                    metricCard("Pacientes", "148"),
                    metricCard("Citas", "26"),
                    metricCard("Profesionales", "7"),
                    metricCard("Cobros", "$ 1.240")
            );
            spotlight.getChildren().addAll(chip, metric, headline, body, micro);

            VBox side = new VBox(12);
            side.getStyleClass().add("panel-card");
            side.setPadding(new Insets(20));
            Label sideTitle = new Label("Vista rápida del día");
            sideTitle.getStyleClass().add("panel-title");
            ListView<String> list = new ListView<>();
            list.getItems().setAll(
                    "08:00 · Control general · Dra. Mora",
                    "10:30 · Primera consulta · Dr. Paredes",
                    "14:00 · Seguimiento · Ana Recepción",
                    "Cobros del día · $ 1.240"
            );
            list.setPrefHeight(240);
            side.getChildren().addAll(sideTitle, list);

            HBox.setHgrow(spotlight, Priority.ALWAYS);
            HBox.setHgrow(side, Priority.ALWAYS);
            spotlight.setMaxWidth(Double.MAX_VALUE);
            side.setMaxWidth(340);
            layout.getChildren().addAll(spotlight, side);
            return layout;
        }

        FlowPane flow = new FlowPane();
        flow.setHgap(12);
        flow.setVgap(12);
        flow.getChildren().addAll(
                metricCard("Pacientes", session.hasBearerToken() ? "cargando..." : "requiere login"),
                metricCard("Citas", session.hasBearerToken() ? "cargando..." : "requiere login"),
                metricCard("Profesionales", session.hasBearerToken() ? "cargando..." : "requiere login"),
                metricCard("Cobros", session.hasBearerToken() ? "cargando..." : "requiere login")
        );
        return flow;
    }


    private VBox metricCard(String title, String value) {
        VBox card = new VBox(8);
        card.getStyleClass().add("metric-card");
        card.setPadding(new Insets(16));
        card.setPrefWidth(220);
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("metric-title");
        Label valueLabel = new Label(value);
        valueLabel.getStyleClass().add("metric-value");
        valueLabel.setId("metric-" + title.toLowerCase());
        card.getChildren().addAll(titleLabel, valueLabel);
        return card;
    }

    private void refreshDashboardMetrics() {
        Thread.ofVirtual().start(() -> {
            try {
                Map<String, Long> metrics = api.fetchDashboardMetrics(session);
                Platform.runLater(() -> updateMetricCards(metrics));
            } catch (RuntimeException ex) {
                if (session.isAuthenticated() && !session.hasBearerToken()) {
                    return;
                }
                Platform.runLater(() -> contentBox.getChildren().add(buildErrorCard(ex.getMessage())));
            }
        });
    }

    private void updateMetricCards(Map<String, Long> metrics) {
        for (Node node : ((FlowPane) contentBox.getChildren().get(1)).getChildren()) {
            if (node instanceof VBox card && card.getChildren().size() >= 2) {
                Label title = (Label) card.getChildren().get(0);
                Label value = (Label) card.getChildren().get(1);
                Long metric = metrics.get(title.getText());
                if (metric != null) {
                    value.setText(String.valueOf(metric));
                }
            }
        }
    }

    private void showModule(String title, String subtitle, Button activeButton, String endpoint, String... previewFields) {
        setActive(activeButton);
        contentTitleLabel.setText(title);
        contentSubtitleLabel.setText(subtitle);

        VBox wrapper = new VBox(12);

        if (!session.hasBearerToken()) {
            wrapper.getChildren().add(session.isAuthenticated() ? buildDemoModuleCard(title) : buildUiExplorationCard(title));
            contentBox.getChildren().setAll(wrapper);
            return;
        }

        VBox loadingCard = buildSimpleCard("Cargando datos", "Preparando información del consultorio...");
        wrapper.getChildren().add(loadingCard);
        contentBox.getChildren().setAll(wrapper);

        Thread.ofVirtual().start(() -> {
            try {
                long total = api.fetchTotalElements(endpoint, session);
                List<String> preview = api.fetchPreviewItems(endpoint, session, previewFields);
                Platform.runLater(() -> {
                    wrapper.getChildren().remove(loadingCard);
                    wrapper.getChildren().add(buildTotalCard(total));
                    wrapper.getChildren().add(buildPreviewList(preview));
                });
            } catch (RuntimeException ex) {
                Platform.runLater(() -> {
                    wrapper.getChildren().remove(loadingCard);
                    wrapper.getChildren().add(buildErrorCard(ex.getMessage()));
                });
            }
        });
    }

    private void showReportesModule() {
        setActive(reportesButton);
        contentTitleLabel.setText("Reportes");
        contentSubtitleLabel.setText("Listados, resúmenes y salidas operativas del consultorio.");

        VBox wrapper = new VBox(12);

        VBox summary = buildSimpleCard(
                "Centro de reportes",
                session.hasBearerToken()
                        ? "Consulta salidas operativas, cierres diarios y actividad clínica consolidada."
                        : "Revisa una muestra local de reportes administrativos y clínicos del consultorio."
        );
        summary.setPrefWidth(320);

        VBox quickList = new VBox(8);
        quickList.getStyleClass().add("panel-card");
        quickList.setPadding(new Insets(16));
        Label listTitle = new Label("Salidas disponibles");
        listTitle.getStyleClass().add("panel-title");
        ListView<String> listView = new ListView<>();
        listView.getItems().setAll(
                "Reporte diario de citas",
                "Pacientes atendidos por profesional",
                "Cobros por método de pago",
                "Trazabilidad operativa del día",
                "Agenda consolidada por fecha"
        );
        listView.setPrefHeight(220);
        quickList.getChildren().addAll(listTitle, listView);

        VBox statusCard = buildSimpleCard(
                "Lectura rápida",
                session.hasBearerToken()
                        ? "Desde aquí podrás centralizar exportaciones y listados del consultorio sin salir del panel principal."
                        : "La muestra local ya enseña el aspecto general del centro de reportes y sus salidas más comunes."
        );

        HBox top = new HBox(16, summary, quickList);
        HBox.setHgrow(quickList, Priority.ALWAYS);
        wrapper.getChildren().setAll(top, statusCard);
        contentBox.getChildren().setAll(wrapper);
    }

    private void showStaticModule(String title, String subtitle) {
        contentTitleLabel.setText(title);
        contentSubtitleLabel.setText(subtitle);
        contentBox.getChildren().setAll(
                buildSimpleCard("Resumen", "Esta vista quedará dedicada al flujo operativo específico del consultorio."));
    }

    private VBox buildModuleSummaryCard(String title, String subtitle) {
        VBox box = new VBox(8);
        box.getStyleClass().add("panel-card");
        box.setPadding(new Insets(16));
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("panel-title");
        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.setWrapText(true);
        box.getChildren().addAll(titleLabel, subtitleLabel);
        return box;
    }

    private VBox buildTotalCard(long total) {
        return buildSimpleCard("Total registrado", String.valueOf(total));
    }

    private VBox buildPreviewList(List<String> previewItems) {
        VBox box = new VBox(8);
        box.getStyleClass().add("panel-card");
        box.setPadding(new Insets(16));
        Label title = new Label("Muestra rápida");
        title.getStyleClass().add("panel-title");
        if (previewItems == null || previewItems.isEmpty()) {
            box.getChildren().addAll(title, new Label("No hubo elementos que mostrar en esta consulta."));
            return box;
        }
        ListView<String> listView = new ListView<>();
        listView.getItems().setAll(previewItems);
        listView.setPrefHeight(180);
        box.getChildren().addAll(title, listView);
        return box;
    }



    private VBox buildDemoModuleCard(String moduleName) {
        VBox box = new VBox(12);
        box.getStyleClass().add("panel-card");
        box.setPadding(new Insets(18));
        Label title = new Label("Vista de referencia");
        title.getStyleClass().add("panel-title");
        String moduleBody = switch (moduleName) {
            case "Reportes" -> "Esta sección presenta una muestra de reportes para revisar listados, resúmenes y salidas administrativas del consultorio.";
            default -> "Esta sección presenta una muestra de " + moduleName.toLowerCase() + " para revisar agenda, lectura visual y estructura operativa del consultorio.";
        };
        Label body = new Label(moduleBody);
        body.setWrapText(true);
        ListView<String> listView = new ListView<>();
        listView.getItems().setAll(switch (moduleName) {
            case "Pacientes" -> List.of("María Gómez · 29 años · Activa", "Carlos Vera · Control anual", "Ana Lucía Torres · Seguimiento");
            case "Citas" -> List.of("08:00 · General · Confirmada", "10:30 · Primera consulta · Pendiente", "15:00 · Revisión · Confirmada");
            case "Cobros" -> List.of("Factura 00124 · $35,00 · Pagado", "Factura 00125 · $18,50 · Pendiente", "Factura 00126 · $42,00 · Tarjeta");
            case "Profesionales" -> List.of("Dra. Mora · Medicina interna", "Dr. Paredes · Medicina general", "Lcda. Ana Ruiz · Recepción");
            case "Atenciones" -> List.of("Paciente María Gómez · Diagnóstico inicial", "Paciente Carlos Vera · Seguimiento", "Paciente Lucía Torres · Revisión clínica");
            case "Auditoría" -> List.of("LOGIN_ADMIN · admin.consultorio", "CREAR_CITA · recepcion.ana", "ACTUALIZAR_PACIENTE · admin.consultorio");
            case "Reportes" -> List.of("Reporte diario de citas", "Cobros del día", "Pacientes por profesional");
            default -> List.of("Elemento de ejemplo 1", "Elemento de ejemplo 2", "Elemento de ejemplo 3");
        });
        listView.setPrefHeight(190);
        box.getChildren().addAll(title, body, listView);
        return box;
    }

    private VBox buildUiExplorationCard(String moduleName) {
        VBox box = buildSimpleCard(
                "Vista preliminar",
                "Esta vista está en modo de referencia. "
                        + "Aquí puedes revisar la composición general de " + moduleName.toLowerCase() + ": encabezado, resumen, lista breve y zona de acciones del consultorio."
        );
        box.getStyleClass().add("neutral-card");
        return box;
    }

    private VBox buildSimpleCard(String titleText, String bodyText) {
        VBox box = new VBox(8);
        box.getStyleClass().add("panel-card");
        box.setPadding(new Insets(16));
        Label title = new Label(titleText);
        title.getStyleClass().add("panel-title");
        Label body = new Label(bodyText);
        body.setWrapText(true);
        box.getChildren().addAll(title, body);
        return box;
    }

    private VBox buildErrorCard(String message) {
        VBox box = buildSimpleCard("Observación", message);
        box.getStyleClass().add("error-card");
        return box;
    }

    private String resolveRoleLabel() {
        if (!session.isAuthenticated()) {
            return "Exploración";
        }
        if (!session.hasBearerToken()) {
            return "Administración";
        }
        return session.getRolCodigo() == null ? "Administración" : session.getRolCodigo();
    }

    /**
     * Filtra la visibilidad de botones del sidebar según el rol del usuario.
     * ADMIN_CONSULTORIO: acceso total.
     * OPERADOR_CONSULTORIO: sin Auditoría.
     * PROFESIONAL_CONSULTORIO: solo Dashboard, Citas, Atenciones.
     */
    private void aplicarVisibilidadPorRol() {
        String rol = session.getRolCodigo();
        if (rol == null) rol = "ADMIN_CONSULTORIO";

        boolean isAdmin = "ADMIN_CONSULTORIO".equals(rol);
        boolean isOperador = "OPERADOR_CONSULTORIO".equals(rol);
        boolean isProfesional = "PROFESIONAL_CONSULTORIO".equals(rol);

        // Helper para mostrar/ocultar sin dejar espacio vacío
        java.util.function.BiConsumer<Button, Boolean> setVisibleManaged = (btn, visible) -> {
            btn.setVisible(visible);
            btn.setManaged(visible);
        };

        // Todos ven: Dashboard, Pacientes, Citas, Cobros, Profesionales, Atenciones
        setVisibleManaged.accept(dashboardButton, true);
        setVisibleManaged.accept(usuariosButton, isAdmin);
        setVisibleManaged.accept(pacientesButton, true);
        setVisibleManaged.accept(citasButton, true);
        setVisibleManaged.accept(cobrosButton, true);
        setVisibleManaged.accept(profesionalesButton, true);
        setVisibleManaged.accept(atencionesButton, true);

        // Reportes: Admin y Operador
        setVisibleManaged.accept(reportesButton, isAdmin || isOperador);

        // Auditoría: solo Admin
        setVisibleManaged.accept(auditoriaButton, isAdmin);

        // Profesional: ocultar administrativos
        if (isProfesional) {
            setVisibleManaged.accept(pacientesButton, false);
            setVisibleManaged.accept(cobrosButton, false);
            setVisibleManaged.accept(profesionalesButton, false);
            setVisibleManaged.accept(reportesButton, false);
            setVisibleManaged.accept(auditoriaButton, false);
        }
    }

    private void setActive(Button activeButton) {
        Button[] allButtons = {dashboardButton, usuariosButton, pacientesButton, citasButton, cobrosButton, profesionalesButton, atencionesButton, reportesButton, auditoriaButton};
        for (Button button : allButtons) {
            if (button.isVisible()) {
                button.getStyleClass().remove("sidebar-button-active");
            }
        }
        activeButton.getStyleClass().add("sidebar-button-active");
    }
}
