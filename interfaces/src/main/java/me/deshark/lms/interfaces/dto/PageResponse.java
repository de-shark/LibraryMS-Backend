package me.deshark.lms.interfaces.dto;

import java.util.List;

/**
 * @author DE_SHARK
 */
public record PageResponse<T>(
        List<T> data,
        int currentPage,
        int totalPages,
        long totalItems
) {
    public PageResponse(List<T> data, int currentPage, int totalPages, long totalItems) {
        this.data = data;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
    }

    public static <T> PageResponse<T> of(List<T> data, int currentPage, int totalPages, long totalItems) {
        return new PageResponse<>(data, currentPage, totalPages, totalItems);
    }
}