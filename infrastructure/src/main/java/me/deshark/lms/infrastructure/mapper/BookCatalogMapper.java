package me.deshark.lms.infrastructure.mapper;

import me.deshark.lms.domain.model.catalog.entity.BookCatalog;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.infrastructure.entity.BookCatalogDO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Objects;
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
     * 更新图书信息
     */
    @Update("UPDATE book_catalog SET " +
            "title = #{title}, " +
            "author = #{author}, " +
            "publisher = #{publisher}, " +
            "publish_year = #{publishYear}, " +
            "description = #{description}, " +
            "cover_url = #{coverUrl} " +
            "WHERE isbn = #{isbn}")
    int update(BookCatalogDO bookCatalogDO);

    /**
     * 删除图书信息
     */
    @Delete("DELETE FROM book_catalog WHERE isbn = #{isbn}")
    int deleteByIsbn(@Param("isbn") String isbn);

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

    /**
     * 将DO转换为领域实体
     */
    default BookCatalog toDomainEntity(BookCatalogDO bookCatalogDO) {
        Objects.requireNonNull(bookCatalogDO, "BookInfoDO不能为空");
        Objects.requireNonNull(bookCatalogDO.getIsbn(), "ISBN不能为空");

        return new BookCatalog(new Isbn(bookCatalogDO.getIsbn()), bookCatalogDO.getTitle(), bookCatalogDO.getAuthor());
    }

    /**
     * 将领域实体转换为DO
     */
    default BookCatalogDO toDataObject(BookCatalog bookCatalog) {
        Objects.requireNonNull(bookCatalog, "BookInfo不能为空");
        Objects.requireNonNull(bookCatalog.getIsbn(), "ISBN不能为空");

        return BookCatalogDO.builder()
                .isbn(bookCatalog.getIsbn().toString())
                .title(bookCatalog.getTitle())
                .author(bookCatalog.getAuthor())
                .build();
    }
}
