package com.library.circulation.application.usecase.borrowing;

import com.library.circulation.application.dto.response.BorrowingTransactionResponse;

import java.util.List;

/**
 * Use case for getting borrowing transactions by user ID.
 */
public interface GetTransactionsByUserIdUseCase {
    List<BorrowingTransactionResponse> execute(Long userId);
}
