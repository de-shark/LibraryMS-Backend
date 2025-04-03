package me.deshark.lms.application.cqrs.book.query;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.cqrs.core.QueryHandler;
import me.deshark.lms.domain.model.catalog.entity.BookMetadata;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.FileStorageRepo;
import me.deshark.lms.domain.repository.catalog.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetBookCoverQueryHandler implements QueryHandler<GetBookCoverQuery, String> {

    private final BookRepository bookRepository;
    private final FileStorageRepo fileStorageRepo;


    @Override
    public Optional<String> handle(GetBookCoverQuery query) {

        Isbn isbn = new Isbn(query.getIsbn());

        String objectName = bookRepository.findByIsbn(isbn)
                .map(BookMetadata::getCoverImageUrl)
                .orElseThrow(null);

        return Optional.of(fileStorageRepo.getFileUrl(objectName));
    }
}
