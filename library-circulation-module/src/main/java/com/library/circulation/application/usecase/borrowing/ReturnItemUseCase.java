package com.library.circulation.application.usecase.borrowing;

import com.library.circulation.application.dto.request.ReturnItemRequest;
import com.library.circulation.application.dto.response.BorrowingTransactionResponse;

/**
 * Use case for returning a borrowed item.
 */
public interface ReturnItemUseCase {
    BorrowingTransactionResponse execute(ReturnItemRequest request);
}
