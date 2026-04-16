package dev.marcosmoreira.desktopconsultorio.components.shared.layout;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

/**
 * Contenedor tipo tarjeta con sombra ligera y bordes redondeados.
 *
 * <p>Se usa como bloque de contenido dentro de los módulos. Agrupa
 * información relacionada con un título opcional.</p>
 *
 * <p>Clase CSS: {@code .card}</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class Card extends VBox {

    public Card() {
        getStyleClass().add("card");
        setPadding(new Insets(16));
        setSpacing(12);
    }

    public Card(String title) {
        this();
        javafx.scene.control.Label titleLabel = new javafx.scene.control.Label(title);
        titleLabel.getStyleClass().add("card-title");
        getChildren().add(titleLabel);
    }
}
