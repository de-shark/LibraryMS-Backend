package me.deshark.lms.interfaces.dto;

/**
 * @author DE_SHARK
 */
public record BookSummaryResponse(
        String isbn,
        String title,
        String author,
        int availableCopies
) {
}
