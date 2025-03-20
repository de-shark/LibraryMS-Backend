package me.deshark.lms.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.common.utils.Page;
import me.deshark.lms.domain.model.borrow.query.BorrowRecordQuery;
import me.deshark.lms.domain.repository.borrow.BorrowQueryRepository;
import me.deshark.lms.infrastructure.mapper.LoanRecordMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Repository
@RequiredArgsConstructor
public class BorrowQueryRepositoryImpl implements BorrowQueryRepository {

    private final LoanRecordMapper loanRecordMapper;

    @Override
    public Page<BorrowRecordQuery> getPatronTransactions(UUID userId, int pageNumber, int pageSize) {
        // 获取总记录数
        long total = loanRecordMapper.selectCountByUserId(userId);
        // 计算offset
        long offset = (long) (pageNumber - 1) * pageSize;
        // 从数据库中获取借阅信息
        List<BorrowRecordQuery> list = loanRecordMapper.findBorrowRecordsPageByUserId(userId, offset, pageSize);
        Page<BorrowRecordQuery> result = new Page<>(pageNumber, pageSize);
        result.setRecords(list);
        result.setTotal(total);
        return result;
    }
}
