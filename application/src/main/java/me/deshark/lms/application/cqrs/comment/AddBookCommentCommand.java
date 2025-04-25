package me.deshark.lms.application.cqrs.comment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.cqrs.core.Command;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class AddBookCommentCommand  implements Command {
    private final String isbn;
    private final UUID userId;
    private final String content;
    private final int rating;
}
