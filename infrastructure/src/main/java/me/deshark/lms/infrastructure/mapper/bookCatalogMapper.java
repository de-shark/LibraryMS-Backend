package me.deshark.lms.infrastructure.mapper;

import me.deshark.lms.domain.model.catalog.entity.BookCatalog;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.infrastructure.entity.BookInfoDO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 图书信息数据访问接口
 * @author deshark
 */
@Mapper
public interface bookCatalogMapper {

    /**
     * 根据ISBN查询图书信息
     */
    @Select("SELECT * FROM book_catalog WHERE isbn = #{isbn}")
    Optional<BookInfoDO> findByIsbn(@Param("isbn") String isbn);

    /**
     * 插入图书信息
     */
    @Insert("INSERT INTO book_catalog (isbn, title, author, publisher, publish_year, description, cover_url) " +
            "VALUES (#{isbn}, #{title}, #{author}, #{publisher}, #{publishYear}, #{description}, #{coverUrl})")
    int insert(BookInfoDO bookInfoDO);

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
    int update(BookInfoDO bookInfoDO);

    /**
     * 删除图书信息
     */
    @Delete("DELETE FROM book_catalog WHERE isbn = #{isbn}")
    int deleteByIsbn(@Param("isbn") String isbn);

    /**
     * 查询所有图书信息
     */
    @Select("SELECT * FROM book_catalog ORDER BY created_at DESC")
    List<BookInfoDO> findAll();

    /**
     * 根据标题模糊查询图书信息
     */
    @Select("SELECT * FROM book_catalog WHERE title LIKE CONCAT('%', #{title}, '%')")
    List<BookInfoDO> findByTitleContaining(@Param("title") String title);

    /**
     * 根据作者查询图书信息
     */
    @Select("SELECT * FROM book_catalog WHERE author = #{author}")
    List<BookInfoDO> findByAuthor(@Param("author") String author);

    /**
     * 统计图书数量
     */
    @Select("SELECT COUNT(*) FROM book_catalog")
    long count();

    /**
     * 将DO转换为领域实体
     */
    default BookCatalog toDomainEntity(BookInfoDO bookInfoDO) {
        Objects.requireNonNull(bookInfoDO, "BookInfoDO不能为空");
        Objects.requireNonNull(bookInfoDO.getIsbn(), "ISBN不能为空");

        return new BookCatalog(new Isbn(bookInfoDO.getIsbn()), bookInfoDO.getTitle(), bookInfoDO.getAuthor());
    }

    /**
     * 将领域实体转换为DO
     */
    default BookInfoDO toDataObject(BookCatalog bookCatalog) {
        Objects.requireNonNull(bookCatalog, "BookInfo不能为空");
        Objects.requireNonNull(bookCatalog.getIsbn(), "ISBN不能为空");

        return BookInfoDO.builder()
                .isbn(bookCatalog.getIsbn().getIsbn())
                .title(bookCatalog.getTitle())
                .author(bookCatalog.getAuthor())
                .build();
    }
}
