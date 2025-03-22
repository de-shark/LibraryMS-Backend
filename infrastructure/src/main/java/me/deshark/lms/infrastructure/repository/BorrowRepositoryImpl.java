package me.deshark.lms.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.deshark.lms.domain.model.borrow.aggregate.LoanRecord;
import me.deshark.lms.domain.model.borrow.entity.Patron;
import me.deshark.lms.domain.model.borrow.vo.LoanPeriod;
import me.deshark.lms.domain.model.borrow.vo.LoanStatus;
import me.deshark.lms.domain.model.catalog.entity.BookCopy;
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
@Slf4j
public class BorrowRepositoryImpl implements BorrowRepository {

    private final LoanRecordMapper loanRecordMapper;

    @Override
    public void save(LoanRecord record) {
        loanRecordMapper.insert(toDataObject(record));
    }

    @Override
    public Optional<LoanRecord> findById(UUID recordId) {
        log.info("正在查找借阅记录: {}", recordId);
        return loanRecordMapper.findById(recordId).map(this::toDomainModel);
    }

    @Override
    public void update(LoanRecord record) {
        log.info("正在更新借阅记录: {}", record.getRecordId());
        loanRecordMapper.update(toDataObject(record));
    }

    @Override
    public List<LoanRecord> findHistoricalBorrowsByPatron(UUID patronId) {
        return List.of();
    }

    @Override
    public List<LoanRecord> findCurrentBorrowsByPatron(UUID patronId) {
        return List.of();
    }

    private LoanRecordDO toDataObject(LoanRecord record) {
        return LoanRecordDO.builder()
                .recordId(record.getRecordId())
                .copyId(record.getBookCopy().getCopyId())
                .userId(record.getPatron().getId())
                .loanDate(record.getLoanPeriod().getLoanDate())
                .dueDate(record.getLoanPeriod().getDueDate())
                .returnDate(record.getLoanPeriod().getReturnDate())
                .status(LoanStatusType.valueOf(record.getStatus().name()))
                .build();
    }

    private LoanRecord toDomainModel(LoanRecordDO recordDO) {
        BookCopy bookCopy = BookCopy.builder().copyId(recordDO.getCopyId()).build();
        Patron patron = Patron.builder().id(recordDO.getUserId()).build();
        LoanPeriod loanPeriod = LoanPeriod.builder()
                .loanDate(recordDO.getLoanDate())
                .dueDate(recordDO.getDueDate())
                .returnDate(recordDO.getReturnDate())
                .build();

        return LoanRecord.builder()
                .recordId(recordDO.getRecordId())
                .bookCopy(bookCopy)
                .patron(patron)
                .loanPeriod(loanPeriod)
                .status(LoanStatus.valueOf(recordDO.getStatus().name()))
                .build();
    }
}
