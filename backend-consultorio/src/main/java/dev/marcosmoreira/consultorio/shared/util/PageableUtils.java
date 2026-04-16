package dev.marcosmoreira.consultorio.shared.util;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Utilidades para sanear y normalizar objetos {@link Pageable}.
 */
public final class PageableUtils {

    private PageableUtils() {
    }

    public static Pageable sanitize(Pageable pageable, Sort defaultSort, String... allowedProperties) {
        int page = pageable == null ? 0 : Math.max(pageable.getPageNumber(), 0);
        int size = pageable == null ? 20 : Math.min(Math.max(pageable.getPageSize(), 1), 100);
        Set<String> allowed = new LinkedHashSet<>(Arrays.asList(allowedProperties));

        Sort sanitizedSort = defaultSort;
        if (pageable != null && pageable.getSort().isSorted()) {
            Sort candidate = Sort.unsorted();
            for (Sort.Order order : pageable.getSort()) {
                if (allowed.contains(order.getProperty())) {
                    candidate = candidate.and(Sort.by(order));
                }
            }
            if (candidate.isSorted()) {
                sanitizedSort = candidate;
            }
        }

        return PageRequest.of(page, size, sanitizedSort);
    }
}
