package com.library.circulation.application.usecase.borrowing;

import com.library.catalog.domain.entities.Item;
import com.library.catalog.domain.repository.ItemRepository;
import com.library.circulation.application.dto.response.BorrowingTransactionResponse;
import com.library.circulation.application.mapper.BorrowingTransactionMapper;
import com.library.circulation.domain.entities.BorrowingTransaction;
import com.library.circulation.domain.repository.BorrowingTransactionRepository;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import com.library.user.domain.entities.User;
import com.library.user.domain.repository.UserRepository;
import com.library.user.domain.valueobject.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of GetTransactionsByUserIdUseCase.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GetTransactionsByUserIdUseCaseImpl implements GetTransactionsByUserIdUseCase {

    private final BorrowingTransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BorrowingTransactionMapper transactionMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BorrowingTransactionResponse> execute(Long userId) {
        log.info("Getting borrowing transactions for user: {}", userId);

        UserId userIdObj = UserId.of(userId);

        // Verify user exists
        User user = userRepository.findById(userIdObj)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        List<BorrowingTransaction> transactions = transactionRepository.findByUserId(userIdObj);

        return transactions.stream()
            .map(transaction -> {
                BorrowingTransactionResponse response = transactionMapper.toResponse(transaction);

                Item item = itemRepository.findById(transaction.getItemId()).orElse(null);

                return new BorrowingTransactionResponse(
                    response.id(),
                    response.userId(),
                    user.getProfile().getFullName(),
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
