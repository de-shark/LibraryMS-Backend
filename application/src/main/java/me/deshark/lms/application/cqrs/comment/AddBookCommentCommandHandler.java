package me.deshark.lms.application.cqrs.comment;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.cqrs.core.CommandHandler;
import me.deshark.lms.domain.repository.comment.BookCommentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddBookCommentCommandHandler  implements CommandHandler<AddBookCommentCommand> {

    private final BookCommentRepository bookCommentRepository;

    @Override
    public void handle(AddBookCommentCommand command) {
        // 这里实现具体的评论保存逻辑
        // 会调用bookCommentRepository保存评论

    }
}
