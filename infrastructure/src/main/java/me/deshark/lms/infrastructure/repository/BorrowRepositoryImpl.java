package me.deshark.lms.infrastructure.repository;

import me.deshark.lms.domain.model.borrowing.aggregate.BorrowTransaction;
import me.deshark.lms.domain.repository.borrow.BorrowRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Repository
public class BorrowRepositoryImpl implements BorrowRepository {
    @Override
    public void save(BorrowTransaction transaction) {

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
}
