package me.deshark.lms.interfaces.dto;

import java.time.LocalDate;

/**
 * @author DE_SHARK
 */
public record BookDetailResponse(
        String isbn,
        String title,
        String author,
        String publisher,
        Integer publishYear,
        String description,
        LocalDate acquisitionDate,
        int availableCopies
) {
}
