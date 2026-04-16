package dev.marcosmoreira.desktopconsultorio.components.shared.buttons;

import javafx.scene.control.Button;

/**
 * Botón principal para acciones dominantes del módulo.
 *
 * <p>Se usa para la acción primaria de cada pantalla: "Guardar", "Crear",
 * "Confirmar", "Registrar". Visualmente es el botón más llamativo con
 * fondo verde y texto blanco.</p>
 *
 * <p>Clase CSS: {@code .btn-primary}</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class PrimaryButton extends Button {

    public PrimaryButton(String text) {
        super(text);
        getStyleClass().add("btn-primary");
    }

    public PrimaryButton(String text, Runnable action) {
        this(text);
        setOnAction(e -> action.run());
    }
}
