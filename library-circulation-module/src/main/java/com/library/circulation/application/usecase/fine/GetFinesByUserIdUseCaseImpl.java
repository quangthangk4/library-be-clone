package com.library.circulation.application.usecase.fine;

import com.library.catalog.domain.entities.Item;
import com.library.catalog.domain.repository.ItemRepository;
import com.library.catalog.domain.valueobject.ItemId;
import com.library.circulation.application.dto.response.FineResponse;
import com.library.circulation.application.mapper.FineMapper;
import com.library.circulation.domain.entities.BorrowingTransaction;
import com.library.circulation.domain.entities.Fine;
import com.library.circulation.domain.repository.BorrowingTransactionRepository;
import com.library.circulation.domain.repository.FineRepository;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of GetFinesByUserIdUseCase
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GetFinesByUserIdUseCaseImpl implements GetFinesByUserIdUseCase {

    private final FineRepository fineRepository;
    private final BorrowingTransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final FineMapper fineMapper;

    @Override
    @Transactional(readOnly = true)
    public List<FineResponse> execute(Long userId) {
        log.info("Getting fines for user ID: {}", userId);

        // Validate user exists
        User user = userRepository.findById(UserId.of(userId))
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Find all fines by user
        List<Fine> fines = fineRepository.findByUserId(UserId.of(userId));

        if (fines.isEmpty()) {
            return List.of();
        }

        // Get all unique transaction IDs
        List<TransactionId> transactionIds = fines.stream()
            .map(Fine::getTransactionId)
            .distinct()
            .toList();

        // Batch load transactions
        Map<Long, BorrowingTransaction> transactionMap = transactionIds.stream()
            .map(transactionRepository::findById)
            .filter(opt -> opt.isPresent())
            .map(opt -> opt.get())
            .collect(Collectors.toMap(
                t -> t.getId().getValue(),
                t -> t
            ));

        // Get all unique item IDs from transactions
        List<ItemId> itemIds = transactionMap.values().stream()
            .map(BorrowingTransaction::getItemId)
            .distinct()
            .toList();

        // Batch load items
        Map<Long, Item> itemMap = itemIds.stream()
            .map(itemRepository::findById)
            .filter(opt -> opt.isPresent())
            .map(opt -> opt.get())
            .collect(Collectors.toMap(
                i -> i.getId().getValue(),
                i -> i
            ));

        // Map to responses
        List<FineResponse> responses = fines.stream()
            .map(fine -> {
                BorrowingTransaction transaction = transactionMap.get(fine.getTransactionId().getValue());
                if (transaction == null) {
                    log.warn("Transaction not found for fine ID: {}", fine.getId().getValue());
                    return null;
                }

                Item item = itemMap.get(transaction.getItemId().getValue());
                if (item == null) {
                    log.warn("Item not found for transaction ID: {}", transaction.getId().getValue());
                    return null;
                }

                FineResponse baseResponse = fineMapper.toResponse(fine);
                return new FineResponse(
                    baseResponse.id(),
                    baseResponse.transactionId(),
                    user.getId().getValue(),
                    user.getProfile().getFullName(),
                    item.getBarcode().getValue(),
                    baseResponse.fineAmount(),
                    baseResponse.fineDate(),
                    baseResponse.paymentStatus(),
                    baseResponse.paidDate()
                );
            })
            .filter(response -> response != null)
            .collect(Collectors.toList());

        log.info("Successfully retrieved {} fines for user ID: {}", responses.size(), userId);

        return responses;
    }
}
