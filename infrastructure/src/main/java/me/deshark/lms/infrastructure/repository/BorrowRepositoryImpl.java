package me.deshark.lms.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.borrow.aggregate.LoanRecord;
import me.deshark.lms.domain.repository.borrow.BorrowRepository;
import me.deshark.lms.infrastructure.entity.LoanRecordDO;
import me.deshark.lms.infrastructure.enums.LoanStatusType;
import me.deshark.lms.infrastructure.mapper.LoanRecordMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Repository
@RequiredArgsConstructor
public class BorrowRepositoryImpl implements BorrowRepository {

    private final LoanRecordMapper loanRecordMapper;

    @Override
    public void save(LoanRecord transaction) {
        loanRecordMapper.insert(toDataObject(transaction));
    }

    @Override
    public Optional<LoanRecord> findById(UUID transactionId) {
        return Optional.empty();
    }

    @Override
    public List<LoanRecord> findHistoricalBorrowsByPatron(UUID patronId) {
        return List.of();
    }

    @Override
    public List<LoanRecord> findCurrentBorrowsByPatron(UUID patronId) {
        return List.of();
    }

    private LoanRecordDO toDataObject(LoanRecord transaction) {
        return LoanRecordDO.builder()
                .recordId(transaction.getTransactionId())
                .copyId(transaction.getBookCopy().getCopyId())
                .userId(transaction.getPatron().getId())
                .loanDate(transaction.getLoanPeriod().getLoanDate())
                .dueDate(transaction.getLoanPeriod().getDueDate())
                .returnDate(transaction.getLoanPeriod().getReturnDate())
                .status(LoanStatusType.valueOf(transaction.getStatus().name()))
                .build();
    }
}
