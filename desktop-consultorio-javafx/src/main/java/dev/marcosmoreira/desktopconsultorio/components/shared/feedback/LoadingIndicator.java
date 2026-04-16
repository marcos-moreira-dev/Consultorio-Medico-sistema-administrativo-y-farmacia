package dev.marcosmoreira.desktopconsultorio.components.shared.feedback;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;

/**
 * Indicador de carga con spinner y mensaje.
 *
 * <p>Se muestra mientras se esperan datos del backend. Incluye
 * un ProgressIndicator nativo y un label descriptivo.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class LoadingIndicator extends HBox {

    public LoadingIndicator(String message) {
        super(12);
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(16, 0, 16, 0));

        ProgressIndicator spinner = new ProgressIndicator();
        spinner.setPrefSize(24, 24);

        Label label = new Label(message != null ? message : "Cargando datos...");
        label.setStyle("-fx-text-fill: #6b7f8e; -fx-font-size: 14px;");

        getChildren().addAll(spinner, label);
    }

    public LoadingIndicator() {
        this(null);
    }
}
