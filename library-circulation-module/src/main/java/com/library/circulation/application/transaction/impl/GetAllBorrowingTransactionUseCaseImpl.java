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
    public PageResponse<TransactionListResponse> execute(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page,size, sort);
        Page<TransactionListResponse> transactions = jpaRepository.getAllTransactionWithPagination(pageable);
        return PageResponse.from(transactions);
    }
}
