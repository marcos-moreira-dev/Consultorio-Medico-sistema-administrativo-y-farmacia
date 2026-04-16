package dev.marcosmoreira.desktopconsultorio.components.shared.pagination;

import dev.marcosmoreira.desktopconsultorio.components.shared.buttons.GhostButton;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.function.Consumer;

/**
 * Barra de paginación con botones anterior/siguiente y contador.
 */
public class PaginationBar extends HBox {

    private int currentPage = 0;
    private int pageSize;
    private int totalItems;
    private int totalPages;
    private final Consumer<Integer> onPageChange;

    public PaginationBar(int pageSize, Consumer<Integer> onPageChange) {
        this.pageSize = pageSize;
        this.onPageChange = onPageChange;
        setSpacing(8);
        setAlignment(Pos.CENTER);
        setPadding(new javafx.geometry.Insets(8));
    }

    public void update(int totalItems) {
        this.totalItems = totalItems;
        this.totalPages = totalItems == 0 ? 1 : (int) Math.ceil((double) totalItems / pageSize);
        if (currentPage >= totalPages) currentPage = Math.max(0, totalPages - 1);
        render();
    }

    private void render() {
        getChildren().clear();
        if (totalPages <= 1) return;

        GhostButton prev = new GhostButton("◀ Anterior");
        prev.setDisable(currentPage == 0);
        prev.setOnAction(e -> goToPage(currentPage - 1));

        Label pageInfo = new Label(String.format("Página %d de %d · %d registros", currentPage + 1, totalPages, totalItems));
        pageInfo.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b7f8e;");

        GhostButton next = new GhostButton("Siguiente ▶");
        next.setDisable(currentPage >= totalPages - 1);
        next.setOnAction(e -> goToPage(currentPage + 1));

        getChildren().addAll(prev, pageInfo, next);
    }

    private void goToPage(int page) {
        currentPage = page;
        render();
        onPageChange.accept(page);
    }
}
