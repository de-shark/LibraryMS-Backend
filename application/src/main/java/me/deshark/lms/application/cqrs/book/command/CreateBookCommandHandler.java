package me.deshark.lms.application.cqrs.book.command;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.catalog.entity.BookMetadata;
import me.deshark.lms.domain.model.catalog.exception.BookAlreadyExistsException;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.BookRepository;
import org.springframework.stereotype.Service;

/**
 * 图书创建命令处理器
 * 
 * <p>处理图书创建业务逻辑，包括：
 * 1. ISBN格式校验
 * 2. 调用API查询信息
 * 3. 图书信息创建
 * 4. 图书信息持久化
 * 
 * @author DE_SHARK
 */
@Service
@RequiredArgsConstructor
public class CreateBookCommandHandler {

    private final BookRepository bookRepository;

    private final BookMetadataProvider bookMetadataProvider;

    /**
     * 处理图书创建命令
     * @param command 创建命令
     * @throws BookAlreadyExistsException 当图书已存在时抛出
     * @throws IllegalArgumentException 当ISBN格式错误时抛出
     */
    public void handle(CreateBookCommand command) {
        // 验证ISBN
        Isbn isbn = new Isbn(command.isbn());

        // 检查图书是否已存在
        if (bookRepository.existsByIsbn(isbn.toString())) {
            throw new BookAlreadyExistsException("ISBN为" + isbn + "的图书已存在");
        }

        // 调用 API 获取图书信息

        // 保存到仓库
        bookCatalogRepository.save(bookCatalog);
    }
}
