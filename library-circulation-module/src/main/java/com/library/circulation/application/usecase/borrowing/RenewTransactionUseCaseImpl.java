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
 * Implementation of RenewTransactionUseCase.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RenewTransactionUseCaseImpl implements RenewTransactionUseCase {

    private final BorrowingTransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BorrowingTransactionMapper transactionMapper;

    @Override
    @Transactional
    public BorrowingTransactionResponse execute(Long transactionId) {
        log.info("Renewing transaction: {}", transactionId);

        TransactionId txnId = TransactionId.of(transactionId);

        // Find transaction
        BorrowingTransaction transaction = transactionRepository.findById(txnId)
            .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));

        // Check if renewal is possible
        if (!transaction.canRenew()) {
            throw new AppException(ErrorCode.CANNOT_RENEW_TRANSACTION);
        }

        // Renew transaction
        transaction.renew();

        // Save transaction
        BorrowingTransaction savedTransaction = transactionRepository.save(transaction);

        // Build response
        BorrowingTransactionResponse response = transactionMapper.toResponse(savedTransaction);

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

        log.info("Successfully renewed transaction: {}", transactionId);

        return response;
    }
}
