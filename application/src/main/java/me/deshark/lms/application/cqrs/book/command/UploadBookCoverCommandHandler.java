package me.deshark.lms.application.cqrs.book.command;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.cqrs.core.CommandHandler;
import me.deshark.lms.common.exception.book.BookNotFoundException;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.catalog.BookRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UploadBookCoverCommandHandler implements CommandHandler<UploadBookCoverCommand> {

    private final BookRepository bookRepository;

    /**
     * 上传图书封面
     * @param command 图书ISBN及封面文件
     */
    @Override
    public void handle(UploadBookCoverCommand command) {

        // 1. 校验ISBN
        Isbn isbn = new Isbn(command.getIsbn());

        // 2. 验证图书是否存在
        if (!bookRepository.existsByIsbn(isbn.toString())) {
            throw new BookNotFoundException("ISBN为" + isbn + "的图书不存在");
        }

        // 3. 上传到MinIO

        // 4. 更新图书封面信息
        bookRepository.updateCoverImage(isbn.toString(), coverImage);
    }
}
