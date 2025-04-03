package me.deshark.lms.application.cqrs.book.command;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.cqrs.core.CommandHandler;
import me.deshark.lms.common.exception.FileStorageException;
import me.deshark.lms.common.exception.book.BookNotFoundException;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.model.common.FileData;
import me.deshark.lms.domain.repository.catalog.BookRepository;
import me.deshark.lms.domain.repository.FileStorageRepo;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UploadBookCoverCommandHandler implements CommandHandler<UploadBookCoverCommand> {

    private final BookRepository bookRepository;
    private final FileStorageRepo fileStorageRepo;

    /**
     * 上传图书封面
     * @param command 图书ISBN及封面文件
     */
    @Override
    public void handle(UploadBookCoverCommand command) {

        // 校验ISBN
        Isbn isbn = new Isbn(command.getIsbn());

        // 验证图书是否存在
        if (!bookRepository.existsByIsbn(isbn.toString())) {
            throw new BookNotFoundException("ISBN为" + isbn + "的图书不存在");
        }

        // 转换MultipartFile为领域对象
        FileData fileData;
        try {
            fileData = new FileData(
                    command.getFile().getOriginalFilename(),
                    command.getFile().getContentType(),
                    command.getFile().getSize(),
                    command.getFile().getInputStream()
            );
        } catch (IOException e) {
            throw new FileStorageException("转换MultipartFile为领域对象时出错：" + e.getMessage());
        }

        // 上传到文件存储
        String objectName = "book-covers/" + isbn + ".jpg";
        fileStorageRepo.uploadFile(fileData, objectName);

        // 更新图书封面信息
        bookRepository.updateCoverImage(isbn.toString(), objectName);
    }
}
