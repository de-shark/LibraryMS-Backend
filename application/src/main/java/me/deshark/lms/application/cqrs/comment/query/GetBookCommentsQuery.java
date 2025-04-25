package me.deshark.lms.application.cqrs.comment.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.deshark.lms.application.cqrs.core.Query;
import me.deshark.lms.application.info.BookCommentInfo;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBookCommentsQuery implements Query<List<BookCommentInfo>> {
    private String isbn;
}