package dev.marcosmoreira.desktopconsultorio.components.shared.layout;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Encabezado de página con título, subtítulo y acciones opcionales.
 *
 * <p>Se coloca al inicio de cada módulo. Muestra el nombre del módulo
 * y una descripción breve. Opcionalmente acepta botones de acción
 * alineados a la derecha.</p>
 *
 * <p>Clases CSS: {@code .page-header}, {@code .page-title}, {@code .page-subtitle}</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class PageHeader extends VBox {

    public PageHeader(String title, String subtitle) {
        getStyleClass().add("page-header");
        setPadding(new Insets(0, 0, 16, 0));
        setSpacing(4);

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("page-title");

        getChildren().add(titleLabel);

        if (subtitle != null && !subtitle.isEmpty()) {
            Label subtitleLabel = new Label(subtitle);
            subtitleLabel.getStyleClass().add("page-subtitle");
            getChildren().add(subtitleLabel);
        }
    }

    public PageHeader(String title, String subtitle, HBox actions) {
        this(title, subtitle);

        HBox headerRow = new HBox(16);
        headerRow.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        VBox textArea = new VBox(4);
        textArea.getChildren().addAll(new Label(title));
        if (subtitle != null && !subtitle.isEmpty()) {
            Label subtitleLabel = new Label(subtitle);
            subtitleLabel.getStyleClass().add("page-subtitle");
            textArea.getChildren().add(subtitleLabel);
        }
        HBox.setHgrow(textArea, javafx.scene.layout.Priority.ALWAYS);

        headerRow.getChildren().addAll(textArea, actions);
        getChildren().clear();
        getChildren().add(headerRow);
    }

    public PageHeader(String title) {
        this(title, null);
    }
}
