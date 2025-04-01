package me.deshark.lms.application.converter;

import me.deshark.lms.application.info.BookInfo;
import me.deshark.lms.domain.model.catalog.entity.BookMetadata;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMetadataConverter {

    BookMetadataConverter INSTANCE = Mappers.getMapper(BookMetadataConverter.class);

    @Mapping(target = "isbn", source = "isbn.isbn")
    BookInfo entityToInfo(BookMetadata book);

    default String mapIsbn(Isbn isbn) {
        return isbn != null ? isbn.getIsbn() : null;
    }
}
