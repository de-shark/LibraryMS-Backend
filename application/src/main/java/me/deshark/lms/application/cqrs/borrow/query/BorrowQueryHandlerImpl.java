package me.deshark.lms.application.cqrs.borrow.query;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.info.BorrowRecord;
import me.deshark.lms.application.info.PageResult;
import me.deshark.lms.domain.model.borrow.aggregate.LoanRecord;
import me.deshark.lms.domain.model.common.Page;
import me.deshark.lms.domain.repository.auth.UserQueryRepository;
import me.deshark.lms.domain.repository.borrow.BorrowQueryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Service
@RequiredArgsConstructor
public class BorrowQueryHandlerImpl implements BorrowQueryHandler {

    private final UserQueryRepository userQueryRepository;
    private final BorrowQueryRepository borrowQueryRepository;

    @Override
    public PageResult<BorrowRecord> handle(GetBorrowRecordsQuery query) {

        UUID userId = userQueryRepository.getUserIdByUsername(query.getUsername());

        Page<LoanRecord> page = borrowQueryRepository.getPatronTransactions(
                userId,
                query.getPage() - 1,
                query.getSize()
        );

        List<BorrowRecord> list = page.getContent().stream()
                .map(transaction -> BorrowRecord.builder()
//                        .isbn()
//                        .title()
//                        .author()
                        .borrowDate(transaction.getLoanPeriod().getLoanDate())
                        .dueDate(transaction.getLoanPeriod().getDueDate())
                        .returnDate(transaction.getLoanPeriod().getReturnDate())
                        .status(transaction.getStatus().name())
                        .build())
                .toList();

        return PageResult.of(
                list,
                query.getPage(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }
}
