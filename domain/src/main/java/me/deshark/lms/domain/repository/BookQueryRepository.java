package me.deshark.lms.domain.repository;

import me.deshark.lms.domain.model.catalog.vo.Isbn;


/**
 * @author DE_SHARK
 */
public interface BookQueryRepository {
    void findBookByIsbn(Isbn isbn);
}
