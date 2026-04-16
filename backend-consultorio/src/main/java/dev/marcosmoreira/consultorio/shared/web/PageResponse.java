package dev.marcosmoreira.consultorio.shared.web;

import java.util.List;

/**
 * Respuesta paginada estándar del backend.
 *
 * @param <T> tipo de elemento contenido en la página
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class PageResponse<T> {

    private List<T> items;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;

    /**
     * Constructor vacío requerido por serialización.
     */
    public PageResponse() {
    }

    /**
     * Construye una respuesta paginada completa.
     *
     * @param items elementos de la página actual
     * @param page índice de página actual
     * @param size tamaño de página
     * @param totalElements total de elementos disponibles
     * @param totalPages total de páginas
     * @param hasNext indica si existe página siguiente
     * @param hasPrevious indica si existe página anterior
     */
    public PageResponse(
            List<T> items,
            int page,
            int size,
            long totalElements,
            int totalPages,
            boolean hasNext,
            boolean hasPrevious
    ) {
        this.items = items;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
    }

    /**
     * Método fábrica para construir una respuesta paginada.
     *
     * @param items elementos de la página actual
     * @param page índice de página actual
     * @param size tamaño de página
     * @param totalElements total de elementos disponibles
     * @param totalPages total de páginas
     * @param hasNext indica si existe página siguiente
     * @param hasPrevious indica si existe página anterior
     * @param <T> tipo de elemento contenido
     * @return respuesta paginada
     */
    public static <T> PageResponse<T> of(
            List<T> items,
            int page,
            int size,
            long totalElements,
            int totalPages,
            boolean hasNext,
            boolean hasPrevious
    ) {
        return new PageResponse<>(
                items,
                page,
                size,
                totalElements,
                totalPages,
                hasNext,
                hasPrevious
        );
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }
}
