package me.deshark.lms.infrastructure.mapper;

import me.deshark.lms.domain.model.catalog.entity.BookCopy;
import me.deshark.lms.domain.model.catalog.vo.BookCopyStatus;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.infrastructure.entity.BookCopyDO;
import me.deshark.lms.infrastructure.enums.CopyStatusType;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 图书副本数据访问接口
 * @author DE_SHARK
 * @date 2025/2/26 19:28
 */
@Mapper
public interface BookCopyMapper {

    /**
     * 插入新的图书副本
     * @param bookCopyDO 图书副本数据对象
     * @return 插入记录数
     */
    @Insert("INSERT INTO book_copy(copy_id, isbn, location, status, loan_count, acquisition_date) " +
            "VALUES(#{copyId}, #{isbn}, #{location}, #{status}, #{loanCount}, #{acquisitionDate})")
    int insert(BookCopyDO bookCopyDO);

    /**
     * 根据副本ID更新图书副本信息
     * @param bookCopyDO 图书副本数据对象
     * @return 更新记录数
     */
    @Update("UPDATE book_copy SET " +
            "isbn = #{isbn}, " +
            "location = #{location}, " +
            "status = #{status}, " +
            "loan_count = #{loanCount}, " +
            "acquisition_date = #{acquisitionDate} " +
            "WHERE copy_id = #{copyId}")
    int update(BookCopyDO bookCopyDO);

    /**
     * 根据副本ID删除图书副本
     * @param copyId 副本ID
     * @return 删除记录数
     */
    @Delete("DELETE FROM book_copy WHERE copy_id = #{copyId}")
    int deleteById(@Param("copyId") UUID copyId);

    /**
     * 根据副本ID查找图书副本
     * @param copyId 副本ID
     * @return 图书副本数据对象
     */
    @Select("SELECT * FROM book_copy WHERE copy_id = #{copyId}")
    Optional<BookCopyDO> findById(@Param("copyId") UUID copyId);

    /**
     * 根据ISBN查找可用的图书副本
     * @param isbn 国际标准书号
     * @return 图书副本数据对象
     */
    @Select("SELECT * FROM book_copy WHERE isbn = #{isbn} AND status = 'AVAILABLE' LIMIT 1")
    Optional<BookCopyDO> findAvailableByIsbn(@Param("isbn") String isbn);

    /**
     * 更新图书副本状态
     * @param copyId 副本ID
     * @param status 新状态
     * @return 更新记录数
     */
    @Update("UPDATE book_copy SET status = #{status} WHERE copy_id = #{copyId}")
    void updateStatus(@Param("copyId") UUID copyId, @Param("status") CopyStatusType status);

    default Optional<BookCopy> toDomain(BookCopyDO bookCopyDO) {
        return Optional.ofNullable(bookCopyDO).map(d -> BookCopy.builder()
                .copyId(d.getCopyId())
                .isbn(new Isbn(d.getIsbn()))
                .status(BookCopyStatus.valueOf(d.getStatus().name()))
                .loanCount(d.getLoanCount())
                .build());
    }

    /**
     * 批量插入图书副本
     * @param bookCopies 图书副本数据对象列表
     */
    @Insert({
            "<script>",
            "INSERT INTO book_copy (copy_id, isbn, location, status, loan_count, acquisition_date) VALUES ",
            "<foreach collection='list' item='item' separator=','>",
            "(#{item.copyId}, #{item.isbn}, #{item.location}, #{item.status}, #{item.loanCount}, #{item.acquisitionDate})",
            "</foreach>",
            "</script>"
    })
    void insertAll(@Param("list") List<BookCopyDO> bookCopies);
}
