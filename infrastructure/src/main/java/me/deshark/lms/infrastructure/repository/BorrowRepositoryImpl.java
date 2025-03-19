package me.deshark.lms.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.borrowing.aggregate.BorrowTransaction;
import me.deshark.lms.domain.repository.borrow.BorrowRepository;
import me.deshark.lms.infrastructure.entity.LoanRecordDO;
import me.deshark.lms.infrastructure.enums.LoanStatusType;
import me.deshark.lms.infrastructure.mapper.LoanRecordMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Repository
@RequiredArgsConstructor
public class BorrowRepositoryImpl implements BorrowRepository {

    private final LoanRecordMapper loanRecordMapper;

    @Override
    public void save(BorrowTransaction transaction) {
        loanRecordMapper.insert(toDataObject(transaction));
    }

    @Override
    public BorrowTransaction findById(UUID transactionId) {
        return null;
    }

    @Override
    public List<BorrowTransaction> findHistoricalBorrowsByPatron(UUID patronId) {
        return List.of();
    }

    @Override
    public List<BorrowTransaction> findCurrentBorrowsByPatron(UUID patronId) {
        return List.of();
    }

    private LoanRecordDO toDataObject(BorrowTransaction transaction) {
        return LoanRecordDO.builder()
                .recordId(transaction.getTransactionId())
                .copyId(transaction.getBookCopyId())
                .userId(transaction.getPatron().getId())
                .loanDate(transaction.getStartDate())
                .dueDate(transaction.getDueDate())
                .returnDate(transaction.getEndDate())
                .status(LoanStatusType.valueOf(transaction.getStatus()))
                .build();
    }
}
