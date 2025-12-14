package com.library.circulation.application.usecase.borrowing;

import com.library.circulation.application.dto.response.BorrowingTransactionResponse;

import java.util.List;

/**
 * Use case for getting all overdue transactions.
 */
public interface GetOverdueTransactionsUseCase {
    List<BorrowingTransactionResponse> execute();
}
