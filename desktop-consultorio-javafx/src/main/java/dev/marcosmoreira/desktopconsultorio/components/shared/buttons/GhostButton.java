package dev.marcosmoreira.desktopconsultorio.components.shared.buttons;

import javafx.scene.control.Button;

/**
 * Botón discreto para acciones menores.
 *
 * <p>Se usa para "Cerrar", "Limpiar filtro", "Ver más".
 * Fondo transparente con texto gris.</p>
 *
 * <p>Clase CSS: {@code .btn-ghost}</p>
 */
public class GhostButton extends Button {

    public GhostButton(String text) {
        super(text);
        getStyleClass().add("btn-ghost");
    }

    public GhostButton(String text, Runnable action) {
        this(text);
        setOnAction(e -> action.run());
    }
}
