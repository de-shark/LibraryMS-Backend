package me.deshark.lms.domain.service.catalog;

import me.deshark.lms.domain.model.catalog.entity.BookMetadata;
import me.deshark.lms.domain.model.catalog.vo.Isbn;

/**
 * @author DE_SHARK
 */
public interface BookMetadataProvider {
    BookMetadata fetch(Isbn isbn);
}