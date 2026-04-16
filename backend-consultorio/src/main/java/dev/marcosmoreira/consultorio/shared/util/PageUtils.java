package dev.marcosmoreira.consultorio.shared.util;

import dev.marcosmoreira.consultorio.shared.web.PageResponse;
import org.springframework.data.domain.Page;

/**
 * Utilidades para conversión entre estructuras de paginación de Spring
 * y el contrato HTTP estandarizado del proyecto.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public final class PageUtils {

    private PageUtils() {
    }

    /**
     * Convierte un {@link Page} de Spring Data a un {@link PageResponse}.
     *
     * @param page página de Spring Data
     * @param <T> tipo de elemento contenido
     * @return respuesta paginada estandarizada del proyecto
     */
    public static <T> PageResponse<T> fromPage(Page<T> page) {
        return PageResponse.of(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasNext(),
                page.hasPrevious()
        );
    }
}
