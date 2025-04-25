package me.deshark.lms.infrastructure.mapper;

import me.deshark.lms.infrastructure.entity.BookCommentDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookCommentMapper {
    @Insert("INSERT INTO book_comment (comment_id, isbn, user_id, content, rating) " +
            "VALUES (#{commentId}, #{isbn}, #{userId}, #{content}, #{rating})")
    void save(BookCommentDO bookCommentDO);
}
