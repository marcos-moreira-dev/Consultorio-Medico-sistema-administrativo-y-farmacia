package dev.marcosmoreira.desktopconsultorio.components.shared.layout;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Tarjeta de métrica para dashboards y resúmenes financieros.
 *
 * <p>Muestra un valor numérico grande con etiqueta descriptiva
 * y opcionalmente un ícono. Se usa en el Dashboard y en Cobros.</p>
 *
 * <p>Clases CSS: {@code .metric-card} + {@code .metric-{tipo}}</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class MetricCard extends VBox {

    public MetricCard(String icon, String label, String value, String type) {
        getStyleClass().add("metric-card");
        setPadding(new Insets(16));
        setSpacing(8);

        HBox topRow = new HBox(8);
        topRow.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        Label iconLabel = new Label(icon);
        iconLabel.getStyleClass().add("metric-icon");
        iconLabel.setStyle("-fx-font-size: 22px;");

        Label labelNode = new Label(label);
        labelNode.getStyleClass().add("metric-label");

        topRow.getChildren().addAll(iconLabel, labelNode);

        Label valueLabel = new Label(value);
        valueLabel.getStyleClass().add("metric-value");
        valueLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        getChildren().addAll(topRow, valueLabel);

        if (type != null && !type.isEmpty()) {
            getStyleClass().add("metric-" + type.toLowerCase());
        }
    }

    public MetricCard(String icon, String label, String value) {
        this(icon, label, value, null);
    }
}
