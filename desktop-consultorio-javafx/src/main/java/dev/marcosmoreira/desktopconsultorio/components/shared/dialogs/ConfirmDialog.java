package dev.marcosmoreira.desktopconsultorio.components.shared.dialogs;

import dev.marcosmoreira.desktopconsultorio.components.shared.buttons.DangerButton;
import dev.marcosmoreira.desktopconsultorio.components.shared.buttons.GhostButton;
import dev.marcosmoreira.desktopconsultorio.components.shared.buttons.PrimaryButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Diálogo de confirmación modal para acciones importantes.
 *
 * <p>Se usa antes de operaciones destructivas o irreversibles:
 * cancelar citas, eliminar registros, cambiar estados críticos.
 * Muestra un ícono, mensaje y botones de Confirmar/Cancelar.</p>
 *
 * <p>Clases CSS: {@code .dialog-box}, {@code .dialog-title}, {@code .dialog-message}</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class ConfirmDialog {

    private final String title;
    private final String message;
    private final String confirmText;
    private final boolean danger;

    public ConfirmDialog(String title, String message, String confirmText, boolean danger) {
        this.title = title;
        this.message = message;
        this.confirmText = confirmText;
        this.danger = danger;
    }

    public ConfirmDialog(String title, String message) {
        this(title, message, "Confirmar", false);
    }

    /**
     * Muestra el diálogo y retorna true si el usuario confirma.
     *
     * @param owner ventana propietaria
     * @return true si el usuario confirmó, false si canceló
     */
    public boolean showAndWait(Stage owner) {
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initStyle(StageStyle.UNDECORATED);

        VBox root = new VBox(20);
        root.getStyleClass().add("dialog-box");
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(24));
        root.setMinWidth(380);

        // Icon
        Label icon = new Label(danger ? "!" : "i");
        icon.getStyleClass().addAll("confirm-icon", danger ? "confirm-icon-danger" : "confirm-icon-info");

        // Title
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("dialog-title");

        // Message
        Label messageLabel = new Label(message);
        messageLabel.getStyleClass().add("confirm-message");
        messageLabel.setMaxWidth(320);
        messageLabel.setAlignment(Pos.CENTER);

        // Actions
        HBox actions = new HBox(12);
        actions.setAlignment(Pos.CENTER_RIGHT);

        GhostButton cancelBtn = new GhostButton("Cancelar");
        cancelBtn.setOnAction(e -> dialog.close());

        PrimaryButton confirmBtn;
        if (danger) {
            confirmBtn = new PrimaryButton(confirmText);
            confirmBtn.getStyleClass().add("btn-danger");
            confirmBtn.getStyleClass().remove("btn-primary");
        } else {
            confirmBtn = new PrimaryButton(confirmText);
        }
        confirmBtn.setOnAction(e -> {
            confirmed = true;
            dialog.close();
        });

        actions.getChildren().addAll(cancelBtn, confirmBtn);

        VBox content = new VBox(16, icon, titleLabel, messageLabel);
        content.setAlignment(Pos.CENTER);

        root.getChildren().addAll(content, actions);

        Scene scene = new Scene(root);
        // Load stylesheets from owner if possible
        dialog.setScene(scene);

        if (owner != null) {
            dialog.setX(owner.getX() + (owner.getWidth() - 400) / 2);
            dialog.setY(owner.getY() + (owner.getHeight() - 200) / 2);
        }

        confirmed = false;
        dialog.showAndWait();
        return confirmed;
    }

    private boolean confirmed;

    /**
     * Muestra el diálogo sin ventana propietaria.
     */
    public boolean showAndWait() {
        return showAndWait(null);
    }
}
