package me.deshark.lms.domain.repository.borrow;

import me.deshark.lms.domain.model.borrow.entity.Patron;

import java.util.UUID;

/**
 * @author DE_SHARK
 */
public interface PatronRepository {
    Patron findById(UUID id);

    Patron findByUsername(String username);
}
