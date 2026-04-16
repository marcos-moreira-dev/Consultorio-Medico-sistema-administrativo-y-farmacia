package dev.marcosmoreira.desktopconsultorio.components.shared.feedback;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Panel de estado vacío para listas y tablas sin datos.
 *
 * <p>Muestra un ícono, mensaje y opcionalmente un botón de acción
 * (ej: "Crear primer paciente"). Se usa cuando una consulta no
 * devuelve resultados o un módulo aún no tiene datos.</p>
 *
 * <p>Clase CSS: {@code .empty-state}</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class EmptyStatePane extends VBox {

    public EmptyStatePane(String icon, String title, String message) {
        this(icon, title, message, null, null);
    }

    public EmptyStatePane(String icon, String title, String message, String actionText, Runnable action) {
        getStyleClass().add("empty-state");
        setAlignment(Pos.CENTER);
        setPadding(new Insets(40));
        setSpacing(16);

        Label iconLabel = new Label(icon);
        iconLabel.getStyleClass().add("empty-state-icon");

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("empty-state-title");

        Label messageLabel = new Label(message);
        messageLabel.getStyleClass().add("empty-state-text");
        messageLabel.setWrapText(true);

        getChildren().addAll(iconLabel, titleLabel, messageLabel);

        if (actionText != null && action != null) {
            Button actionButton = new Button(actionText);
            actionButton.getStyleClass().add("btn-primary");
            actionButton.setOnAction(e -> action.run());
            getChildren().add(actionButton);
        }
    }
}
