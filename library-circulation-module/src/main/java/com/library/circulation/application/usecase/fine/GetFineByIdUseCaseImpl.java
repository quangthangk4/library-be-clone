package com.library.circulation.application.usecase.fine;

import com.library.catalog.domain.entities.Item;
import com.library.catalog.domain.repository.ItemRepository;
import com.library.circulation.application.dto.response.FineResponse;
import com.library.circulation.application.mapper.FineMapper;
import com.library.circulation.domain.entities.BorrowingTransaction;
import com.library.circulation.domain.entities.Fine;
import com.library.circulation.domain.repository.BorrowingTransactionRepository;
import com.library.circulation.domain.repository.FineRepository;
import com.library.circulation.domain.valueobject.FineId;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import com.library.user.domain.entities.User;
import com.library.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of GetFineByIdUseCase
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GetFineByIdUseCaseImpl implements GetFineByIdUseCase {

    private final FineRepository fineRepository;
    private final BorrowingTransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final FineMapper fineMapper;

    @Override
    @Transactional(readOnly = true)
    public FineResponse execute(Long fineId) {
        log.info("Getting fine with ID: {}", fineId);

        // Find fine
        Fine fine = fineRepository.findById(FineId.of(fineId))
            .orElseThrow(() -> new AppException(ErrorCode.FINE_NOT_FOUND));

        // Find associated transaction
        BorrowingTransaction transaction = transactionRepository.findById(fine.getTransactionId())
            .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));

        // Find user
        User user = userRepository.findById(transaction.getUserId())
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Find item
        Item item = itemRepository.findById(transaction.getItemId())
            .orElseThrow(() -> new AppException(ErrorCode.ITEM_NOT_FOUND));

        // Map to response with enriched data
        FineResponse response = fineMapper.toResponse(fine);
        FineResponse enrichedResponse = new FineResponse(
            response.id(),
            response.transactionId(),
            user.getId().getValue(),
            user.getFullName(),
            item.getBarcode(),
            response.fineAmount(),
            response.fineDate(),
            response.paymentStatus(),
            response.paidDate()
        );

        log.info("Successfully retrieved fine with ID: {}", fineId);

        return enrichedResponse;
    }
}
