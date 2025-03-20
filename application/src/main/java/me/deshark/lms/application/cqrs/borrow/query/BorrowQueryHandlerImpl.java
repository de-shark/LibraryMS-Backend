package me.deshark.lms.application.cqrs.borrow.query;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.info.BorrowRecord;
import me.deshark.lms.common.utils.Page;
import me.deshark.lms.domain.model.borrow.query.BorrowRecordQuery;
import me.deshark.lms.domain.repository.auth.UserQueryRepository;
import me.deshark.lms.domain.repository.borrow.BorrowQueryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author DE_SHARK
 */
@Service
@RequiredArgsConstructor
public class BorrowQueryHandlerImpl implements BorrowQueryHandler {

    private final UserQueryRepository userQueryRepository;
    private final BorrowQueryRepository borrowQueryRepository;

    @Override
    public Page<BorrowRecord> handle(GetBorrowRecordsQuery query) {
        // 获取用户 ID
        UUID userId = userQueryRepository.getUserIdByUsername(query.getUsername());

        // 查询分页数据
        Page<BorrowRecordQuery> page = borrowQueryRepository.getPatronTransactions(userId, query.getPage(), query.getSize());

        // 创建结果分页对象
        Page<BorrowRecord> result = new Page<>(page.getPages(), page.getSize());
        result.setTotal(page.getTotal());

        // 将 BorrowRecordQuery 列表转换为 BorrowRecord 列表
        List<BorrowRecord> records = page.getRecords().stream()
                .map(this::convertToBorrowRecord)
                .collect(Collectors.toList());
        result.setRecords(records);

        return result;
    }

    // 转换方法：BorrowRecordQuery -> BorrowRecord
    private BorrowRecord convertToBorrowRecord(BorrowRecordQuery query) {
        return BorrowRecord.builder()
                .isbn(query.getIsbn())
                .title(query.getTitle())
                .author(query.getAuthor())
                .borrowDate(query.getBorrowDate())
                .dueDate(query.getDueDate())
                .returnDate(query.getReturnDate())
                .status(query.getStatus())
                .build();
    }
}
