package me.deshark.lms.interfaces.converter;

import me.deshark.lms.application.info.BookInfo;
import me.deshark.lms.interfaces.dto.BookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface BookDetailConverter {

    BookDetailConverter INSTANCE = Mappers.getMapper(BookDetailConverter.class);

    BookResponse infoToResponse(BookInfo bookInfo);

}
