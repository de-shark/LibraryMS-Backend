package me.deshark.lms.infrastructure.mapper;

import me.deshark.lms.infrastructure.entity.BookInventoryViewDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

/**
 * @author DE_SHARK
 */
@Mapper
public interface BookInventoryMapper {

    @Select("SELECT * FROM book_inventory_view WHERE current_copy_count < #{minCopyCount}")
    Optional<BookInventoryViewDO> findByIsbn(String isbn);
}
