package me.deshark.lms.domain.service.catalog;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.catalog.entity.BookCopy;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.catalog.BookCopyRepository;
import me.deshark.lms.domain.repository.catalog.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 图书副本领域服务
 * 
 * <p>负责管理图书副本的生成、分配等业务逻辑
 * 注意：副本生成操作需要考虑并发场景下的数据一致性</p>
 *
 * @author DE_SHARK
 */
@RequiredArgsConstructor
public class BookCopyService {

    private static final int DEFAULT_COPY_COUNT = 10;

    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;

    /**
     * 为所有副本不足的图书生成默认数量的副本
     * 
     * <p>业务规则：
     * 1. 查找所有副本数量小于10本的图书
     * 2. 为这些图书生成副本，使总数达到10本
     * 3. 将新生成的副本持久化到数据库</p>
     */
    public void generateDefaultCopiesForAllBooks() {
        // 获取所有需要补充副本的图书isbn
        List<Isbn> booksNeedCopies = bookRepository.findAllIsbn()
                .stream()
                .filter(book -> book.getCopyCount() < DEFAULT_COPY_COUNT)
                .collect(Collectors.toList());

        // 为每本图书生成缺失的副本
        booksNeedCopies.forEach(book -> {
            int existingCopies = book.getCopyCount();
            int copiesToAdd = DEFAULT_COPY_COUNT - existingCopies;
            
            List<BookCopy> newCopies = book.generateCopies(copiesToAdd);
            bookCopyRepository.saveAll(newCopies);
        });
    }
}
