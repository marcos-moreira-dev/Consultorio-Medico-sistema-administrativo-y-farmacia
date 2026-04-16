package dev.marcosmoreira.desktopconsultorio.components.shared.forms;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Campo de búsqueda con ícono de lupa integrado.
 *
 * <p>TextField estilizado para búsquedas globales dentro de cada módulo.
 * Placeholder configurable y evento onSearch para reaccionar al input.</p>
 *
 * <p>Clase CSS: {@code .search-field}</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class SearchField extends TextField {

    public SearchField(String placeholder) {
        super();
        setPromptText(placeholder);
        getStyleClass().add("search-field");
        setPrefWidth(300);

        // Búsqueda en tiempo real (cada tecla)
        textProperty().addListener((obs, old, newVal) -> {
            if (onSearch != null) {
                onSearch.accept(newVal != null ? newVal.trim() : "");
            }
        });

        // Búsqueda al presionar Enter (para compatibilidad)
        setOnAction(e -> {
            String query = getText().trim();
            if (onSearch != null) {
                onSearch.accept(query);
            }
        });
    }

    public SearchField() {
        this("Buscar...");
    }

    private java.util.function.Consumer<String> onSearch;

    public void onSearch(java.util.function.Consumer<String> handler) {
        this.onSearch = handler;
    }
}
