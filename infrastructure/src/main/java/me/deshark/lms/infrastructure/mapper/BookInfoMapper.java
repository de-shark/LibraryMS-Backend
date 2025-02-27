package me.deshark.lms.infrastructure.mapper;

import me.deshark.lms.infrastructure.entity.BookInfoDO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

/**
 * 图书信息数据访问接口
 * @author deshark
 */
@Mapper
public interface BookInfoMapper {

    /**
     * 根据ISBN查询图书信息
     */
    @Select("SELECT * FROM book_info WHERE isbn = #{isbn}")
    Optional<BookInfoDO> findByIsbn(@Param("isbn") String isbn);

    /**
     * 插入图书信息
     */
    @Insert("INSERT INTO book_info (isbn, title, author, publisher, publish_year, description, cover_url) " +
            "VALUES (#{isbn}, #{title}, #{author}, #{publisher}, #{publishYear}, #{description}, #{coverUrl})")
    int insert(BookInfoDO bookInfoDO);

    /**
     * 更新图书信息
     */
    @Update("UPDATE book_info SET " +
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
    @Delete("DELETE FROM book_info WHERE isbn = #{isbn}")
    int deleteByIsbn(@Param("isbn") String isbn);

    /**
     * 查询所有图书信息
     */
    @Select("SELECT * FROM book_info ORDER BY created_at DESC")
    List<BookInfoDO> findAll();

    /**
     * 根据标题模糊查询图书信息
     */
    @Select("SELECT * FROM book_info WHERE title LIKE CONCAT('%', #{title}, '%')")
    List<BookInfoDO> findByTitleContaining(@Param("title") String title);

    /**
     * 根据作者查询图书信息
     */
    @Select("SELECT * FROM book_info WHERE author = #{author}")
    List<BookInfoDO> findByAuthor(@Param("author") String author);

    /**
     * 统计图书数量
     */
    @Select("SELECT COUNT(*) FROM book_info")
    long count();
}
