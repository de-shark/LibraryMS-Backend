package me.deshark.lms.interfaces.dto;

/**
 * @author DE_SHARK
 */
public record BookResponse(
        String isbn,
        String title,
        String author,
        String publisher,
        int publishYear,
        String description,
        String coverImage,
        int availableCopies
) {}
