package me.deshark.lms.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.BookQueryRepository;
import org.springframework.stereotype.Repository;

/**
 * @author DE_SHARK
 */
@Repository
@RequiredArgsConstructor
public class BookQueryRepositoryImpl implements BookQueryRepository {

    @Override
    public void findBookByIsbn(Isbn isbn) {

    }
}
