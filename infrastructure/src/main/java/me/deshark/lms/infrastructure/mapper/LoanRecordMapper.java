package me.deshark.lms.infrastructure.mapper;

import me.deshark.lms.domain.model.borrow.query.BorrowRecordQuery;
import me.deshark.lms.infrastructure.entity.LoanRecordDO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Mapper
public interface LoanRecordMapper {

    @Insert("INSERT INTO loan_record (record_id, copy_id, user_id, loan_date, due_date, return_date, status) " +
            "VALUES (#{recordId}, #{copyId}, #{userId}, #{loanDate}, #{dueDate}, #{returnDate}, #{status}::loan_status_type)")
    void insert(LoanRecordDO loanRecordDO);

    @Update("UPDATE loan_record SET " +
            "copy_id = #{copyId}, " +
            "user_id = #{userId}, " +
            "loan_date = #{loanDate}, " +
            "due_date = #{dueDate}, " +
            "return_date = #{returnDate}, " +
            "status = #{status}::loan_status_type " +
            "WHERE record_id = #{recordId}")
    void update(LoanRecordDO loanRecordDO);

    @Delete("DELETE FROM loan_record WHERE record_id = #{recordId}")
    void delete(@Param("recordId") UUID recordId);

    @Select("SELECT * FROM loan_record WHERE record_id = #{recordId}")
    @Results(id = "loanRecordMap", value = {
            @Result(property = "recordId", column = "record_id"),
            @Result(property = "copyId", column = "copy_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "loanDate", column = "loan_date"),
            @Result(property = "dueDate", column = "due_date"),
            @Result(property = "returnDate", column = "return_date"),
            @Result(property = "status", column = "status")
    })
    LoanRecordDO findById(@Param("recordId") UUID recordId);

    @Select("SELECT * FROM loan_record WHERE user_id = #{userId} ORDER BY loan_date DESC")
    @ResultMap("loanRecordMap")
    List<LoanRecordDO> findByUserId(@Param("userId") UUID userId);

    @Select("SELECT * FROM loan_record WHERE status = #{status}::loan_status_type")
    @ResultMap("loanRecordMap")
    List<LoanRecordDO> findByStatus(@Param("status") String status);

    @Select("SELECT COUNT(*) FROM loan_record WHERE user_id = #{userId} AND status = 'BORROWED'")
    int countActiveLoansByUser(@Param("userId") UUID userId);

    @Update("UPDATE loan_record SET status = 'RETURNED', return_date = CURRENT_TIMESTAMP " +
            "WHERE record_id = #{recordId}")
    void markAsReturned(@Param("recordId") UUID recordId);

    long selectCountByUserId(@Param("userId") UUID userId);

    List<BorrowRecordQuery> findBorrowRecordsPageByUserId(@Param("userId") UUID userId, long offset, long limit);
}
