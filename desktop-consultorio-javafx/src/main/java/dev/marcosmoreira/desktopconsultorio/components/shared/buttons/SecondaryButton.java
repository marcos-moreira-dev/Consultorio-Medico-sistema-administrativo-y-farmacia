package dev.marcosmoreira.desktopconsultorio.components.shared.buttons;

import javafx.scene.control.Button;

/**
 * Botón secundario para acciones complementarias.
 *
 * <p>Se usa para acciones como "Editar", "Ver detalle", "Exportar".
 * Fondo blanco con borde gris sutil.</p>
 *
 * <p>Clase CSS: {@code .btn-secondary}</p>
 */
public class SecondaryButton extends Button {

    public SecondaryButton(String text) {
        super(text);
        getStyleClass().add("btn-secondary");
    }

    public SecondaryButton(String text, Runnable action) {
        this(text);
        setOnAction(e -> action.run());
    }
}
