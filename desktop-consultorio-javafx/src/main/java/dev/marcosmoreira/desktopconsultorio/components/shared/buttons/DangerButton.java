package dev.marcosmoreira.desktopconsultorio.components.shared.buttons;

import javafx.scene.control.Button;

/**
 * Botón de acción destructiva.
 *
 * <p>Se usa para "Eliminar", "Cancelar cita", "Inactivar".
 * Fondo rojo para indicar peligro.</p>
 *
 * <p>Clase CSS: {@code .btn-danger}</p>
 */
public class DangerButton extends Button {

    public DangerButton(String text) {
        super(text);
        getStyleClass().add("btn-danger");
    }

    public DangerButton(String text, Runnable action) {
        this(text);
        setOnAction(e -> action.run());
    }
}
