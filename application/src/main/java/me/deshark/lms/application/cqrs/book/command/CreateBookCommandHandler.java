package me.deshark.lms.application.cqrs.book.command;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.catalog.entity.BookCatalog;
import me.deshark.lms.domain.model.catalog.exception.BookAlreadyExistsException;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.BookCatalogRepository;
import org.springframework.stereotype.Service;

/**
 * 图书创建命令处理器
 * 
 * <p>处理图书创建业务逻辑，包括：
 * 1. ISBN格式校验
 * 2. 图书信息创建
 * 3. 图书信息持久化
 * 
 * @author DE_SHARK
 */
@Service
@RequiredArgsConstructor
public class CreateBookCommandHandler {

    private final BookCatalogRepository bookCatalogRepository;

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
        if (bookCatalogRepository.existsByIsbn(isbn.toString())) {
            throw new BookAlreadyExistsException("ISBN为" + isbn + "的图书已存在");
        }

        // 创建图书信息
        BookCatalog bookCatalog = BookCatalog.builder()
                .isbn(isbn)
                .title(command.title())
                .author(command.author())
                .build();

        // 保存到仓库
        bookCatalogRepository.save(bookCatalog);
    }
}
