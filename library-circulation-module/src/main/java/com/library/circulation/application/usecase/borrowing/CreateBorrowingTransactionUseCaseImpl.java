package com.library.circulation.application.usecase.borrowing;

import com.library.catalog.domain.entities.Item;
import com.library.catalog.domain.repository.ItemRepository;
import com.library.catalog.domain.valueobject.ItemId;
import com.library.circulation.application.dto.request.CreateBorrowingTransactionRequest;
import com.library.circulation.application.dto.response.BorrowingTransactionResponse;
import com.library.circulation.application.mapper.BorrowingTransactionMapper;
import com.library.circulation.domain.entities.BorrowingTransaction;
import com.library.circulation.domain.repository.BorrowingTransactionRepository;
import com.library.circulation.domain.service.CirculationDomainService;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import com.library.user.domain.entities.User;
import com.library.user.domain.repository.UserRepository;
import com.library.user.domain.valueobject.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Implementation of CreateBorrowingTransactionUseCase.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CreateBorrowingTransactionUseCaseImpl implements CreateBorrowingTransactionUseCase {

    private final BorrowingTransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final CirculationDomainService circulationDomainService;
    private final BorrowingTransactionMapper transactionMapper;

    @Override
    @Transactional
    public BorrowingTransactionResponse execute(CreateBorrowingTransactionRequest request) {
        log.info("Creating borrowing transaction for user: {}, item: {}", request.userId(), request.itemId());

        UserId userId = UserId.of(request.userId());
        ItemId itemId = ItemId.of(request.itemId());
        UserId librarianIdIssue = request.librarianIdIssue() != null ?
            UserId.of(request.librarianIdIssue()) : null;

        // Validate user can borrow
        circulationDomainService.validateUserCanBorrow(userId);
        circulationDomainService.checkUserBorrowLimit(userId);

        // Validate item is available
        circulationDomainService.validateItemAvailableForBorrow(itemId);

        // Calculate due date
        LocalDateTime borrowedDate = LocalDateTime.now();
        LocalDate dueDate = circulationDomainService.calculateDueDate(borrowedDate);

        // Create transaction
        BorrowingTransaction transaction = BorrowingTransaction.create(
            userId,
            itemId,
            librarianIdIssue,
            dueDate
        );

        // Mark item as borrowed
        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new AppException(ErrorCode.ITEM_NOT_FOUND));
        item.markAsBorrowed();
        itemRepository.save(item);

        // Save transaction
        BorrowingTransaction savedTransaction = transactionRepository.save(transaction);

        // Build response with enriched data
        BorrowingTransactionResponse response = transactionMapper.toResponse(savedTransaction);

        // Enrich response with user and item details
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        response = new BorrowingTransactionResponse(
            response.id(),
            response.userId(),
            user.getProfile().getFullName(),
            response.itemId(),
            item.getBarcode().getValue(),
            null, // publicationTitle - would need publication repository
            response.librarianIdIssue(),
            response.librarianIdReturn(),
            response.borrowedDate(),
            response.dueDate(),
            response.returnedDate(),
            response.status(),
            response.renewalCount(),
            response.daysOverdue()
        );

        log.info("Successfully created borrowing transaction with ID: {}", savedTransaction.getId().getValue());

        return response;
    }
}
