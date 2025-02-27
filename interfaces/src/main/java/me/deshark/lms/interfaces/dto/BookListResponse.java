package me.deshark.lms.interfaces.dto;

import java.util.List;

/**
 * @author DE_SHARK
 */
public record BookListResponse(
        List<BookResponse> books,
        int totalPages,
        long totalItems
) {}