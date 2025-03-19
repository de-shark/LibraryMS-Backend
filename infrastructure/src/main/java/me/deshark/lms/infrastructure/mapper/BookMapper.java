package me.deshark.lms.infrastructure.mapper;

import me.deshark.lms.infrastructure.entity.BookDO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 图书信息数据访问接口
 * @author deshark
 */
@Mapper
public interface BookMapper {

    @Select("SELECT EXISTS (SELECT 1 FROM book WHERE isbn = #{isbn})")
    boolean existsByIsbn(@Param("isbn") String isbn);

    /**
     * 根据ISBN查询图书信息
     */
    @Select("SELECT * FROM book WHERE isbn = #{isbn}")
    Optional<BookDO> findByIsbn(@Param("isbn") String isbn);

    /**
     * 插入图书信息
     */
    @Insert("INSERT INTO book (isbn, title, author, publisher, published_date) " +
            "VALUES (#{isbn}, #{title}, #{author}, #{publisher}, #{publishedDate})")
    void insert(BookDO bookDO);

    /**
     * 删除图书信息
     */
    @Delete("DELETE FROM book WHERE isbn = #{isbn}")
    void delete(@Param("isbn") String isbn);

    /**
     * 查询所有图书信息
     */
    @Select("SELECT * FROM book ORDER BY created_at DESC")
    List<BookDO> findAll();

    /**
     * 根据标题模糊查询图书信息
     */
    @Select("SELECT * FROM book WHERE title LIKE CONCAT('%', #{title}, '%')")
    List<BookDO> findByTitleContaining(@Param("title") String title);

    /**
     * 根据作者查询图书信息
     */
    @Select("SELECT * FROM book WHERE author = #{author}")
    List<BookDO> findByAuthor(@Param("author") String author);

    /**
     * 统计图书数量
     */
    @Select("SELECT COUNT(*) FROM book")
    long count();

    @Select("SELECT isbn, current_copy_count FROM book_inventory_view WHERE current_copy_count < #{minCopyCount}")
    List<Map<String, Integer>> findBooksWithLowInventory(@Param("minCopyCount") int minCopyCount);
}
