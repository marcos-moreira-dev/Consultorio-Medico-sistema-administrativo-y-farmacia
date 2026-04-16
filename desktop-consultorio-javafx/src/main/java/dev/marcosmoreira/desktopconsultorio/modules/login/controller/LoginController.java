package dev.marcosmoreira.desktopconsultorio.modules.login.controller;

import dev.marcosmoreira.desktopconsultorio.app.ConsultorioDesktopApi;
import dev.marcosmoreira.desktopconsultorio.app.DesktopConsultorioApplication;
import dev.marcosmoreira.desktopconsultorio.session.SessionSnapshot;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;
    @FXML private Button loginButton;
    @FXML private ImageView brandingImageView;
    @FXML private ImageView brandingImageViewSmall;

    private ConsultorioDesktopApi api;

    @FXML
    public void initialize() {
        this.api = DesktopConsultorioApplication.getApi();

        var brandingResource = getClass().getResource("/images/branding/logo-consultorio.png");
        var iconResource = getClass().getResource("/images/branding/logo-consultorio-icon.png");
        if (brandingResource != null && brandingImageView != null) {
            brandingImageView.setImage(new Image(brandingResource.toExternalForm()));
        }
        if (iconResource != null && brandingImageViewSmall != null) {
            brandingImageViewSmall.setImage(new Image(iconResource.toExternalForm()));
        } else if (brandingResource != null && brandingImageViewSmall != null) {
            brandingImageViewSmall.setImage(new Image(brandingResource.toExternalForm()));
        }

        usernameField.setText(System.getProperty("DESKTOP_DEMO_USERNAME", "admin.consultorio"));
        usernameField.setOnAction(event -> passwordField.requestFocus());
        passwordField.setOnAction(event -> onLogin());
    }

    @FXML
    private void onLogin() {
        String username = usernameField.getText() == null ? "" : usernameField.getText().trim();
        String password = passwordField.getText() == null ? "" : passwordField.getText();

        if (username.isBlank() || password.isBlank()) {
            showError("Debes ingresar usuario y contraseña.");
            return;
        }

        loginButton.setDisable(true);
        loginButton.setText("Ingresando...");

        Thread.ofVirtual().start(() -> {
            try {
                SessionSnapshot snapshot = api.login(username, password);
                Platform.runLater(() -> DesktopConsultorioApplication.showMainShell(snapshot));
            } catch (RuntimeException ex) {
                if (isLocalDemoUser(username, password)) {
                    SessionSnapshot demoSnapshot = SessionSnapshot.authenticated(
                            null, null, null, null, username,
                            buildDemoDisplayName(username),
                            "DEMO_LOCAL", "Demo local", true);
                    Platform.runLater(() -> DesktopConsultorioApplication.showMainShell(demoSnapshot));
                } else {
                    Platform.runLater(() -> {
                        loginButton.setDisable(false);
                        loginButton.setText("Entrar");
                        showError("Usuario o contraseña incorrectos.");
                    });
                }
            }
        });
    }

    private void showError(String message) {
        statusLabel.setText(message);
        statusLabel.setVisible(true);
        statusLabel.setStyle("-fx-text-fill: #dc2626; -fx-font-size: 13px;");
    }

    private boolean isLocalDemoUser(String username, String password) {
        if (!"123456".equals(password)) return false;
        return switch (username) {
            case "admin.consultorio", "recepcion.ana", "dr.paredes", "dra.mora" -> true;
            default -> false;
        };
    }

    private String buildDemoDisplayName(String username) {
        return switch (username) {
            case "recepcion.ana" -> "Ana Recepción";
            case "dr.paredes" -> "Dr. Paredes";
            case "dra.mora" -> "Dra. Mora";
            default -> "Administrador del consultorio";
        };
    }
}
