package me.deshark.lms.infrastructure;

import me.deshark.lms.domain.model.catalog.entity.BookMetadata;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.infrastructure.entity.BookDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookConverter {

    BookConverter INSTANCE = Mappers.getMapper(BookConverter.class);

    @Mapping(target = "isbn", source = "isbn")
    @Mapping(target = "coverImageUrl", ignore = true)
    @Mapping(target = "currentCopyCount", ignore = true)
    @Mapping(target = "availableCopyCount", ignore = true)
    BookMetadata doToEntity(BookDO bookDO);

    default Isbn mapIsbn(String value) {
        if (value == null) {
            return null;
        }
        return new Isbn(value);
    }
}
