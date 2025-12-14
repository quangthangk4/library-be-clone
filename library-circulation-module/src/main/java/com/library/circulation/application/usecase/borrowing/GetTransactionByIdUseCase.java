package com.library.circulation.application.usecase.borrowing;

import com.library.circulation.application.dto.response.BorrowingTransactionResponse;

/**
 * Use case for getting a borrowing transaction by ID.
 */
public interface GetTransactionByIdUseCase {
    BorrowingTransactionResponse execute(Long transactionId);
}
