package dev.marcosmoreira.desktopconsultorio.components.shared.feedback;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Duration;

/**
 * Notificación toast que aparece en la esquina superior derecha
 * y desaparece automáticamente después de 3 segundos.
 */
public class ToastNotification extends HBox {

    public enum ToastType {
        SUCCESS("✓", "success"),
        ERROR("✕", "error"),
        INFO("ℹ", "info"),
        WARNING("⚠", "warning");

        final String icon;
        final String cssType;

        ToastType(String icon, String cssType) {
            this.icon = icon;
            this.cssType = cssType;
        }
    }

    public ToastNotification(String message, ToastType type) {
        this(message, type, 3000);
    }

    public ToastNotification(String message, ToastType type, int durationMs) {
        getStyleClass().addAll("toast-notification", "toast-" + type.cssType);
        setSpacing(10);
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(12, 16, 12, 16));
        setMaxWidth(380);
        setMinWidth(280);

        Label iconLabel = new Label(type.icon);
        iconLabel.getStyleClass().addAll("toast-icon", "toast-icon-" + type.cssType);

        Label messageLabel = new Label(message);
        messageLabel.getStyleClass().addAll("toast-message", "toast-message-" + type.cssType);
        messageLabel.setWrapText(true);
        HBox.setHgrow(messageLabel, Priority.ALWAYS);

        getChildren().addAll(iconLabel, messageLabel);

        setOpacity(0);
        show(durationMs);
    }

    private void show(int durationMs) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), this);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        PauseTransition stay = new PauseTransition(Duration.millis(durationMs));

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), this);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> {
            if (getParent() != null) {
                ((javafx.scene.layout.Pane) getParent()).getChildren().remove(this);
            }
        });

        SequentialTransition seq = new SequentialTransition(fadeIn, stay, fadeOut);
        seq.play();
    }

    /**
     * Muestra un toast en el contenedor especificado.
     */
    public static void show(javafx.scene.layout.Pane parent, String message, ToastType type) {
        ToastNotification toast = new ToastNotification(message, type);
        parent.getChildren().add(toast);
        javafx.scene.layout.StackPane.setAlignment(toast, Pos.TOP_RIGHT);
        toast.setTranslateX(-20);
        toast.setTranslateY(20);
    }

    /**
     * Muestra un toast de éxito.
     */
    public static void success(javafx.scene.layout.Pane parent, String message) {
        show(parent, message, ToastType.SUCCESS);
    }

    /**
     * Muestra un toast de error.
     */
    public static void error(javafx.scene.layout.Pane parent, String message) {
        show(parent, message, ToastType.ERROR);
    }

    /**
     * Muestra un toast de información.
     */
    public static void info(javafx.scene.layout.Pane parent, String message) {
        show(parent, message, ToastType.INFO);
    }
}
