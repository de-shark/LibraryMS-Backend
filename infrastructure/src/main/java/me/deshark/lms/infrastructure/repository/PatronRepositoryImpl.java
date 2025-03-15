package me.deshark.lms.infrastructure.repository;

import me.deshark.lms.domain.model.borrowing.entity.Patron;
import me.deshark.lms.domain.repository.borrow.PatronRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Repository
public class PatronRepositoryImpl implements PatronRepository {
    @Override
    public Patron findById(UUID id) {
        return null;
    }

    @Override
    public Patron findByUsername(String username) {
        return null;
    }
}
