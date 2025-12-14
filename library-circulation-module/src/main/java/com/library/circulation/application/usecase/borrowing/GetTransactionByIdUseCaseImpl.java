package com.library.circulation.application.usecase.borrowing;

import com.library.catalog.domain.entities.Item;
import com.library.catalog.domain.repository.ItemRepository;
import com.library.circulation.application.dto.response.BorrowingTransactionResponse;
import com.library.circulation.application.mapper.BorrowingTransactionMapper;
import com.library.circulation.domain.entities.BorrowingTransaction;
import com.library.circulation.domain.repository.BorrowingTransactionRepository;
import com.library.circulation.domain.valueobject.TransactionId;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import com.library.user.domain.entities.User;
import com.library.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of GetTransactionByIdUseCase.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GetTransactionByIdUseCaseImpl implements GetTransactionByIdUseCase {

    private final BorrowingTransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BorrowingTransactionMapper transactionMapper;

    @Override
    @Transactional(readOnly = true)
    public BorrowingTransactionResponse execute(Long transactionId) {
        log.info("Getting borrowing transaction by ID: {}", transactionId);

        BorrowingTransaction transaction = transactionRepository.findById(TransactionId.of(transactionId))
            .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));

        BorrowingTransactionResponse response = transactionMapper.toResponse(transaction);

        // Enrich response with user and item details
        User user = userRepository.findById(transaction.getUserId())
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Item item = itemRepository.findById(transaction.getItemId())
            .orElseThrow(() -> new AppException(ErrorCode.ITEM_NOT_FOUND));

        response = new BorrowingTransactionResponse(
            response.id(),
            response.userId(),
            user.getProfile().getFullName(),
            response.itemId(),
            item.getBarcode().getValue(),
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

        return response;
    }
}
