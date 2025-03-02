package me.deshark.lms.interfaces.dto;

import lombok.Builder;

/**
 * @author DE_SHARK
 */
@Builder
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
