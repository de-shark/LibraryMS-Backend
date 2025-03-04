package me.deshark.lms.infrastructure.mapper;

import me.deshark.lms.infrastructure.entity.BookCatalogDO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

/**
 * 图书信息数据访问接口
 * @author deshark
 */
@Mapper
public interface BookCatalogMapper {

    @Select("SELECT EXISTS (SELECT 1 FROM book_catalog WHERE isbn = #{isbn})")
    boolean existsByIsbn(@Param("isbn") String isbn);

    /**
     * 根据ISBN查询图书信息
     */
    @Select("SELECT * FROM book_catalog WHERE isbn = #{isbn}")
    Optional<BookCatalogDO> findByIsbn(@Param("isbn") String isbn);

    /**
     * 插入图书信息
     */
    @Insert("INSERT INTO book_catalog (isbn, title, author, publisher, publish_year, description, cover_url) " +
            "VALUES (#{isbn}, #{title}, #{author}, #{publisher}, #{publishYear}, #{description}, #{coverUrl})")
    void insert(BookCatalogDO bookCatalogDO);

    /**
     * 删除图书信息
     */
    @Delete("DELETE FROM book_catalog WHERE isbn = #{isbn}")
    void delete(@Param("isbn") String isbn);

    /**
     * 查询所有图书信息
     */
    @Select("SELECT * FROM book_catalog ORDER BY created_at DESC")
    List<BookCatalogDO> findAll();

    /**
     * 根据标题模糊查询图书信息
     */
    @Select("SELECT * FROM book_catalog WHERE title LIKE CONCAT('%', #{title}, '%')")
    List<BookCatalogDO> findByTitleContaining(@Param("title") String title);

    /**
     * 根据作者查询图书信息
     */
    @Select("SELECT * FROM book_catalog WHERE author = #{author}")
    List<BookCatalogDO> findByAuthor(@Param("author") String author);

    /**
     * 统计图书数量
     */
    @Select("SELECT COUNT(*) FROM book_catalog")
    long count();

}
