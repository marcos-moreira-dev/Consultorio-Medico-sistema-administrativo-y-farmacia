package dev.marcosmoreira.desktopconsultorio.app;

import dev.marcosmoreira.desktopconsultorio.config.AppConfig;
import dev.marcosmoreira.desktopconsultorio.session.InMemorySessionStore;
import dev.marcosmoreira.desktopconsultorio.session.SessionSnapshot;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Runtime principal del cliente JavaFX del consultorio.
 */
public class DesktopConsultorioApplication extends Application {

    private static DesktopConsultorioApplication instance;
    private static final InMemorySessionStore SESSION_STORE = new InMemorySessionStore();

    private AppConfig config;
    private ConsultorioDesktopApi api;
    private Stage primaryStage;
    private Scene scene;

    @Override
    public void start(Stage primaryStage) {
        instance = this;
        this.config = AppConfig.load();
        this.api = new ConsultorioDesktopApi(config);
        this.primaryStage = primaryStage;

        Parent root = loadView("/fxml/modules/login/login-view.fxml");
        this.scene = new Scene(root, 1180, 760);
        applyStyles();

        primaryStage.setTitle(config.getAppName());
        var icon = getClass().getResource("/images/branding/logo-consultorio-icon.png");
        if (icon != null) {
            primaryStage.getIcons().add(new Image(icon.toExternalForm()));
        }
        primaryStage.setMinWidth(1024);
        primaryStage.setMinHeight(680);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static DesktopConsultorioApplication getInstance() {
        return instance;
    }

    public static AppConfig getConfig() {
        return getInstance().config;
    }

    public static ConsultorioDesktopApi getApi() {
        return getInstance().api;
    }

    public static InMemorySessionStore getSessionStore() {
        return SESSION_STORE;
    }

    public static void showLoginView() {
        getSessionStore().clear();
        getInstance().replaceRoot("/fxml/modules/login/login-view.fxml", getConfig().getAppName());
    }

    public static void showMainShell() {
        getInstance().replaceRoot("/fxml/shell/main-shell.fxml", getConfig().getAppName() + " · Operación");
    }

    public static void showMainShell(SessionSnapshot sessionSnapshot) {
        getSessionStore().save(sessionSnapshot);
        showMainShell();
    }

    private void replaceRoot(String fxmlPath, String title) {
        Parent root = loadView(fxmlPath);
        boolean wasMaximized = primaryStage.isMaximized();
        double currentWidth = primaryStage.getWidth();
        double currentHeight = primaryStage.getHeight();
        scene.setRoot(root);
        primaryStage.setTitle(title);
        if (wasMaximized) {
            primaryStage.setMaximized(true);
            return;
        }
        if (currentWidth < 1180) {
            currentWidth = 1180;
        }
        if (currentHeight < 760) {
            currentHeight = 760;
        }
        primaryStage.setWidth(currentWidth);
        primaryStage.setHeight(currentHeight);
        primaryStage.centerOnScreen();
    }

    private Parent loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            return loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("No se pudo cargar la vista " + fxmlPath + ".", ex);
        }
    }

    private void applyStyles() {
        // Base
        addStylesheetIfPresent("/styles/base/app.css");
        addStylesheetIfPresent("/styles/base/reset.css");
        addStylesheetIfPresent("/styles/base/controls.css");
        // Layouts
        addStylesheetIfPresent("/styles/layouts/login.css");
        addStylesheetIfPresent("/styles/layouts/shell.css");
        addStylesheetIfPresent("/styles/layouts/module.css");
        // Componentes
        addStylesheetIfPresent("/styles/components/buttons.css");
        addStylesheetIfPresent("/styles/components/forms.css");
        addStylesheetIfPresent("/styles/components/tables.css");
        addStylesheetIfPresent("/styles/components/cards.css");
        addStylesheetIfPresent("/styles/components/badges.css");
        addStylesheetIfPresent("/styles/components/dialogs.css");
        addStylesheetIfPresent("/styles/components/feedback.css");
    }

    private void addStylesheetIfPresent(String resourcePath) {
        if (getClass().getResource(resourcePath) != null) {
            scene.getStylesheets().add(getClass().getResource(resourcePath).toExternalForm());
        }
    }
}
