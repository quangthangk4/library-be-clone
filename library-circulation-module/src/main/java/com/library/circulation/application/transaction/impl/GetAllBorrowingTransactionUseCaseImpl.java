package com.library.circulation.application.transaction.impl;

import com.library.circulation.application.transaction.GetAllBorrowingTransactionUseCase;
import com.library.circulation.dto.response.TransactionListResponse;
import com.library.circulation.infrastructure.persistence.repository.BorrowingTransactionJpaRepository;
import com.library.shared.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAllBorrowingTransactionUseCaseImpl implements GetAllBorrowingTransactionUseCase {

    private final BorrowingTransactionJpaRepository jpaRepository;

    @Override
    public PageResponse<TransactionListResponse> execute(int page, int size, String keyword, String sortBy, String sortDir) {
        String sortField = switch (sortBy == null ? "" : sortBy) {
            case "borrowedDate"  -> "borrowedDate";
            case "returnedDate"  -> "returnedDate";
            case "dueDate"       -> "dueDate";
            default              -> "createdAt";
        };
        Sort.Direction direction = "ASC".equalsIgnoreCase(sortDir) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        String kw = (keyword == null || keyword.isBlank()) ? "" : keyword.trim();
        Page<TransactionListResponse> transactions = jpaRepository.searchTransactions(kw, pageable);
        return PageResponse.from(transactions);
    }
}
