package com.library.circulation.application.usecase.borrowing;

import com.library.circulation.application.dto.response.BorrowingTransactionResponse;

/**
 * Use case for renewing a borrowing transaction.
 */
public interface RenewTransactionUseCase {
    BorrowingTransactionResponse execute(Long transactionId);
}
