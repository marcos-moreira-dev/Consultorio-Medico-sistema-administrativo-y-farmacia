package dev.marcosmoreira.desktopconsultorio.http.dto.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Respuesta paginada del backend.
 * Alineado con {@code dev.marcosmoreira.consultorio.shared.web.PageResponse}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageResponseDto<T> {

    @JsonProperty("items")
    private List<T> items;

    @JsonProperty("page")
    private int page;

    @JsonProperty("size")
    private int size;

    @JsonProperty("totalElements")
    private long totalElements;

    @JsonProperty("totalPages")
    private int totalPages;

    @JsonProperty("hasNext")
    private boolean hasNext;

    @JsonProperty("hasPrevious")
    private boolean hasPrevious;

    public List<T> getItems() { return items; }
    public void setItems(List<T> items) { this.items = items; }
    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }
    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }
    public long getTotalElements() { return totalElements; }
    public void setTotalElements(long totalElements) { this.totalElements = totalElements; }
    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }
    public boolean isHasNext() { return hasNext; }
    public void setHasNext(boolean hasNext) { this.hasNext = hasNext; }
    public boolean isHasPrevious() { return hasPrevious; }
    public void setHasPrevious(boolean hasPrevious) { this.hasPrevious = hasPrevious; }

    /** Compatibility getter for code that used getData() */
    public List<T> getData() { return items; }
    public void setData(List<T> data) { this.items = data; }

    /** Compatibility getter for code that used getMeta() */
    public PageMetaDto getMeta() {
        if (items == null) return null;
        PageMetaDto meta = new PageMetaDto();
        meta.setPage(page);
        meta.setSize(size);
        meta.setTotalElements(totalElements);
        meta.setTotalPages(totalPages);
        meta.setHasNext(hasNext);
        meta.setHasPrevious(hasPrevious);
        return meta;
    }
}
