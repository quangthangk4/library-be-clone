package com.library.circulation.application.usecase.borrowing;

import com.library.circulation.application.dto.request.CreateBorrowingTransactionRequest;
import com.library.circulation.application.dto.response.BorrowingTransactionResponse;

/**
 * Use case for creating a borrowing transaction (borrowing a book).
 */
public interface CreateBorrowingTransactionUseCase {
    BorrowingTransactionResponse execute(CreateBorrowingTransactionRequest request);
}
