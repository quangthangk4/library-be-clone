package com.library.circulation.application.usecase.borrowing;

import com.library.catalog.domain.entities.Item;
import com.library.catalog.domain.repository.ItemRepository;
import com.library.circulation.application.dto.response.BorrowingTransactionResponse;
import com.library.circulation.application.mapper.BorrowingTransactionMapper;
import com.library.circulation.domain.entities.BorrowingTransaction;
import com.library.circulation.domain.repository.BorrowingTransactionRepository;
import com.library.user.domain.entities.User;
import com.library.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of GetOverdueTransactionsUseCase.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GetOverdueTransactionsUseCaseImpl implements GetOverdueTransactionsUseCase {

    private final BorrowingTransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BorrowingTransactionMapper transactionMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BorrowingTransactionResponse> execute() {
        log.info("Getting all overdue transactions");

        List<BorrowingTransaction> transactions = transactionRepository.findOverdueTransactions();

        return transactions.stream()
            .map(transaction -> {
                BorrowingTransactionResponse response = transactionMapper.toResponse(transaction);

                User user = userRepository.findById(transaction.getUserId()).orElse(null);
                Item item = itemRepository.findById(transaction.getItemId()).orElse(null);

                return new BorrowingTransactionResponse(
                    response.id(),
                    response.userId(),
                    user != null ? user.getProfile().getFullName() : null,
                    response.itemId(),
                    item != null ? item.getBarcode().getValue() : null,
                    null, // publicationTitle
                    response.librarianIdIssue(),
                    response.librarianIdReturn(),
                    response.borrowedDate(),
                    response.dueDate(),
                    response.returnedDate(),
                    response.status(),
                    response.renewalCount(),
                    response.daysOverdue()
                );
            })
            .collect(Collectors.toList());
    }
}
