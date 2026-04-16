package dev.marcosmoreira.desktopconsultorio.components.shared.layout;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

/**
 * Barra de herramientas de módulo con filtros y botones de acción.
 *
 * <p>Contiene la barra de búsqueda, filtros y botones de acción
 * principal del módulo. Se coloca debajo del PageHeader.</p>
 *
 * <p>Clase CSS: {@code .page-toolbar}</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class PageToolbar extends HBox {

    public PageToolbar() {
        getStyleClass().add("page-toolbar");
        setPadding(new Insets(12, 16, 12, 16));
        setSpacing(12);
        setAlignment(Pos.CENTER_LEFT);
    }

    public void addFilter(javafx.scene.Node filter) {
        if (!getChildren().contains(filter)) {
            getChildren().add(filter);
        }
    }

    public void addAction(javafx.scene.Node action) {
        HBox rightArea = getRightArea();
        rightArea.getChildren().add(action);
    }

    private HBox getRightArea() {
        HBox rightArea = null;
        for (javafx.scene.Node child : getChildren()) {
            if (child instanceof HBox && child.getId() != null && child.getId().equals("toolbar-actions")) {
                rightArea = (HBox) child;
                break;
            }
        }
        if (rightArea == null) {
            rightArea = new HBox(10);
            rightArea.setId("toolbar-actions");
            rightArea.setAlignment(Pos.CENTER_RIGHT);
            HBox.setHgrow(rightArea, javafx.scene.layout.Priority.ALWAYS);
            getChildren().add(rightArea);
        }
        return rightArea;
    }
}
