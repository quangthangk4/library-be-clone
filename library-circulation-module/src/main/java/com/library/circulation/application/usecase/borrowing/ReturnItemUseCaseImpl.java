package com.library.circulation.application.usecase.borrowing;

import com.library.catalog.domain.entities.Item;
import com.library.catalog.domain.repository.ItemRepository;
import com.library.circulation.application.dto.request.ReturnItemRequest;
import com.library.circulation.application.dto.response.BorrowingTransactionResponse;
import com.library.circulation.application.mapper.BorrowingTransactionMapper;
import com.library.circulation.domain.entities.BorrowingTransaction;
import com.library.circulation.domain.entities.Fine;
import com.library.circulation.domain.repository.BorrowingTransactionRepository;
import com.library.circulation.domain.repository.FineRepository;
import com.library.circulation.domain.service.CirculationDomainService;
import com.library.circulation.domain.valueobject.TransactionId;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import com.library.user.domain.entities.User;
import com.library.user.domain.repository.UserRepository;
import com.library.user.domain.valueobject.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Implementation of ReturnItemUseCase.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReturnItemUseCaseImpl implements ReturnItemUseCase {

    private final BorrowingTransactionRepository transactionRepository;
    private final FineRepository fineRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final CirculationDomainService circulationDomainService;
    private final BorrowingTransactionMapper transactionMapper;

    @Override
    @Transactional
    public BorrowingTransactionResponse execute(ReturnItemRequest request) {
        log.info("Returning item for transaction: {}", request.transactionId());

        TransactionId transactionId = TransactionId.of(request.transactionId());
        UserId librarianIdReturn = request.librarianIdReturn() != null ?
            UserId.of(request.librarianIdReturn()) : null;

        // Find transaction
        BorrowingTransaction transaction = transactionRepository.findById(transactionId)
            .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));

        // Calculate and create fine if overdue
        if (transaction.isOverdue()) {
            int daysOverdue = transaction.calculateDaysOverdue();
            BigDecimal fineAmount = circulationDomainService.calculateFineAmount(daysOverdue);

            Fine fine = Fine.create(transactionId, fineAmount);
            fineRepository.save(fine);

            log.info("Created fine for overdue transaction. Amount: {}, Days: {}", fineAmount, daysOverdue);
        }

        // Return the item
        transaction.returnItem(librarianIdReturn);

        // Mark item as available
        Item item = itemRepository.findById(transaction.getItemId())
            .orElseThrow(() -> new AppException(ErrorCode.ITEM_NOT_FOUND));
        item.returnItem();
        itemRepository.save(item);

        // Save transaction
        BorrowingTransaction savedTransaction = transactionRepository.save(transaction);

        // Build response
        BorrowingTransactionResponse response = transactionMapper.toResponse(savedTransaction);

        User user = userRepository.findById(transaction.getUserId())
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

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

        log.info("Successfully returned item for transaction: {}", transactionId.getValue());

        return response;
    }
}
