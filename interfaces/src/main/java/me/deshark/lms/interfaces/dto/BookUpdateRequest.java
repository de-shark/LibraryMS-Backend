package me.deshark.lms.interfaces.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * @author DE_SHARK
 */
public record BookUpdateRequest(
        @NotBlank String title,
        @NotBlank String author,
        String publisher,
        Integer publishYear,
        String description
) {
}
