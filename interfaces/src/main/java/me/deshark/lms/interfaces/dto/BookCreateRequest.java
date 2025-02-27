package me.deshark.lms.interfaces.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * @author DE_SHARK
 */
public record BookCreateRequest(
        @NotBlank String isbn,
        @NotBlank String title,
        @NotBlank String author,
        Integer publishYear,
        String publisher,
        String description
) {
}
