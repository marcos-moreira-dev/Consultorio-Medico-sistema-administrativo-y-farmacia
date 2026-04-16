package dev.marcosmoreira.desktopconsultorio.components.shared.feedback;

import javafx.scene.control.Label;

/**
 * Badge visual de estado con color semántico.
 *
 * <p>Muestra estados de entidad como PROGRAMADA, CANCELADA, PAGADO, etc.
 * El color se asigna automáticamente según el tipo de estado.</p>
 *
 * <p>Clases CSS: {@code .status-badge} + {@code .badge-{estado}}</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class StatusBadge extends Label {

    public enum StateType {
        PROGRAMADA("badge-programada"),
        ATENDIDA("badge-atendida"),
        CANCELADA("badge-cancelada"),
        EN_ATENCION("badge-en-atencion"),
        DISPONIBLE("badge-disponible"),
        PAGADO("badge-pagado"),
        PENDIENTE("badge-pendiente"),
        ANULADO("badge-anulado"),
        ACTIVO("badge-activo"),
        INACTIVO("badge-inactivo");

        private final String cssClass;

        StateType(String cssClass) {
            this.cssClass = cssClass;
        }

        public String getCssClass() {
            return cssClass;
        }
    }

    public StatusBadge(String text, StateType type) {
        super(text);
        getStyleClass().add("status-badge");
        getStyleClass().add(type.getCssClass());
        setAlignment(javafx.geometry.Pos.CENTER);
        setMinWidth(80);
    }

    public StatusBadge(String text) {
        this(text, StateType.ACTIVO);
    }
}
