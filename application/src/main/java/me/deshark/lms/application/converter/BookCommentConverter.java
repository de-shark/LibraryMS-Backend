package me.deshark.lms.application.converter;

import me.deshark.lms.application.cqrs.comment.AddBookCommentCommand;
import me.deshark.lms.application.info.BookCommentInfo;
import me.deshark.lms.common.utils.GUID;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.model.comment.BookComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface BookCommentConverter {
    BookCommentConverter INSTANCE = Mappers.getMapper(BookCommentConverter.class);

    @Mapping(target = "commentId", expression  = "java(generateCommentId())")
    BookComment infoToModel(AddBookCommentCommand command);
    default UUID generateCommentId() {
        return GUID.v7();
    }
    default Isbn mapIsbn(String value) {
        return new Isbn(value);
    }

    @Mapping(target = "createdAt", ignore = true)
    BookCommentInfo modelToInfo(BookComment comment);
    default String mapIsbn(Isbn value) {
        return value.getIsbn();
    }
}
