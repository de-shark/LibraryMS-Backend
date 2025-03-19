package me.deshark.lms.domain.service.catalog;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.catalog.entity.BookCopy;
import me.deshark.lms.domain.model.catalog.vo.LowInventoryInfo;
import me.deshark.lms.domain.repository.catalog.BookCopyRepository;
import me.deshark.lms.domain.repository.catalog.BookRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * 图书副本领域服务
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

        // 获取需要处理的列表
        List<LowInventoryInfo> lowInventoryIsbns = bookRepository.findBooksWithLowInventory(DEFAULT_COPY_COUNT);

        // 用于存储所有新生成的副本
        List<BookCopy> newCopies = new ArrayList<>();

        // 为每本库存不足的书生成副本
        for (LowInventoryInfo info : lowInventoryIsbns) {
            // 计算需要补充的副本数量
            int copiesToGenerate = DEFAULT_COPY_COUNT - info.getCopyCount();

            // 生成所需数量的副本
            for (int i = 0; i < copiesToGenerate; i++) {
                BookCopy newCopy = BookCopy.copyOf(info.getIsbn());
                newCopies.add(newCopy);
            }
        }

        // 将副本写入存储
        bookCopyRepository.saveAllCopies(newCopies);
    }
}
