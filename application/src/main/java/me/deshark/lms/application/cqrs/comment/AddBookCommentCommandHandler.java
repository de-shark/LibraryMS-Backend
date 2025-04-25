package me.deshark.lms.application.cqrs.comment;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.converter.BookCommentConverter;
import me.deshark.lms.application.cqrs.core.CommandHandler;
import me.deshark.lms.domain.repository.comment.BookCommentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddBookCommentCommandHandler  implements CommandHandler<AddBookCommentCommand> {

    private final BookCommentRepository bookCommentRepository;

    @Override
    public void handle(AddBookCommentCommand command) {
        bookCommentRepository.save(BookCommentConverter.INSTANCE.infoToModel(command));
    }
}
