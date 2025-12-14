package com.library.circulation.application.usecase.borrowing;

import com.library.circulation.application.dto.response.BorrowingTransactionResponse;

import java.util.List;

/**
 * Use case for getting all borrowing transactions.
 */
public interface GetAllTransactionsUseCase {
    List<BorrowingTransactionResponse> execute();
}
