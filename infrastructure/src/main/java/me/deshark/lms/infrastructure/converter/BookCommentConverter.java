package me.deshark.lms.infrastructure.converter;

import me.deshark.lms.domain.model.comment.BookComment;
import me.deshark.lms.infrastructure.entity.BookCommentDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookCommentConverter {

    BookCommentConverter INSTANCE = Mappers.getMapper(BookCommentConverter.class);

    @Mapping(target = "isbn", source = "isbn.isbn")
    @Mapping(target = "parentCommentId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    BookCommentDO modelToDO(BookComment bookComment);

    BookComment doToModel(BookCommentDO bookCommentDO);
}
