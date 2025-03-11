package me.deshark.lms.application.dto;

import java.util.List;

/**
 * @author DE_SHARK
 */
public record PageResult<T>(
        List<T> data,
        int currentPage,
        int totalPages,
        long totalItems
) {
    public static <T> PageResult<T> createPageResult(List<T> data, int currentPage, int totalPages, long totalItems) {
        return new PageResult<>(data, currentPage, totalPages, totalItems);
    }
}
